package proxy;

import jakarta.servlet.http.HttpServletRequest;
import model.Order;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VNPayHelper {

    private static final String VNP_VERSION = "2.1.0";
    private static final String VNP_COMMAND = "pay";
    private static final String VNP_TMNCODE = "30NRFAGC";
    private static final String VNP_HASH_SECRET = "9S4WHMNDYXVZBFDE76T90A3U12ZKYV06";
    private static final String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNP_RETURN_URL = "http://localhost:9999/Online_Shop_System_war_exploded/vnpay_return";

    public static String createPaymentUrl(Order order, HttpServletRequest request) {
        try {
            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", VNP_VERSION);
            vnpParams.put("vnp_Command", VNP_COMMAND);
            vnpParams.put("vnp_TmnCode", VNP_TMNCODE);
            vnpParams.put("vnp_Amount", String.valueOf((long)(order.getTotal() * 100))); // VND nhân 100
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_TxnRef", order.getOrderNumber());
            vnpParams.put("vnp_OrderInfo", "Thanh toan don hang: " + order.getOrderNumber());
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_IpAddr", request.getRemoteAddr());
            vnpParams.put("vnp_ReturnUrl", VNP_RETURN_URL);

            // Đặt đúng TimeZone Việt Nam
            TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar calendar = Calendar.getInstance(vnTimeZone);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setTimeZone(vnTimeZone);

            // vnp_CreateDate
            String vnp_CreateDate = formatter.format(calendar.getTime());
            vnpParams.put("vnp_CreateDate", vnp_CreateDate);

            // vnp_ExpireDate (+15 phút)
            calendar.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(calendar.getTime());
            vnpParams.put("vnp_ExpireDate", vnp_ExpireDate);

            // Sắp xếp key theo thứ tự a-z
            List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (String name : fieldNames) {
                String value = vnpParams.get(name);
                if (hashData.length() > 0) {
                    hashData.append('&');
                    query.append('&');
                }

                // Dùng đúng charset khi encode
                String encodedValue = URLEncoder.encode(value, StandardCharsets.US_ASCII.toString());
                hashData.append(name).append('=').append(encodedValue);
                query.append(URLEncoder.encode(name, StandardCharsets.US_ASCII.toString()))
                     .append('=').append(encodedValue);
            }

            // Sinh chữ ký
            String secureHash = VNPaySecurity.hmacSHA512(VNP_HASH_SECRET, hashData.toString());
            query.append("&vnp_SecureHash=").append(secureHash);

            // Debug log
            System.out.println("========== VNPAY DEBUG LOG ==========");
            System.out.println("== SYSTEM TIME == " + new Date());
            System.out.println("OrderNumber: " + order.getOrderNumber());
            System.out.println("CreateDate: " + vnp_CreateDate);
            System.out.println("ExpireDate: " + vnp_ExpireDate);
            System.out.println("Client IP: " + request.getRemoteAddr());
            System.out.println("Amount: " + vnpParams.get("vnp_Amount"));
            System.out.println("HashData (for HMAC): " + hashData);
            System.out.println("Query (encoded): " + query);
            System.out.println("=====================================");
            System.out.println("Final Redirect URL: " + VNP_PAY_URL + "?" + query);

            return VNP_PAY_URL + "?" + query.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
