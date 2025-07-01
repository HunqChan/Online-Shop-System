
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
            JSONObject jsonRequest = new JSONObject(requestData);
            System.out.println("CalculateShippingFee Request: " + jsonRequest.toString());

            int fromDistrictId = jsonRequest.getInt("fromDistrictId");
            String fromWardCode = jsonRequest.getString("fromWardCode");
            int toDistrictId = jsonRequest.getInt("toDistrictId");
            String toWardCode = jsonRequest.getString("toWardCode");
            int weight = jsonRequest.getInt("weight");
            int length = jsonRequest.getInt("length");
            int width = jsonRequest.getInt("width");
            int height = jsonRequest.getInt("height");

            if (weight < 200) weight = 200;
            if (weight > 100000) weight = 100000;

            String url = GHN_API_URL + "/shipping-order/fee";
            JSONObject requestBody = new JSONObject();
            requestBody.put("from_district_id", fromDistrictId);
            requestBody.put("from_ward_code", fromWardCode);
            requestBody.put("to_district_id", toDistrictId);
            requestBody.put("to_ward_code", toWardCode);
            requestBody.put("service_id", 53321);
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
                    JSONObject jsonResponseGHN = new JSONObject(responseBody.toString());
                    System.out.println("GHN Fee Response: " + jsonResponseGHN.toString());
                    if (jsonResponseGHN.getInt("code") == 200) {
                        JSONObject data = jsonResponseGHN.getJSONObject("data");
                        double shippingFee = data.getInt("total") / 25000.0;
                        jsonResponse.put("success", true);
                        jsonResponse.put("shippingFee", shippingFee);
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "API Error: " + jsonResponseGHN.getString("message"));
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

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse.toString());
                out.flush();
            }
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Server error: " + e.getMessage());
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse.toString());
                out.flush();
            }
        }
    }
}