package za.ac.nwu.ac.logic.implementation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ValidationServiceImpl {

    /**
     * Validates that a name / surname only contains letters
     * @param name The name that needs to be verified
     * @return True if the name only contains letters and starts with a capital letter
     */

    public static boolean isValidName(String name){
        if (!name.substring(0,1).toUpperCase().equals(name.substring(0,1)))
            return false;
        for (Character letter: name.toCharArray()) {
            if (!Character.isLetter(letter))
                return false;
        }
        return true;
    }


    public static boolean isValidPassword(String password) {
        if (password.length() < 8)
            return false;
        int numOfDigits = 0;
        int numOfCapitalLetters = 0;
        int numOfSpecialCharacters = 0;
        for (char letter : password.toCharArray()) {
            if (Character.isDigit(letter)) {
                numOfDigits++;
            }
            if (Character.isUpperCase(letter)) {
                numOfCapitalLetters++;
            }
            if (!Character.isLetterOrDigit(letter) && letter != ' ') {
                numOfSpecialCharacters++;
            }
        }
        return numOfCapitalLetters >= 2 && numOfDigits >= 2 && numOfSpecialCharacters >= 1;
    }

    public static boolean isValidEmail(String email) {
        String[] emailParts = email.split("@");
        if (emailParts.length == 2) {
            String[] domainParts = emailParts[1].split("[.]");
            return domainParts.length >= 2;
        } else
            return false;
    }
}
