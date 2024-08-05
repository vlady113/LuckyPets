namespace LuckyPets
{
    partial class AddUser
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AddUser));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txtBoxAddUserEmail = new System.Windows.Forms.TextBox();
            this.txtBoxAddUserRepeatEmail = new System.Windows.Forms.TextBox();
            this.txtBoxAddUserPassword = new System.Windows.Forms.TextBox();
            this.txtBoxAddUserRepeatPassword = new System.Windows.Forms.TextBox();
            this.btn_RegistrarUsuario = new System.Windows.Forms.Button();
            this.toolTipAddUser = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(59, 33);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(155, 18);
            this.label1.TabIndex = 10;
            this.label1.Text = "Correo electrónico:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(59, 94);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(223, 18);
            this.label2.TabIndex = 11;
            this.label2.Text = "Repita el correo electrónico:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(341, 33);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(290, 18);
            this.label3.TabIndex = 12;
            this.label3.Text = "Contraseña (entre 9 y 15 caracteres):";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(341, 94);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(163, 18);
            this.label4.TabIndex = 13;
            this.label4.Text = "Repita la contraseña";
            // 
            // txtBoxAddUserEmail
            // 
            this.txtBoxAddUserEmail.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxAddUserEmail.ForeColor = System.Drawing.Color.Black;
            this.txtBoxAddUserEmail.Location = new System.Drawing.Point(62, 54);
            this.txtBoxAddUserEmail.Name = "txtBoxAddUserEmail";
            this.txtBoxAddUserEmail.Size = new System.Drawing.Size(229, 24);
            this.txtBoxAddUserEmail.TabIndex = 17;
            this.toolTipAddUser.SetToolTip(this.txtBoxAddUserEmail, "Introduzca un correo electrónico");
            // 
            // txtBoxAddUserRepeatEmail
            // 
            this.txtBoxAddUserRepeatEmail.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxAddUserRepeatEmail.ForeColor = System.Drawing.Color.Black;
            this.txtBoxAddUserRepeatEmail.Location = new System.Drawing.Point(62, 115);
            this.txtBoxAddUserRepeatEmail.Name = "txtBoxAddUserRepeatEmail";
            this.txtBoxAddUserRepeatEmail.Size = new System.Drawing.Size(229, 24);
            this.txtBoxAddUserRepeatEmail.TabIndex = 18;
            this.toolTipAddUser.SetToolTip(this.txtBoxAddUserRepeatEmail, "Vuelva a escribir el correo electrónico");
            // 
            // txtBoxAddUserPassword
            // 
            this.txtBoxAddUserPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxAddUserPassword.ForeColor = System.Drawing.Color.Black;
            this.txtBoxAddUserPassword.Location = new System.Drawing.Point(344, 54);
            this.txtBoxAddUserPassword.Name = "txtBoxAddUserPassword";
            this.txtBoxAddUserPassword.PasswordChar = '*';
            this.txtBoxAddUserPassword.Size = new System.Drawing.Size(229, 24);
            this.txtBoxAddUserPassword.TabIndex = 19;
            this.toolTipAddUser.SetToolTip(this.txtBoxAddUserPassword, "Introduzca una contraseña");
            // 
            // txtBoxAddUserRepeatPassword
            // 
            this.txtBoxAddUserRepeatPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxAddUserRepeatPassword.ForeColor = System.Drawing.Color.Black;
            this.txtBoxAddUserRepeatPassword.Location = new System.Drawing.Point(344, 115);
            this.txtBoxAddUserRepeatPassword.Name = "txtBoxAddUserRepeatPassword";
            this.txtBoxAddUserRepeatPassword.PasswordChar = '*';
            this.txtBoxAddUserRepeatPassword.Size = new System.Drawing.Size(229, 24);
            this.txtBoxAddUserRepeatPassword.TabIndex = 20;
            this.toolTipAddUser.SetToolTip(this.txtBoxAddUserRepeatPassword, "Vuelva a escribir la contraseña");
            // 
            // btn_RegistrarUsuario
            // 
            this.btn_RegistrarUsuario.BackColor = System.Drawing.Color.White;
            this.btn_RegistrarUsuario.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_RegistrarUsuario.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_RegistrarUsuario.Location = new System.Drawing.Point(163, 181);
            this.btn_RegistrarUsuario.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_RegistrarUsuario.Name = "btn_RegistrarUsuario";
            this.btn_RegistrarUsuario.Size = new System.Drawing.Size(309, 38);
            this.btn_RegistrarUsuario.TabIndex = 21;
            this.btn_RegistrarUsuario.Text = "REGISTRAR NUEVO USUARIO";
            this.toolTipAddUser.SetToolTip(this.btn_RegistrarUsuario, "Registrar un nuevo usuario en el sistema");
            this.btn_RegistrarUsuario.UseVisualStyleBackColor = false;
            this.btn_RegistrarUsuario.Click += new System.EventHandler(this.btn_RegistrarUsuario_Click);
            // 
            // AddUser
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(664, 272);
            this.Controls.Add(this.btn_RegistrarUsuario);
            this.Controls.Add(this.txtBoxAddUserRepeatPassword);
            this.Controls.Add(this.txtBoxAddUserPassword);
            this.Controls.Add(this.txtBoxAddUserRepeatEmail);
            this.Controls.Add(this.txtBoxAddUserEmail);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "AddUser";
            this.Text = "Añadir nuevo usuario";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtBoxAddUserEmail;
        private System.Windows.Forms.TextBox txtBoxAddUserRepeatEmail;
        private System.Windows.Forms.TextBox txtBoxAddUserPassword;
        private System.Windows.Forms.TextBox txtBoxAddUserRepeatPassword;
        private System.Windows.Forms.Button btn_RegistrarUsuario;
        private System.Windows.Forms.ToolTip toolTipAddUser;
    }
}