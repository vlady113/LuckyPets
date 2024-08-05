using System;
using System.Net.Http;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class AddCard : Form
    {
        public event EventHandler CardAdded;

        public AddCard()
        {
            InitializeComponent();
            InitializeComboBox();
            InitializeTextBoxValidations();
        }

        private void InitializeComboBox()
        {
            comboBoxAddCardEmisor.Items.AddRange(new string[] { "Visa", "MasterCard", "AmericanExpress", "Others" });
        }

        private void InitializeTextBoxValidations()
        {
            txtBoxAddCardNumeroTarjeta.MaxLength = 16;
            txtBoxAddCardNumeroTarjeta.KeyPress += TxtBoxAddCardNumeroTarjeta_KeyPress;
            textBoxAddCaArdCvv.MaxLength = 3;
            textBoxAddCaArdCvv.KeyPress += TextBoxAddCaArdCvv_KeyPress;
        }

        private void TxtBoxAddCardNumeroTarjeta_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private void TextBoxAddCaArdCvv_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private async void btn_RegistrarAddCard_Click(object sender, EventArgs e)
        {
            string email = txtBoxAddCardEmail.Text.Trim();
            string numeroTarjeta = txtBoxAddCardNumeroTarjeta.Text.Trim();
            string titular = textBoxAddCardTitular.Text.Trim();
            string emisor = comboBoxAddCardEmisor.SelectedItem?.ToString();
            string cvv = textBoxAddCaArdCvv.Text.Trim();
            DateTime fechaCaducidad = dateTimePickerAddCard.Value;

            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(numeroTarjeta) || string.IsNullOrEmpty(titular) ||
                string.IsNullOrEmpty(emisor) || string.IsNullOrEmpty(cvv) || fechaCaducidad == null)
            {
                MessageBox.Show("Todos los campos son obligatorios.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (!IsValidEmail(email))
            {
                MessageBox.Show("Formato de correo electrónico inválido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (!await IsEmailRegistered(email))
            {
                MessageBox.Show("El correo electrónico no está registrado en la base de datos.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (fechaCaducidad <= DateTime.Now)
            {
                MessageBox.Show("La fecha de expiración no puede ser inferior o igual a la fecha actual.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            bool isRegistered = await RegisterCard(email, numeroTarjeta, titular, emisor, cvv, fechaCaducidad);

            if (isRegistered)
            {
                MessageBox.Show("Tarjeta bancaria registrada correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CardAdded?.Invoke(this, EventArgs.Empty);
                this.Close();
            }
            else
            {
                MessageBox.Show("Error al registrar la tarjeta bancaria.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private bool IsValidEmail(string email)
        {
            try
            {
                var addr = new System.Net.Mail.MailAddress(email);
                return addr.Address == email;
            }
            catch
            {
                return false;
            }
        }

        private async Task<bool> IsEmailRegistered(string email)
        {
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage response = await client.GetAsync($"http://localhost:8080/api/usuarios/email/{email}");
                return response.IsSuccessStatusCode;
            }
        }

        private async Task<bool> RegisterCard(string email, string numeroTarjeta, string titular, string emisor, string cvv, DateTime fechaCaducidad)
        {
            using (HttpClient client = new HttpClient())
            {
                var tarjeta = new
                {
                    usuarioEmail = email,
                    numeroTarjeta = long.Parse(numeroTarjeta),
                    titularTarjeta = titular,
                    emisorTarjeta = emisor,
                    cvv = int.Parse(cvv),
                    fechaCaducidad = fechaCaducidad.ToString("yyyy-MM-dd")
                };
                string json = JsonConvert.SerializeObject(tarjeta);
                StringContent content = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await client.PostAsync("http://localhost:8080/api/tarjetas", content);

                return response.IsSuccessStatusCode;
            }
        }
    }

}