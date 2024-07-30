using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LuckyPets
{
    public partial class Principal : Form
    {
        private Timer timer;

        public Principal()
        {
            InitializeComponent();

            // Inicializar y configurar el Timer
            timer = new Timer();
            timer.Interval = 1000; // 1 segundo
            timer.Tick += new EventHandler(Timer_Tick);
            timer.Start();

            // Configurar el ToolStripStatusLabel para que se alinee a la derecha
            toolStripStatusLblFechaHora.TextAlign = ContentAlignment.MiddleRight;

            // Establecer la fecha y la hora inicial
            toolStripStatusLblFechaHora.Text = $"Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            // Actualizar la fecha y la hora
            toolStripStatusLblFechaHora.Text = $"Fecha: {DateTime.Now.ToString("dd/MM/yyyy  ")} Hora: {DateTime.Now.ToString("HH:mm:ss  ")}";
        }
    }
}
