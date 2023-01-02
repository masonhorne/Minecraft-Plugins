package basicneeds.utility;

public class InputSanitation {

    public static boolean isValidUsername(String username) {
        // Make sure size requirements are met
        if(username.length() < 3 || username.length() > 16) return false;
        // Make sure all characters are valid
        for(int i = 0; i < username.length(); i++){
            char ch = username.charAt(i);
            if(!Character.isDigit(ch) && !Character.isAlphabetic(ch) && ch != '_') return false;
        }
        // Return true if name was valid
        return true;
    }
}
