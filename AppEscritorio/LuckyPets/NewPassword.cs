using System;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using RestSharp;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class NewPassword : Form
    {
        private string _email;

        public NewPassword(string email)
        {
            InitializeComponent();
            _email = email;
        }

        private async void btn_ConfirmarContrasenia_Click(object sender, EventArgs e)
        {
            var newPassword = txtBoxNewPassword.Text.Trim();
            var confirmNewPassword = textBoxConfirmNewPassword.Text.Trim();

            if (IsValidPassword(newPassword) && newPassword == confirmNewPassword)
            {
                await ChangePassword(_email, newPassword);
            }
            else
            {
                MessageBox.Show("Las contraseñas no coinciden o no cumplen con los requisitos.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private bool IsValidPassword(string password)
        {
            return password.Length >= 9 && password.Length <= 25 && Regex.IsMatch(password, @"[A-Z]") && Regex.IsMatch(password, @"[a-z]") && Regex.IsMatch(password, @"[0-9]");
        }

        private async Task ChangePassword(string email, string newPassword)
        {
            var client = new RestClient("http://localhost:8080");
            var request = new RestRequest("/api/usuarios/changePassword", Method.Post);
            var requestBody = new { email = email, newPassword = newPassword };

            request.AddJsonBody(requestBody);

            try
            {
                var response = await client.ExecuteAsync(request);

                if (response.IsSuccessful)
                {
                    MessageBox.Show("¡Contraseña actualizada correctamente!", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    this.Hide();
                    Login loginForm = new Login();
                    loginForm.ShowDialog();
                    this.Close();
                }
                else
                {
                    MessageBox.Show($"Error al actualizar la contraseña: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }

}