package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.GHNService;
import service.UserService;
import validation.UserValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
@WebServlet(urlPatterns = {
        "/update-profile",
        "/upload-avatar",
})
public class UpdateProfileServlet extends HttpServlet {
    private final GHNService ghnService = new GHNService();
    private final UserService userService = new UserService();
    private final UserValidator userValidator = new UserValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            resp.sendRedirect("login");
            return;
        }

        switch (path) {
            case "/upload-avatar" -> handleAvatarUpload(req, resp, session, sessionUser);
            case "/update-profile" -> handleProfileUpdate(req, resp, session, sessionUser);
        }
    }

    private void handleAvatarUpload(HttpServletRequest req, HttpServletResponse resp, HttpSession session, User sessionUser)
            throws ServletException, IOException {
        try {
            Part avatarPart = req.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                byte[] bytes = avatarPart.getInputStream().readAllBytes();
                Map<String, Object> uploadParams = com.cloudinary.utils.ObjectUtils.asMap(
                        "resource_type", "auto",
                        "public_id", "user_avatar_" + sessionUser.getUserId(),
                        "overwrite", true
                );
                Map<String, Object> uploadResult = service.CloudinaryService.getInstance().uploader().upload(bytes, uploadParams);
                String uploadedUrl = (String) uploadResult.get("secure_url");

                // Update user session
                sessionUser.setAvatarUrl(uploadedUrl);
                session.setAttribute("user", sessionUser);
                req.setAttribute("message", "Avatar updated successfully.");
            } else {
                req.setAttribute("error", "No file selected.");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Failed to upload avatar.");
        }

        req.getRequestDispatcher("update-profile.jsp").forward(req, resp);
    }

    private void handleProfileUpdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session, User sessionUser)
            throws ServletException, IOException {
        User formUser = userService.extractUserFromRequest(req, sessionUser.getUserId());
        formUser.setAvatarUrl(sessionUser.getAvatarUrl()); // giữ lại avatar hiện tại

        Map<String, String> errors = userValidator.validateProfile(formUser);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("user", formUser);
            req.getRequestDispatcher("update-profile.jsp").forward(req, resp);
            return;
        }

        boolean updated = userService.updateProfile(formUser);
        if (updated) {
            session.setAttribute("user", formUser);
            req.setAttribute("message", "Profile updated successfully.");
        } else {
            req.setAttribute("error", "Failed to update profile.");
        }

        req.setAttribute("user", formUser);
        req.getRequestDispatcher("update-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/update-profile")) {
            req.getRequestDispatcher("update-profile.jsp").forward(req, resp);
        }
    }

    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(data));
    }
}
