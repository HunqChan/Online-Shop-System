package controller;

import dao.OrderDAO;
import model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import proxy.VNPayHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/payment")
public class VNPayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String paymentMethod = request.getParameter("paymentMethod");
        String shippingAddress = request.getParameter("shippingAddress");
        double subtotal = Double.parseDouble(request.getParameter("subtotal"));
        double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
        double total = Double.parseDouble(request.getParameter("total"));

        // Tạo đơn hàng (lưu vào DB)
        Order order = new Order();
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        order.setOrderNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("PENDING");
        order.setStatus("PENDING");
        order.setShippingAddress(shippingAddress);
        order.setShippingFee(shippingFee);
        order.setSubtotal(subtotal);
        order.setTotal(total);

        OrderDAO orderDAO = new OrderDAO();
        boolean added = orderDAO.addOrder(order);

        if (!added) {
            request.setAttribute("error", "Không thể tạo đơn hàng.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            return;
        }

        // Nếu là VNPAY thì chuyển hướng tới VNPay sandbox
        if ("VNPAY".equals(paymentMethod)) {
            String paymentUrl = VNPayHelper.createPaymentUrl(order, request);
            response.sendRedirect(paymentUrl);
        } else {
            // Thẻ Ngân Hàng - xử lý tại chỗ (ví dụ demo)
            response.sendRedirect("jsp/success.jsp");
        }
    }
}
