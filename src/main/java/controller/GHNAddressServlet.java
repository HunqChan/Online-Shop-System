package controller;

import service.GhnServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/ghn")
public class GHNAddressServlet extends HttpServlet {
    private final GhnServices ghnService = new GhnServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String result;

        try {
            if ("province".equalsIgnoreCase(type)) {
                result = ghnService.getProvinces();
            } else {
                result = "{\"error\":\"Invalid GET type\"}";
            }
        } catch (Exception e) {
            result = "{\"error\":\"Server error: " + e.getMessage() + "\"}";
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonRequest = jsonBuffer.toString();
        String result;

        try {
            switch (type) {
                case "district" -> result = ghnService.getDistricts(jsonRequest);
                case "ward" -> result = ghnService.getWards(jsonRequest);
                case "shippingFee" -> result = ghnService.calculateShippingFee(jsonRequest);
                default -> result = "{\"error\":\"Invalid POST type\"}";
            }
        } catch (Exception e) {
            result = "{\"error\":\"Server error: " + e.getMessage() + "\"}";
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}