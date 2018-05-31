namespace CentralDuack
{
    partial class Agregar
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
            this.label1 = new System.Windows.Forms.Label();
            this.inshotel = new System.Windows.Forms.Button();
            this.nombreBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.latitudBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.longitudBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.direccionBox = new System.Windows.Forms.TextBox();
            this.paisBox = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.provinciaBox = new System.Windows.Forms.ComboBox();
            this.label7 = new System.Windows.Forms.Label();
            this.localidadBox = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.idiomaBox = new System.Windows.Forms.ComboBox();
            this.label9 = new System.Windows.Forms.Label();
            this.telefonoBox = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.urlBox = new System.Windows.Forms.TextBox();
            this.error = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.precioBox = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 17F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.OrangeRed;
            this.label1.Location = new System.Drawing.Point(12, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(499, 29);
            this.label1.TabIndex = 0;
            this.label1.Text = "Hoteles";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // inshotel
            // 
            this.inshotel.Location = new System.Drawing.Point(216, 370);
            this.inshotel.Name = "inshotel";
            this.inshotel.Size = new System.Drawing.Size(75, 36);
            this.inshotel.TabIndex = 1;
            this.inshotel.Text = "Insertar Hotel";
            this.inshotel.UseVisualStyleBackColor = true;
            this.inshotel.Click += new System.EventHandler(this.inshotel_Click);
            // 
            // nombreBox
            // 
            this.nombreBox.Location = new System.Drawing.Point(116, 69);
            this.nombreBox.Name = "nombreBox";
            this.nombreBox.Size = new System.Drawing.Size(258, 20);
            this.nombreBox.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(116, 50);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(47, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Nombre:";
            // 
            // latitudBox
            // 
            this.latitudBox.Location = new System.Drawing.Point(33, 173);
            this.latitudBox.Name = "latitudBox";
            this.latitudBox.Size = new System.Drawing.Size(100, 20);
            this.latitudBox.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(33, 154);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(42, 13);
            this.label3.TabIndex = 5;
            this.label3.Text = "Latitud:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(191, 154);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(51, 13);
            this.label4.TabIndex = 7;
            this.label4.Text = "Longitud:";
            // 
            // longitudBox
            // 
            this.longitudBox.Location = new System.Drawing.Point(191, 173);
            this.longitudBox.Name = "longitudBox";
            this.longitudBox.Size = new System.Drawing.Size(100, 20);
            this.longitudBox.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(33, 102);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(55, 13);
            this.label5.TabIndex = 9;
            this.label5.Text = "Direccion:";
            // 
            // direccionBox
            // 
            this.direccionBox.Location = new System.Drawing.Point(33, 121);
            this.direccionBox.Name = "direccionBox";
            this.direccionBox.Size = new System.Drawing.Size(258, 20);
            this.direccionBox.TabIndex = 8;
            // 
            // paisBox
            // 
            this.paisBox.FormattingEnabled = true;
            this.paisBox.Items.AddRange(new object[] {
            "España",
            "Francia"});
            this.paisBox.Location = new System.Drawing.Point(33, 233);
            this.paisBox.Name = "paisBox";
            this.paisBox.Size = new System.Drawing.Size(121, 21);
            this.paisBox.TabIndex = 10;
            this.paisBox.Text = "España";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(33, 214);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(30, 13);
            this.label6.TabIndex = 11;
            this.label6.Text = "Pais:";
            // 
            // provinciaBox
            // 
            this.provinciaBox.FormattingEnabled = true;
            this.provinciaBox.Items.AddRange(new object[] {
            "Alicante",
            "Valencia"});
            this.provinciaBox.Location = new System.Drawing.Point(191, 233);
            this.provinciaBox.Name = "provinciaBox";
            this.provinciaBox.Size = new System.Drawing.Size(121, 21);
            this.provinciaBox.TabIndex = 12;
            this.provinciaBox.Text = "Alicante";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(191, 217);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(54, 13);
            this.label7.TabIndex = 13;
            this.label7.Text = "Provincia:";
            // 
            // localidadBox
            // 
            this.localidadBox.FormattingEnabled = true;
            this.localidadBox.Items.AddRange(new object[] {
            "Elda",
            "Petrer",
            "Alicante",
            "San Vicente"});
            this.localidadBox.Location = new System.Drawing.Point(339, 233);
            this.localidadBox.Name = "localidadBox";
            this.localidadBox.Size = new System.Drawing.Size(121, 21);
            this.localidadBox.TabIndex = 14;
            this.localidadBox.Text = "Elda";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(336, 214);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(56, 13);
            this.label8.TabIndex = 15;
            this.label8.Text = "Localidad:";
            // 
            // idiomaBox
            // 
            this.idiomaBox.FormattingEnabled = true;
            this.idiomaBox.Items.AddRange(new object[] {
            "Español",
            "Frances",
            "Valenciano"});
            this.idiomaBox.Location = new System.Drawing.Point(339, 173);
            this.idiomaBox.Name = "idiomaBox";
            this.idiomaBox.Size = new System.Drawing.Size(121, 21);
            this.idiomaBox.TabIndex = 16;
            this.idiomaBox.Text = "Español";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(336, 157);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(41, 13);
            this.label9.TabIndex = 17;
            this.label9.Text = "Idioma:";
            // 
            // telefonoBox
            // 
            this.telefonoBox.Location = new System.Drawing.Point(33, 294);
            this.telefonoBox.Name = "telefonoBox";
            this.telefonoBox.Size = new System.Drawing.Size(100, 20);
            this.telefonoBox.TabIndex = 18;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.ForeColor = System.Drawing.SystemColors.Highlight;
            this.label11.Location = new System.Drawing.Point(191, 275);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(71, 13);
            this.label11.TabIndex = 21;
            this.label11.Text = "Pagina WEB:";
            // 
            // urlBox
            // 
            this.urlBox.Location = new System.Drawing.Point(191, 294);
            this.urlBox.Name = "urlBox";
            this.urlBox.Size = new System.Drawing.Size(258, 20);
            this.urlBox.TabIndex = 20;
            // 
            // error
            // 
            this.error.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.error.ForeColor = System.Drawing.Color.Red;
            this.error.Location = new System.Drawing.Point(12, 337);
            this.error.Name = "error";
            this.error.Size = new System.Drawing.Size(499, 23);
            this.error.TabIndex = 22;
            this.error.Text = "ERROR: Los cuadros de texto que no están en azul no pueden estar vacíos.";
            this.error.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.error.Visible = false;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(337, 102);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(40, 13);
            this.label12.TabIndex = 24;
            this.label12.Text = "Precio:";
            // 
            // precioBox
            // 
            this.precioBox.Location = new System.Drawing.Point(337, 121);
            this.precioBox.Name = "precioBox";
            this.precioBox.Size = new System.Drawing.Size(100, 20);
            this.precioBox.TabIndex = 23;
            this.precioBox.Text = "0.0";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.ForeColor = System.Drawing.SystemColors.Highlight;
            this.label10.Location = new System.Drawing.Point(33, 275);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(52, 13);
            this.label10.TabIndex = 25;
            this.label10.Text = "Telefono:";
            // 
            // Agregar
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(523, 418);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.precioBox);
            this.Controls.Add(this.error);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.urlBox);
            this.Controls.Add(this.telefonoBox);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.idiomaBox);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.localidadBox);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.provinciaBox);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.paisBox);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.direccionBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.longitudBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.latitudBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.nombreBox);
            this.Controls.Add(this.inshotel);
            this.Controls.Add(this.label1);
            this.Name = "Agregar";
            this.Text = "Agregar";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button inshotel;
        private System.Windows.Forms.TextBox nombreBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox latitudBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox longitudBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox direccionBox;
        private System.Windows.Forms.ComboBox paisBox;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.ComboBox provinciaBox;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.ComboBox localidadBox;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.ComboBox idiomaBox;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox telefonoBox;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox urlBox;
        private System.Windows.Forms.Label error;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox precioBox;
        private System.Windows.Forms.Label label10;
    }
}