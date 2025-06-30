package controller;

import dao.CartDAO;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import model.Order;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    // Hàm tạo HMAC-SHA512
    private String hmacSHA512(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hmacData) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            CartDAO cartDAO = new CartDAO();
            double subtotal = cartDAO.calculateCartTotal(cartId);
            double shippingFee = 0.0;
            double total = subtotal + shippingFee;

            request.setAttribute("subtotal", subtotal);
            request.setAttribute("shippingFee", shippingFee);
            request.setAttribute("total", total);
            request.getRequestDispatcher("jsp/checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long cartId = (Long) session.getAttribute("cartId");
        String paymentMethod = request.getParameter("paymentMethod");
        String shippingAddress = request.getParameter("shippingAddress");
        String bankCode = request.getParameter("bankCode");
        String cardNumber = request.getParameter("cardNumber");
        String cardHolder = request.getParameter("cardHolder");
        String cardExpiry = request.getParameter("cardExpiry");
        String cardCvv = request.getParameter("cardCvv");

        if (cartId != null && paymentMethod != null && shippingAddress != null) {
            CartDAO cartDAO = new CartDAO();
            OrderDAO orderDAO = new OrderDAO();
            double subtotal = cartDAO.calculateCartTotal(cartId);
            double shippingFee = 0.0;
            double total = subtotal + shippingFee;

            Order order = new Order();
            order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            order.setOrderNumber(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
            order.setPaymentMethod(paymentMethod);
            order.setPaymentStatus("PENDING");
            order.setPaymentTransactionId(paymentMethod.equals("VNPAY") || paymentMethod.equals("CARD") ? null : "N/A");
            order.setShippingAddress(shippingAddress);
            order.setShippingFee(shippingFee);
            order.setStatus("PENDING");
            order.setSubtotal(subtotal);
            order.setTotal(total);
            order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            order.setUserId((Long) session.getAttribute("userId"));

            if (orderDAO.addOrder(order)) {
                if ("COD".equals(paymentMethod)) {
                    order.setPaymentStatus("PENDING");
                    order.setStatus("PROCESSING");
                    response.sendRedirect(request.getContextPath() + "/orderConfirmation?orderId=" + order.getId());
                } else if ("VNPAY".equals(paymentMethod) || "CARD".equals(paymentMethod)) {
                    String vnp_Version = "2.1.0";
                    String vnp_Command = "pay";
                    String vnp_TmnCode = "30NRFAGC";
                    String vnp_HashSecret = "9S4WHMNDYXVZBFDE76T90A3U12ZKYV06";
                    String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
                    String vnp_ReturnUrl = "http://localhost:8081/Online-Shop-System/orderList"; 
                    String vnp_TxnRef = order.getOrderNumber();
                    String vnp_OrderInfo = "Thanh toan don hang " + vnp_TxnRef;
                    long vnp_Amount = (long) (total * 100);
                    String vnp_IpAddr = request.getRemoteAddr();
                    String orderType = "other";
                    String vnp_Locale = "vn";

                    // Tạo ngày giờ
                    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String vnp_CreateDate = formatter.format(cld.getTime());
                    cld.add(Calendar.MINUTE, 15);
                    String vnp_ExpireDate = formatter.format(cld.getTime());

                    // Tạo map tham số
                    Map<String, String> vnp_Params = new HashMap<>();
                    vnp_Params.put("vnp_Version", vnp_Version);
                    vnp_Params.put("vnp_Command", vnp_Command);
                    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                    vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
                    vnp_Params.put("vnp_CurrCode", "VND");
                    if ("VNPAY".equals(paymentMethod) && bankCode != null && !bankCode.isEmpty()) {
                        vnp_Params.put("vnp_BankCode", bankCode);
                    }
                    if ("CARD".equals(paymentMethod)) {
                        vnp_Params.put("vnp_BankCode", "INTCARD"); // Thanh toán thẻ quốc tế
                        if (cardNumber != null && cardHolder != null && cardExpiry != null && cardCvv != null) {
                            vnp_OrderInfo += " | Card: " + cardNumber + ", Holder: " + cardHolder + ", Expiry: " + cardExpiry;
                            // Lưu ý: Thông tin thẻ không gửi trực tiếp qua API, VNPAY sẽ hiển thị form nhập thẻ
                        }
                    }
                    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
                    vnp_Params.put("vnp_OrderType", orderType);
                    vnp_Params.put("vnp_Locale", vnp_Locale);
                    vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
                    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
                    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
                    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

                    // Sắp xếp tham số và tạo chuỗi hash
                    List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
                    Collections.sort(fieldNames);
                    StringBuilder hashData = new StringBuilder();
                    StringBuilder query = new StringBuilder();
                    Iterator<String> itr = fieldNames.iterator();
                    while (itr.hasNext()) {
                        String fieldName = itr.next();
                        String fieldValue = vnp_Params.get(fieldName);
                        if (fieldValue != null && !fieldValue.isEmpty()) {
                            hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            if (itr.hasNext()) {
                                hashData.append('&');
                                query.append('&');
                            }
                        }
                    }

                    // Tạo chữ ký HMAC-SHA512
                    String vnp_SecureHash;
                    try {
                        vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
                    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        throw new ServletException("Lỗi khi tạo chữ ký HMAC-SHA512: " + e.getMessage());
                    }

                    // Tạo URL thanh toán
                    String paymentUrl = vnp_Url + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;

                    // Debug
                    System.out.println("vnp_Params: " + vnp_Params);
                    System.out.println("hashData: " + hashData.toString());
                    System.out.println("vnp_SecureHash: " + vnp_SecureHash);
                    System.out.println("Full Payment URL: " + paymentUrl);

                    response.sendRedirect(paymentUrl);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}