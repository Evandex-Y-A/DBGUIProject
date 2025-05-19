package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Utility class for hashing plaintext passwords using the SHA-256 algorithm.
 * <p>
 * Provides a single static method to compute a hexadecimal representation
 * of the SHA-256 digest of an input password.
 * </p>
 * 
 * @author evandex
 */
public class Passwords {
    /**
     * Computes the SHA-256 hash of the given plaintext password and returns
     * it as a hexadecimal string.
     * <p>
     * Internally, this method:
     * <ol>
     *   <li>Obtains a {@link MessageDigest} instance for SHA-256.</li>
     *   <li>Computes the digest of the UTF-8–encoded password bytes.</li>
     *   <li>Converts each byte of the resulting hash into a two-character
     *       lowercase hexadecimal representation and appends it to a
     *       {@link StringBuilder}.</li>
     * </ol>
     * </p>
     *
     * @param password
     *        the plaintext password to be hashed; must not be {@code null}
     * @return
     *        the SHA-256 hash of the password, encoded as a lowercase
     *        hexadecimal string (64 characters long)
     * @throws RuntimeException
     *        if the SHA-256 algorithm is not available in the environment;
     *        wraps the underlying {@link NoSuchAlgorithmException}
     */
    public static String hasher(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : hash) {
                result.append(String.format("02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error", e);
        }
    }
}
