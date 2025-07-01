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

    private static final String GHN_TOKEN = "20741efc-5683-11f0-9b81-222185cb68c8"; // Thay bằng token hợp lệ
    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String districtId = request.getParameter("districtId");
        response.setContentType("application/json");

        if (districtId != null) {
            JSONObject requestBody = new JSONObject();
            requestBody.put("district_id", Integer.parseInt(districtId));

            HttpURLConnection conn = (HttpURLConnection) new URL(GHN_API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Token", GHN_TOKEN);
            conn.setDoOutput(true);

            try (var os = conn.getOutputStream()) {
                os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder responseBody = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responseBody.append(inputLine);
                    }
                    JSONObject jsonResponse = new JSONObject(responseBody.toString());
                    if (jsonResponse.getInt("code") == 200) {
                        JSONArray data = jsonResponse.getJSONArray("data");
                        try (PrintWriter out = response.getWriter()) {
                            out.print(data.toString());
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "API Error: " + jsonResponse.getString("message"));
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to connect to GHN API");
            }
            conn.disconnect();
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.print("[]");
            }
        }
    }
}