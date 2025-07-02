package service;

import com.google.api.services.oauth2.model.Userinfo;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.User;
import util.GoogleUtils;
import validation.LoginValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    public void loginWithEmail(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!LoginValidator.isValid(email, password)) {
            request.setAttribute("error", "Invalid email or password format.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.findByEmailAndPassword(email, password);
        if (user == null || user.isDeleted()) {
            request.setAttribute("error", user == null ? "Incorrect email or password." : "Your account has been deactivated.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/home");
    }

    public void loginWithGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        try {
            if (code == null || code.isEmpty()) {
                response.sendRedirect(GoogleUtils.getLoginURL());
                return;
            }

            Userinfo googleUser = service.GoogleService.getUserInfoFromCode(code);
            String email = googleUser.getEmail();
            User user = userDAO.findByEmail(email);

            if (user != null && user.isDeleted()) {
                request.setAttribute("error", "Your account has been deactivated.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            if (user == null) {
                HttpSession session = request.getSession();
                session.setAttribute("google_user_name", googleUser.getName());
                session.setAttribute("google_user_email", email);
                session.setAttribute("google_user_avatar", googleUser.getPicture());
                response.sendRedirect(request.getContextPath() + "/complete-profile.jsp");
            } else {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home");
            }

        } catch (GeneralSecurityException | IOException | ServletException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
