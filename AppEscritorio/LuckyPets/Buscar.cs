using System;
using System.Windows.Forms;

namespace LuckyPets
{
    public partial class Buscar : Form
    {
        public event EventHandler<string> BusquedaRealizada;

        public Buscar()
        {
            InitializeComponent();
        }

        private void btn_BusquedaAvanzada_Click(object sender, EventArgs e)
        {
            string searchValue = txtBoxBuscarBusquedaAvanzada.Text.Trim();

            if (!string.IsNullOrEmpty(searchValue))
            {
                BusquedaRealizada?.Invoke(this, searchValue);
                this.Close();
            }
            else
            {
                MessageBox.Show("Por favor, ingrese un valor para buscar.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        public void ClearAdvancedSearch()
        {
            txtBoxBuscarBusquedaAvanzada.Clear();
        }
    }
}
