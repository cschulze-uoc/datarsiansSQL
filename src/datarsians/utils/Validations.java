package datarsians.utils;

import java.util.regex.Pattern;

public class Validations {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean esEmailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean esNifValido (String nif) {
        return nif != null && nif.trim().length() == 9;
    }
}
