using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using DTO;
using BLL;

namespace GUI
{
    public partial class DANGNHAP : Form
    {
        Taikhoan taikhoan = new Taikhoan();
        TaiKhoanBLL TKBLL = new TaiKhoanBLL();
        public DANGNHAP()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            taikhoan.sMaTK = txtTK.Text;
            taikhoan.sMatKhau = txtMK.Text;

            string getuser = TKBLL.Checklogin(taikhoan);

            switch (getuser)
            {
                case "requeid_taikhoan":
                    MessageBox.Show("Tài khoản hông được để trống");
                    return;
                case "requeid_matkhau":
                    MessageBox.Show("Mật khẩu hông được để trống");
                    return;
                case "Tài khoảng hoặc mật khẩu không chính xác!":
                    MessageBox.Show("Tài khoảng hoặc mật khẩu không chính xác!");
                    return;
            }
            MessageBox.Show("Đăng nhập thành công"); 
            GiaoDien giaoDien = new GiaoDien();
            giaoDien.ShowDialog();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            DialogResult dg = MessageBox.Show("Bạn có muốn thoát ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (dg == DialogResult.Yes)
                Application.Exit();
        }

        private void groupBox1_Enter(object sender, EventArgs e)
        {

        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click_1(object sender, EventArgs e)
        {
               


        }
    }
}
