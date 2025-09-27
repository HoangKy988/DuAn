namespace GUI.AllControls
{
    partial class UC_Checkpoints
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
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.MGV = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.TGV = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.SEX = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.THS = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.NCT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.BIRTH = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.BC = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.txbMGV = new System.Windows.Forms.TextBox();
            this.txbTGV = new System.Windows.Forms.TextBox();
            this.txbSEX = new System.Windows.Forms.TextBox();
            this.txbLCN = new System.Windows.Forms.TextBox();
            this.txbNCT = new System.Windows.Forms.TextBox();
            this.txbBIRTH = new System.Windows.Forms.TextBox();
            this.cbBC = new System.Windows.Forms.ComboBox();
            this.btnTHEM = new System.Windows.Forms.Button();
            this.btnSUA = new System.Windows.Forms.Button();
            this.btnXOA = new System.Windows.Forms.Button();
            this.btnLUU = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.MGV,
            this.TGV,
            this.SEX,
            this.THS,
            this.NCT,
            this.BIRTH,
            this.BC});
            this.dataGridView1.Location = new System.Drawing.Point(43, 65);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(501, 357);
            this.dataGridView1.TabIndex = 0;
            // 
            // MGV
            // 
            this.MGV.HeaderText = "Mã giáo viên";
            this.MGV.MinimumWidth = 6;
            this.MGV.Name = "MGV";
            this.MGV.Width = 90;
            // 
            // TGV
            // 
            this.TGV.HeaderText = "Tên Giáo Viên";
            this.TGV.MinimumWidth = 6;
            this.TGV.Name = "TGV";
            this.TGV.Width = 90;
            // 
            // SEX
            // 
            this.SEX.HeaderText = "Giới Tính";
            this.SEX.MinimumWidth = 6;
            this.SEX.Name = "SEX";
            this.SEX.Width = 90;
            // 
            // THS
            // 
            this.THS.HeaderText = "Lớp Chủ Nhiệm";
            this.THS.MinimumWidth = 6;
            this.THS.Name = "THS";
            this.THS.Width = 90;
            // 
            // NCT
            // 
            this.NCT.HeaderText = "Nơi Cư Trú";
            this.NCT.MinimumWidth = 6;
            this.NCT.Name = "NCT";
            this.NCT.Width = 90;
            // 
            // BIRTH
            // 
            this.BIRTH.HeaderText = "Ngày Sinh";
            this.BIRTH.MinimumWidth = 6;
            this.BIRTH.Name = "BIRTH";
            this.BIRTH.Width = 90;
            // 
            // BC
            // 
            this.BC.HeaderText = "Bằng Cấp";
            this.BC.MinimumWidth = 6;
            this.BC.Name = "BC";
            this.BC.Width = 90;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Times New Roman", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(124, 18);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(224, 25);
            this.label1.TabIndex = 1;
            this.label1.Text = "Danh Sách Giáo Viên";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(619, 65);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(86, 16);
            this.label2.TabIndex = 2;
            this.label2.Text = "Mã Giáo viên";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(619, 111);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(93, 16);
            this.label3.TabIndex = 3;
            this.label3.Text = "Tên Giáo Viên";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(619, 159);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(60, 16);
            this.label4.TabIndex = 4;
            this.label4.Text = "Giới Tính";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(619, 206);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(98, 16);
            this.label5.TabIndex = 5;
            this.label5.Text = "Lớp Chủ Nhiệm";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(619, 255);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(70, 16);
            this.label6.TabIndex = 6;
            this.label6.Text = "Nơi Cư Trú";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(619, 304);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(69, 16);
            this.label7.TabIndex = 7;
            this.label7.Text = "Ngày Sinh";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(619, 352);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(67, 16);
            this.label8.TabIndex = 8;
            this.label8.Text = "Bằng Cấp";
            // 
            // txbMGV
            // 
            this.txbMGV.Location = new System.Drawing.Point(784, 59);
            this.txbMGV.Name = "txbMGV";
            this.txbMGV.Size = new System.Drawing.Size(121, 22);
            this.txbMGV.TabIndex = 9;
            // 
            // txbTGV
            // 
            this.txbTGV.Location = new System.Drawing.Point(784, 105);
            this.txbTGV.Name = "txbTGV";
            this.txbTGV.Size = new System.Drawing.Size(121, 22);
            this.txbTGV.TabIndex = 10;
            // 
            // txbSEX
            // 
            this.txbSEX.Location = new System.Drawing.Point(784, 153);
            this.txbSEX.Name = "txbSEX";
            this.txbSEX.Size = new System.Drawing.Size(121, 22);
            this.txbSEX.TabIndex = 11;
            // 
            // txbLCN
            // 
            this.txbLCN.Location = new System.Drawing.Point(784, 200);
            this.txbLCN.Name = "txbLCN";
            this.txbLCN.Size = new System.Drawing.Size(121, 22);
            this.txbLCN.TabIndex = 12;
            this.txbLCN.TextChanged += new System.EventHandler(this.textBox4_TextChanged);
            // 
            // txbNCT
            // 
            this.txbNCT.Location = new System.Drawing.Point(784, 249);
            this.txbNCT.Name = "txbNCT";
            this.txbNCT.Size = new System.Drawing.Size(121, 22);
            this.txbNCT.TabIndex = 13;
            // 
            // txbBIRTH
            // 
            this.txbBIRTH.Location = new System.Drawing.Point(784, 298);
            this.txbBIRTH.Name = "txbBIRTH";
            this.txbBIRTH.Size = new System.Drawing.Size(121, 22);
            this.txbBIRTH.TabIndex = 14;
            // 
            // cbBC
            // 
            this.cbBC.FormattingEnabled = true;
            this.cbBC.Items.AddRange(new object[] {
            "Hạng 1",
            "Hạng 2",
            "Hạng 3"});
            this.cbBC.Location = new System.Drawing.Point(784, 344);
            this.cbBC.Name = "cbBC";
            this.cbBC.Size = new System.Drawing.Size(121, 24);
            this.cbBC.TabIndex = 15;
            this.cbBC.SelectedIndexChanged += new System.EventHandler(this.cbBC_SelectedIndexChanged);
            // 
            // btnTHEM
            // 
            this.btnTHEM.Location = new System.Drawing.Point(719, 393);
            this.btnTHEM.Name = "btnTHEM";
            this.btnTHEM.Size = new System.Drawing.Size(75, 23);
            this.btnTHEM.TabIndex = 16;
            this.btnTHEM.Text = "Thêm";
            this.btnTHEM.UseVisualStyleBackColor = true;
            // 
            // btnSUA
            // 
            this.btnSUA.Location = new System.Drawing.Point(830, 393);
            this.btnSUA.Name = "btnSUA";
            this.btnSUA.Size = new System.Drawing.Size(75, 23);
            this.btnSUA.TabIndex = 17;
            this.btnSUA.Text = "Sửa";
            this.btnSUA.UseVisualStyleBackColor = true;
            // 
            // btnXOA
            // 
            this.btnXOA.Location = new System.Drawing.Point(719, 433);
            this.btnXOA.Name = "btnXOA";
            this.btnXOA.Size = new System.Drawing.Size(75, 23);
            this.btnXOA.TabIndex = 18;
            this.btnXOA.Text = "Xóa";
            this.btnXOA.UseVisualStyleBackColor = true;
            // 
            // btnLUU
            // 
            this.btnLUU.Location = new System.Drawing.Point(830, 433);
            this.btnLUU.Name = "btnLUU";
            this.btnLUU.Size = new System.Drawing.Size(75, 23);
            this.btnLUU.TabIndex = 19;
            this.btnLUU.Text = "Lưu";
            this.btnLUU.UseVisualStyleBackColor = true;
            // 
            // UC_Checkpoints
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.btnLUU);
            this.Controls.Add(this.btnXOA);
            this.Controls.Add(this.btnSUA);
            this.Controls.Add(this.btnTHEM);
            this.Controls.Add(this.cbBC);
            this.Controls.Add(this.txbBIRTH);
            this.Controls.Add(this.txbNCT);
            this.Controls.Add(this.txbLCN);
            this.Controls.Add(this.txbSEX);
            this.Controls.Add(this.txbTGV);
            this.Controls.Add(this.txbMGV);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridView1);
            this.Name = "UC_Checkpoints";
            this.Size = new System.Drawing.Size(963, 468);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn MGV;
        private System.Windows.Forms.DataGridViewTextBoxColumn TGV;
        private System.Windows.Forms.DataGridViewTextBoxColumn SEX;
        private System.Windows.Forms.DataGridViewTextBoxColumn THS;
        private System.Windows.Forms.DataGridViewTextBoxColumn NCT;
        private System.Windows.Forms.DataGridViewTextBoxColumn BIRTH;
        private System.Windows.Forms.DataGridViewTextBoxColumn BC;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txbMGV;
        private System.Windows.Forms.TextBox txbTGV;
        private System.Windows.Forms.TextBox txbSEX;
        private System.Windows.Forms.TextBox txbLCN;
        private System.Windows.Forms.TextBox txbNCT;
        private System.Windows.Forms.TextBox txbBIRTH;
        private System.Windows.Forms.ComboBox cbBC;
        private System.Windows.Forms.Button btnTHEM;
        private System.Windows.Forms.Button btnSUA;
        private System.Windows.Forms.Button btnXOA;
        private System.Windows.Forms.Button btnLUU;
    }
}
