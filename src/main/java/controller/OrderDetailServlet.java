/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Order;

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID đơn hàng từ request
        String idStr = request.getParameter("id");
        if (idStr != null) {
            long id = Long.parseLong(idStr);
            
            // Lấy thông tin đơn hàng từ DAO (giả định chỉ lấy một đơn hàng, cần mở rộng logic)
            OrderDAO orderDAO = new OrderDAO();
            Order order = null; // Cần triển khai logic lấy order cụ thể
            // Ví dụ: order = orderDAO.getOrderById(id); (chưa có phương thức này)
            
            // Đưa đơn hàng vào request
            request.setAttribute("order", order);
            // Đưa danh sách order items vào request (cần mở rộng sau)
            request.setAttribute("orderItems", null); // Giả định tạm thời
            
            // Chuyển hướng đến trang orderDetail.jsp
            request.getRequestDispatcher("jsp/orderDetail.jsp").forward(request, response);
        } else {
            response.sendRedirect("orderList");
        }
    }
}