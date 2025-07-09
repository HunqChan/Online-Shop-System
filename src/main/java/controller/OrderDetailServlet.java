
package controller;

import dao.OrderDAO;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.OrderItem;
import java.io.IOException;
import java.util.List;

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderId = Long.parseLong(request.getParameter("id"));
        
        Order order = orderDAO.getOrderById(orderId);
        List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);

        if (order != null) {
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.getRequestDispatcher("/jsp/orderDetail.jsp").forward(request, response);
        } 
	else {
            response.sendRedirect("/error.jsp");
        }
    }
}