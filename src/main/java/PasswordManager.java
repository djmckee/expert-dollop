import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple password manager, stores passwords in memory and checks them against attempts.
 * <p>
 * <p>
 * I looked up the SHA265 hashing performed in this class at https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
 * <p>
 * Obviously SHA265 isn't ideal/production ready alone, but this is just in-memory password storage for a proof of
 * concept web application designed for a simple assignment, with no access to a real database for techniques like
 * salting, so it'll do for this example case.
 * <p>
 * Created by Dylan McKee on 21/11/2016.
 */
public class PasswordManager {

    private static final String[] COMMON_WORDS = {"password", "letmein"};
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    private static final PasswordManager ourInstance = new PasswordManager();

    private final Map<String, String> passwords = new HashMap<String, String>();

    private PasswordManager() {


    }

    public static PasswordManager getInstance() {
        return ourInstance;
    }

    private String hash(String password) {
        // SHA265 implementation based off of https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
        MessageDigest hashOfText = null;
        try {
            hashOfText = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            hashOfText.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = hashOfText.digest();

        return String.format("%064x", new BigInteger(1, digest));

    }

    public void store(String dogName, String password) {
        String hashedPassword = hash(password);
        passwords.put(dogName, hashedPassword);

    }

    public void unstore(String dogName) {
        passwords.put(dogName, null);
    }

    public boolean checkPassword(String dogName, String attemptedPassword) {
        String hashedPassword = hash(attemptedPassword);

        if (passwords.get(dogName) != null) {
            String storedHash = passwords.get(dogName);
            if (storedHash.equals(hashedPassword)) {
                // Hashes match; correct password...
                return true;
            }
        }

        return false;
    }

    public boolean isPasswordStrongEnough(String password) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            return false;
        }

        for (String w : COMMON_WORDS) {
            if (w.equalsIgnoreCase(password)) {
                // Too common for this doggy palace.
                return false;
            }
        }

        // We'll have to assume it's tough enough...
        return true;

    }

}
