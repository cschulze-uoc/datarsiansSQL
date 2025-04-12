package datarsians.utils;

import java.util.regex.Pattern;

public class validations {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean esEmailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
