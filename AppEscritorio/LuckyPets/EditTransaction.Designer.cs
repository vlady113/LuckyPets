namespace LuckyPets
{
    partial class EditTransaction
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(EditTransaction));
            this.label7 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.btn_EliminarEditTransaction = new System.Windows.Forms.Button();
            this.btn_GuardarEditTransaction = new System.Windows.Forms.Button();
            this.txtBoxeEditTransactionUserID = new System.Windows.Forms.TextBox();
            this.txtBoxeEditTransactionClienteID = new System.Windows.Forms.TextBox();
            this.txtBoxeEditTransactionID = new System.Windows.Forms.TextBox();
            this.txtBoxeEditTransactionReservaID = new System.Windows.Forms.TextBox();
            this.dateTimePickerEditTransaction = new System.Windows.Forms.DateTimePicker();
            this.txtBoxeEditTransactionMontoCR = new System.Windows.Forms.TextBox();
            this.txtBoxeEditTransactionTipo = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.ForeColor = System.Drawing.Color.White;
            this.label7.Location = new System.Drawing.Point(32, 35);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(93, 18);
            this.label7.TabIndex = 19;
            this.label7.Text = "Usuario ID:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(32, 183);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(127, 18);
            this.label1.TabIndex = 20;
            this.label1.Text = "Transacción ID:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(347, 35);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(59, 18);
            this.label2.TabIndex = 21;
            this.label2.Text = "Fecha:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(347, 107);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(90, 18);
            this.label3.TabIndex = 22;
            this.label3.Text = "Monto CR:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(347, 183);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(46, 18);
            this.label4.TabIndex = 23;
            this.label4.Text = "Tipo:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.ForeColor = System.Drawing.Color.White;
            this.label5.Location = new System.Drawing.Point(32, 256);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(96, 18);
            this.label5.TabIndex = 24;
            this.label5.Text = "Reserva ID:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.ForeColor = System.Drawing.Color.White;
            this.label6.Location = new System.Drawing.Point(32, 107);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(86, 18);
            this.label6.TabIndex = 25;
            this.label6.Text = "Cliente ID:";
            // 
            // btn_EliminarEditTransaction
            // 
            this.btn_EliminarEditTransaction.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.btn_EliminarEditTransaction.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_EliminarEditTransaction.ForeColor = System.Drawing.Color.White;
            this.btn_EliminarEditTransaction.Location = new System.Drawing.Point(122, 389);
            this.btn_EliminarEditTransaction.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_EliminarEditTransaction.Name = "btn_EliminarEditTransaction";
            this.btn_EliminarEditTransaction.Size = new System.Drawing.Size(374, 38);
            this.btn_EliminarEditTransaction.TabIndex = 46;
            this.btn_EliminarEditTransaction.Text = "ELIMINAR TRANSACCIÓN";
            this.btn_EliminarEditTransaction.UseVisualStyleBackColor = false;
            // 
            // btn_GuardarEditTransaction
            // 
            this.btn_GuardarEditTransaction.BackColor = System.Drawing.Color.White;
            this.btn_GuardarEditTransaction.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_GuardarEditTransaction.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.btn_GuardarEditTransaction.Location = new System.Drawing.Point(122, 345);
            this.btn_GuardarEditTransaction.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_GuardarEditTransaction.Name = "btn_GuardarEditTransaction";
            this.btn_GuardarEditTransaction.Size = new System.Drawing.Size(374, 38);
            this.btn_GuardarEditTransaction.TabIndex = 45;
            this.btn_GuardarEditTransaction.Text = "GUARDAR DATOS TRANSACCIÓN";
            this.btn_GuardarEditTransaction.UseVisualStyleBackColor = false;
            // 
            // txtBoxeEditTransactionUserID
            // 
            this.txtBoxeEditTransactionUserID.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionUserID.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionUserID.Location = new System.Drawing.Point(35, 56);
            this.txtBoxeEditTransactionUserID.Name = "txtBoxeEditTransactionUserID";
            this.txtBoxeEditTransactionUserID.Size = new System.Drawing.Size(229, 24);
            this.txtBoxeEditTransactionUserID.TabIndex = 47;
            // 
            // txtBoxeEditTransactionClienteID
            // 
            this.txtBoxeEditTransactionClienteID.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionClienteID.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionClienteID.Location = new System.Drawing.Point(35, 128);
            this.txtBoxeEditTransactionClienteID.Name = "txtBoxeEditTransactionClienteID";
            this.txtBoxeEditTransactionClienteID.Size = new System.Drawing.Size(229, 24);
            this.txtBoxeEditTransactionClienteID.TabIndex = 48;
            // 
            // txtBoxeEditTransactionID
            // 
            this.txtBoxeEditTransactionID.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionID.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionID.Location = new System.Drawing.Point(35, 204);
            this.txtBoxeEditTransactionID.Name = "txtBoxeEditTransactionID";
            this.txtBoxeEditTransactionID.Size = new System.Drawing.Size(229, 24);
            this.txtBoxeEditTransactionID.TabIndex = 49;
            // 
            // txtBoxeEditTransactionReservaID
            // 
            this.txtBoxeEditTransactionReservaID.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionReservaID.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionReservaID.Location = new System.Drawing.Point(35, 277);
            this.txtBoxeEditTransactionReservaID.Name = "txtBoxeEditTransactionReservaID";
            this.txtBoxeEditTransactionReservaID.Size = new System.Drawing.Size(229, 24);
            this.txtBoxeEditTransactionReservaID.TabIndex = 50;
            // 
            // dateTimePickerEditTransaction
            // 
            this.dateTimePickerEditTransaction.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(254)));
            this.dateTimePickerEditTransaction.Location = new System.Drawing.Point(350, 56);
            this.dateTimePickerEditTransaction.Name = "dateTimePickerEditTransaction";
            this.dateTimePickerEditTransaction.Size = new System.Drawing.Size(229, 24);
            this.dateTimePickerEditTransaction.TabIndex = 51;
            // 
            // txtBoxeEditTransactionMontoCR
            // 
            this.txtBoxeEditTransactionMontoCR.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionMontoCR.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionMontoCR.Location = new System.Drawing.Point(350, 128);
            this.txtBoxeEditTransactionMontoCR.Name = "txtBoxeEditTransactionMontoCR";
            this.txtBoxeEditTransactionMontoCR.Size = new System.Drawing.Size(229, 24);
            this.txtBoxeEditTransactionMontoCR.TabIndex = 52;
            // 
            // txtBoxeEditTransactionTipo
            // 
            this.txtBoxeEditTransactionTipo.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtBoxeEditTransactionTipo.ForeColor = System.Drawing.Color.Black;
            this.txtBoxeEditTransactionTipo.Location = new System.Drawing.Point(350, 204);
            this.txtBoxeEditTransactionTipo.Name = "txtBoxeEditTransactionTipo";
            this.txtBoxeEditTransactionTipo.Size = new System.Drawing.Size(136, 24);
            this.txtBoxeEditTransactionTipo.TabIndex = 53;
            // 
            // EditTransaction
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(146)))), ((int)(((byte)(115)))), ((int)(((byte)(111)))));
            this.ClientSize = new System.Drawing.Size(640, 462);
            this.Controls.Add(this.txtBoxeEditTransactionTipo);
            this.Controls.Add(this.txtBoxeEditTransactionMontoCR);
            this.Controls.Add(this.dateTimePickerEditTransaction);
            this.Controls.Add(this.txtBoxeEditTransactionReservaID);
            this.Controls.Add(this.txtBoxeEditTransactionID);
            this.Controls.Add(this.txtBoxeEditTransactionClienteID);
            this.Controls.Add(this.txtBoxeEditTransactionUserID);
            this.Controls.Add(this.btn_EliminarEditTransaction);
            this.Controls.Add(this.btn_GuardarEditTransaction);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label7);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "EditTransaction";
            this.Text = "Modificar transacción";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public System.Windows.Forms.Label label7;
        public System.Windows.Forms.Label label1;
        public System.Windows.Forms.Label label2;
        public System.Windows.Forms.Label label3;
        public System.Windows.Forms.Label label4;
        public System.Windows.Forms.Label label5;
        public System.Windows.Forms.Label label6;
        public System.Windows.Forms.Button btn_EliminarEditTransaction;
        public System.Windows.Forms.Button btn_GuardarEditTransaction;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionUserID;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionClienteID;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionID;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionReservaID;
        public System.Windows.Forms.DateTimePicker dateTimePickerEditTransaction;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionMontoCR;
        public System.Windows.Forms.TextBox txtBoxeEditTransactionTipo;
    }
}