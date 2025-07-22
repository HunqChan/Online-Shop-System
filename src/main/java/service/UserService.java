package service;

import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import model.User;
import dal.UserDAO;
import proxy.CloudinaryProxy;
import validation.UserValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean updateUserProfile(User sessionUser, HttpServletRequest req) {
        try {
            // Bước 1: Tạo đối tượng User tạm để validate
            User userToValidate = User.builder()
                    .userId(sessionUser.getUserId()) // giữ ID
                    .fullName(req.getParameter("full_name"))
                    .email(req.getParameter("email"))
                    .phoneNumber(req.getParameter("phone_number"))
                    .gender(req.getParameter("gender") != null ? Boolean.parseBoolean(req.getParameter("gender")) : null)
                    .provinceId(parseIntSafe(req.getParameter("province_id")))
                    .provinceName(req.getParameter("province_name"))
                    .districtId(parseIntSafe(req.getParameter("district_id")))
                    .districtName(req.getParameter("district_name"))
                    .wardCode(req.getParameter("ward_code"))
                    .wardName(req.getParameter("ward_name"))
                    .detailAddress(req.getParameter("detail_address"))
                    .build();

            // Bước 2: Gọi validator
            List<String> errors = UserValidator.validateForUpdate(userToValidate);
            if (!errors.isEmpty()) {
                req.setAttribute("validationErrors", errors);
                return false;
            }

            // Bước 3: Nếu hợp lệ, cập nhật sessionUser
            sessionUser.setFullName(userToValidate.getFullName());
            sessionUser.setEmail(userToValidate.getEmail());
            sessionUser.setPhoneNumber(userToValidate.getPhoneNumber());
            sessionUser.setGender(userToValidate.getGender());
            sessionUser.setProvinceId(userToValidate.getProvinceId());
            sessionUser.setProvinceName(userToValidate.getProvinceName());
            sessionUser.setDistrictId(userToValidate.getDistrictId());
            sessionUser.setDistrictName(userToValidate.getDistrictName());
            sessionUser.setWardCode(userToValidate.getWardCode());
            sessionUser.setWardName(userToValidate.getWardName());
            sessionUser.setDetailAddress(userToValidate.getDetailAddress());

            // Cập nhật DB
            userDAO.update(sessionUser);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Hàm parseInt an toàn
    private int parseIntSafe(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1; // hoặc throw nếu muốn bắt lỗi
        }
    }


    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }

    public String uploadAvatar(User user, Part avatarPart) throws IOException {
        if (avatarPart == null || avatarPart.getSize() == 0) {
            throw new IllegalArgumentException("No file selected");
        }

        byte[] bytes = avatarPart.getInputStream().readAllBytes();
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "resource_type", "auto",
                "public_id", "user_avatar_" + user.getUserId(),
                "overwrite", true
        );

        Map<String, Object> uploadResult = CloudinaryProxy.getInstance().uploader().upload(bytes, uploadParams);
        return (String) uploadResult.get("secure_url");
    }

}
