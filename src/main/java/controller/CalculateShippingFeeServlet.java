package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.GhnServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/order/calculateShippingFee")
public class CalculateShippingFeeServlet extends HttpServlet {
    private final GhnServices ghnService = new GhnServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Đọc JSON body từ request
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Gọi service để tính phí vận chuyển
            String result = ghnService.calculateShippingFee(jsonBuilder.toString());

            // Trả kết quả JSON về client
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            
      } catch (Exception e) {
    e.printStackTrace(); // thêm dòng này để thấy lỗi trong console/log
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    response.setContentType("application/json");
    response.getWriter().write("{\"error\": \"Tính phí thất bại: " + e.getMessage() + "\"}");
}

    }
}
