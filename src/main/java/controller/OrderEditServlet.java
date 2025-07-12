package controller;

import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.UserDAO;
import dao.dbConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.OrderItem;
import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

@WebServlet("/orderEdit")
public class OrderEditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int orderId = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = dbConnect.getConnection()) {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            UserDAO userDAO = new UserDAO();

            Order order = orderDAO.getOrderById(orderId);
            User user = userDAO.getUserById((order.getUserId()));
            List<OrderItem> items = orderItemDAO.getItemsByOrderId(orderId);

            request.setAttribute("order", order);
            request.setAttribute("user", user);
            request.setAttribute("items", items);

            request.getRequestDispatcher("/jsp/orderEdit.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try (Connection conn = dbConnect.getConnection()) {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();

            Order order = orderDAO.getOrderById(orderId);
            order.setShippingAddress(request.getParameter("shippingAddress"));
            order.setStatus(request.getParameter("status"));
            order.setNote(request.getParameter("note"));
            order.setShippingMethod(request.getParameter("shippingMethod"));
            order.setTrackingNumber(request.getParameter("trackingNumber"));
            order.setShippingStatus(request.getParameter("shippingStatus"));

            orderDAO.updateOrder(order);

            // cập nhật các order item
            String[] itemIds = request.getParameterValues("itemId");
            String[] quantities = request.getParameterValues("quantity");
            String[] prices = request.getParameterValues("price");

            if (itemIds != null) {
                for (int i = 0; i < itemIds.length; i++) {
                    OrderItem item = new OrderItem();
                    item.setId(Integer.parseInt(itemIds[i]));
                    item.setQuantity(Integer.parseInt(quantities[i]));
                    item.setPrice(Double.parseDouble(prices[i]));
                    orderItemDAO.updateOrderItem(item);
                }
            }

            response.sendRedirect("orderList"); // redirect về list
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
