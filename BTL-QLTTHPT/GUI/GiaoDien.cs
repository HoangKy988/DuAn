using GUI.AllControls;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GUI
{
    public partial class GiaoDien : Form
    {
        public GiaoDien()
        {
            InitializeComponent();
        }

        private void GiaoDien_Load(object sender, EventArgs e)
        {
            uC_Checkpoints1.Visible = false;
            btnQLGV.PerformClick();  
            uC_QLHS1.Visible = false;
            uC_QLLH1.Visible = false;
            uC_QLCT1.Visible = false;
        }

        private void bttDX_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void bttThoat_Click(object sender, EventArgs e)
        {
            DialogResult dg = MessageBox.Show("Bạn có muốn thoát ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (dg == DialogResult.Yes)
                Application.Exit();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            uC_Checkpoints1.Visible = true;
            uC_Checkpoints1.BringToFront();
        }

        private void panel2_Paint(object sender, PaintEventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            uC_QLHS1.Visible = true;
            uC_QLHS1.BringToFront();
        }

        private void btnQLLH_Click(object sender, EventArgs e)
        {
            uC_QLLH1.Visible = true;
            uC_QLLH1.BringToFront();
        }

        private void btnQLCT_Click(object sender, EventArgs e)
        {
            uC_QLCT1.Visible = true;
            uC_QLCT1.BringToFront();
        }
    }
}
