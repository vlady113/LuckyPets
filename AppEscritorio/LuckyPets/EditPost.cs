using System;
using System.Drawing;
using System.IO;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class EditPost : Form
    {
        public EditPost()
        {
            InitializeComponent();
        }

        private async void btn_GuardarDatosEditPost_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea guardar los cambios?",
                                                "Confirmar Guardado",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                var anuncio = new
                {
                    email_cliente = TxtBoxEditPostEmailCliente.Text,
                    email_anunciante = TxtBoxEditPostEmailAnunciante.Text,
                    imagen = PictureBoxEditPost.Image != null ? Convert.ToBase64String(ImageToByteArray(PictureBoxEditPost.Image)) : null,
                    fecha_hora_inicio = $"{DateTimePickerEditPostFechaInicio.Value.ToString("yyyy-MM-dd")} {TextBoxEditPostHoraInicio.Text}",
                    fecha_hora_fin = $"{DateTimePickerEditPostFechaFin.Value.ToString("yyyy-MM-dd")} {TextBoxEditPostHoraFin.Text}",
                    precio_CR = decimal.Parse(TextBoxEditPostCR.Text),
                    descripcion = TextBoxEditPostDescripcion.Text
                };

                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri("http://localhost:8080");
                    client.DefaultRequestHeaders.Accept.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                    var json = JsonConvert.SerializeObject(anuncio);
                    var content = new StringContent(json, Encoding.UTF8, "application/json");

                    try
                    {
                        var response = await client.PutAsync($"/api/anuncios/{TxtBoxEditPostEmailAnunciante.Text}", content);
                        if (response.IsSuccessStatusCode)
                        {
                            MessageBox.Show("Datos guardados correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            DialogResult = DialogResult.OK;
                            Close();
                        }
                        else
                        {
                            MessageBox.Show($"Error al guardar los datos: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
        }

        private async void btn_EliminarDatosEditPost_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea eliminar este anuncio?",
                                                "Confirmar Eliminación",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri("http://localhost:8080");
                    client.DefaultRequestHeaders.Accept.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                    try
                    {
                        var response = await client.DeleteAsync($"/api/anuncios/{TxtBoxEditPostEmailAnunciante.Text}");
                        if (response.IsSuccessStatusCode)
                        {
                            MessageBox.Show("Anuncio eliminado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            DialogResult = DialogResult.OK;
                            Close();
                        }
                        else
                        {
                            MessageBox.Show($"Error al eliminar el anuncio: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
        }

        private byte[] ImageToByteArray(Image image)
        {
            using (var ms = new MemoryStream())
            {
                image.Save(ms, image.RawFormat);
                return ms.ToArray();
            }
        }

        public TextBox TxtBoxEditPostEmailAnunciante => txtBoxEditPostEmailAnunciante;
        public TextBox TxtBoxEditPostEmailCliente => txtBoxEditPostEmailCliente;
        public PictureBox PictureBoxEditPost => pictureBoxEditPost;
        public DateTimePicker DateTimePickerEditPostFechaInicio => dateTimePickerEditPostFechaInicio;
        public TextBox TextBoxEditPostHoraInicio => textBoxEditPostHoraInicio;
        public DateTimePicker DateTimePickerEditPostFechaFin => dateTimePickerEditPostFechaFin;
        public TextBox TextBoxEditPostHoraFin => textBoxEditPostHoraFin;
        public TextBox TextBoxEditPostCR => textBoxEditPostCR;
        public TextBox TextBoxEditPostDescripcion => textBoxEditPostDescripcion;
        public Button BtnGuardarDatosEditPost => btn_GuardarDatosEditPost;
        public Button BtnEliminarDatosEditPost => btn_EliminarDatosEditPost;
    }
}
