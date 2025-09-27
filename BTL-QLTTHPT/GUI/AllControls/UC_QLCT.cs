using DTO;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DTO;


namespace GUI.AllControls
{
    public partial class UC_QLCT : UserControl
    {
        
        

        public UC_QLCT()
        {
            InitializeComponent();
            LoadData();
        }

        private void tabPage2_Click(object sender, EventArgs e)
        {

        }

        private void UC_QLCT_Load(object sender, EventArgs e)
        {

        }
        private void LoadData()
        {
            // Lấy dữ liệu từ databaseAccess
            List<KhoanThuHS> listKhoanThuHS = databaseAccess.GetKhoanThuHocSinhData();

            // Gán dữ liệu cho DataGridView
            dataGridView1.DataSource = listKhoanThuHS;
        }
    }
}
