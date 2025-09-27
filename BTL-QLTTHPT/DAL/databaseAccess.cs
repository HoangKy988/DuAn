using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DTO;
using System.Data;

namespace DAL
{
    public class SqlConnectionData
    {
        public static SqlConnection Connect()
        {
            string strcon = @"Data Source=LAPTOP-LFG807CT\SQLEXPRESS01;Initial Catalog=QLHS;Integrated Security=True;TrustServerCertificate=True";
            SqlConnection conn = new SqlConnection(strcon);
            return conn;
        }
    }

    public class databaseAccess
    {
        public static string CheckLoginDTO(Taikhoan taikhoan)
        {
            string user = null;

            if (string.IsNullOrEmpty(taikhoan.sTaiKhoan))
            {
                return "Tên đăng nhập không được để trống.";
            }

            if (string.IsNullOrEmpty(taikhoan.sMatKhau))
            {
                return "Mật khẩu không được để trống.";
            }

            try
            {
                using (SqlConnection connection = SqlConnectionData.Connect())
                {
                    connection.Open();

                    using (SqlCommand command = new SqlCommand("proc_login", connection))
                    {
                        command.CommandType = CommandType.StoredProcedure;
                        command.Parameters.AddWithValue("@user", taikhoan.sTaiKhoan);
                        command.Parameters.AddWithValue("@pass", taikhoan.sMatKhau);

                        using (SqlDataReader reader = command.ExecuteReader())
                        {
                            if (reader.HasRows)
                            {
                                while (reader.Read())
                                {
                                    user = reader.GetString(0);
                                }
                            }
                            else
                            {
                                return "Tài khoản hoặc mật khẩu không chính xác!";
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return $"Đã xảy ra lỗi: {ex.Message}";
            }

            return user;
        
        }
    }
    public static List<KhoanThuHS> GetKhoanThuHocSinhData()
    {
        List<KhoanThuHS> listKhoanThuHS = new List<KhoanThuHS>();

        try
        {
            using (SqlConnection connection = SqlConnectionData.Connect())
            {
                connection.Open();

                string query = "SELECT MaHocSinh, TenHocSinh, KhoanThu, SoTienPhaiNop, SoTienDaNop, SoTienHoanTra, (SoTienDaNop - SoTienPhaiNop + SoTienHoanTra) AS SoTienThuaThieu FROM KhoanThuHocSinh";
                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            KhoanThuHS khoanThuHS = new KhoanThuHS
                            {
                                MaHocSinh = reader.GetInt32(0),
                                TenHocSinh = reader.GetString(1),
                                KhoanThu = reader.GetString(2),
                                SoTienPhaiNop = reader.GetDecimal(3).ToString("F2"),
                                SoTienDaNop = reader.GetDecimal(4).ToString("F2"),
                                SoTienHoanTra = reader.GetDecimal(5).ToString("F2"),
                                SoTienThuaThieu = reader.GetDecimal(6).ToString("F2")
                            };
                            listKhoanThuHS.Add(khoanThuHS);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            // Handle or log the error
            Console.WriteLine($"Đã xảy ra lỗi: {ex.Message}");
        }

        return listKhoanThuHS;
    }
}

