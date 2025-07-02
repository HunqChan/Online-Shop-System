package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.LoginService;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/login-google"})
public class LoginServlet extends HttpServlet {

    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            loginService.loginWithEmail(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            request.getRequestDispatcher("login.jsp").forward(request, response); // Forward to login page
        } else if ("/login-google".equals(path)) {
            loginService.loginWithGoogle(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
