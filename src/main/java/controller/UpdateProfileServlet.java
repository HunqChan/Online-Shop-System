package controller;

import model.User;
import proxy.GHNProxy;
import service.UserService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/update-profile", "/upload-avatar"})
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final GHNProxy ghnProxy = new GHNProxy();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User fullUser = userService.getUserById(sessionUser.getUserId());
        List<Map<String, String>> provinces = ghnProxy.getProvinces();

        req.setAttribute("user", fullUser);
        req.setAttribute("provinces", provinces);
        req.getRequestDispatcher("/update-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath(); // "/update-profile" hoặc "/upload-avatar"
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if ("/upload-avatar".equals(servletPath)) {
            handleAvatarUpload(req, resp, session, sessionUser);
            return;
        }

        // ✅ Xử lý cập nhật thông tin cá nhân
        try {
            boolean success = userService.updateUserProfile(sessionUser, req);

            if (success) {
                session.setAttribute("user", sessionUser); // cập nhật lại user trong session
                req.setAttribute("message", "Profile updated successfully.");
            } else {
                req.setAttribute("error", "Failed to update profile. Please try again.");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Server error while updating profile.");
        }

        doGet(req, resp);
    }

    private void handleAvatarUpload(HttpServletRequest req, HttpServletResponse resp, HttpSession session, User sessionUser)
            throws ServletException, IOException {
        try {
            Part avatarPart = req.getPart("avatar");
            String uploadedUrl = userService.uploadAvatar(sessionUser, avatarPart);

            sessionUser.setAvatarUrl(uploadedUrl);
            session.setAttribute("user", sessionUser);
            req.setAttribute("message", "Avatar updated successfully.");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            req.setAttribute("error", "Failed to upload avatar.");
        }

        doGet(req, resp);
    }
}
