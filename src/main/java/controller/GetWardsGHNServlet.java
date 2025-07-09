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
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

@WebServlet("/GetWardsGHN")
public class GetWardsGHNServlet extends HttpServlet {

    private static final String GHN_TOKEN = "20741efc-5683-11f0-9b81-222185cb68c8"; // Cập nhật token hợp lệ từ GHN
    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String districtId = request.getParameter("districtId");

        if (districtId == null || districtId.trim().isEmpty()) {
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"code\":400,\"message\":\"No districtId provided\",\"data\":[]}");
                System.out.println("No districtId provided, returning empty array");
            }
            return;
        }

        try {
            int districtIdInt = Integer.parseInt(districtId);
            JSONObject requestBody = new JSONObject();
            requestBody.put("district_id", districtIdInt);

            HttpURLConnection conn = (HttpURLConnection) new URL(GHN_API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Token", GHN_TOKEN);
            conn.setDoOutput(true);

            try (var os = conn.getOutputStream()) {
                os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("GHN API response code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder responseBody = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responseBody.append(inputLine);
                    }
                    JSONObject jsonResponse = new JSONObject(responseBody.toString());
                    System.out.println("GHN API raw response: " + responseBody.toString());
                    if (jsonResponse.getInt("code") == 200) {
                        JSONArray data = jsonResponse.getJSONArray("data");
                        System.out.println("GHN API data length: " + data.length());
                        JSONObject responseObj = new JSONObject();
                        responseObj.put("code", 200);
                        responseObj.put("message", "Success");
                        responseObj.put("data", data);
                        try (PrintWriter out = response.getWriter()) {
                            out.print(responseObj.toString());
                            System.out.println("Sent wards data: " + data.toString());
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "API Error: " + jsonResponse.getString("message"));
                        System.out.println("API Error: " + jsonResponse.getString("message"));
                    }
                }
            } else {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String inputLine;
                    while ((inputLine = errorReader.readLine()) != null) {
                        errorResponse.append(inputLine);
                    }
                    System.out.println("GHN API error response: " + errorResponse.toString());
                }
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to connect to GHN API: HTTP " + responseCode);
            }
            conn.disconnect();
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid districtId format");
            System.out.println("Invalid districtId format: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}