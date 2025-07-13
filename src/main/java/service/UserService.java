package service;

import jakarta.servlet.http.HttpServletRequest;
import model.User;
import dal.UserDAO;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean updateProfile(User user) {
        return userDAO.update(user) > 0;
    }

    public User extractUserFromRequest(HttpServletRequest req, int userId) {
        return User.builder()
                .userId(userId)
                .roleId(Integer.parseInt(req.getParameter("roleId"))) // nếu không có, thì lấy từ session
                .fullName(req.getParameter("fullName"))
                .phoneNumber(req.getParameter("phoneNumber"))
                .gender(parseNullableBoolean(req.getParameter("gender")))
                .avatarUrl(req.getParameter("avatarUrl")) // xử lý riêng nếu dùng upload
                .provinceId(parseNullableInt(req.getParameter("provinceId")))
                .provinceName(req.getParameter("provinceName"))
                .districtId(parseNullableInt(req.getParameter("districtId")))
                .districtName(req.getParameter("districtName"))
                .wardCode(req.getParameter("wardCode"))
                .wardName(req.getParameter("wardName"))
                .detailAddress(req.getParameter("detailAddress"))
                .build();
    }

    private Integer parseNullableInt(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseNullableBoolean(String value) {
        if (value == null || value.isBlank()) return null;
        return Boolean.parseBoolean(value); // "true" -> true, "false" -> false
    }

}
