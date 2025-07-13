package validation;

public class LoginValidator {

    public static boolean isValid(String email, String password) {
        return email != null && password != null &&
                email.matches("^\\S+@\\S+\\.\\S+$") &&
                password.length() >= 6;
    }
}
