package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.cloudinary.json.JSONObject;

@WebServlet("/order/calculateShippingFee")
public class CalculateShippingFeeServlet extends HttpServlet {

    private static final String GHN_TOKEN = "20741efc-5683-11f0-9b81-222185cb68c8";
    private static final String GHN_SHOP_ID = "196989";
    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        try {
            String requestData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest;
            try {
                jsonRequest = new JSONObject(requestData);
            } catch (Exception e) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid JSON request: " + e.getMessage());
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse.toString());
                }
                return;
            }

            int fromDistrictId = jsonRequest.optInt("fromDistrictId", 0);
            String fromWardCode = jsonRequest.optString("fromWardCode", "");
            int toDistrictId = jsonRequest.optInt("toDistrictId", 0);
            String toWardCode = jsonRequest.optString("toWardCode", "");
            int weight = jsonRequest.optInt("weight", 200);
            int length = jsonRequest.optInt("length", 1);
            int width = jsonRequest.optInt("width", 1);
            int height = jsonRequest.optInt("height", 1);

            if (fromDistrictId == 0 || fromWardCode.isEmpty() || toDistrictId == 0 || toWardCode.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Missing required parameters");
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse.toString());
                }
                return;
            }

            weight = Math.max(200, Math.min(weight, 100000));

            String url = GHN_API_URL + "/shipping-order/fee";
            JSONObject requestBody = new JSONObject();
            requestBody.put("from_district_id", fromDistrictId);
            requestBody.put("from_ward_code", fromWardCode);
            requestBody.put("to_district_id", toDistrictId);
            requestBody.put("to_ward_code", toWardCode);
            int serviceId = jsonRequest.optInt("serviceId", 53321); // TODO: Lấy động từ API GHN
            requestBody.put("service_id", serviceId);
            requestBody.put("weight", weight);
            requestBody.put("length", length);
            requestBody.put("width", width);
            requestBody.put("height", height);
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
                    StringBuilder responseBody = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responseBody.append(inputLine);
                    }
                    try {
                        JSONObject jsonResponseGHN = new JSONObject(responseBody.toString());
                        System.out.println("GHN Fee Response: " + jsonResponseGHN.toString());
                        if (jsonResponseGHN.getInt("code") == 200) {
                            JSONObject data = jsonResponseGHN.getJSONObject("data");
                            double shippingFee = data.getInt("total");
                            jsonResponse.put("success", true);
                            jsonResponse.put("shippingFee", shippingFee);
                        } else {
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "API Error: " + jsonResponseGHN.getString("message"));
                        }
                    } catch (Exception e) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Invalid JSON response: " + e.getMessage());
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
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Failed to connect to GHN API: HTTP " + responseCode);
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Server error: " + e.getMessage());
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
        }
    }
}