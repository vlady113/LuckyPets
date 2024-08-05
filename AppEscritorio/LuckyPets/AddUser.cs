using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class AddUser : Form
    {
        public event EventHandler UserAdded;

        public AddUser()
        {
            InitializeComponent();
        }

        private async void btn_RegistrarUsuario_Click(object sender, EventArgs e)
        {
            string email = txtBoxAddUserEmail.Text.Trim();
            string repeatEmail = txtBoxAddUserRepeatEmail.Text.Trim();
            string password = txtBoxAddUserPassword.Text.Trim();
            string repeatPassword = txtBoxAddUserRepeatPassword.Text.Trim();

            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(repeatEmail) || string.IsNullOrEmpty(password) || string.IsNullOrEmpty(repeatPassword))
            {
                MessageBox.Show("Todos los campos son obligatorios.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (email != repeatEmail)
            {
                MessageBox.Show("Los correos electrónicos no coinciden.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (password != repeatPassword)
            {
                MessageBox.Show("Las contraseñas no coinciden.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (password.Length < 9 || password.Length > 15)
            {
                MessageBox.Show("La contraseña debe tener entre 9 y 15 caracteres.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            bool isRegistered = await RegisterUser(email, password);

            if (isRegistered)
            {
                MessageBox.Show("Usuario registrado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                UserAdded?.Invoke(this, EventArgs.Empty);
                this.Close();
            }
            else
            {
                MessageBox.Show("Error al registrar el usuario.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async Task<bool> RegisterUser(string email, string password)
        {
            using (HttpClient client = new HttpClient())
            {
                var usuario = new { email, password };
                string json = JsonConvert.SerializeObject(usuario);
                StringContent content = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await client.PostAsync("http://localhost:8080/api/usuarios", content);

                return response.IsSuccessStatusCode;
            }
        }
    }
}
