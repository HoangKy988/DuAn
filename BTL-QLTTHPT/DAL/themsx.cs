using DTO;
using System;
using System.Data;
using System.Data.SqlClient;
namespace DAL
{
    public class DatabaseAccess
    {
        protected string connectionString = "Data Source=NGUYENTHUAN;Initial Catalog=Quan_Ly_Truong_THPT3;Integrated Security=True";

        public DataTable ExecuteQuery(string query, SqlParameter[] parameters = null)
        {
            DataTable dt = new DataTable();
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                using (SqlCommand cmd = new SqlCommand(query, conn))
                {
                    if (parameters != null)
                    {
                        cmd.Parameters.AddRange(parameters);
                    }
                    SqlDataAdapter da = new SqlDataAdapter(cmd);
                    conn.Open();
                    da.Fill(dt);
                }
            }
            return dt;
        }

        public int ExecuteNonQuery(string query, SqlParameter[] parameters = null)
        {
            int result;
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                using (SqlCommand cmd = new SqlCommand(query, conn))
                {
                    if (parameters != null)
                    {
                        cmd.Parameters.AddRange(parameters);
                    }
                    conn.Open();
                    result = cmd.ExecuteNonQuery();
                }
            }
            return result;
        }
    }

    public class HocSinhDAL : DatabaseAccess
    {
        public bool InsertHocSinh(HocSinh hocSinh)
        {
            string query = "INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, Lop, DiaChi, NgaySinh, LienHe, GhiChu) VALUES (@MaHS, @TenHS, @GioiTinh, @Lop, @DiaChi, @NgaySinh, @LienHe, @GhiChu)";
            SqlParameter[] parameters = {
                new SqlParameter("@MaHS", hocSinh.MaHS),
                new SqlParameter("@TenHS", hocSinh.TenHS),
                new SqlParameter("@GioiTinh", hocSinh.GioiTinh),
                new SqlParameter("@Lop", hocSinh.Lop),
                new SqlParameter("@DiaChi", hocSinh.DiaChi),
                new SqlParameter("@NgaySinh", hocSinh.NgaySinh),
                new SqlParameter("@LienHe", hocSinh.LienHe),
                new SqlParameter("@GhiChu", hocSinh.GhiChu)
            };
            return ExecuteNonQuery(query, parameters) > 0;
        }

        // Tương tự, bạn có thể thêm các phương thức Update và Delete
        public bool UpdateHocSinh(HocSinh hocSinh)
        {
            string query = "UPDATE HocSinh SET TenHS = @TenHS, GioiTinh = @GioiTinh, Lop = @Lop, DiaChi = @DiaChi, NgaySinh = @NgaySinh, LienHe = @LienHe, GhiChu = @GhiChu WHERE MaHS = @MaHS";
            SqlParameter[] parameters = {
                new SqlParameter("@MaHS", hocSinh.MaHS),
                new SqlParameter("@TenHS", hocSinh.TenHS),
                new SqlParameter("@GioiTinh", hocSinh.GioiTinh),
                new SqlParameter("@Lop", hocSinh.Lop),
                new SqlParameter("@DiaChi", hocSinh.DiaChi),
                new SqlParameter("@NgaySinh", hocSinh.NgaySinh),
                new SqlParameter("@LienHe", hocSinh.LienHe),
                new SqlParameter("@GhiChu", hocSinh.GhiChu)
            };
            return ExecuteNonQuery(query, parameters) > 0;
        }

        public bool DeleteHocSinh(string maHS)
        {
            string query = "DELETE FROM HocSinh WHERE MaHS = @MaHS";
            SqlParameter[] parameters = {
        new SqlParameter("@MaHS", maHS)
    };
            return ExecuteNonQuery(query, parameters) > 0;
        }
    }
}
