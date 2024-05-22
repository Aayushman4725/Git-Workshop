import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Create a new ClientHandler for the new client
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                // Notify existing clients about the new client
                // broadcastMessage(clientHandler.getNickname() + " joined the chat");

                // Add the new client to the list
                clients.add(clientHandler);

                // Start a new thread for the new client
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private Scanner in;
        private String nickname;
    
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Set the nickname for the client
                if (in.hasNextLine()) {
                    nickname = in.nextLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Notify the existing clients about the new client
                broadcastMessage(nickname + " joined the chat");

                while (true) {
                    if (in.hasNextLine()) {
                        String message = in.nextLine();
                        System.out.println("Received: " + message);

                        // Broadcast the message to all clients
                        broadcastMessage(nickname + ": " + message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        public void sendMessage(String message) {
            out.println(message);
        }
    
        public String getNickname() {
            return nickname;
        }
    }
}
