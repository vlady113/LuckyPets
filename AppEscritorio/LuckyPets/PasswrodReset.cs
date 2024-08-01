using System;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using RestSharp;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class PasswrodReset : Form
    {
        private bool isSendingResetCode = false;

        public PasswrodReset()
        {
            InitializeComponent();
            linklblOlvideContraseniaReset.Click += LinklblOlvideContraseniaReset_Click;
            btn_ResetPassword.Click += btn_ResetPassword_Click;
            lblEsperar.Visible = false; // Ocultar lblEsperar inicialmente
        }

        private async void btn_ResetPassword_Click(object sender, EventArgs e)
        {
            var email = txtBoxEmailResetPassword.Text.Trim();

            if (IsValidEmail(email) && !isSendingResetCode)
            {
                isSendingResetCode = true;
                lblEsperar.Visible = true; // Mostrar lblEsperar
                await SendResetCodeAsync(email);
                lblEsperar.Visible = false; // Ocultar lblEsperar
            }
            else if (!IsValidEmail(email))
            {
                MessageBox.Show("Proporcione un correo electrónico válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private bool IsValidEmail(string email)
        {
            var emailPattern = @"^[^@\s]+@[^@\s]+\.[^@\s]+$";
            return Regex.IsMatch(email, emailPattern);
        }

        private async Task SendResetCodeAsync(string email)
        {
            var client = new RestClient("http://localhost:8080");
            var request = new RestRequest("/api/usuarios/sendResetCode", Method.Post);
            var requestBody = new { email = email };

            request.AddJsonBody(requestBody);

            try
            {
                var response = await client.ExecuteAsync(request);

                if (response.IsSuccessful)
                {
                    MessageBox.Show("¡Código de restablecimiento enviado! Por favor, revise su correo electrónico.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);

                    this.Hide();
                    CodigoRest codigoRestForm = new CodigoRest(email);
                    codigoRestForm.ShowDialog();
                    this.Close();
                }
                else
                {
                    MessageBox.Show($"Error al enviar el código de restablecimiento: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                isSendingResetCode = false;
                lblEsperar.Visible = false; // Asegurar que lblEsperar esté oculto en caso de error
            }
        }

        private void LinklblOlvideContraseniaReset_Click(object sender, EventArgs e)
        {
            this.Hide();
            Login LoginForm = new Login();
            LoginForm.ShowDialog();
        }
    }
}