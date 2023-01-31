package cnu.swabe.v2.service.util;

public class UserServiceUtil {
    public static boolean checkInputLength(String input) {
        if(input.length() > 0 && input.length() < 10) {
            return true;
        }
         return false;
    }
}
