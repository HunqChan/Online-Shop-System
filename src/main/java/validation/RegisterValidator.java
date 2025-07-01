package validation;

public class RegisterValidator {

    public String validate(String fullName, String email, String password, String confirmPassword) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name is required.";
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return "Invalid email format.";
        }
        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        return null;
    }
}
