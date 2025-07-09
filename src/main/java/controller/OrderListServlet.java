package controller;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Order;

@WebServlet("/orderList")
public class OrderListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo OrderDAO để lấy danh sách đơn hàng
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();

        // Đưa danh sách đơn hàng vào request attribute
        request.setAttribute("orderList", orderList);

        // Chuyển hướng đến trang JSP
        request.getRequestDispatcher("/jsp/orderList.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Phương thức POST có thể được mở rộng nếu cần thêm chức năng (ví dụ: lọc, tìm kiếm)
        // Hiện tại, chuyển hướng lại đến doGet
        doGet(request, response);
    }
}