namespace LuckyPets
{
    partial class Principal
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Principal));
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.statusStripFechaHora = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLblFechaHora = new System.Windows.Forms.ToolStripStatusLabel();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.statusStripFechaHora.SuspendLayout();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(72, 149);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(709, 301);
            this.dataGridView1.TabIndex = 0;
            // 
            // statusStripFechaHora
            // 
            this.statusStripFechaHora.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLblFechaHora});
            this.statusStripFechaHora.Location = new System.Drawing.Point(0, 511);
            this.statusStripFechaHora.Name = "statusStripFechaHora";
            this.statusStripFechaHora.Size = new System.Drawing.Size(876, 23);
            this.statusStripFechaHora.TabIndex = 1;
            this.statusStripFechaHora.Text = "statusStrip1";
            // 
            // toolStripStatusLblFechaHora
            // 
            this.toolStripStatusLblFechaHora.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold);
            this.toolStripStatusLblFechaHora.ForeColor = System.Drawing.Color.White;
            this.toolStripStatusLblFechaHora.Name = "toolStripStatusLblFechaHora";
            this.toolStripStatusLblFechaHora.Size = new System.Drawing.Size(830, 18);
            this.toolStripStatusLblFechaHora.Spring = true;
            this.toolStripStatusLblFechaHora.Text = "toolStripStatusLabel1";
            // 
            // Principal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(876, 534);
            this.Controls.Add(this.statusStripFechaHora);
            this.Controls.Add(this.dataGridView1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Principal";
            this.Text = "Principal LuckyPets";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.statusStripFechaHora.ResumeLayout(false);
            this.statusStripFechaHora.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.StatusStrip statusStripFechaHora;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLblFechaHora;
    }
}