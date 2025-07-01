package controller;

import com.google.api.services.oauth2.model.Userinfo;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.GoogleService;
import util.GoogleUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            try {
                response.sendRedirect(GoogleUtils.getLoginURL());
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            return;
        }

        try {
            Userinfo googleUser = GoogleService.getUserInfoFromCode(code);
            String email = googleUser.getEmail();

            User user = userDAO.findByEmail(email);

            if (user != null) {
                if (user.isDeleted()) {
                    request.setAttribute("error", "Your account has been deactivated.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                // Đăng nhập người dùng đã tồn tại
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Lưu thông tin tạm từ Google vào session
                HttpSession session = request.getSession();
                session.setAttribute("google_user_name", googleUser.getName());
                session.setAttribute("google_user_email", googleUser.getEmail());
                session.setAttribute("google_user_avatar", googleUser.getPicture());

                // Chuyển đến form nhập thông tin bổ sung
                response.sendRedirect(request.getContextPath() + "/complete-profile.jsp");
            }

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
