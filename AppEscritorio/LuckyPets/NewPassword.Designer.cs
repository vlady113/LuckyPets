namespace LuckyPets
{
    partial class NewPassword
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(NewPassword));
            this.label2 = new System.Windows.Forms.Label();
            this.txtBoxNewPassword = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.textBoxConfirmNewPassword = new System.Windows.Forms.TextBox();
            this.btn_ConfirmarContrasenia = new System.Windows.Forms.Button();
            this.toolTipNewPassword = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(35, 45);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(348, 18);
            this.label2.TabIndex = 6;
            this.label2.Text = "Nueva ccontraseña (entre 9 y 25 caracteres):";
            // 
            // txtBoxNewPassword
            // 
            this.txtBoxNewPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxNewPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.txtBoxNewPassword.Location = new System.Drawing.Point(38, 66);
            this.txtBoxNewPassword.Name = "txtBoxNewPassword";
            this.txtBoxNewPassword.PasswordChar = '*';
            this.txtBoxNewPassword.Size = new System.Drawing.Size(374, 24);
            this.txtBoxNewPassword.TabIndex = 8;
            this.toolTipNewPassword.SetToolTip(this.txtBoxNewPassword, "Introduzca una nueva contraseña");
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(35, 108);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(222, 18);
            this.label1.TabIndex = 9;
            this.label1.Text = "Repita su nueva contraseña:";
            // 
            // textBoxConfirmNewPassword
            // 
            this.textBoxConfirmNewPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxConfirmNewPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.textBoxConfirmNewPassword.Location = new System.Drawing.Point(38, 129);
            this.textBoxConfirmNewPassword.Name = "textBoxConfirmNewPassword";
            this.textBoxConfirmNewPassword.PasswordChar = '*';
            this.textBoxConfirmNewPassword.Size = new System.Drawing.Size(374, 24);
            this.textBoxConfirmNewPassword.TabIndex = 10;
            this.toolTipNewPassword.SetToolTip(this.textBoxConfirmNewPassword, "Repita su nueva contraseña");
            // 
            // btn_ConfirmarContrasenia
            // 
            this.btn_ConfirmarContrasenia.BackColor = System.Drawing.Color.White;
            this.btn_ConfirmarContrasenia.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_ConfirmarContrasenia.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_ConfirmarContrasenia.Location = new System.Drawing.Point(38, 204);
            this.btn_ConfirmarContrasenia.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_ConfirmarContrasenia.Name = "btn_ConfirmarContrasenia";
            this.btn_ConfirmarContrasenia.Size = new System.Drawing.Size(374, 38);
            this.btn_ConfirmarContrasenia.TabIndex = 11;
            this.btn_ConfirmarContrasenia.Text = "ESTABLECER CONTRASEÑA";
            this.toolTipNewPassword.SetToolTip(this.btn_ConfirmarContrasenia, "Establecer nueva contraseña");
            this.btn_ConfirmarContrasenia.UseVisualStyleBackColor = false;
            this.btn_ConfirmarContrasenia.Click += new System.EventHandler(this.btn_ConfirmarContrasenia_Click);
            // 
            // NewPassword
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(453, 269);
            this.Controls.Add(this.btn_ConfirmarContrasenia);
            this.Controls.Add(this.textBoxConfirmNewPassword);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtBoxNewPassword);
            this.Controls.Add(this.label2);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "NewPassword";
            this.Text = "Establecer nueva contraseña";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtBoxNewPassword;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBoxConfirmNewPassword;
        private System.Windows.Forms.Button btn_ConfirmarContrasenia;
        private System.Windows.Forms.ToolTip toolTipNewPassword;
    }
}