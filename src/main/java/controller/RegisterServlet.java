package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RegisterService;
import validation.RegisterValidator;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final RegisterService registerService = new RegisterService();
    private final RegisterValidator validator = new RegisterValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // Lấy form data
        String fullName = req.getParameter("full_name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String phone = req.getParameter("phone_number");
        String genderStr = req.getParameter("gender");
        Boolean gender = genderStr != null && genderStr.equals("1");

        // Validation
        String error = validator.validate(fullName, email, password, confirmPassword);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        // Gọi service
        error = registerService.register(fullName, email, password, phone, gender);
        if (error == null) {
            resp.sendRedirect(req.getContextPath() + "login.jsp");
        } else {
            req.setAttribute("error", error);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
