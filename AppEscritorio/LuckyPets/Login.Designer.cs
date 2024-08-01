namespace LuckyPets
{
    partial class Login
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Login));
            this.btn_Login = new System.Windows.Forms.Button();
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.pictureBoxLogin = new System.Windows.Forms.PictureBox();
            this.txtBoxEmailLogin = new System.Windows.Forms.TextBox();
            this.lblEmailLogin = new System.Windows.Forms.Label();
            this.lblPasswordLogin = new System.Windows.Forms.Label();
            this.txtBoxPasswordLogin = new System.Windows.Forms.TextBox();
            this.checkBoxRecordarme = new System.Windows.Forms.CheckBox();
            this.toolTipLogin = new System.Windows.Forms.ToolTip(this.components);
            this.linklblOlvideContrasenia = new System.Windows.Forms.LinkLabel();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxLogin)).BeginInit();
            this.SuspendLayout();
            // 
            // btn_Login
            // 
            this.btn_Login.BackColor = System.Drawing.Color.White;
            this.btn_Login.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_Login.Location = new System.Drawing.Point(64, 477);
            this.btn_Login.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_Login.Name = "btn_Login";
            this.btn_Login.Size = new System.Drawing.Size(309, 38);
            this.btn_Login.TabIndex = 0;
            this.btn_Login.Text = "INICIAR SESIÓN";
            this.toolTipLogin.SetToolTip(this.btn_Login, "Haga \"Click\" para Iniciar Sesión");
            this.btn_Login.UseVisualStyleBackColor = false;
            this.btn_Login.Click += new System.EventHandler(this.btn_Login_Click);
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "notifyIcon1";
            this.notifyIcon1.Visible = true;
            // 
            // pictureBoxLogin
            // 
            this.pictureBoxLogin.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureBoxLogin.BackgroundImage")));
            this.pictureBoxLogin.Location = new System.Drawing.Point(104, 48);
            this.pictureBoxLogin.Name = "pictureBoxLogin";
            this.pictureBoxLogin.Size = new System.Drawing.Size(229, 212);
            this.pictureBoxLogin.TabIndex = 1;
            this.pictureBoxLogin.TabStop = false;
            // 
            // txtBoxEmailLogin
            // 
            this.txtBoxEmailLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxEmailLogin.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.txtBoxEmailLogin.Location = new System.Drawing.Point(64, 315);
            this.txtBoxEmailLogin.Name = "txtBoxEmailLogin";
            this.txtBoxEmailLogin.Size = new System.Drawing.Size(309, 24);
            this.txtBoxEmailLogin.TabIndex = 2;
            this.toolTipLogin.SetToolTip(this.txtBoxEmailLogin, "Introduzca su corréo electrónico");
            // 
            // lblEmailLogin
            // 
            this.lblEmailLogin.AutoSize = true;
            this.lblEmailLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblEmailLogin.ForeColor = System.Drawing.Color.White;
            this.lblEmailLogin.Location = new System.Drawing.Point(61, 294);
            this.lblEmailLogin.Name = "lblEmailLogin";
            this.lblEmailLogin.Size = new System.Drawing.Size(61, 18);
            this.lblEmailLogin.TabIndex = 3;
            this.lblEmailLogin.Text = "E-Mail:";
            // 
            // lblPasswordLogin
            // 
            this.lblPasswordLogin.AutoSize = true;
            this.lblPasswordLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPasswordLogin.ForeColor = System.Drawing.Color.White;
            this.lblPasswordLogin.Location = new System.Drawing.Point(61, 353);
            this.lblPasswordLogin.Name = "lblPasswordLogin";
            this.lblPasswordLogin.Size = new System.Drawing.Size(100, 18);
            this.lblPasswordLogin.TabIndex = 4;
            this.lblPasswordLogin.Text = "Contraseña:";
            // 
            // txtBoxPasswordLogin
            // 
            this.txtBoxPasswordLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxPasswordLogin.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.txtBoxPasswordLogin.Location = new System.Drawing.Point(64, 374);
            this.txtBoxPasswordLogin.Name = "txtBoxPasswordLogin";
            this.txtBoxPasswordLogin.PasswordChar = '*';
            this.txtBoxPasswordLogin.Size = new System.Drawing.Size(309, 24);
            this.txtBoxPasswordLogin.TabIndex = 5;
            this.toolTipLogin.SetToolTip(this.txtBoxPasswordLogin, "Introduzca su contraseña");
            // 
            // checkBoxRecordarme
            // 
            this.checkBoxRecordarme.AutoSize = true;
            this.checkBoxRecordarme.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkBoxRecordarme.ForeColor = System.Drawing.Color.White;
            this.checkBoxRecordarme.Location = new System.Drawing.Point(64, 404);
            this.checkBoxRecordarme.Name = "checkBoxRecordarme";
            this.checkBoxRecordarme.Size = new System.Drawing.Size(120, 22);
            this.checkBoxRecordarme.TabIndex = 6;
            this.checkBoxRecordarme.Text = "Recordarme";
            this.toolTipLogin.SetToolTip(this.checkBoxRecordarme, "Marque esta casilla para recordar sus credenciales");
            this.checkBoxRecordarme.UseVisualStyleBackColor = true;
            // 
            // linklblOlvideContrasenia
            // 
            this.linklblOlvideContrasenia.AutoSize = true;
            this.linklblOlvideContrasenia.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold);
            this.linklblOlvideContrasenia.LinkColor = System.Drawing.Color.White;
            this.linklblOlvideContrasenia.Location = new System.Drawing.Point(101, 536);
            this.linklblOlvideContrasenia.Name = "linklblOlvideContrasenia";
            this.linklblOlvideContrasenia.Size = new System.Drawing.Size(232, 18);
            this.linklblOlvideContrasenia.TabIndex = 8;
            this.linklblOlvideContrasenia.TabStop = true;
            this.linklblOlvideContrasenia.Text = "¿Has olvidado tú contraseña?";
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 14F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(445, 579);
            this.Controls.Add(this.linklblOlvideContrasenia);
            this.Controls.Add(this.checkBoxRecordarme);
            this.Controls.Add(this.txtBoxPasswordLogin);
            this.Controls.Add(this.lblPasswordLogin);
            this.Controls.Add(this.lblEmailLogin);
            this.Controls.Add(this.txtBoxEmailLogin);
            this.Controls.Add(this.pictureBoxLogin);
            this.Controls.Add(this.btn_Login);
            this.Font = new System.Drawing.Font("Lucida Sans", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.MaximizeBox = false;
            this.Name = "Login";
            this.Text = "Inicio de Sesión LuckyPets";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxLogin)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_Login;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.PictureBox pictureBoxLogin;
        private System.Windows.Forms.TextBox txtBoxEmailLogin;
        private System.Windows.Forms.Label lblEmailLogin;
        private System.Windows.Forms.Label lblPasswordLogin;
        private System.Windows.Forms.TextBox txtBoxPasswordLogin;
        private System.Windows.Forms.CheckBox checkBoxRecordarme;
        private System.Windows.Forms.ToolTip toolTipLogin;
        private System.Windows.Forms.LinkLabel linklblOlvideContrasenia;
    }
}

