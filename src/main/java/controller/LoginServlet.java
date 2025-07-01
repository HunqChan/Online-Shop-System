package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import dal.UserDAO;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Xác thực người dùng
        User user = userDAO.findByEmailAndPassword(email, password);

        if (user != null) {
            if (user.isDeleted()) {
                request.setAttribute("error", "Your account has been deactivated.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Lưu user vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // GET sẽ chuyển đến trang login.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
