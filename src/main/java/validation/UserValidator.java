package validation;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {
    public static List<String> validateForUpdate(User user) {
        List<String> errors = new ArrayList<>();

        if (user.getFullName() == null || user.getFullName().isBlank())
            errors.add("Full name is required.");
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            errors.add("Invalid email format.");
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().matches("^\\d{10,11}$"))
            errors.add("Phone number must be 10-11 digits.");
        if (user.getGender() == null)
            errors.add("Gender is required.");
        if (user.getProvinceId() <= 0)
            errors.add("Province is required.");
        if (user.getDistrictId() <= 0)
            errors.add("District is required.");
        if (user.getWardCode() == null || user.getWardCode().isBlank())
            errors.add("Ward is required.");

        return errors;
    }
}


