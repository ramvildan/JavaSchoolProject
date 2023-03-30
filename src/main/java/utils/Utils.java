package utils;

public class Utils {
    public static String getString (String rawValue) {
        return rawValue.replaceAll("^['’‘]", "").replaceAll("['’‘]$", "");
    }
}
