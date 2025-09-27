using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;


namespace DTO
{
    public class Taikhoan
    {
        public string sMaTK {  get; set; }
        public string sTaiKhoan { get; set; }

        public string sMatKhau { get; set; }
        public string FK_iMaQuyen { get; set; }
    }
}
