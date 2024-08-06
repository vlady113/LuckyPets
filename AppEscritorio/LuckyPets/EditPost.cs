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
        public long AnuncioID { get; set; }

        public EditPost()
        {
            InitializeComponent();
            PictureBoxEditPost.Click += PictureBoxEditPost_Click;
            btn_GuardarDatosEditPost.Click += btn_GuardarDatosEditPost_Click;
            btn_EliminarDatosEditPost.Click += btn_EliminarDatosEditPost_Click;
        }

        private void PictureBoxEditPost_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "Image Files|*.jpg;*.jpeg;*.png;*.bmp";
                openFileDialog.Title = "Seleccione una imagen";

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    PictureBoxEditPost.Image = Image.FromFile(openFileDialog.FileName);
                }
            }
        }

        private async void btn_GuardarDatosEditPost_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea guardar los cambios?",
                                                "Confirmar Guardado",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                if (string.IsNullOrWhiteSpace(TextBoxEditPostCR.Text))
                {
                    MessageBox.Show("El campo 'Costo CR' no puede estar vacío.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                if (TextBoxEditPostDescripcion == null)
                {
                    MessageBox.Show("El campo 'Descripción' no está inicializado.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                string descripcion = TextBoxEditPostDescripcion.Text;
                if (descripcion == null)
                {
                    MessageBox.Show("El campo 'Descripción' no puede ser nulo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                byte[] imagenBytes = null;
                if (PictureBoxEditPost.Image != null)
                {
                    imagenBytes = ImageToByteArray(PictureBoxEditPost.Image);
                    if (imagenBytes == null)
                    {
                        MessageBox.Show("Error al convertir la imagen.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }

                var anuncio = new
                {
                    email_cliente = TxtBoxEditPostEmailCliente.Text,
                    email_anunciante = TxtBoxEditPostEmailAnunciante.Text,
                    imagen = imagenBytes != null ? Convert.ToBase64String(imagenBytes) : null,
                    fecha_hora_inicio = $"{DateTimePickerEditPostFechaInicio.Value:yyyy-MM-dd HH:mm:ss}",
                    fecha_hora_fin = $"{DateTimePickerEditPostFechaFin.Value:yyyy-MM-dd HH:mm:ss}",
                    precio_CR = decimal.Parse(TextBoxEditPostCR.Text),
                    descripcion = descripcion
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
                        var response = await client.PutAsync($"/api/anuncios/{this.AnuncioID}", content);
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
                        var response = await client.DeleteAsync($"/api/anuncios/{this.AnuncioID}");
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
            if (image == null)
            {
                MessageBox.Show("La imagen no puede ser nula.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }

            try
            {
                using (var ms = new MemoryStream())
                {
                    image.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
                    return ms.ToArray();
                }
            }
            catch (System.Runtime.InteropServices.ExternalException ex)
            {
                MessageBox.Show($"Error al convertir la imagen a byte array: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
            catch (ArgumentNullException ex)
            {
                MessageBox.Show($"Error: Parámetro nulo - {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
            catch (OutOfMemoryException ex)
            {
                MessageBox.Show($"Error: Memoria insuficiente - {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error inesperado: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
        }

        public TextBox TxtBoxEditPostEmailAnunciante => txtBoxEditPostEmailAnunciante;
        public TextBox TxtBoxEditPostEmailCliente => txtBoxEditPostEmailCliente;
        public PictureBox PictureBoxEditPost => pictureBoxEditPost;
        public DateTimePicker DateTimePickerEditPostFechaInicio => dateTimePickerEditPostFechaInicio;
        public DateTimePicker DateTimePickerEditPostFechaFin => dateTimePickerEditPostFechaFin;
        public TextBox TextBoxEditPostCR => textBoxEditPostCR;
        public TextBox TextBoxEditPostDescripcion => textBoxEditPostDescripcion;
        public Button BtnGuardarDatosEditPost => btn_GuardarDatosEditPost;
        public Button BtnEliminarDatosEditPost => btn_EliminarDatosEditPost;
    }
}
