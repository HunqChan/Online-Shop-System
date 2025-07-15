package controller;

import dao.OrderDAO;
import model.Order;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/vnpay_return")
public class VNPayReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderNumber = request.getParameter("vnp_TxnRef");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String responseCode = request.getParameter("vnp_ResponseCode");

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderByNumber(orderNumber);

        if (order == null) {
            response.sendRedirect("jsp/error.jsp?message=Không tìm thấy đơn hàng");
            return;
        }

        if ("00".equals(responseCode)) {
            order.setPaymentStatus("PAID");
            order.setPaymentTransactionId(transactionNo);
            order.setStatus("CONFIRMED");
        } else {
            order.setPaymentStatus("FAILED");
            order.setStatus("CANCELLED");
        }

        order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        orderDAO.updateOrder(order);

        response.sendRedirect("jsp/success.jsp?order=" + order.getOrderNumber());
    }
}
