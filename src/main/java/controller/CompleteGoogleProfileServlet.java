package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.MailService;

import java.io.IOException;

@WebServlet("/complete-profile")
public class CompleteGoogleProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin tạm từ session
        String fullName = (String) session.getAttribute("google_user_name");
        String email = (String) session.getAttribute("google_user_email");
        String avatarUrl = (String) session.getAttribute("google_user_avatar");

        // Lấy thông tin từ form
        String phone = request.getParameter("phone");
        String genderStr = request.getParameter("gender");

        if (fullName == null || email == null || avatarUrl == null || phone == null || genderStr == null) {
            request.setAttribute("error", "Missing profile information. Please login again.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        boolean gender = "1".equals(genderStr); // 1 = Male, 0 = Female

        // Tạo user
        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .avatarUrl(avatarUrl)
                .phoneNumber(phone)
                .gender(gender)
                .roleId(2) // ROLE: User
                .isDeleted(false)
                .build();

        // Lưu vào DB
        userDAO.insert(user);

        // Gửi email chào mừng
        MailService.sendWelcomeEmail(user.getEmail(), user.getFullName());

        // Lưu user vào session (có thể lấy lại từ DB để có ID)
        user = userDAO.findByEmail(email);
        session.setAttribute("user", user);

        // Xoá thông tin Google tạm
        session.removeAttribute("google_user_name");
        session.removeAttribute("google_user_email");
        session.removeAttribute("google_user_avatar");

        // Chuyển về trang chính
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
