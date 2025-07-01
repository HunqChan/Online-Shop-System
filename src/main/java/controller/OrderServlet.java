
package controller;

import dao.CartDAO;
import dao.OrderDAO;
import dao.dbConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final String GHN_TOKEN = "20741efc-5683-11f0-9b81-222185cb68c8";
    private static final String GHN_SHOP_ID = "196989";
    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2";
    private static final String GHN_MASTER_DATA_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data";

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

    private Map<String, Object> getDefaultWarehouse() throws SQLException {
        Map<String, Object> warehouse = new HashMap<>();
        String sql = "SELECT district_id, ward_code FROM warehouses WHERE id = 1";
        try (var conn = dbConnect.getConnection();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            if (rs.next()) {
                warehouse.put("district_id", rs.getInt("district_id"));
                warehouse.put("ward_code", rs.getString("ward_code"));
            } else {
                throw new SQLException("No default warehouse found");
            }
        }
        return warehouse;
    }

    private double calculateShippingFee(int fromDistrictId, String fromWardCode, int toDistrictId,
            String toWardCode, int weight, int length, int width, int height) throws IOException {
        String url = GHN_API_URL + "/shipping-order/fee";
        JSONObject requestBody = new JSONObject();
        requestBody.put("from_district_id", fromDistrictId);
        requestBody.put("from_ward_code", fromWardCode);
        requestBody.put("to_district_id", toDistrictId);
        requestBody.put("to_ward_code", toWardCode);
        requestBody.put("service_id", 53321);
        requestBody.put("weight", weight > 0 ? weight : 1000);
        requestBody.put("length", length > 0 ? length : 1);
        requestBody.put("width", width > 0 ? width : 1);
        requestBody.put("height", height > 0 ? height : 1);
        requestBody.put("insurance_value", 0);
        requestBody.put("cod_failed_amount", 0);

        System.out.println("GHN Fee Request Body: " + requestBody.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setRequestProperty("ShopId", GHN_SHOP_ID);
        conn.setDoOutput(true);

        try (var os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (var in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("GHN Fee Response: " + jsonResponse.toString());
                if (jsonResponse.getInt("code") == 200) {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    return data.getInt("total") / 25000.0;
                }
            }
        } else {
            try (var errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String inputLine;
                while ((inputLine = errorReader.readLine()) != null) {
                    errorResponse.append(inputLine);
                }
                System.out.println("GHN Fee Error: HTTP " + responseCode + ", Details: " + errorResponse.toString());
            }
        }
        conn.disconnect();
        return 0.0;
    }

    private List<Map<String, Object>> getDistricts(int provinceId) throws IOException {
        List<Map<String, Object>> districts = new ArrayList<>();
        String url = GHN_MASTER_DATA_URL + "/district";
        JSONObject requestBody = new JSONObject();
        requestBody.put("province_id", provinceId);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setDoOutput(true);

        try (var os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (var in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("GHN Districts Response: " + jsonResponse.toString());
                if (jsonResponse.getInt("code") == 200) {
                    JSONArray data = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject district = data.getJSONObject(i);
                        Map<String, Object> districtMap = new HashMap<>();
                        districtMap.put("id", district.getInt("DistrictID"));
                        districtMap.put("name", district.getString("DistrictName"));
                        districts.add(districtMap);
                    }
                }
            }
        } else {
            try (var errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String inputLine;
                while ((inputLine = errorReader.readLine()) != null) {
                    errorResponse.append(inputLine);
                }
                System.out.println("GHN Districts Error: HTTP " + responseCode + ", Details: " + errorResponse.toString());
            }
        }
        conn.disconnect();
        return districts;
    }

    private List<Map<String, Object>> getWards(int districtId) throws IOException {
        List<Map<String, Object>> wards = new ArrayList<>();
        String url = GHN_MASTER_DATA_URL + "/ward";
        JSONObject requestBody = new JSONObject();
        requestBody.put("district_id", districtId);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setDoOutput(true);

        try (var os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (var in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("GHN Wards Response: " + jsonResponse.toString());
                if (jsonResponse.getInt("code") == 200) {
                    JSONArray data = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ward = data.getJSONObject(i);
                        Map<String, Object> wardMap = new HashMap<>();
                        wardMap.put("id", ward.getString("WardCode"));
                        wardMap.put("name", ward.getString("WardName"));
                        wards.add(wardMap);
                    }
                }
            }
        } else {
            try (var errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String inputLine;
                while ((inputLine = errorReader.readLine()) != null) {
                    errorResponse.append(inputLine);
                }
                System.out.println("GHN Wards Error: HTTP " + responseCode + ", Details: " + errorResponse.toString());
            }
        }
        conn.disconnect();
        return wards;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            CartDAO cartDAO = new CartDAO();
            double subtotal = cartDAO.calculateCartTotal(cartId);

            String toDistrictIdParam = request.getParameter("toDistrictId");
            int toDistrictId = (toDistrictIdParam != null && !toDistrictIdParam.isEmpty()) ? Integer.parseInt(toDistrictIdParam) : 1444;

            String toWardCodeParam = request.getParameter("toWardCode");
            String toWardCode = (toWardCodeParam != null && !toWardCodeParam.isEmpty()) ? toWardCodeParam : "20308";

            Map<String, Object> warehouse;
            try {
                warehouse = getDefaultWarehouse();
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Lỗi khi lấy thông tin kho: " + e.getMessage());
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                return;
            }
            int fromDistrictId = (int) warehouse.get("district_id");
            String fromWardCode = (String) warehouse.get("ward_code");

            int totalWeight = 1000;
            int maxLength = 50, maxWidth = 30, maxHeight = 20;
            String sql = "SELECT p.weight, p.length, p.width, p.height FROM cart_items ci JOIN products p ON ci.product_id = p.id WHERE ci.cart_id = ?";
            try (var conn = dbConnect.getConnection();
                 var ps = conn.prepareStatement(sql)) {
                ps.setLong(1, cartId);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        totalWeight += rs.getInt("weight");
                        maxLength = Math.max(maxLength, rs.getInt("length"));
                        maxWidth = Math.max(maxWidth, rs.getInt("width"));
                        maxHeight = Math.max(maxHeight, rs.getInt("height"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Lỗi khi lấy thông tin sản phẩm: " + e.getMessage());
            }

            double shippingFee = calculateShippingFee(fromDistrictId, fromWardCode, toDistrictId, toWardCode,
                    totalWeight, maxLength, maxWidth, maxHeight);
            double total = subtotal + shippingFee;

            List<Map<String, Object>> districts = getDistricts(202);
            List<Map<String, Object>> wards = new ArrayList<>();
            if (toDistrictId != 0) {
                wards = getWards(toDistrictId);
            }

            request.setAttribute("districts", districts);
            request.setAttribute("wards", wards);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("shippingFee", shippingFee);
            request.setAttribute("total", total);
            request.setAttribute("toDistrictId", toDistrictId);
            request.setAttribute("toWardCode", toWardCode);
            request.setAttribute("fromDistrictId", fromDistrictId);
            request.setAttribute("fromWardCode", fromWardCode);
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
        int toDistrictId = Integer.parseInt(request.getParameter("toDistrictId"));
        String toWardCode = request.getParameter("toWardCode");

        if (cartId != null && paymentMethod != null) {
            CartDAO cartDAO = new CartDAO();
            OrderDAO orderDAO = new OrderDAO();
            double subtotal = cartDAO.calculateCartTotal(cartId);

            Map<String, Object> warehouse;
            try {
                warehouse = getDefaultWarehouse();
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Lỗi khi lấy thông tin kho: " + e.getMessage());
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                return;
            }
            int fromDistrictId = (int) warehouse.get("district_id");
            String fromWardCode = (String) warehouse.get("ward_code");

            int totalWeight = 1000;
            int maxLength = 50, maxWidth = 30, maxHeight = 20;
            String sql = "SELECT p.weight, p.length, p.width, p.height FROM cart_items ci JOIN products p ON ci.product_id = p.id WHERE ci.cart_id = ?";
            try (var conn = dbConnect.getConnection();
                 var ps = conn.prepareStatement(sql)) {
                ps.setLong(1, cartId);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        totalWeight += rs.getInt("weight");
                        maxLength = Math.max(maxLength, rs.getInt("length"));
                        maxWidth = Math.max(maxWidth, rs.getInt("width"));
                        maxHeight = Math.max(maxHeight, rs.getInt("height"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Lỗi khi lấy thông tin sản phẩm: " + e.getMessage());
            }

            double shippingFee = calculateShippingFee(fromDistrictId, fromWardCode, toDistrictId, toWardCode,
                    totalWeight, maxLength, maxWidth, maxHeight);
            double total = subtotal + shippingFee;

            Order order = new Order();
            order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            order.setOrderNumber(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
            order.setPaymentMethod(paymentMethod);
            order.setPaymentStatus("PENDING");
            order.setPaymentTransactionId(null);
            order.setShippingAddress(toDistrictId + ", " + toWardCode);
            order.setShippingFee(shippingFee);
            order.setStatus("PENDING");
            order.setSubtotal(subtotal);
            order.setTotal(total);
            order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            order.setUserId((Long) session.getAttribute("userId"));
            order.setToDistrictId(toDistrictId);
            order.setToWardCode(toWardCode);
            order.setServiceId(53321);

            if (orderDAO.addOrder(order)) {
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

                Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String vnp_CreateDate = formatter.format(cld.getTime());
                cld.add(Calendar.MINUTE, 15);
                String vnp_ExpireDate = formatter.format(cld.getTime());

                Map<String, String> vnp_Params = new HashMap<>();
                vnp_Params.put("vnp_Version", vnp_Version);
                vnp_Params.put("vnp_Command", vnp_Command);
                vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
                vnp_Params.put("vnp_CurrCode", "VND");
                if ("CARD".equals(paymentMethod)) {
                    vnp_Params.put("vnp_BankCode", "INTCARD");
                }
                vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
                vnp_Params.put("vnp_OrderType", orderType);
                vnp_Params.put("vnp_Locale", vnp_Locale);
                vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
                vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
                vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
                vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

                List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
                Collections.sort(fieldNames);
                StringBuilder hashData = new StringBuilder();
                StringBuilder query = new StringBuilder();
                Iterator<String> itr = fieldNames.iterator();
                while (itr.hasNext()) {
                    String fieldName = itr.next();
                    String fieldValue = vnp_Params.get(fieldName);
                    if (fieldValue != null && !fieldValue.isEmpty()) {
                        hashData.append(fieldName).append('=').append(java.net.URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                        query.append(java.net.URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(java.net.URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                        if (itr.hasNext()) {
                            hashData.append('&');
                            query.append('&');
                        }
                    }
                }

                String vnp_SecureHash;
                try {
                    vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    throw new ServletException("Lỗi khi tạo chữ ký HMAC-SHA512: " + e.getMessage());
                }

                String paymentUrl = vnp_Url + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
                response.sendRedirect(paymentUrl);
            } else {
                request.setAttribute("errorMessage", "Lỗi khi thêm order vào cơ sở dữ liệu.");
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}