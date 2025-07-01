package service;

import model.User;
import dal.UserDAO;

public class RegisterService {
    private final UserDAO userDAO = new UserDAO();
    private final int DEFAULT_ROLE = 2;

    public String register(String fullName, String email, String password, String phone, Boolean gender) {
        if (userDAO.findByEmail(email) != null) {
            return "Email is already in use.";
        }

        User user = User.builder()
                .roleId(DEFAULT_ROLE)
                .fullName(fullName)
                .email(email)
                .password(password) // TODO: mã hóa bằng util nếu cần
                .phoneNumber(phone)
                .gender(gender)
                .isDeleted(false)
                .build();

        return userDAO.insert(user) > 0 ? null : "Registration failed.";

    }
}
