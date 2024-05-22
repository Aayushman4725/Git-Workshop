import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Hash {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Hash the password
        String hashedPassword = getMd5Hash(password);
        System.out.println("Hashed Password: " + hashedPassword);

        scanner.close();
    }

    // Hash function to get the MD5 hash
    public static String getMd5Hash(String input) {
        try {
            // Get MD5 instance
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calculating message digest of an input that returns an array of bytes
            byte[] messageDigest = md.digest(input.getBytes());

            // Converting byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Converting message digest into hex value
            String hashtext = no.toString(16);

            // Adding leading zeros if needed
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
