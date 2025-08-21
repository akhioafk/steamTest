package utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public static String normalizePrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            return "0";
        }

        return price
                .replaceAll("[^\\d.,]", "")
                .replaceAll("\\s+", "")
                .replaceAll(",(?=\\d{3})", "")
                .replace(",", ".")
                .replaceAll("\\.00$", "");
    }
}
