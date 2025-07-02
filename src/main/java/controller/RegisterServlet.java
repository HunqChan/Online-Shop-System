package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.RegisterService;
import validation.RegisterValidator;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register", "/register-google"})
public class RegisterServlet extends HttpServlet {
    private final RegisterService registerService = new RegisterService();
    private final RegisterValidator validator = new RegisterValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/register".equals(path)) {
            handleNormalRegister(req, resp);
        } else if ("/register-google".equals(path)) {
            handleGoogleRegister(req, resp);
        }
    }

    private void handleNormalRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fullName = req.getParameter("full_name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String phone = req.getParameter("phone_number");
        String genderStr = req.getParameter("gender");
        Boolean gender = genderStr != null && genderStr.equals("1");

        String error = validator.validate(fullName, email, password, confirmPassword);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        error = registerService.register(fullName, email, password, phone, gender);
        if (error == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("error", error);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    private void handleGoogleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String fullName = (String) session.getAttribute("google_user_name");
        String email = (String) session.getAttribute("google_user_email");
        String avatarUrl = (String) session.getAttribute("google_user_avatar");

        String phone = req.getParameter("phone");
        String genderStr = req.getParameter("gender");

        if (fullName == null || email == null || avatarUrl == null || phone == null || genderStr == null) {
            req.setAttribute("error", "Missing profile information. Please login again.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        boolean gender = "1".equals(genderStr);

        // Dùng service để xử lý đăng ký Google
        User user = registerService.registerGoogleUser(fullName, email, avatarUrl, phone, gender);

        if (user == null) {
            req.setAttribute("error", "Failed to complete registration. Try again.");
            req.getRequestDispatcher("complete-profile.jsp").forward(req, resp);
            return;
        }

        // Lưu vào session & xóa session tạm
        session.setAttribute("user", user);
        session.removeAttribute("google_user_name");
        session.removeAttribute("google_user_email");
        session.removeAttribute("google_user_avatar");

        resp.sendRedirect(req.getContextPath() + "/home");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}