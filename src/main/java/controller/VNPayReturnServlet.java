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

@WebServlet("/vnpayReturn")
public class VNPayReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_PayDate = request.getParameter("vnp_PayDate");

        System.out.println("vnp_ResponseCode: " + vnp_ResponseCode);
        System.out.println("vnp_TxnRef: " + vnp_TxnRef);
        System.out.println("vnp_TransactionNo: " + vnp_TransactionNo);
        System.out.println("vnp_PayDate: " + vnp_PayDate);

        if ("00".equals(vnp_ResponseCode)) {
            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.getAllOrders().stream()
                    .filter(o -> o.getOrderNumber().equals(vnp_TxnRef))
                    .findFirst()
                    .orElse(null);
            if (order != null) {
                order.setPaymentStatus("SUCCESS");
                order.setPaymentTransactionId(vnp_TransactionNo);
                order.setStatus("PROCESSING");
                order.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                orderDAO.addOrder(order); // Cập nhật lại order
                response.sendRedirect(request.getContextPath() + "/orderConfirmation?orderId=" + order.getId());
            } else {
                System.out.println("Order not found for vnp_TxnRef: " + vnp_TxnRef);
                response.sendRedirect(request.getContextPath() + "/cart?error=Order not found");
            }
        } else {
            System.out.println("Payment failed with response code: " + vnp_ResponseCode);
            response.sendRedirect(request.getContextPath() + "/cart?error=Payment failed");
        }
    }
}