namespace GUI.AllControls
{
    partial class UC_QLCT
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
            this.label1 = new System.Windows.Forms.Label();
            this.tabHS = new System.Windows.Forms.TabControl();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.btnLUU = new System.Windows.Forms.Button();
            this.btnXOA = new System.Windows.Forms.Button();
            this.btnSUA = new System.Windows.Forms.Button();
            this.btnTHEM = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.TK = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STN = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STDN = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STHT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STTT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.STT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.KL = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STPT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STDT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STHT1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.STTT1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tabHS.SuspendLayout();
            this.tabPage2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Times New Roman", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(35, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(128, 25);
            this.label1.TabIndex = 41;
            this.label1.Text = "Chi Và Thu";
            // 
            // tabHS
            // 
            this.tabHS.Controls.Add(this.tabPage2);
            this.tabHS.Controls.Add(this.tabPage1);
            this.tabHS.Location = new System.Drawing.Point(36, 73);
            this.tabHS.Name = "tabHS";
            this.tabHS.SelectedIndex = 0;
            this.tabHS.Size = new System.Drawing.Size(882, 348);
            this.tabHS.TabIndex = 42;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.btnLUU);
            this.tabPage2.Controls.Add(this.btnXOA);
            this.tabPage2.Controls.Add(this.btnSUA);
            this.tabPage2.Controls.Add(this.btnTHEM);
            this.tabPage2.Controls.Add(this.dataGridView1);
            this.tabPage2.Location = new System.Drawing.Point(4, 25);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(874, 319);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Học Sinh";
            this.tabPage2.UseVisualStyleBackColor = true;
            this.tabPage2.Click += new System.EventHandler(this.tabPage2_Click);
            // 
            // btnLUU
            // 
            this.btnLUU.Location = new System.Drawing.Point(751, 170);
            this.btnLUU.Name = "btnLUU";
            this.btnLUU.Size = new System.Drawing.Size(75, 23);
            this.btnLUU.TabIndex = 23;
            this.btnLUU.Text = "Lưu";
            this.btnLUU.UseVisualStyleBackColor = true;
            // 
            // btnXOA
            // 
            this.btnXOA.Location = new System.Drawing.Point(751, 225);
            this.btnXOA.Name = "btnXOA";
            this.btnXOA.Size = new System.Drawing.Size(75, 23);
            this.btnXOA.TabIndex = 22;
            this.btnXOA.Text = "Xóa";
            this.btnXOA.UseVisualStyleBackColor = true;
            // 
            // btnSUA
            // 
            this.btnSUA.Location = new System.Drawing.Point(751, 112);
            this.btnSUA.Name = "btnSUA";
            this.btnSUA.Size = new System.Drawing.Size(75, 23);
            this.btnSUA.TabIndex = 21;
            this.btnSUA.Text = "Sửa";
            this.btnSUA.UseVisualStyleBackColor = true;
            // 
            // btnTHEM
            // 
            this.btnTHEM.Location = new System.Drawing.Point(751, 65);
            this.btnTHEM.Name = "btnTHEM";
            this.btnTHEM.Size = new System.Drawing.Size(75, 23);
            this.btnTHEM.TabIndex = 20;
            this.btnTHEM.Text = "Thêm";
            this.btnTHEM.UseVisualStyleBackColor = true;
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.TK,
            this.STN,
            this.STDN,
            this.STHT,
            this.STTT});
            this.dataGridView1.Location = new System.Drawing.Point(22, 21);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(678, 259);
            this.dataGridView1.TabIndex = 0;
            // 
            // TK
            // 
            this.TK.HeaderText = "Khoản Thu";
            this.TK.MinimumWidth = 6;
            this.TK.Name = "TK";
            this.TK.Width = 125;
            // 
            // STN
            // 
            this.STN.HeaderText = "Số Tiền Phải Nộp";
            this.STN.MinimumWidth = 6;
            this.STN.Name = "STN";
            this.STN.Width = 125;
            // 
            // STDN
            // 
            this.STDN.HeaderText = "Số Tiền Đã Nộp";
            this.STDN.MinimumWidth = 6;
            this.STDN.Name = "STDN";
            this.STDN.Width = 125;
            // 
            // STHT
            // 
            this.STHT.HeaderText = "Số Tiền Hoàn Trả";
            this.STHT.MinimumWidth = 6;
            this.STHT.Name = "STHT";
            this.STHT.Width = 125;
            // 
            // STTT
            // 
            this.STTT.HeaderText = "Số Tiền Thiếu Thừa";
            this.STTT.MinimumWidth = 6;
            this.STTT.Name = "STTT";
            this.STTT.Width = 125;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.button1);
            this.tabPage1.Controls.Add(this.button2);
            this.tabPage1.Controls.Add(this.button3);
            this.tabPage1.Controls.Add(this.button4);
            this.tabPage1.Controls.Add(this.dataGridView2);
            this.tabPage1.Location = new System.Drawing.Point(4, 25);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(874, 319);
            this.tabPage1.TabIndex = 2;
            this.tabPage1.Text = "Giáo Viên";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(768, 181);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 28;
            this.button1.Text = "Lưu";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(768, 236);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 27;
            this.button2.Text = "Xóa";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(768, 123);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 26;
            this.button3.Text = "Sửa";
            this.button3.UseVisualStyleBackColor = true;
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(768, 76);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 25;
            this.button4.Text = "Thêm";
            this.button4.UseVisualStyleBackColor = true;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.STT,
            this.KL,
            this.STPT,
            this.STDT,
            this.STHT1,
            this.STTT1});
            this.dataGridView2.Location = new System.Drawing.Point(22, 21);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowHeadersWidth = 51;
            this.dataGridView2.RowTemplate.Height = 24;
            this.dataGridView2.Size = new System.Drawing.Size(678, 259);
            this.dataGridView2.TabIndex = 24;
            // 
            // STT
            // 
            this.STT.HeaderText = "STT";
            this.STT.MinimumWidth = 6;
            this.STT.Name = "STT";
            this.STT.Width = 125;
            // 
            // KL
            // 
            this.KL.HeaderText = "Khoản Lương";
            this.KL.MinimumWidth = 6;
            this.KL.Name = "KL";
            this.KL.Width = 125;
            // 
            // STPT
            // 
            this.STPT.HeaderText = "Số Tiền Phải Trả";
            this.STPT.MinimumWidth = 6;
            this.STPT.Name = "STPT";
            this.STPT.Width = 125;
            // 
            // STDT
            // 
            this.STDT.HeaderText = "Số Tiền Đã Trả";
            this.STDT.MinimumWidth = 6;
            this.STDT.Name = "STDT";
            this.STDT.Width = 125;
            // 
            // STHT1
            // 
            this.STHT1.HeaderText = "Số Tiền Hoàn Trả";
            this.STHT1.MinimumWidth = 6;
            this.STHT1.Name = "STHT1";
            this.STHT1.Width = 125;
            // 
            // STTT1
            // 
            this.STTT1.HeaderText = "Số Tiền Thiếu Thừa";
            this.STTT1.MinimumWidth = 6;
            this.STTT1.Name = "STTT1";
            this.STTT1.Width = 125;
            // 
            // UC_QLCT
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.tabHS);
            this.Controls.Add(this.label1);
            this.Name = "UC_QLCT";
            this.Size = new System.Drawing.Size(963, 468);
            this.Load += new System.EventHandler(this.UC_QLCT_Load);
            this.tabHS.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.tabPage1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TabControl tabHS;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn TK;
        private System.Windows.Forms.DataGridViewTextBoxColumn STN;
        private System.Windows.Forms.DataGridViewTextBoxColumn STDN;
        private System.Windows.Forms.DataGridViewTextBoxColumn STHT;
        private System.Windows.Forms.DataGridViewTextBoxColumn STTT;
        private System.Windows.Forms.Button btnLUU;
        private System.Windows.Forms.Button btnXOA;
        private System.Windows.Forms.Button btnSUA;
        private System.Windows.Forms.Button btnTHEM;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.DataGridViewTextBoxColumn STT;
        private System.Windows.Forms.DataGridViewTextBoxColumn KL;
        private System.Windows.Forms.DataGridViewTextBoxColumn STPT;
        private System.Windows.Forms.DataGridViewTextBoxColumn STDT;
        private System.Windows.Forms.DataGridViewTextBoxColumn STHT1;
        private System.Windows.Forms.DataGridViewTextBoxColumn STTT1;
    }
}
