using System;
using System.Collections.Generic;
using System.Windows.Forms;
using RestSharp;

namespace LuckyPets
{
    public partial class EditUser : Form
    {
        public EditUser()
        {
            InitializeComponent();
        }

        private async void btn_GuardarDatosUser_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea guardar los cambios?",
                                                 "Confirmar Guardado",
                                                 MessageBoxButtons.YesNo,
                                                 MessageBoxIcon.Question);

            if (confirmResult == DialogResult.Yes)
            {
                try
                {
                    var client = new RestClient("http://localhost:8080");
                    var request = new RestRequest($"/api/usuarios/email/{TxtBoxeEditUserEmail.Text}", Method.Put);

                    var usuario = new Dictionary<string, object>();

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserDNI.Text))
                        usuario["dni"] = TxtBoxeEditUserDNI.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserNombre.Text))
                        usuario["nombre"] = TxtBoxeEditUserNombre.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserApellidos.Text))
                        usuario["apellidos"] = TxtBoxeEditUserApellidos.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserDireccion.Text))
                        usuario["direccion"] = TxtBoxeEditUserDireccion.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserProvincia.Text))
                        usuario["provincia"] = TxtBoxeEditUserProvincia.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserCP.Text))
                        usuario["codigoPostal"] = TxtBoxeEditUserCP.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserTelefono.Text))
                        usuario["telefono"] = TxtBoxeEditUserTelefono.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserEmail.Text))
                        usuario["email"] = TxtBoxeEditUserEmail.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserPassword.Text))
                        usuario["password"] = TxtBoxeEditUserPassword.Text;

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserSaldo.Text))
                    {
                        if (!decimal.TryParse(TxtBoxeEditUserSaldo.Text, out decimal saldoCR))
                        {
                            throw new FormatException("El saldo debe ser un número válido.");
                        }
                        usuario["saldoCR"] = saldoCR;
                    }

                    if (!string.IsNullOrWhiteSpace(TxtBoxeEditUserCodRest.Text))
                        usuario["codigo_restablecimiento"] = TxtBoxeEditUserCodRest.Text;

                    usuario["esAdministrador"] = CheckBoxEditUserAdmin.Checked ? 1 : 0;

                    usuario["fechaRegistro"] = DateTimePickerEditUserFechaRegistro.Value.ToString("yyyy-MM-ddTHH:mm:ss");

                    request.AddJsonBody(usuario);

                    var response = await client.ExecuteAsync(request);

                    if (response.IsSuccessful)
                    {
                        MessageBox.Show("Datos guardados correctamente.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show($"Error al guardar los datos: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                catch (FormatException ex)
                {
                    MessageBox.Show($"Error en el formato de los datos: {ex.Message}", "Error de Formato", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Guardado cancelado.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private async void btn_EliminarDatosUser_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea eliminar este usuario?",
                                                 "Confirmar Eliminación",
                                                 MessageBoxButtons.YesNo,
                                                 MessageBoxIcon.Warning);

            if (confirmResult == DialogResult.Yes)
            {
                try
                {
                    var client = new RestClient("http://localhost:8080");
                    var request = new RestRequest($"/api/usuarios/{TxtBoxeEditUserID.Text}", Method.Delete);

                    var response = await client.ExecuteAsync(request);

                    if (response.IsSuccessful)
                    {
                        MessageBox.Show("Usuario eliminado correctamente.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show($"Error al eliminar el usuario: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Eliminación cancelada.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        public TextBox TxtBoxeEditUserID => txtBoxeEditUserID;
        public TextBox TxtBoxeEditUserDNI => txtBoxeEditUserDNI;
        public TextBox TxtBoxeEditUserNombre => txtBoxeEditUserNombre;
        public TextBox TxtBoxeEditUserApellidos => txtBoxeEditUserApellidos;
        public TextBox TxtBoxeEditUserEmail => txtBoxeEditUserEmail;
        public TextBox TxtBoxeEditUserPassword => txtBoxeEditUserPassword;
        public TextBox TxtBoxeEditUserDireccion => txtBoxeEditUserDireccion;
        public TextBox TxtBoxeEditUserProvincia => txtBoxeEditUserProvincia;
        public TextBox TxtBoxeEditUserCP => txtBoxeEditUserCP;
        public TextBox TxtBoxeEditUserTelefono => txtBoxeEditUserTelefono;
        public DateTimePicker DateTimePickerEditUserFechaRegistro => dateTimePickerEditUserFechaRegistro;
        public TextBox TxtBoxeEditUserSaldo => txtBoxeEditUserSaldo;
        public TextBox TxtBoxeEditUserCodRest => txtBoxeEditUserCodRest;
        public CheckBox CheckBoxEditUserAdmin => checkBoxEditUserAdmin;
        public Button BtnGuardarDatosUser => btn_GuardarDatosUser;
        public Button BtnEliminarDatosUser => btn_EliminarDatosUser;
    }

}