namespace LuckyPets
{
    partial class Buscar
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Buscar));
            this.lblBusquedaAvanzada = new System.Windows.Forms.Label();
            this.txtBoxBuscarBusquedaAvanzada = new System.Windows.Forms.TextBox();
            this.btn_BusquedaAvanzada = new System.Windows.Forms.Button();
            this.toolTipBuscar = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // lblBusquedaAvanzada
            // 
            this.lblBusquedaAvanzada.AutoSize = true;
            this.lblBusquedaAvanzada.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblBusquedaAvanzada.ForeColor = System.Drawing.Color.White;
            this.lblBusquedaAvanzada.Location = new System.Drawing.Point(30, 19);
            this.lblBusquedaAvanzada.Name = "lblBusquedaAvanzada";
            this.lblBusquedaAvanzada.Size = new System.Drawing.Size(163, 18);
            this.lblBusquedaAvanzada.TabIndex = 10;
            this.lblBusquedaAvanzada.Text = "Búsqueda avanzada:";
            // 
            // txtBoxBuscarBusquedaAvanzada
            // 
            this.txtBoxBuscarBusquedaAvanzada.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxBuscarBusquedaAvanzada.ForeColor = System.Drawing.Color.Black;
            this.txtBoxBuscarBusquedaAvanzada.Location = new System.Drawing.Point(33, 40);
            this.txtBoxBuscarBusquedaAvanzada.Name = "txtBoxBuscarBusquedaAvanzada";
            this.txtBoxBuscarBusquedaAvanzada.Size = new System.Drawing.Size(287, 24);
            this.txtBoxBuscarBusquedaAvanzada.TabIndex = 11;
            this.toolTipBuscar.SetToolTip(this.txtBoxBuscarBusquedaAvanzada, "Introduzca los datos que desea buscar");
            // 
            // btn_BusquedaAvanzada
            // 
            this.btn_BusquedaAvanzada.BackColor = System.Drawing.Color.White;
            this.btn_BusquedaAvanzada.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_BusquedaAvanzada.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_BusquedaAvanzada.Location = new System.Drawing.Point(338, 30);
            this.btn_BusquedaAvanzada.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_BusquedaAvanzada.Name = "btn_BusquedaAvanzada";
            this.btn_BusquedaAvanzada.Size = new System.Drawing.Size(115, 45);
            this.btn_BusquedaAvanzada.TabIndex = 12;
            this.btn_BusquedaAvanzada.Text = "BUSCAR";
            this.toolTipBuscar.SetToolTip(this.btn_BusquedaAvanzada, "Realizar búsqueda avanzada");
            this.btn_BusquedaAvanzada.UseVisualStyleBackColor = false;
            this.btn_BusquedaAvanzada.Click += new System.EventHandler(this.btn_BusquedaAvanzada_Click);
            // 
            // Buscar
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(490, 103);
            this.Controls.Add(this.btn_BusquedaAvanzada);
            this.Controls.Add(this.txtBoxBuscarBusquedaAvanzada);
            this.Controls.Add(this.lblBusquedaAvanzada);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Buscar";
            this.Text = "Buscar";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblBusquedaAvanzada;
        private System.Windows.Forms.TextBox txtBoxBuscarBusquedaAvanzada;
        private System.Windows.Forms.Button btn_BusquedaAvanzada;
        private System.Windows.Forms.ToolTip toolTipBuscar;
    }
}