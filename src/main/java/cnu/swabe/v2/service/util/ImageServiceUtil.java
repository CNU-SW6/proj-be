package cnu.swabe.v2.service.util;

public class ImageServiceUtil {
    public static String randomAlphabet() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <10; i++) {
            char ch = (char) ((Math.random() * 26) + 97);
            sb.append(ch);
        }

        return sb.toString();
    }
}
