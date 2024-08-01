using System;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;
using RestSharp;

namespace LuckyPets
{
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
            toolTipLogin.SetToolTip(this.btn_Login, "Haga clic para iniciar sesión");

            txtBoxPasswordLogin.PasswordChar = '*';

            txtBoxEmailLogin.KeyPress += TxtBox_KeyPress;
            txtBoxPasswordLogin.KeyPress += TxtBox_KeyPress;

            txtBoxEmailLogin.TextChanged += TxtBoxEmailLogin_TextChanged; // Agregar el evento TextChanged

            if (Properties.Settings.Default.RememberMe)
            {
                txtBoxEmailLogin.Text = Properties.Settings.Default.Email;
                txtBoxPasswordLogin.Text = Properties.Settings.Default.Password;
                checkBoxRecordarme.Checked = true;
            }
        }

        private void TxtBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                e.Handled = true;
            }
        }

        private void TxtBoxEmailLogin_TextChanged(object sender, EventArgs e)
        {
            txtBoxPasswordLogin.Clear(); // Limpiar el contenido de txtBoxPasswordLogin
        }

        private async void btn_Login_Click(object sender, EventArgs e)
        {
            var email = txtBoxEmailLogin.Text.Trim();
            var password = txtBoxPasswordLogin.Text.Trim();

            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(password))
            {
                MessageBox.Show("¡Los campos de correo electrónico y contraseña no pueden estar vacíos!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            var loginRequest = new LoginRequest
            {
                Email = email,
                Password = password
            };

            try
            {
                var response = await LoginUserAsync(loginRequest);

                if (response.IsSuccessful)
                {
                    if (checkBoxRecordarme.Checked)
                    {
                        Properties.Settings.Default.Email = email;
                        Properties.Settings.Default.Password = password;
                        Properties.Settings.Default.RememberMe = true;
                    }
                    else
                    {
                        Properties.Settings.Default.Email = string.Empty;
                        Properties.Settings.Default.Password = string.Empty;
                        Properties.Settings.Default.RememberMe = false;
                    }

                    Properties.Settings.Default.Save();

                    Principal principalForm = new Principal();
                    principalForm.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show($"Error al iniciar sesión: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async Task<RestResponse> LoginUserAsync(LoginRequest loginRequest)
        {
            var client = new RestClient("http://localhost:8080");
            var request = new RestRequest("/api/usuarios/login", Method.Post);
            request.AddJsonBody(loginRequest);

            Console.WriteLine("Request URL: " + client.BuildUri(request));
            Console.WriteLine("Request Body: " + JsonConvert.SerializeObject(loginRequest));

            return await client.ExecuteAsync(request);
        }
    }

    public class LoginRequest
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }

}