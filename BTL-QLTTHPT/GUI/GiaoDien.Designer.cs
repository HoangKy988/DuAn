namespace GUI
{
    partial class GiaoDien
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
            this.bttDX = new System.Windows.Forms.Button();
            this.bttThoat = new System.Windows.Forms.Button();
            this.btnQLGV = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnQLCT = new System.Windows.Forms.Button();
            this.btnQLLH = new System.Windows.Forms.Button();
            this.btnQLHS = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.uC_QLCT1 = new GUI.AllControls.UC_QLCT();
            this.uC_QLLH1 = new GUI.AllControls.UC_QLLH();
            this.uC_QLHS1 = new GUI.AllControls.UC_QLHS();
            this.uC_Checkpoints1 = new GUI.AllControls.UC_Checkpoints();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // bttDX
            // 
            this.bttDX.Location = new System.Drawing.Point(837, 52);
            this.bttDX.Name = "bttDX";
            this.bttDX.Size = new System.Drawing.Size(105, 31);
            this.bttDX.TabIndex = 0;
            this.bttDX.Text = "Đăng xuất";
            this.bttDX.UseVisualStyleBackColor = true;
            this.bttDX.Click += new System.EventHandler(this.bttDX_Click);
            // 
            // bttThoat
            // 
            this.bttThoat.Location = new System.Drawing.Point(837, 12);
            this.bttThoat.Name = "bttThoat";
            this.bttThoat.Size = new System.Drawing.Size(105, 30);
            this.bttThoat.TabIndex = 1;
            this.bttThoat.Text = "Thoát";
            this.bttThoat.UseVisualStyleBackColor = true;
            this.bttThoat.Click += new System.EventHandler(this.bttThoat_Click);
            // 
            // btnQLGV
            // 
            this.btnQLGV.Location = new System.Drawing.Point(26, 24);
            this.btnQLGV.Name = "btnQLGV";
            this.btnQLGV.Size = new System.Drawing.Size(121, 56);
            this.btnQLGV.TabIndex = 2;
            this.btnQLGV.Text = "Quản lý giáo viên";
            this.btnQLGV.UseVisualStyleBackColor = true;
            this.btnQLGV.Click += new System.EventHandler(this.button1_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ControlDark;
            this.panel1.Controls.Add(this.btnQLCT);
            this.panel1.Controls.Add(this.btnQLLH);
            this.panel1.Controls.Add(this.btnQLHS);
            this.panel1.Controls.Add(this.btnQLGV);
            this.panel1.Controls.Add(this.bttThoat);
            this.panel1.Controls.Add(this.bttDX);
            this.panel1.Location = new System.Drawing.Point(12, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(963, 95);
            this.panel1.TabIndex = 3;
            // 
            // btnQLCT
            // 
            this.btnQLCT.Location = new System.Drawing.Point(649, 24);
            this.btnQLCT.Name = "btnQLCT";
            this.btnQLCT.Size = new System.Drawing.Size(101, 56);
            this.btnQLCT.TabIndex = 5;
            this.btnQLCT.Text = "Quản lý chi và thu";
            this.btnQLCT.UseVisualStyleBackColor = true;
            this.btnQLCT.Click += new System.EventHandler(this.btnQLCT_Click);
            // 
            // btnQLLH
            // 
            this.btnQLLH.Location = new System.Drawing.Point(441, 24);
            this.btnQLLH.Name = "btnQLLH";
            this.btnQLLH.Size = new System.Drawing.Size(116, 56);
            this.btnQLLH.TabIndex = 4;
            this.btnQLLH.Text = "Quản lý thời khóa biểu";
            this.btnQLLH.UseVisualStyleBackColor = true;
            this.btnQLLH.Click += new System.EventHandler(this.btnQLLH_Click);
            // 
            // btnQLHS
            // 
            this.btnQLHS.Location = new System.Drawing.Point(224, 21);
            this.btnQLHS.Name = "btnQLHS";
            this.btnQLHS.Size = new System.Drawing.Size(121, 59);
            this.btnQLHS.TabIndex = 3;
            this.btnQLHS.Text = "Quản lý học sinh";
            this.btnQLHS.UseVisualStyleBackColor = true;
            this.btnQLHS.Click += new System.EventHandler(this.button2_Click);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.uC_QLCT1);
            this.panel2.Controls.Add(this.uC_QLLH1);
            this.panel2.Controls.Add(this.uC_QLHS1);
            this.panel2.Controls.Add(this.uC_Checkpoints1);
            this.panel2.Location = new System.Drawing.Point(12, 104);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(963, 468);
            this.panel2.TabIndex = 4;
            this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
            // 
            // uC_QLCT1
            // 
            this.uC_QLCT1.Location = new System.Drawing.Point(3, 0);
            this.uC_QLCT1.Name = "uC_QLCT1";
            this.uC_QLCT1.Size = new System.Drawing.Size(963, 468);
            this.uC_QLCT1.TabIndex = 3;
            // 
            // uC_QLLH1
            // 
            this.uC_QLLH1.Location = new System.Drawing.Point(3, 3);
            this.uC_QLLH1.Name = "uC_QLLH1";
            this.uC_QLLH1.Size = new System.Drawing.Size(963, 468);
            this.uC_QLLH1.TabIndex = 2;
            // 
            // uC_QLHS1
            // 
            this.uC_QLHS1.Location = new System.Drawing.Point(-3, 3);
            this.uC_QLHS1.Name = "uC_QLHS1";
            this.uC_QLHS1.Size = new System.Drawing.Size(963, 468);
            this.uC_QLHS1.TabIndex = 1;
            // 
            // uC_Checkpoints1
            // 
            this.uC_Checkpoints1.Location = new System.Drawing.Point(0, 0);
            this.uC_Checkpoints1.Name = "uC_Checkpoints1";
            this.uC_Checkpoints1.Size = new System.Drawing.Size(963, 468);
            this.uC_Checkpoints1.TabIndex = 0;
            // 
            // GiaoDien
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.ClientSize = new System.Drawing.Size(990, 584);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.ForeColor = System.Drawing.SystemColors.ControlText;
            this.Name = "GiaoDien";
            this.Text = "GiaoDien";
            this.Load += new System.EventHandler(this.GiaoDien_Load);
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button bttDX;
        private System.Windows.Forms.Button bttThoat;
        private System.Windows.Forms.Button btnQLGV;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btnQLHS;
        private System.Windows.Forms.Panel panel2;
        private AllControls.UC_Checkpoints uC_Checkpoints1;
        private AllControls.UC_QLHS uC_QLHS1;
        private System.Windows.Forms.Button btnQLLH;
        private AllControls.UC_QLLH uC_QLLH1;
        private System.Windows.Forms.Button btnQLCT;
        private AllControls.UC_QLCT uC_QLCT1;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
    }
}