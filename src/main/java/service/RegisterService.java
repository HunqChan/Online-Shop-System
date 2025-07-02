package service;

import model.User;
import dal.UserDAO;

public class RegisterService {
    private final UserDAO userDAO = new UserDAO();
    private final int DEFAULT_ROLE = 2;
    private final MailService mailService = new MailService(); // Khởi tạo MailService

    public String register(String fullName, String email, String password, String phone, Boolean gender) {
        if (userDAO.findByEmail(email) != null) {
            return "Email is already in use.";
        }

        User user = User.builder()
                .roleId(DEFAULT_ROLE)
                .fullName(fullName)
                .email(email)
                .password(password)
                .phoneNumber(phone)
                .gender(gender)
                .isDeleted(false)
                .build();

        int result = userDAO.insert(user);

        if (result > 0) {
            mailService.sendWelcomeEmail(email, fullName);
            return null;
        }

        return "Registration failed.";
    }

    public User registerGoogleUser(String fullName, String email, String avatarUrl, String phone, boolean gender) {
        // Kiểm tra trùng email nếu cần, ở đây ta giả định email chưa tồn tại

        User user = User.builder()
                .roleId(DEFAULT_ROLE)
                .fullName(fullName)
                .email(email)
                .avatarUrl(avatarUrl)
                .phoneNumber(phone)
                .gender(gender)
                .isDeleted(false)
                .build();

        int result = userDAO.insert(user);

        if (result > 0) {
            mailService.sendWelcomeEmail(email, fullName);
            return userDAO.findByEmail(email); // Trả về user có đầy đủ ID
        }

        return null;
    }
}
