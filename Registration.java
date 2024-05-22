import java.util.Scanner;
import java.sql.*;

public class Registration {
    private static String JDBC_URL = "jdbc:mysql://localhost:3306/chatapp";
    private static String USER = "root";
    private static String PASSWORD = "";
    private static String INSERT_QUERY = "INSERT INTO userinfo (email, passcode, username) VALUES (?,?,?)";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

            System.out.println("Connected to the database");


            System.out.println("Enter email");
            String inputEmail = scanner.nextLine();

            EmailVerification ev = new EmailVerification(inputEmail);
            // Hash hash = new Hash();

            if (ev.isValid()) {
                
                System.out.println("Email is valid.");
                System.out.println("Enter a username:");
                String username = scanner.nextLine();
                System.out.println("Enter a password:");
                String passcode = Hash.getMd5Hash(scanner.nextLine()); // Hash the password
                
                insertUser(connection, inputEmail, passcode, username);
                displayUsers(connection, inputEmail, passcode, username);
                
            } else {
                System.out.println("Email is not valid.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isEmailUnique(Connection connection, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM userinfo WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0; // If count is 0, email is unique
                }
            }
        }
        return false; // Error occurred
    }
    private static void insertUser(Connection connection, String email, String passcode, String username) throws SQLException {
        
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY)) {
            
            if (!isEmailUnique(connection, email)) {
                System.out.println("Email already exists. Insertion unsuccessful.");
                return;
            }

            // stmt.setInt(1,);
            stmt.setString(1, email);
            stmt.setString(2, passcode);
            stmt.setString(3, username);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    private static void displayUsers(Connection connection, String email, String passcode, String username) throws SQLException {
        String query = "SELECT * FROM userinfo";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // int id = resultSet.getInt("id");
                String emailDb= resultSet.getString("email");
                String passcodeDb = resultSet.getString("passcode");
                String usernameDb = resultSet.getString("username");
                System.out.println(
                        "email: " + emailDb + ", passcode: " + passcodeDb + ", username: " + usernameDb);
            }
        }
    }
}
