using System;
using System.Drawing;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class AddPost : Form
    {
        public event EventHandler PostAdded;

        public AddPost()
        {
            InitializeComponent();
            pictureBoxAddPost.SizeMode = PictureBoxSizeMode.Zoom;
            pictureBoxAddPost.Click += PictureBoxAddPost_Click;
        }

        private void btn_AddPostPublicar_Click(object sender, EventArgs e)
        {
            _ = ValidateAndSubmitPostAsync();
        }

        private async Task ValidateAndSubmitPostAsync()
        {
            string email = txtBoxAddPostEmail.Text.Trim();
            DateTime fechaInicio = dateTimePickerAddPostFechaInicio.Value.Date + TimeSpan.Parse(textBoxAddPostHoraInicio.Text);
            DateTime fechaFin = dateTimePickerAddPostFechaFin.Value.Date + TimeSpan.Parse(textBoxAddPostHoraFin.Text);
            string descripcion = textBoxAddPostDescripcion.Text.Trim();
            double costoCR = 0;

            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(textBoxAddCardCR.Text) ||
                string.IsNullOrEmpty(descripcion) || pictureBoxAddPost.Image == null)
            {
                MessageBox.Show("Todos los campos son obligatorios.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (!double.TryParse(textBoxAddCardCR.Text, out costoCR))
            {
                MessageBox.Show("El precio debe ser un número válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (!checkBoxAddCardAceptarCoste.Checked)
            {
                MessageBox.Show("Debe aceptar el coste para publicar el anuncio.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (!IsValidEmail(email))
            {
                MessageBox.Show("Formato de correo electrónico inválido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            var usuario = await GetUsuarioByEmail(email);

            if (usuario == null)
            {
                MessageBox.Show("El usuario no está registrado en la base de datos.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (fechaInicio <= DateTime.Now)
            {
                MessageBox.Show("La fecha de inicio no puede ser anterior a la fecha actual.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (fechaInicio >= fechaFin)
            {
                MessageBox.Show("La fecha de fin debe ser posterior a la fecha de inicio.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            bool isPublished = await PublishPost(usuario, fechaInicio, fechaFin, descripcion, costoCR);

            if (isPublished)
            {
                MessageBox.Show("Anuncio publicado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                PostAdded?.Invoke(this, EventArgs.Empty);
                this.Close();
            }
            else
            {
                MessageBox.Show("Error al publicar el anuncio.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
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

        private async Task<Usuarios> GetUsuarioByEmail(string email)
        {
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage response = await client.GetAsync($"http://localhost:8080/api/usuarios/email/{email}");
                if (response.IsSuccessStatusCode)
                {
                    string jsonResponse = await response.Content.ReadAsStringAsync();
                    return JsonConvert.DeserializeObject<Usuarios>(jsonResponse);
                }
                return null;
            }
        }

        private async Task<bool> PublishPost(Usuarios usuario, DateTime fechaInicio, DateTime fechaFin, string descripcion, double costoCR)
        {
            using (HttpClient client = new HttpClient())
            {
                var anuncio = new
                {
                    usuario = new
                    {
                        email = usuario.Email,
                        nombre = usuario.Nombre,
                        apellidos = usuario.Apellidos,
                        direccion = usuario.Direccion,
                        telefono = usuario.Telefono
                    },
                    fechaInicio = fechaInicio.ToString("yyyy-MM-ddTHH:mm:ss"),
                    fechaFin = fechaFin.ToString("yyyy-MM-ddTHH:mm:ss"),
                    descripcion = descripcion,
                    costoCR = costoCR,
                    estado = "pendiente",
                    fotoAnuncio = Convert.ToBase64String(ImageToByteArray(pictureBoxAddPost.Image))
                };

                string json = JsonConvert.SerializeObject(anuncio);
                StringContent content = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await client.PostAsync("http://localhost:8080/api/anuncios", content);

                if (response.IsSuccessStatusCode)
                {
                    return true;
                }
                else
                {
                    string responseContent = await response.Content.ReadAsStringAsync();
                    MessageBox.Show($"Error al publicar el anuncio: {responseContent}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }
            }
        }

        private void PictureBoxAddPost_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "Archivos de imagen (*.jpg;*.jpeg;*.png;*.bmp)|*.jpg;*.jpeg;*.png;*.bmp";
                openFileDialog.Title = "Seleccionar una imagen";

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    try
                    {
                        pictureBoxAddPost.Image = Image.FromFile(openFileDialog.FileName);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show($"No se pudo cargar la imagen: {ex.Message}");
                    }
                }
            }
        }

        private byte[] ImageToByteArray(Image image)
        {
            using (var ms = new System.IO.MemoryStream())
            {
                image.Save(ms, image.RawFormat);
                return ms.ToArray();
            }
        }

        public class Usuarios
        {
            public string Nombre { get; set; }
            public string Apellidos { get; set; }
            public string Direccion { get; set; }
            public string Telefono { get; set; }
            public string Email { get; set; }
        }

        private void AddPost_Load(object sender, EventArgs e)
        {

        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void dateTimePickerAddPostFechaInicio_ValueChanged(object sender, EventArgs e)
        {

        }

        private void textBoxAddPostHoraInicio_TextChanged(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void dateTimePickerAddPostFechaFin_ValueChanged(object sender, EventArgs e)
        {

        }

        private void textBoxAddPostHoraFin_TextChanged(object sender, EventArgs e)
        {

        }

        private void txtBoxAddPostEmail_TextChanged(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void textBoxAddCardCR_TextChanged(object sender, EventArgs e)
        {

        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void textBoxAddPostDescripcion_TextChanged(object sender, EventArgs e)
        {

        }

        private void checkBoxAddCardAceptarCoste_CheckedChanged(object sender, EventArgs e)
        {

        }
    }

}