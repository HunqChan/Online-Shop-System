package controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.GHNService;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/api/ghn/provinces",
        "/api/ghn/districts",
        "/api/ghn/wards"
})
public class GHNApiServlet extends HttpServlet {
    private final GHNService ghnService = new GHNService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        System.out.println("GHN API GET: " + path);

        switch (path) {
            case "/api/ghn/provinces" -> writeJson(resp, ghnService.getProvinces());
            case "/api/ghn/districts" -> {
                int provinceId = Integer.parseInt(req.getParameter("provinceId"));
                writeJson(resp, ghnService.getDistricts(provinceId));
            }
            case "/api/ghn/wards" -> {
                int districtId = Integer.parseInt(req.getParameter("districtId"));
                writeJson(resp, ghnService.getWards(districtId));
            }
        }
    }

    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(data));
    }
}
