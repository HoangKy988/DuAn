namespace GUI.AllControls
{
    partial class UC_QLLH
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnLUU = new System.Windows.Forms.Button();
            this.btnXOA = new System.Windows.Forms.Button();
            this.btnSUA = new System.Windows.Forms.Button();
            this.btnTHEM = new System.Windows.Forms.Button();
            this.txbLH = new System.Windows.Forms.TextBox();
            this.txbGV = new System.Windows.Forms.TextBox();
            this.txbTLH = new System.Windows.Forms.TextBox();
            this.txbTMH = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.THM = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.TLH = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.GV = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.LH = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // btnLUU
            // 
            this.btnLUU.Location = new System.Drawing.Point(791, 403);
            this.btnLUU.Name = "btnLUU";
            this.btnLUU.Size = new System.Drawing.Size(75, 23);
            this.btnLUU.TabIndex = 39;
            this.btnLUU.Text = "Lưu";
            this.btnLUU.UseVisualStyleBackColor = true;
            // 
            // btnXOA
            // 
            this.btnXOA.Location = new System.Drawing.Point(641, 403);
            this.btnXOA.Name = "btnXOA";
            this.btnXOA.Size = new System.Drawing.Size(75, 23);
            this.btnXOA.TabIndex = 38;
            this.btnXOA.Text = "Xóa";
            this.btnXOA.UseVisualStyleBackColor = true;
            // 
            // btnSUA
            // 
            this.btnSUA.Location = new System.Drawing.Point(791, 348);
            this.btnSUA.Name = "btnSUA";
            this.btnSUA.Size = new System.Drawing.Size(75, 23);
            this.btnSUA.TabIndex = 37;
            this.btnSUA.Text = "Sửa";
            this.btnSUA.UseVisualStyleBackColor = true;
            // 
            // btnTHEM
            // 
            this.btnTHEM.Location = new System.Drawing.Point(641, 348);
            this.btnTHEM.Name = "btnTHEM";
            this.btnTHEM.Size = new System.Drawing.Size(75, 23);
            this.btnTHEM.TabIndex = 36;
            this.btnTHEM.Text = "Thêm";
            this.btnTHEM.UseVisualStyleBackColor = true;
            // 
            // txbLH
            // 
            this.txbLH.Location = new System.Drawing.Point(745, 272);
            this.txbLH.Name = "txbLH";
            this.txbLH.Size = new System.Drawing.Size(121, 22);
            this.txbLH.TabIndex = 32;
            // 
            // txbGV
            // 
            this.txbGV.Location = new System.Drawing.Point(745, 196);
            this.txbGV.Name = "txbGV";
            this.txbGV.Size = new System.Drawing.Size(121, 22);
            this.txbGV.TabIndex = 31;
            // 
            // txbTLH
            // 
            this.txbTLH.Location = new System.Drawing.Point(745, 123);
            this.txbTLH.Name = "txbTLH";
            this.txbTLH.Size = new System.Drawing.Size(121, 22);
            this.txbTLH.TabIndex = 30;
            // 
            // txbTMH
            // 
            this.txbTMH.Location = new System.Drawing.Point(745, 56);
            this.txbTMH.Name = "txbTMH";
            this.txbTMH.Size = new System.Drawing.Size(121, 22);
            this.txbTMH.TabIndex = 29;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(608, 272);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(59, 16);
            this.label5.TabIndex = 25;
            this.label5.Text = "Lịch Học";
            this.label5.Click += new System.EventHandler(this.label5_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(601, 196);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(66, 16);
            this.label4.TabIndex = 24;
            this.label4.Text = "Giáo Viên";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(601, 123);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(85, 16);
            this.label3.TabIndex = 23;
            this.label3.Text = "Tên Lớp Học";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(598, 56);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(88, 16);
            this.label2.TabIndex = 22;
            this.label2.Text = "Tên Môn Học\r\n";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Times New Roman", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(195, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(170, 25);
            this.label1.TabIndex = 21;
            this.label1.Text = "Thời Khóa Biểu";
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.THM,
            this.TLH,
            this.GV,
            this.LH});
            this.dataGridView1.Location = new System.Drawing.Point(43, 65);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(533, 357);
            this.dataGridView1.TabIndex = 20;
            // 
            // THM
            // 
            this.THM.HeaderText = "Tên Môn Học";
            this.THM.MinimumWidth = 6;
            this.THM.Name = "THM";
            this.THM.Width = 120;
            // 
            // TLH
            // 
            this.TLH.HeaderText = "Tên Lớp Học";
            this.TLH.MinimumWidth = 6;
            this.TLH.Name = "TLH";
            this.TLH.Width = 120;
            // 
            // GV
            // 
            this.GV.HeaderText = "Giáo Viên";
            this.GV.MinimumWidth = 6;
            this.GV.Name = "GV";
            this.GV.Width = 120;
            // 
            // LH
            // 
            this.LH.HeaderText = "Lịch học";
            this.LH.MinimumWidth = 6;
            this.LH.Name = "LH";
            this.LH.Width = 120;
            // 
            // UC_QLLH
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.btnLUU);
            this.Controls.Add(this.btnXOA);
            this.Controls.Add(this.btnSUA);
            this.Controls.Add(this.btnTHEM);
            this.Controls.Add(this.txbLH);
            this.Controls.Add(this.txbGV);
            this.Controls.Add(this.txbTLH);
            this.Controls.Add(this.txbTMH);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridView1);
            this.Name = "UC_QLLH";
            this.Size = new System.Drawing.Size(963, 468);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnLUU;
        private System.Windows.Forms.Button btnXOA;
        private System.Windows.Forms.Button btnSUA;
        private System.Windows.Forms.Button btnTHEM;
        private System.Windows.Forms.TextBox txbLH;
        private System.Windows.Forms.TextBox txbGV;
        private System.Windows.Forms.TextBox txbTLH;
        private System.Windows.Forms.TextBox txbTMH;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn THM;
        private System.Windows.Forms.DataGridViewTextBoxColumn TLH;
        private System.Windows.Forms.DataGridViewTextBoxColumn GV;
        private System.Windows.Forms.DataGridViewTextBoxColumn LH;
    }
}
