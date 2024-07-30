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
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
        }

        private void txtBoxEmailLogin_TextChanged(object sender, EventArgs e)
        {

        }

        private void txtBoxPasswordLogin_TextChanged(object sender, EventArgs e)
        {

        }

        private void btn_Login_Click(object sender, EventArgs e)
        {
            Principal principalForm = new Principal();
            principalForm.Show();
            this.Hide();
        }
    }
}
