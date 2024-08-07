using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class EditTransaction : Form
    {
        public long TransactionID { get; set; }
        private HttpClient client;

        public EditTransaction()
        {
            InitializeComponent();
            client = new HttpClient
            {
                BaseAddress = new Uri("http://localhost:8080")
            };
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }

        private async void EditTransaction_Load(object sender, EventArgs e)
        {
            await LoadTransactionDetails();
        }

        private async Task LoadTransactionDetails()
        {
            try
            {
                HttpResponseMessage response = await client.GetAsync($"/api/transacciones/{TransactionID}");
                if (response.IsSuccessStatusCode)
                {
                    var transaccion = JsonConvert.DeserializeObject<TransaccionDTO>(await response.Content.ReadAsStringAsync());
                    if (transaccion != null)
                    {
                        txtBoxeEditTransactionID.Text = transaccion.TransaccionID.ToString();
                        txtBoxeEditTransactionUserID.Text = transaccion.UsuarioID.ToString();
                        txtBoxeEditTransactionClienteID.Text = transaccion.ClienteID.ToString();
                        txtBoxeEditTransactionReservaID.Text = transaccion.ReservaID.ToString();
                        txtBoxeEditTransactionMontoCR.Text = transaccion.MontoCR.ToString();
                        txtBoxeEditTransactionTipo.Text = transaccion.Tipo;

                        dateTimePickerEditTransaction.Value = transaccion.Fecha;
                    }
                }
                else
                {
                    MessageBox.Show($"Error al obtener los detalles de la transacción: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btn_GuardarEditTransaction_Click(object sender, EventArgs e)
        {
            try
            {
                var transaccion = new TransaccionDTO
                {
                    TransaccionID = long.Parse(txtBoxeEditTransactionID.Text),
                    UsuarioID = long.Parse(txtBoxeEditTransactionUserID.Text),
                    ClienteID = long.Parse(txtBoxeEditTransactionClienteID.Text),
                    ReservaID = long.Parse(txtBoxeEditTransactionReservaID.Text),
                    MontoCR = decimal.Parse(txtBoxeEditTransactionMontoCR.Text),
                    Tipo = txtBoxeEditTransactionTipo.Text,
                    Fecha = dateTimePickerEditTransaction.Value
                };

                var json = JsonConvert.SerializeObject(transaccion);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                HttpResponseMessage response = await client.PutAsync($"/api/transacciones/{TransactionID}", content);
                if (response.IsSuccessStatusCode)
                {
                    MessageBox.Show("Datos guardados correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
                else
                {
                    MessageBox.Show($"Error al guardar los datos: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btn_EliminarEditTransaction_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea eliminar esta transacción?",
                                                "Confirmar Eliminación",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                try
                {
                    HttpResponseMessage response = await client.DeleteAsync($"/api/transacciones/{TransactionID}");
                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Transacción eliminada correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show($"Error al eliminar la transacción: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
    }
}
