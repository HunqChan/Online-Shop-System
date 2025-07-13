package validation;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {

    public Map<String, String> validateProfile(User user) {
        Map<String, String> errors = new HashMap<>();

        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            errors.put("fullName", "Full name is required.");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().length() < 8) {
            errors.put("phoneNumber", "Invalid phone number.");
        }

        boolean hasAnyAddressField =
                user.getProvinceId() != null ||
                        user.getDistrictId() != null ||
                        (user.getWardCode() != null && !user.getWardCode().isBlank());

        if (hasAnyAddressField) {
            if (user.getProvinceId() == null) {
                errors.put("provinceId", "Province must be selected if you enter address.");
            }
            if (user.getDistrictId() == null) {
                errors.put("districtId", "District must be selected if you enter address.");
            }
            if (user.getWardCode() == null || user.getWardCode().trim().isEmpty()) {
                errors.put("wardCode", "Ward must be selected if you enter address.");
            }
        }

        return errors;
    }
}

