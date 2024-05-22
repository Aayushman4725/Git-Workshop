import java.util.Scanner;
import java.sql.*;

public class Login {
    private static String JDBC_URL = "jdbc:mysql://localhost:3306/chatapp";
    private static String USER = "root";
    private static String PASSWORD = "";
    private static String inputEmail;
    private static String inputPassword;

    // Separate function for user input prompts
    public void promptUserCredentials() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter email:");
        inputEmail = scanner.nextLine();

        System.out.println("Enter password:");
        inputPassword = Hash.getMd5Hash(scanner.nextLine()); // Hash the entered password
    }

    public boolean authenticateUser() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            System.out.println("Connected to the database");

            if (isEmailExists(connection, inputEmail)) {
                return checkPassword(connection, inputEmail, inputPassword);
            } else {
                System.out.println("Email does not exist. Authentication failed.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isEmailExists(Connection connection, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM userinfo WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, email exists
                }
            }
        }
        return false; // Error occurred
    }

    private static boolean changePassword(Connection connection,String email){

        String query = "UPDATE userinfo SET passcode=? WHERE email="+email;
        try {
            System.out.println("Enter email:");
            Scanner scanner = new Scanner(System.in);
            email = scanner.nextLine();
            if(isEmailExists(connection,email)) {
                System.out.println("Enter new password:");
                String Password = Hash.getMd5Hash(scanner.nextLine());
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(2, Password);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Password changed Successfully");
                        }
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static boolean checkPassword(Connection connection, String email, String password) throws SQLException {
        String query = "SELECT passcode FROM userinfo WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("passcode");
                    return hashedPasswordFromDB.equals(password);
                }
            }
        }
        return false; // Error occurred
    }

    public String getEmail() {
        return inputEmail;
    }

    public static void main(String[] args) {
        // Start the client
        Login login = new Login();
        login.promptUserCredentials();
        if (login.authenticateUser()) {
            System.out.println("Login successful!");
            // After successful login, start the client and pass the email
            Client client = new Client(login.getEmail());
            client.startClient();
        } else {
            System.out.println("Login failed. Invalid email or password.");
        }
    }
}
