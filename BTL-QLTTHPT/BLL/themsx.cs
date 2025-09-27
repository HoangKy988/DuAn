using System;
using DAL;
using DTO;

namespace BLL
{
    public class HocSinhBLL
    {
        private HocSinhDAL hocSinhDAL = new HocSinhDAL();

        public bool AddHocSinh(HocSinh hocSinh)
        {
            // Thực hiện các kiểm tra nghiệp vụ nếu cần
            return hocSinhDAL.InsertHocSinh(hocSinh);
        }

        public bool UpdateHocSinh(HocSinh hocSinh)
        {
            // Thực hiện các kiểm tra nghiệp vụ nếu cần
            return hocSinhDAL.UpdateHocSinh(hocSinh);
        }

        public bool DeleteHocSinh(string maHS)
        {
            // Thực hiện các kiểm tra nghiệp vụ nếu cần
            return hocSinhDAL.DeleteHocSinh(maHS);
        }
    }
}
