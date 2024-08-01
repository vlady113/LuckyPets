using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestSharp;

namespace LuckyPets
{
    public partial class Principal : Form
    {
        private Timer timer;

        public Principal()
        {
            InitializeComponent();

            timer = new Timer();
            timer.Interval = 1000; // 1 segundo
            timer.Tick += new EventHandler(Timer_Tick);
            timer.Start();

            toolStripStatusLblFechaHora.TextAlign = ContentAlignment.MiddleRight;
            toolStripStatusLblFechaHora.Text = $"Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";

            // Configurar comboBoxPrincipal
            comboBoxPrincipal.Items.AddRange(new string[]
            {
                "Anuncios",
                "Usuarios",
                "Tarjetas Bancarias",
                "Historial Transacciones",
                "Valoraciones"
            });

            comboBoxPrincipal.SelectedIndex = 0; // Establecer "Anuncios" como elemento predeterminado
            comboBoxPrincipal.SelectedIndexChanged += ComboBoxPrincipal_SelectedIndexChanged;

            // Cargar datos iniciales (Anuncios)
            LoadData("Anuncios");
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            toolStripStatusLblFechaHora.Text = $"Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";
        }

        private async void ComboBoxPrincipal_SelectedIndexChanged(object sender, EventArgs e)
        {
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
                    // Log para depuración
                    Console.WriteLine($"Respuesta de {tableName}: {response.Content}");

                    // Verificar si la respuesta es una lista válida
                    var jsonArray = JArray.Parse(response.Content);
                    var flattenedJsonArray = new JArray();

                    foreach (var item in jsonArray)
                    {
                        var flattenedItem = new JObject();
                        FlattenJson((JObject)item, flattenedItem, null);
                        flattenedJsonArray.Add(flattenedItem);
                    }

                    var dataTable = JsonConvert.DeserializeObject<DataTable>(flattenedJsonArray.ToString());

                    // Establecer el DataSource del DataGridView
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
    }
}
