package controller;

import dao.dbConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/GetWards")
public class GetWardsServlet extends HttpServlet {

    private List<Map<String, Object>> getWards(String districtId) throws SQLException {
        List<Map<String, Object>> wards = new ArrayList<>();
        String sql = "SELECT id, name FROM wards WHERE district_id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, districtId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> ward = new HashMap<>();
                    ward.put("id", rs.getString("id"));
                    ward.put("name", rs.getString("name"));
                    wards.add(ward);
                }
            }
        }
        return wards;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String districtId = request.getParameter("districtId");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            if (districtId != null) {
                List<Map<String, Object>> wards = getWards(districtId);
                out.print(new com.google.gson.Gson().toJson(wards));
            } else {
                out.print("[]");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching wards: " + e.getMessage());
        }
    }
}