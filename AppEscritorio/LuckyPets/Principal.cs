using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OfficeOpenXml;
using RestSharp;

namespace LuckyPets
{
    public partial class Principal : Form
    {
        private Timer timer;
        private int advancedSearchIndex = -1;
        private string currentSearchValue = string.Empty;

        public Principal()
        {
            InitializeComponent();

            toolStripStatusLblFechaHora.Spring = true;
            toolStripStatusLblFechaHora.TextAlign = ContentAlignment.MiddleRight;

            timer = new Timer();
            timer.Interval = 1000; // 1 segundo
            timer.Tick += new EventHandler(Timer_Tick);
            timer.Start();

            toolStripStatusLblFechaHora.Text = $"  Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";

            comboBoxPrincipal.Items.AddRange(new string[]
            {
                "Anuncios",
                "Usuarios",
                "Tarjetas Bancarias",
                "Historial Transacciones",
                "Valoraciones"
            });

            comboBoxPrincipal.SelectedIndex = 0;
            comboBoxPrincipal.SelectedIndexChanged += ComboBoxPrincipal_SelectedIndexChanged;

            LoadData("Anuncios");

            toolStripMenuItemSalir.Click += ToolStripMenuItemSalir_Click;
            toolStripMenuItemCerrarSesion.Click += ToolStripMenuItemCerrarSesion_Click;
            toolStripMenuItemAcercaDe.Click += ToolStripMenuItemAcercaDe_Click;
            toolStripMenuItemAbrir.Click += ToolStripMenuItemAbrir_Click;
            toolStripMenuItemGuardarComo.Click += ToolStripMenuItemGuardarComo_Click;
            ToolStripMenuItemLimpiarDatos.Click += ToolStripMenuItemLimpiarDatos_Click;

            toolStripMenuItemDeshacer.Click += ToolStripMenuItemDeshacer_Click;
            toolStripMenuItemCortar.Click += ToolStripMenuItemCortar_Click;
            toolStripMenuItemCopiar.Click += ToolStripMenuItemCopiar_Click;
            toolStripMenuItemPegar.Click += ToolStripMenuItemPegar_Click;
            ToolStripMenuItemBuscar.Click += ToolStripMenuItemBuscar_Click;
            ToolStripMenuItemBuscarSiguiente.Click += ToolStripMenuItemBuscarSiguiente_Click;
            toolStripMenuItemSeleccionarTodo.Click += ToolStripMenuItemSeleccionarTodo_Click;

            // Añadir eventos del ContextMenuStrip
            deshacerToolStripMenuItem.Click += ToolStripMenuItemDeshacer_Click;
            cortarToolStripMenuItem.Click += ToolStripMenuItemCortar_Click;
            copiarToolStripMenuItem.Click += ToolStripMenuItemCopiar_Click;
            pegarToolStripMenuItem.Click += ToolStripMenuItemPegar_Click;
            buscarToolStripMenuItem.Click += ToolStripMenuItemBuscar_Click;
            buscarSiguienteToolStripMenuItem.Click += ToolStripMenuItemBuscarSiguiente_Click;
            limpiarDatosToolStripMenuItem.Click += ToolStripMenuItemLimpiarDatos_Click;
            seleccionarTodoToolStripMenuItem.Click += ToolStripMenuItemSeleccionarTodo_Click;

            txtBoxPrincipalBuscar.TextChanged += TxtBoxPrincipalBuscar_TextChanged;
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            toolStripStatusLblFechaHora.Text = $"  Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";
        }

        private async void ComboBoxPrincipal_SelectedIndexChanged(object sender, EventArgs e)
        {
            txtBoxPrincipalBuscar.Clear();
            var selectedItem = comboBoxPrincipal.SelectedItem.ToString();
            await LoadData(selectedItem);
        }

        private async Task LoadData(string tableName)
        {
            var endpoints = new Dictionary<string, string>
            {
                { "Anuncios", "anuncios" },
                { "Usuarios", "usuarios" },
                { "Tarjetas Bancarias", "tarjetas" },
                { "Historial Transacciones", "historialtransacciones" },
                { "Valoraciones", "valoraciones" }
            };

            if (!endpoints.TryGetValue(tableName, out var apiEndpoint))
            {
                throw new InvalidOperationException("Elemento no reconocido");
            }

            var client = new RestClient("http://localhost:8080");
            var request = new RestRequest($"/api/{apiEndpoint}", Method.Get);

            try
            {
                var response = await client.ExecuteAsync(request);

                if (response.IsSuccessful)
                {
                    Console.WriteLine($"Respuesta de {tableName}: {response.Content}");

                    var jsonArray = JArray.Parse(response.Content);
                    var flattenedJsonArray = new JArray();

                    foreach (var item in jsonArray)
                    {
                        var flattenedItem = new JObject();
                        FlattenJson((JObject)item, flattenedItem, null);
                        flattenedJsonArray.Add(flattenedItem);
                    }

                    var dataTable = JsonConvert.DeserializeObject<DataTable>(flattenedJsonArray.ToString());

                    dataGridViewPrincipal.DataSource = dataTable;
                }
                else
                {
                    MessageBox.Show($"Error al cargar datos de {tableName}: {response.Content}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al enviar la solicitud: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void FlattenJson(JObject original, JObject flattened, string prefix)
        {
            foreach (var property in original.Properties())
            {
                var propertyName = prefix != null ? $"{prefix}.{property.Name}" : property.Name;

                if (property.Value is JObject nestedObject)
                {
                    FlattenJson(nestedObject, flattened, propertyName);
                }
                else
                {
                    flattened[propertyName] = property.Value;
                }
            }
        }

        private void ToolStripMenuItemSalir_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void ToolStripMenuItemCerrarSesion_Click(object sender, EventArgs e)
        {
            foreach (Form form in Application.OpenForms)
            {
                form.Hide();
            }

            var loginForm = new Login();
            loginForm.ResetLoginFields();
            loginForm.Show();
        }

        private void ToolStripMenuItemAcercaDe_Click(object sender, EventArgs e)
        {
            AcercaDe acercaDeForm = new AcercaDe();
            acercaDeForm.ShowDialog();
        }

        private void ToolStripMenuItemAbrir_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "Excel Files|*.xlsx;*.xls";
                openFileDialog.Title = "Seleccione un archivo de Excel";

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    LoadExcelFile(openFileDialog.FileName);
                }
            }
        }

        private void LoadExcelFile(string filePath)
        {
            ExcelPackage.LicenseContext = LicenseContext.NonCommercial; // Añadido para EPPlus

            using (var package = new ExcelPackage(new FileInfo(filePath)))
            {
                ExcelWorksheet worksheet = package.Workbook.Worksheets[0];
                DataTable dataTable = new DataTable();

                foreach (var header in worksheet.Cells[1, 1, 1, worksheet.Dimension.End.Column])
                {
                    dataTable.Columns.Add(header.Text);
                }

                for (var rowNumber = 2; rowNumber <= worksheet.Dimension.End.Row; rowNumber++)
                {
                    var row = worksheet.Cells[rowNumber, 1, rowNumber, worksheet.Dimension.End.Column];
                    var newRow = dataTable.NewRow();

                    foreach (var cell in row)
                    {
                        newRow[cell.Start.Column - 1] = cell.Text;
                    }

                    dataTable.Rows.Add(newRow);
                }

                dataGridViewPrincipal.DataSource = dataTable;
            }
        }

        private void ToolStripMenuItemGuardarComo_Click(object sender, EventArgs e)
        {
            using (SaveFileDialog saveFileDialog = new SaveFileDialog())
            {
                saveFileDialog.Filter = "Excel Files|*.xlsx|Text Files|*.txt|All Files|*.*";
                saveFileDialog.Title = "Guardar como";

                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                {
                    string filePath = saveFileDialog.FileName;
                    string fileExtension = Path.GetExtension(filePath).ToLower();

                    switch (fileExtension)
                    {
                        case ".xlsx":
                            SaveAsExcel(filePath);
                            break;
                        case ".txt":
                            SaveAsText(filePath);
                            break;
                        default:
                            MessageBox.Show("Formato de archivo no soportado.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            break;
                    }
                }
            }
        }

        private void SaveAsExcel(string filePath)
        {
            ExcelPackage.LicenseContext = LicenseContext.NonCommercial;

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Sheet1");
                DataTable dataTable = (DataTable)dataGridViewPrincipal.DataSource;

                for (int i = 0; i < dataTable.Columns.Count; i++)
                {
                    worksheet.Cells[1, i + 1].Value = dataTable.Columns[i].ColumnName;
                }

                for (int i = 0; i < dataTable.Rows.Count; i++)
                {
                    for (int j = 0; j < dataTable.Columns.Count; j++)
                    {
                        worksheet.Cells[i + 2, j + 1].Value = dataTable.Rows[i][j];
                    }
                }

                FileInfo fi = new FileInfo(filePath);
                package.SaveAs(fi);
            }

            MessageBox.Show("Datos guardados como Excel.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void SaveAsText(string filePath)
        {
            using (StreamWriter writer = new StreamWriter(filePath))
            {
                DataTable dataTable = (DataTable)dataGridViewPrincipal.DataSource;

                foreach (DataColumn column in dataTable.Columns)
                {
                    writer.Write(column.ColumnName + "\t");
                }
                writer.WriteLine();

                foreach (DataRow row in dataTable.Rows)
                {
                    foreach (var item in row.ItemArray)
                    {
                        writer.Write(item.ToString() + "\t");
                    }
                    writer.WriteLine();
                }
            }

            MessageBox.Show("Datos guardados como archivo de texto.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void ToolStripMenuItemDeshacer_Click(object sender, EventArgs e)
        {
            if (ActiveControl is TextBox textBox && textBox.CanUndo)
            {
                textBox.Undo();
                textBox.ClearUndo();
            }
        }

        private void ToolStripMenuItemCortar_Click(object sender, EventArgs e)
        {
            if (ActiveControl is TextBox textBox && textBox.SelectedText.Length > 0)
            {
                textBox.Cut();
            }
        }

        private void ToolStripMenuItemCopiar_Click(object sender, EventArgs e)
        {
            if (ActiveControl is TextBox textBox && textBox.SelectedText.Length > 0)
            {
                textBox.Copy();
            }
        }

        private void ToolStripMenuItemPegar_Click(object sender, EventArgs e)
        {
            if (ActiveControl is TextBox textBox)
            {
                textBox.Paste();
            }
        }

        private void ToolStripMenuItemBuscar_Click(object sender, EventArgs e)
        {
            Buscar buscarForm = new Buscar();
            buscarForm.BusquedaRealizada += BuscarForm_BusquedaRealizada;
            buscarForm.ShowDialog();
        }

        private void ToolStripMenuItemBuscarSiguiente_Click(object sender, EventArgs e)
        {
            SeleccionarSiguiente();
        }

        private void ToolStripMenuItemLimpiarDatos_Click(object sender, EventArgs e)
        {
            LimpiarDatos();
        }

        private void ToolStripMenuItemSeleccionarTodo_Click(object sender, EventArgs e)
        {
            if (ActiveControl is TextBox textBox)
            {
                textBox.SelectAll();
            }
        }

        private void TxtBoxPrincipalBuscar_TextChanged(object sender, EventArgs e)
        {
            string searchValue = txtBoxPrincipalBuscar.Text.ToLower();

            if (string.IsNullOrEmpty(searchValue))
            {
                foreach (DataGridViewRow row in dataGridViewPrincipal.Rows)
                {
                    row.Selected = false;
                }
                return;
            }

            foreach (DataGridViewRow row in dataGridViewPrincipal.Rows)
            {
                row.Selected = false;
                foreach (DataGridViewCell cell in row.Cells)
                {
                    if (cell.Value != null && cell.Value.ToString().ToLower().Contains(searchValue))
                    {
                        row.Selected = true;
                        dataGridViewPrincipal.FirstDisplayedScrollingRowIndex = row.Index;
                        break;
                    }
                }
            }
        }

        private void BuscarForm_BusquedaRealizada(object sender, string searchValue)
        {
            advancedSearchIndex = -1;
            currentSearchValue = searchValue.ToLower();
            RealizarBusquedaAvanzada(currentSearchValue);
        }

        private void RealizarBusquedaAvanzada(string searchValue)
        {
            bool found = false;

            for (int i = 0; i < dataGridViewPrincipal.Rows.Count; i++)
            {
                DataGridViewRow row = dataGridViewPrincipal.Rows[i];
                foreach (DataGridViewCell cell in row.Cells)
                {
                    if (cell.Value != null && cell.Value.ToString().ToLower().Contains(searchValue))
                    {
                        advancedSearchIndex = i;
                        row.Selected = true;
                        dataGridViewPrincipal.FirstDisplayedScrollingRowIndex = i;
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }

            if (!found)
            {
                MessageBox.Show("No se encontraron más coincidencias.", "Búsqueda", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void SeleccionarSiguiente()
        {
            if (string.IsNullOrEmpty(currentSearchValue))
            {
                MessageBox.Show("No se ha realizado ninguna búsqueda avanzada.", "Búsqueda", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            bool found = false;

            for (int i = advancedSearchIndex + 1; i < dataGridViewPrincipal.Rows.Count; i++)
            {
                DataGridViewRow row = dataGridViewPrincipal.Rows[i];

                foreach (DataGridViewCell cell in row.Cells)
                {
                    if (cell.Value != null && cell.Value.ToString().ToLower().Contains(currentSearchValue))
                    {
                        advancedSearchIndex = i;
                        row.Selected = true;
                        dataGridViewPrincipal.FirstDisplayedScrollingRowIndex = i;
                        found = true;
                        break;
                    }
                }

                if (found)
                    break;
            }

            if (!found)
            {
                MessageBox.Show("No se encontraron más coincidencias.", "Búsqueda", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void LimpiarDatos()
        {
            txtBoxPrincipalBuscar.Clear();

            var buscarForm = Application.OpenForms.OfType<Buscar>().FirstOrDefault();
            if (buscarForm != null)
            {
                buscarForm.ClearAdvancedSearch();
            }

            foreach (DataGridViewRow row in dataGridViewPrincipal.Rows)
            {
                row.Selected = false;
            }

            advancedSearchIndex = -1;
            currentSearchValue = string.Empty;
        }
    }
}
