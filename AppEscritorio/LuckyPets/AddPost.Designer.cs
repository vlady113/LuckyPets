namespace LuckyPets
{
    partial class AddPost
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AddPost));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.dateTimePickerAddPostFechaInicio = new System.Windows.Forms.DateTimePicker();
            this.label3 = new System.Windows.Forms.Label();
            this.dateTimePickerAddPostFechaFin = new System.Windows.Forms.DateTimePicker();
            this.label4 = new System.Windows.Forms.Label();
            this.textBoxAddCardCR = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.textBoxAddPostDescripcion = new System.Windows.Forms.TextBox();
            this.checkBoxAddCardAceptarCoste = new System.Windows.Forms.CheckBox();
            this.btn_AddPostPublicar = new System.Windows.Forms.Button();
            this.pictureBoxAddPost = new System.Windows.Forms.PictureBox();
            this.textBoxAddPostHoraInicio = new System.Windows.Forms.TextBox();
            this.textBoxAddPostHoraFin = new System.Windows.Forms.TextBox();
            this.toolTipAddPost = new System.Windows.Forms.ToolTip(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxAddPost)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(59, 35);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(298, 18);
            this.label1.TabIndex = 11;
            this.label1.Text = "Seleccione una imagen de la mascota:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(59, 160);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(258, 18);
            this.label2.TabIndex = 12;
            this.label2.Text = "Introduzca fecha y hora de inicio:";
            // 
            // dateTimePickerAddPostFechaInicio
            // 
            this.dateTimePickerAddPostFechaInicio.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(254)));
            this.dateTimePickerAddPostFechaInicio.Location = new System.Drawing.Point(62, 181);
            this.dateTimePickerAddPostFechaInicio.Name = "dateTimePickerAddPostFechaInicio";
            this.dateTimePickerAddPostFechaInicio.Size = new System.Drawing.Size(182, 24);
            this.dateTimePickerAddPostFechaInicio.TabIndex = 24;
            this.toolTipAddPost.SetToolTip(this.dateTimePickerAddPostFechaInicio, "Seleccione una fecha de inicio");
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(59, 233);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(236, 18);
            this.label3.TabIndex = 25;
            this.label3.Text = "Introduzca fecha y hora de fin:";
            // 
            // dateTimePickerAddPostFechaFin
            // 
            this.dateTimePickerAddPostFechaFin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(254)));
            this.dateTimePickerAddPostFechaFin.Location = new System.Drawing.Point(62, 254);
            this.dateTimePickerAddPostFechaFin.Name = "dateTimePickerAddPostFechaFin";
            this.dateTimePickerAddPostFechaFin.Size = new System.Drawing.Size(182, 24);
            this.dateTimePickerAddPostFechaFin.TabIndex = 26;
            this.toolTipAddPost.SetToolTip(this.dateTimePickerAddPostFechaFin, "Seleccione una fecha de finalización");
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(59, 301);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(287, 18);
            this.label4.TabIndex = 27;
            this.label4.Text = "Precio a pagar por el servicio en CR:";
            // 
            // textBoxAddCardCR
            // 
            this.textBoxAddCardCR.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxAddCardCR.ForeColor = System.Drawing.Color.Black;
            this.textBoxAddCardCR.Location = new System.Drawing.Point(62, 322);
            this.textBoxAddCardCR.Name = "textBoxAddCardCR";
            this.textBoxAddCardCR.Size = new System.Drawing.Size(84, 24);
            this.textBoxAddCardCR.TabIndex = 28;
            this.toolTipAddPost.SetToolTip(this.textBoxAddCardCR, "Introduzca el precio a pagar en CR por los servicios");
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.ForeColor = System.Drawing.Color.White;
            this.label5.Location = new System.Drawing.Point(59, 365);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(262, 18);
            this.label5.TabIndex = 29;
            this.label5.Text = "Introduzca una breve descripción:";
            // 
            // textBoxAddPostDescripcion
            // 
            this.textBoxAddPostDescripcion.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxAddPostDescripcion.ForeColor = System.Drawing.Color.Black;
            this.textBoxAddPostDescripcion.Location = new System.Drawing.Point(62, 386);
            this.textBoxAddPostDescripcion.Multiline = true;
            this.textBoxAddPostDescripcion.Name = "textBoxAddPostDescripcion";
            this.textBoxAddPostDescripcion.Size = new System.Drawing.Size(556, 200);
            this.textBoxAddPostDescripcion.TabIndex = 30;
            this.toolTipAddPost.SetToolTip(this.textBoxAddPostDescripcion, "Introduzca una breve descripción");
            // 
            // checkBoxAddCardAceptarCoste
            // 
            this.checkBoxAddCardAceptarCoste.AutoSize = true;
            this.checkBoxAddCardAceptarCoste.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkBoxAddCardAceptarCoste.ForeColor = System.Drawing.Color.White;
            this.checkBoxAddCardAceptarCoste.Location = new System.Drawing.Point(62, 592);
            this.checkBoxAddCardAceptarCoste.Name = "checkBoxAddCardAceptarCoste";
            this.checkBoxAddCardAceptarCoste.Size = new System.Drawing.Size(368, 22);
            this.checkBoxAddCardAceptarCoste.TabIndex = 31;
            this.checkBoxAddCardAceptarCoste.Text = "* Aceptar el coste (15 CR) por la publicación.";
            this.toolTipAddPost.SetToolTip(this.checkBoxAddCardAceptarCoste, "Acepte el coste por la publicación");
            this.checkBoxAddCardAceptarCoste.UseVisualStyleBackColor = true;
            // 
            // btn_AddPostPublicar
            // 
            this.btn_AddPostPublicar.BackColor = System.Drawing.Color.White;
            this.btn_AddPostPublicar.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_AddPostPublicar.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_AddPostPublicar.Location = new System.Drawing.Point(168, 655);
            this.btn_AddPostPublicar.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_AddPostPublicar.Name = "btn_AddPostPublicar";
            this.btn_AddPostPublicar.Size = new System.Drawing.Size(309, 38);
            this.btn_AddPostPublicar.TabIndex = 32;
            this.btn_AddPostPublicar.Text = "PUBLICAR NUEVO ANUNCIO";
            this.toolTipAddPost.SetToolTip(this.btn_AddPostPublicar, "Publicar un nuevo anuncio");
            this.btn_AddPostPublicar.UseVisualStyleBackColor = false;
            // 
            // pictureBoxAddPost
            // 
            this.pictureBoxAddPost.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.pictureBoxAddPost.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureBoxAddPost.BackgroundImage")));
            this.pictureBoxAddPost.Location = new System.Drawing.Point(62, 56);
            this.pictureBoxAddPost.Name = "pictureBoxAddPost";
            this.pictureBoxAddPost.Size = new System.Drawing.Size(98, 101);
            this.pictureBoxAddPost.TabIndex = 33;
            this.pictureBoxAddPost.TabStop = false;
            this.toolTipAddPost.SetToolTip(this.pictureBoxAddPost, "Seleccione una imágen de la mascota");
            // 
            // textBoxAddPostHoraInicio
            // 
            this.textBoxAddPostHoraInicio.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxAddPostHoraInicio.ForeColor = System.Drawing.Color.Black;
            this.textBoxAddPostHoraInicio.Location = new System.Drawing.Point(262, 181);
            this.textBoxAddPostHoraInicio.Name = "textBoxAddPostHoraInicio";
            this.textBoxAddPostHoraInicio.Size = new System.Drawing.Size(59, 24);
            this.textBoxAddPostHoraInicio.TabIndex = 34;
            this.textBoxAddPostHoraInicio.Text = "00:00";
            this.toolTipAddPost.SetToolTip(this.textBoxAddPostHoraInicio, "Introduzca una hora de inicio");
            // 
            // textBoxAddPostHoraFin
            // 
            this.textBoxAddPostHoraFin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxAddPostHoraFin.ForeColor = System.Drawing.Color.Black;
            this.textBoxAddPostHoraFin.Location = new System.Drawing.Point(262, 256);
            this.textBoxAddPostHoraFin.Name = "textBoxAddPostHoraFin";
            this.textBoxAddPostHoraFin.Size = new System.Drawing.Size(59, 24);
            this.textBoxAddPostHoraFin.TabIndex = 35;
            this.textBoxAddPostHoraFin.Text = "00:00";
            this.toolTipAddPost.SetToolTip(this.textBoxAddPostHoraFin, "Introduzca una hora de finalización");
            // 
            // AddPost
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(686, 730);
            this.Controls.Add(this.textBoxAddPostHoraFin);
            this.Controls.Add(this.textBoxAddPostHoraInicio);
            this.Controls.Add(this.pictureBoxAddPost);
            this.Controls.Add(this.btn_AddPostPublicar);
            this.Controls.Add(this.checkBoxAddCardAceptarCoste);
            this.Controls.Add(this.textBoxAddPostDescripcion);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.textBoxAddCardCR);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.dateTimePickerAddPostFechaFin);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.dateTimePickerAddPostFechaInicio);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "AddPost";
            this.Text = "Añadir anuncio";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxAddPost)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DateTimePicker dateTimePickerAddPostFechaInicio;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DateTimePicker dateTimePickerAddPostFechaFin;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox textBoxAddCardCR;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBoxAddPostDescripcion;
        private System.Windows.Forms.CheckBox checkBoxAddCardAceptarCoste;
        private System.Windows.Forms.Button btn_AddPostPublicar;
        private System.Windows.Forms.PictureBox pictureBoxAddPost;
        private System.Windows.Forms.TextBox textBoxAddPostHoraInicio;
        private System.Windows.Forms.TextBox textBoxAddPostHoraFin;
        private System.Windows.Forms.ToolTip toolTipAddPost;
    }
}