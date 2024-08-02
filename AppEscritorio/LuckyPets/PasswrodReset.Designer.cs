namespace LuckyPets
{
    partial class PasswrodReset
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PasswrodReset));
            this.lblPasswordReset1 = new System.Windows.Forms.Label();
            this.lblPasswordReset2 = new System.Windows.Forms.Label();
            this.txtBoxEmailResetPassword = new System.Windows.Forms.TextBox();
            this.btn_ResetPassword = new System.Windows.Forms.Button();
            this.linklblOlvideContraseniaReset = new System.Windows.Forms.LinkLabel();
            this.lblEsperar = new System.Windows.Forms.Label();
            this.toolTipPasswordReset = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // lblPasswordReset1
            // 
            this.lblPasswordReset1.AutoSize = true;
            this.lblPasswordReset1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPasswordReset1.ForeColor = System.Drawing.Color.White;
            this.lblPasswordReset1.Location = new System.Drawing.Point(34, 45);
            this.lblPasswordReset1.Name = "lblPasswordReset1";
            this.lblPasswordReset1.Size = new System.Drawing.Size(377, 18);
            this.lblPasswordReset1.TabIndex = 4;
            this.lblPasswordReset1.Text = "Introduzca su correo electrónico y le enviaremos";
            // 
            // lblPasswordReset2
            // 
            this.lblPasswordReset2.AutoSize = true;
            this.lblPasswordReset2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPasswordReset2.ForeColor = System.Drawing.Color.White;
            this.lblPasswordReset2.Location = new System.Drawing.Point(34, 63);
            this.lblPasswordReset2.Name = "lblPasswordReset2";
            this.lblPasswordReset2.Size = new System.Drawing.Size(380, 18);
            this.lblPasswordReset2.TabIndex = 5;
            this.lblPasswordReset2.Text = "las instrucciones para restablecer su contraseña:";
            // 
            // txtBoxEmailResetPassword
            // 
            this.txtBoxEmailResetPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxEmailResetPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.txtBoxEmailResetPassword.Location = new System.Drawing.Point(37, 104);
            this.txtBoxEmailResetPassword.Name = "txtBoxEmailResetPassword";
            this.txtBoxEmailResetPassword.Size = new System.Drawing.Size(374, 24);
            this.txtBoxEmailResetPassword.TabIndex = 6;
            this.toolTipPasswordReset.SetToolTip(this.txtBoxEmailResetPassword, "Introduzca su correo electrónico");
            // 
            // btn_ResetPassword
            // 
            this.btn_ResetPassword.BackColor = System.Drawing.Color.White;
            this.btn_ResetPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_ResetPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_ResetPassword.Location = new System.Drawing.Point(37, 249);
            this.btn_ResetPassword.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_ResetPassword.Name = "btn_ResetPassword";
            this.btn_ResetPassword.Size = new System.Drawing.Size(374, 38);
            this.btn_ResetPassword.TabIndex = 7;
            this.btn_ResetPassword.Text = "RESTABLECER CONTRASEÑA";
            this.toolTipPasswordReset.SetToolTip(this.btn_ResetPassword, "Procesar a restablecer contraseña");
            this.btn_ResetPassword.UseVisualStyleBackColor = false;
            this.btn_ResetPassword.Click += new System.EventHandler(this.btn_ResetPassword_Click);
            // 
            // linklblOlvideContraseniaReset
            // 
            this.linklblOlvideContraseniaReset.AutoSize = true;
            this.linklblOlvideContraseniaReset.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold);
            this.linklblOlvideContraseniaReset.LinkColor = System.Drawing.Color.White;
            this.linklblOlvideContraseniaReset.Location = new System.Drawing.Point(100, 308);
            this.linklblOlvideContraseniaReset.Name = "linklblOlvideContraseniaReset";
            this.linklblOlvideContraseniaReset.Size = new System.Drawing.Size(269, 18);
            this.linklblOlvideContraseniaReset.TabIndex = 9;
            this.linklblOlvideContraseniaReset.TabStop = true;
            this.linklblOlvideContraseniaReset.Text = "Iniciar sesión con tus credenciales";
            this.toolTipPasswordReset.SetToolTip(this.linklblOlvideContraseniaReset, "Inicie sesión con sus credenciales actuales");
            // 
            // lblEsperar
            // 
            this.lblEsperar.AutoSize = true;
            this.lblEsperar.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblEsperar.ForeColor = System.Drawing.Color.White;
            this.lblEsperar.Location = new System.Drawing.Point(143, 181);
            this.lblEsperar.Name = "lblEsperar";
            this.lblEsperar.Size = new System.Drawing.Size(154, 18);
            this.lblEsperar.TabIndex = 10;
            this.lblEsperar.Text = "Por favor, espere...";
            // 
            // PasswrodReset
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(449, 354);
            this.Controls.Add(this.lblEsperar);
            this.Controls.Add(this.linklblOlvideContraseniaReset);
            this.Controls.Add(this.btn_ResetPassword);
            this.Controls.Add(this.txtBoxEmailResetPassword);
            this.Controls.Add(this.lblPasswordReset2);
            this.Controls.Add(this.lblPasswordReset1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "PasswrodReset";
            this.Text = "Restablecer contraseña";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblPasswordReset1;
        private System.Windows.Forms.Label lblPasswordReset2;
        private System.Windows.Forms.TextBox txtBoxEmailResetPassword;
        private System.Windows.Forms.Button btn_ResetPassword;
        private System.Windows.Forms.LinkLabel linklblOlvideContraseniaReset;
        private System.Windows.Forms.Label lblEsperar;
        private System.Windows.Forms.ToolTip toolTipPasswordReset;
    }
}