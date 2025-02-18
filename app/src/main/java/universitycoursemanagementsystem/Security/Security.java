package universitycoursemanagementsystem.Security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;



public class Security {
    
    class passwordHash{
        private static final int ITERATIONS = 65536;
        private static final int KEY_LENGTH = 256;
        private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
        private static final SecureRandom RAND = new SecureRandom();

        public static String generateSalt(final int length) {
            if (length < 1) {
                System.err.println("error in generateSalt: length must be > 0");
                return null;
            }
            byte[] salt = new byte[length];
            RAND.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }

        public static String hashPassword(String password, String salt) {
            char[] chars = password.toCharArray();
            byte[] bytes = salt.getBytes();
            PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
            try {
                SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
                byte[] securePassword = fac.generateSecret(spec).getEncoded();
                return Base64.getEncoder().encodeToString(securePassword);
            } catch (Exception e) {
                System.err.println("Exception encountered in hashPassword()");
                return null;
            } finally {
                spec.clearPassword();
            }
        }

        public static boolean verifyPassword(String password, String key, String salt) {
            String optEncrypted = hashPassword(password, salt);
            return optEncrypted != null && optEncrypted.equals(key);
        }

        

    }


}
