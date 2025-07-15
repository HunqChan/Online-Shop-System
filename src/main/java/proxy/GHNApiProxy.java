
package proxy;

import java.io.*;
import java.net.*;
import java.util.stream.Collectors;
import model.Item;

public class GHNApiProxy {
    private static final String TOKEN = "35616a46-60c1-11f0-9b81-222185cb68c8";
    private static final String SHOP_ID = "197111";
    private static final String BASE_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/";

    public static String getProvinces() {
        String url = BASE_URL + "master-data/province";
        try {
            return sendGet(url);
        } catch (IOException e) {
            return createErrorJson("GET provinces", e);
        }
    }
    

    public static String getDistricts(int provinceId) {
        String url = BASE_URL + "master-data/district";
        String jsonBody = "{\"province_id\":" + provinceId + "}";
        try {
            return sendPost(url, jsonBody);
        } catch (IOException e) {
            return createErrorJson("POST districts", e);
        }
    }

    public static String getWards(int districtId) {
        String url = BASE_URL + "master-data/ward?district_id";
        String jsonBody = "{\"district_id\":" + districtId + "}";
        try {
            return sendPost(url, jsonBody);
        } catch (IOException e) {
            return createErrorJson("POST wards", e);
        }
    }

    public static String calculateShippingFee(int fromDistrictId, String fromWardCode, int toDistrictId, 
            String toWardCode, int serviceTypeId, int weight, int length, int width, int height, 
            int insuranceValue, Item[] items) {
        String url = BASE_URL + "v2/shipping-order/fee";
        StringBuilder json = new StringBuilder("{");
        json.append("\"from_district_id\":").append(fromDistrictId).append(",");
        json.append("\"from_ward_code\":\"").append(fromWardCode).append("\",");
        json.append("\"service_type_id\":").append(serviceTypeId).append(",");
        json.append("\"to_district_id\":").append(toDistrictId).append(",");
        json.append("\"to_ward_code\":\"").append(toWardCode).append("\",");
        json.append("\"height\":").append(height).append(",");
        json.append("\"length\":").append(length).append(",");
        json.append("\"weight\":").append(weight).append(",");
        json.append("\"width\":").append(width).append(",");
        json.append("\"insurance_value\":").append(insuranceValue).append(",");
        json.append("\"cod_failed_amount\":0");

        // Thêm mảng items nếu service_type_id là 5 (Hàng nặng)
        if (serviceTypeId == 5 && items != null && items.length > 0) {
            json.append(",\"items\":[");
            for (int i = 0; i < items.length; i++) {
                Item item = items[i];
                json.append("{")
                    .append("\"name\":\"").append(item.name).append("\",")
                    .append("\"quantity\":").append(item.quantity).append(",")
                    .append("\"length\":").append(item.length).append(",")
                    .append("\"width\":").append(item.width).append(",")
                    .append("\"height\":").append(item.height).append(",")
                    .append("\"weight\":").append(item.weight)
                    .append("}");
                if (i < items.length - 1) json.append(",");
            }
            json.append("]");
        }
        json.append("}");

        try {
            return sendPost(url, json.toString());
        } catch (IOException e) {
            return createErrorJson("POST shipping fee", e);
        }
    }


    private static String sendGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);

        int code = conn.getResponseCode();
        if (code != 200) {
            String errorResponse = readResponse(conn.getErrorStream(), conn.getContentEncoding());
            logHttpError("GET", urlStr, code, errorResponse);
            throw new IOException("GHN API GET error code: " + code + ", details: " + errorResponse);
        }

        return readResponse(conn.getInputStream(), conn.getContentEncoding());
    }

    private static String sendPost(String urlStr, String jsonBody) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("ShopId", SHOP_ID);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes("UTF-8"));
        }

        int code = conn.getResponseCode();
        if (code != 200) {
            String errorResponse = readResponse(conn.getErrorStream(), conn.getContentEncoding());
            logHttpError("POST", urlStr, code, errorResponse);
            throw new IOException("GHN API POST error code: " + code + ", details: " + errorResponse);
        }

        return readResponse(conn.getInputStream(), conn.getContentEncoding());
    }

    private static String readResponse(InputStream is, String encoding) throws IOException {
        if (is == null) return "{}";
        if (encoding == null) encoding = "UTF-8";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding))) {
            return reader.lines().collect(Collectors.joining());
        }
    }

    private static void logHttpError(String method, String url, int code, String errorResponse) {
        System.err.println("GHN API " + method + " failed:");
        System.err.println("  URL: " + url);
        System.err.println("  HTTP Code: " + code);
        System.err.println("  Error Response: " + errorResponse);
    }

    private static String createErrorJson(String method, Exception e) {
        System.err.println("Error calling GHN API (" + method + "): " + e.getMessage());
        return "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
    }
}