package controller;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet("/vnpayReturn")
public class VNPayReturnServlet extends HttpServlet {
    
    private static final String VNP_HASH_SECRET = "9S4WHMNDYXVZBFDE76T90A3U12ZKYV06";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy các tham số từ VNPay
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_PayDate = request.getParameter("vnp_PayDate");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        System.out.println("=== VNPay Return Parameters ===");
        System.out.println("vnp_ResponseCode: " + vnp_ResponseCode);
        System.out.println("vnp_TxnRef: " + vnp_TxnRef);
        System.out.println("vnp_TransactionNo: " + vnp_TransactionNo);
        System.out.println("vnp_PayDate: " + vnp_PayDate);

        // Kiểm tra tham số bắt buộc
        if (vnp_SecureHash == null || vnp_SecureHash.isEmpty()) {
            System.out.println("Missing vnp_SecureHash parameter");
            response.sendRedirect(request.getContextPath() + "/cart?error=Missing secure hash");
            return;
        }
        if (vnp_ResponseCode == null || vnp_ResponseCode.isEmpty()) {
            System.out.println("Missing vnp_ResponseCode parameter");
            response.sendRedirect(request.getContextPath() + "/cart?error=Missing response code");
            return;
        }
        if (vnp_TxnRef == null || vnp_TxnRef.isEmpty()) {
            System.out.println("Missing vnp_TxnRef parameter");
            response.sendRedirect(request.getContextPath() + "/cart?error=Missing transaction reference");
            return;
        }

        // Xác thực chữ ký
        if (!verifySignature(request)) {
            System.out.println("Invalid signature!");
            response.sendRedirect(request.getContextPath() + "/cart?error=Invalid signature");
            return;
        }
        
        // Xử lý kết quả thanh toán
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderByNumber(vnp_TxnRef);
        
        System.out.println("Looking for order: " + vnp_TxnRef);
        System.out.println("Found order: " + (order != null ? order.getOrderNumber() : "NULL"));
        
        if (order == null) {
            System.out.println("✗ Order not found for vnp_TxnRef: " + vnp_TxnRef);
            System.out.println("=== All Recent Orders ===");
            orderDAO.debugPrintAllOrders();
            response.sendRedirect(request.getContextPath() + "/cart?error=Order not found");
            return;
        }

        System.out.println("✓ Order found: " + order.getOrderNumber());
        System.out.println("✓ Current status: " + order.getStatus());
        System.out.println("✓ Current payment status: " + order.getPaymentStatus());

        // Kiểm tra trạng thái PENDING
        if (!"PENDING".equals(order.getPaymentStatus())) {
            System.out.println("⚠ Order is not in PENDING state: " + order.getPaymentStatus());
            response.sendRedirect(request.getContextPath() + "/orderConfirmation?orderId=" + order.getId());
            return;
        }

        switch (vnp_ResponseCode) {
            case "00":
                // Thanh toán thành công
                order.setPaymentStatus("SUCCESS");
                order.setPaymentTransactionId(vnp_TransactionNo);
                order.setStatus("PROCESSING");
                order.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                
                if (orderDAO.updateOrder(order)) {
                    System.out.println("✓ Order updated successfully: " + vnp_TxnRef);
                    response.sendRedirect(request.getContextPath() + "/orderConfirmation?orderId=" + order.getId());
                } else {
                    System.out.println("✗ Failed to update order: " + vnp_TxnRef);
                    response.sendRedirect(request.getContextPath() + "/cart?error=Failed to update order");
                }
                break;
            case "24":
                // Khách hàng hủy giao dịch
                order.setPaymentStatus("FAILED");
                order.setStatus("CANCELLED");
                order.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                orderDAO.updateOrder(order);
                System.out.println("✓ Order marked as cancelled: " + vnp_TxnRef);
                response.sendRedirect(request.getContextPath() + "/cart?error=Payment cancelled by user");
                break;
            default:
                // Thanh toán thất bại
                order.setPaymentStatus("FAILED");
                order.setStatus("CANCELLED");
                order.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                orderDAO.updateOrder(order);
                System.out.println("✓ Order marked as failed: " + vnp_TxnRef + ", Response code: " + vnp_ResponseCode);
                response.sendRedirect(request.getContextPath() + "/cart?error=Payment failed with code " + vnp_ResponseCode);
                break;
        }
    }
    
    private boolean verifySignature(HttpServletRequest request) {
        try {
            Map<String, String> fields = new HashMap<>();
            
            // Lấy tất cả parameters trừ vnp_SecureHash
            for (String key : request.getParameterMap().keySet()) {
                if (!key.equals("vnp_SecureHash")) {
                    String value = request.getParameter(key);
                    if (value != null && !value.isEmpty()) {
                        fields.put(key, value);
                    }
                }
            }
            
            // Tạo hash data
            List<String> fieldNames = new ArrayList<>(fields.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = fields.get(fieldName);
                hashData.append(fieldName).append('=').append(java.net.URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
            
            // Tính toán hash
            String computedHash = hmacSHA512(VNP_HASH_SECRET, hashData.toString());
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            
            System.out.println("Hash data: " + hashData.toString());
            System.out.println("Computed hash: " + computedHash);
            System.out.println("Received hash: " + vnp_SecureHash);
            
            return computedHash.equals(vnp_SecureHash);
            
        } catch (Exception e) {
            System.out.println("Error verifying signature: " + e.getMessage());
            return false;
        }
    }
    
    private String hmacSHA512(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac512.init(secretKey);
        byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}