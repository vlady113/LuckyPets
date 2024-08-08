﻿using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace LuckyPets
{
    public partial class EditCard : Form
    {
        public long CardID { get; set; }
        private HttpClient client;
        private List<string> emisoresTarjetas = new List<string> { "Visa", "MasterCard", "American Express", "Discover" };

        public EditCard()
        {
            InitializeComponent();
            client = new HttpClient
            {
                BaseAddress = new Uri("http://localhost:8080")
            };
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            PoblarEmisoresTarjetas();
        }

        private void PoblarEmisoresTarjetas()
        {
            comboBoxEditCardEmisor.Items.Clear();
            comboBoxEditCardEmisor.Items.AddRange(emisoresTarjetas.ToArray());
        }

        private async void EditCard_Load(object sender, EventArgs e)
        {
            await LoadCardDetails();
        }

        private async Task LoadCardDetails()
        {
            try
            {
                HttpResponseMessage response = await client.GetAsync($"/api/tarjetas/{CardID}");
                if (response.IsSuccessStatusCode)
                {
                    var tarjeta = JsonConvert.DeserializeObject<TarjetaBancariaDTO>(await response.Content.ReadAsStringAsync());
                    if (tarjeta != null)
                    {
                        txtBoxeEditCardCardID.Text = tarjeta.Id.ToString();
                        txtBoxeEditCardNumTarjeta.Text = tarjeta.NumeroTarjeta.ToString();
                        dateTimePickerEditCard.Value = tarjeta.FechaCaducidad;
                        txtBoxeEditCardTitular.Text = tarjeta.TitularTarjeta;

                        // Si el emisor no está en la lista, lo agrega
                        if (!comboBoxEditCardEmisor.Items.Contains(tarjeta.EmisorTarjeta))
                        {
                            comboBoxEditCardEmisor.Items.Add(tarjeta.EmisorTarjeta);
                        }
                        comboBoxEditCardEmisor.SelectedItem = tarjeta.EmisorTarjeta;

                        textBoxEditCardCvv.Text = tarjeta.Cvv.ToString();
                    }
                }
                else
                {
                    MessageBox.Show($"Error al obtener los detalles de la tarjeta: {response.ReasonPhrase}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btn_GuardarEditCard_Click(object sender, EventArgs e)
        {
            try
            {
                var tarjeta = new TarjetaBancariaDTO
                {
                    Id = CardID,
                    NumeroTarjeta = long.Parse(txtBoxeEditCardNumTarjeta.Text),
                    FechaCaducidad = dateTimePickerEditCard.Value,
                    TitularTarjeta = txtBoxeEditCardTitular.Text,
                    EmisorTarjeta = comboBoxEditCardEmisor.SelectedItem.ToString(),
                    Cvv = int.Parse(textBoxEditCardCvv.Text)
                };

                var json = JsonConvert.SerializeObject(tarjeta);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                HttpResponseMessage response = await client.PutAsync($"/api/tarjetas/{CardID}", content);
                if (response.IsSuccessStatusCode)
                {
                    MessageBox.Show("Datos guardados correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
                else
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    MessageBox.Show($"Error al guardar los datos: {response.ReasonPhrase}. Detalles: {errorResponse}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btn_EliminarEditCard_Click(object sender, EventArgs e)
        {
            var confirmResult = MessageBox.Show("¿Está seguro de que desea eliminar esta tarjeta?",
                                                "Confirmar Eliminación",
                                                MessageBoxButtons.YesNo,
                                                MessageBoxIcon.Question);
            if (confirmResult == DialogResult.Yes)
            {
                try
                {
                    HttpResponseMessage response = await client.DeleteAsync($"/api/tarjetas/{CardID}");
                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Tarjeta eliminada correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        var errorResponse = await response.Content.ReadAsStringAsync();
                        MessageBox.Show($"Error al eliminar la tarjeta: {response.ReasonPhrase}. Detalles: {errorResponse}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
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