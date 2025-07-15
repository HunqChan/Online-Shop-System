package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import proxy.GHNProxy;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/api/ghn/*")
public class GHNApiServlet extends HttpServlet {
    private final GHNProxy proxy = new GHNProxy();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String path = req.getPathInfo(); // ví dụ: /provinces
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing path");
            return;
        }

        System.out.println("🔍 API Request: " + path);

        try {
            switch (path) {
                case "/provinces" -> {
                    List<Map<String, String>> provinces = proxy.getProvinces();
                    System.out.println("📦 Provinces: " + provinces);
                    out.print(mapper.writeValueAsString(provinces));
                }
                case "/districts" -> {
                    String provinceIdStr = req.getParameter("province_id");
                    if (provinceIdStr == null) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing province_id");
                        return;
                    }
                    int provinceId = Integer.parseInt(provinceIdStr);
                    List<Map<String, String>> districts = proxy.getDistricts(provinceId);
                    out.print(mapper.writeValueAsString(districts));
                }
                case "/wards" -> {
                    String districtIdStr = req.getParameter("district_id");
                    if (districtIdStr == null) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing district_id");
                        return;
                    }
                    int districtId = Integer.parseInt(districtIdStr);
                    List<Map<String, String>> wards = proxy.getWards(districtId);
                    out.print(mapper.writeValueAsString(wards));
                }
                default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown API path");
            }
        } catch (Exception e) {
            e.printStackTrace(); // log ra console
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }

        out.flush();
    }
}
