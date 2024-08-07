using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class EditValoration : Form
    {
        public long ValorationID { get; set; }
        private HttpClient client;

        public EditValoration()
        {
            InitializeComponent();
            client = new HttpClient
            {
                BaseAddress = new Uri("http://localhost:8080")
            };
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }

        private async void EditValoration_Load(object sender, EventArgs e)
        {
            await LoadValorationDetails();
        }

        private async Task LoadValorationDetails()
        {
            try
            {
                HttpResponseMessage response = await client.GetAsync($"/api/valoraciones/{ValorationID}");
                if (response.IsSuccessStatusCode)
                {
                    var valoracion = JsonConvert.DeserializeObject<ValoracionDTO>(await response.Content.ReadAsStringAsync());
                    if (valoracion != null)
                    {
                        txtBoxeEditValorationValoracionID.Text = valoracion.ValoracionID.ToString();
                        txtBoxeEditValorationUserID.Text = valoracion.UsuarioID.ToString();
                        txtBoxeEditValorationValoracion.Text = valoracion.Valoracion.ToString();
                    }
                }
                else
                {
                    MessageBox.Show($"Error al obtener los detalles de la valoración: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btn_GuardarEditValoration_Click(object sender, EventArgs e)
        {
            try
            {
                var valoracion = new ValoracionDTO
                {
                    ValoracionID = ValorationID,
                    UsuarioID = long.Parse(txtBoxeEditValorationUserID.Text),
                    Valoracion = int.Parse(txtBoxeEditValorationValoracion.Text)
                };

                var json = JsonConvert.SerializeObject(valoracion);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                HttpResponseMessage response = await client.PutAsync($"/api/valoraciones/{ValorationID}", content);
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

        private async void btn_EliminarEditValoration_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea eliminar esta valoración?",
                                                "Confirmar Eliminación",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                try
                {
                    HttpResponseMessage response = await client.DeleteAsync($"/api/valoraciones/{ValorationID}");
                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Valoración eliminada correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show($"Error al eliminar la valoración: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
