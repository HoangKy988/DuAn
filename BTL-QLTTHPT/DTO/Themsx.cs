using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;


namespace DTO
{
    public class HocSinh
    {
        public string MaHS { get; set; }
        public string TenHS { get; set; }
        public string GioiTinh { get; set; }
        public string Lop { get; set; }
        public string DiaChi { get; set; }
        public string NgaySinh { get; set; }
        public string LienHe { get; set; }
        public string GhiChu { get; set; }
    }
}

