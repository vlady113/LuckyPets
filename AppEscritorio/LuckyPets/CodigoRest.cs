using System;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using RestSharp;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class CodigoRest : Form
    {
        private string _email;

        public CodigoRest(string email)
        {
            InitializeComponent();
            _email = email;
        }

        private async void btn_ConfirmarCodigo_Click(object sender, EventArgs e)
        {
            var codigo = txtBoxResetCode.Text.Trim();

            if (string.IsNullOrEmpty(codigo))
            {
                MessageBox.Show("Proporcione un código de restablecimiento válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            await VerifyResetCode(_email, codigo);
        }

        private async Task VerifyResetCode(string email, string code)
        {
            var client = new RestClient("http://localhost:8080");
            var request = new RestRequest("/api/usuarios/verifyResetCode", Method.Post);
            var requestBody = new { email = email, code = code };

            request.AddJsonBody(requestBody);

            try
            {
                var response = await client.ExecuteAsync(request);

                if (response.IsSuccessful && response.Content.Contains("true"))
                {
                    MessageBox.Show("¡Código verificado correctamente! Ahora puede cambiar su contraseña.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);

                    this.Hide();
                    NewPassword newPasswordForm = new NewPassword(email);
                    newPasswordForm.ShowDialog();
                }
                else
                {
                    MessageBox.Show($"Error al verificar el código: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}