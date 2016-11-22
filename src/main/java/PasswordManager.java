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
 * This class exists to provide abstraction between the in-memory password storage map and the act of password storage, so the storage
 * mechanism could be easily changed to a database, a key value store such as Redis, or something generally
 * more appropriately production ready.
 * <p>
 * Created by Dylan McKee on 21/11/2016.
 */
public class PasswordManager {

    /**
     * Common words that appear in weak passwords. To be expanded in future.
     */
    private static final String[] COMMON_WORDS = {"password", "letmein"};

    /**
     * The minimum number of chars for a password to be considered acceptable.
     */
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    /**
     * PasswordManager singleton instance.
     */
    private static final PasswordManager ourInstance = new PasswordManager();

    /**
     * An in memory map to store the passwords in, associated with dog names.
     * <p>
     * This class exists to provide abstraction between the map and the act of password storage, so the storage
     * mechanism could be easily changed to a database, a key value store such as Redis, or something generally
     * more appropriately production ready.
     */
    private final Map<String, String> passwords = new HashMap<String, String>();

    /**
     * A blank private constructor; ensures singleton pattern is maintained.
     */
    private PasswordManager() {


    }

    /**
     * Gets singleton instance.
     *
     * @return the PasswordManager singleton instance.
     */
    public static PasswordManager getInstance() {
        return ourInstance;
    }

    /**
     * A private method to perform SHA256 hashing.
     * Implementation modified from https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
     *
     * @param password the password to be hashed.
     * @return the SHA256 string of the hashed passsword.
     */
    private String hash(String password) {
        // SHA256 implementation based off of https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
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

    /**
     * Stores the desired password, as a SHA256 hash, in an in memory password store, associated with the dog name.
     *
     * @param dogName  the dog's name.
     * @param password the password to store, as a plaintext string.
     */
    public void store(String dogName, String password) {
        String hashedPassword = hash(password);
        passwords.put(dogName, hashedPassword);

    }

    /**
     * Removes the previously stored password for the dog in the in-memory store (by replacing it with a null value).
     *
     * @param dogName the dog's name.
     */
    public void unstore(String dogName) {
        passwords.remove(dogName);
    }

    /**
     * Checks the password entered against the password stored for a given dog.
     *
     * @param dogName           the dog's name.
     * @param attemptedPassword the password entered by the user for the dog, in plaintext.
     * @return true if the password is correct - otherwise false.
     */
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

    /**
     * Checks if the password that the user desires to use is strong enough for our hyper-secure dog kenneling system.
     * Does this by checking length and ensuring that the password is not one that is commonly used.
     *
     * @param password the password, as a plaintext string.
     * @return true if the password is strong enough for our system, false if not.
     */
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
