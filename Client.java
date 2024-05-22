import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class Client {
    private String email;
    private String username;

    // Constructor to receive email information
    public Client(String email) {
        this.email = email;
    }

    private void fetchUsernameFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapp", "root", "")) {
            String query = "SELECT username FROM userinfo WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                    System.out.println("Fetched username from the database: " + username);
                } else {
                    System.out.println("Username not found for email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startClient() {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            fetchUsernameFromDatabase();
            
            // Send username to the server
            out.println(username);

            // Read messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send messages to the server
            while (true) {
                String message = scanner.nextLine();
                out.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Note: The email is now passed to the Client constructor
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
