using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DTO;
using DAL;

namespace BLL
{
    public class TaiKhoanBLL
    {
        taikhoanAcess taikhoanAcess = new taikhoanAcess();
        public string Checklogin(Taikhoan taikhoan) 
        {
            if(taikhoan.sTaiKhoan== "")
            {
                return "requeid_taikhoan";
            }
            if(taikhoan.sMatKhau=="")
            {
                return "requeid_matkhau";
            }

            string info= taikhoanAcess.Checklogin(taikhoan);
            return info;
        } 
    }
}
