package adaptiveschool.auxiliary;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {

    private static final String dictionary =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789" +
            "!@#$%^&*_=+-/";

    /**
     * Method will generate random string based on the parameters
     * @param                               length of the random string
     * @return                              the random password
     */
    public static String generatePassword(int length) {
        return RandomStringUtils.random(length, dictionary);
    }
}
