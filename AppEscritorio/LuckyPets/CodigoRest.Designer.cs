namespace LuckyPets
{
    partial class CodigoRest
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CodigoRest));
            this.lblResetCode1 = new System.Windows.Forms.Label();
            this.lblResetCode2 = new System.Windows.Forms.Label();
            this.txtBoxResetCode = new System.Windows.Forms.TextBox();
            this.btn_ConfirmarCodigo = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lblResetCode1
            // 
            this.lblResetCode1.AutoSize = true;
            this.lblResetCode1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblResetCode1.ForeColor = System.Drawing.Color.White;
            this.lblResetCode1.Location = new System.Drawing.Point(36, 44);
            this.lblResetCode1.Name = "lblResetCode1";
            this.lblResetCode1.Size = new System.Drawing.Size(348, 18);
            this.lblResetCode1.TabIndex = 5;
            this.lblResetCode1.Text = "Introduzca el código que le hemos enviado al";
            // 
            // lblResetCode2
            // 
            this.lblResetCode2.AutoSize = true;
            this.lblResetCode2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblResetCode2.ForeColor = System.Drawing.Color.White;
            this.lblResetCode2.Location = new System.Drawing.Point(36, 62);
            this.lblResetCode2.Name = "lblResetCode2";
            this.lblResetCode2.Size = new System.Drawing.Size(152, 18);
            this.lblResetCode2.TabIndex = 6;
            this.lblResetCode2.Text = "corréo electrónico:";
            // 
            // txtBoxResetCode
            // 
            this.txtBoxResetCode.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxResetCode.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.txtBoxResetCode.Location = new System.Drawing.Point(39, 103);
            this.txtBoxResetCode.Name = "txtBoxResetCode";
            this.txtBoxResetCode.Size = new System.Drawing.Size(374, 24);
            this.txtBoxResetCode.TabIndex = 7;
            // 
            // btn_ConfirmarCodigo
            // 
            this.btn_ConfirmarCodigo.BackColor = System.Drawing.Color.White;
            this.btn_ConfirmarCodigo.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_ConfirmarCodigo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_ConfirmarCodigo.Location = new System.Drawing.Point(39, 183);
            this.btn_ConfirmarCodigo.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_ConfirmarCodigo.Name = "btn_ConfirmarCodigo";
            this.btn_ConfirmarCodigo.Size = new System.Drawing.Size(374, 38);
            this.btn_ConfirmarCodigo.TabIndex = 8;
            this.btn_ConfirmarCodigo.Text = "CONFIRMAR CÓDIGO";
            this.btn_ConfirmarCodigo.UseVisualStyleBackColor = false;
            this.btn_ConfirmarCodigo.Click += new System.EventHandler(this.btn_ConfirmarCodigo_Click);
            // 
            // CodigoRest
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(453, 246);
            this.Controls.Add(this.btn_ConfirmarCodigo);
            this.Controls.Add(this.txtBoxResetCode);
            this.Controls.Add(this.lblResetCode2);
            this.Controls.Add(this.lblResetCode1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "CodigoRest";
            this.Text = "Confirmar código de restablecimiento";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblResetCode1;
        private System.Windows.Forms.Label lblResetCode2;
        private System.Windows.Forms.TextBox txtBoxResetCode;
        private System.Windows.Forms.Button btn_ConfirmarCodigo;
    }
}