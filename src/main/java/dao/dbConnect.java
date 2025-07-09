/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=swdproject;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa123";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Đăng ký driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Tạo kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
        return conn;
    }

    // Hàm kiểm tra kết nối
    public static boolean isConnectionValid() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Kiểm tra kết nối thành công!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Kiểm tra kết nối thất bại: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        // Kiểm tra kết nối
        if (isConnectionValid()) {
            System.out.println("Kết nối đến SQL Server hoạt động tốt!");
        } else {
            System.out.println("Kết nối không thành công!");
        }
    }
}