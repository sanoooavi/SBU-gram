package Server;

import Client.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private static final int PORT = 2222;
    public static boolean isServerUp = true;
    public static Map<String, Profile> users;

    public static boolean isServerUp() {
        return isServerUp;
    }

    public static void main(String[] args) {
        DataManager.getInstance().initializeServer();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

        } catch (IOException e) {
            System.exit(12);
        }
        while (isServerUp()) {
            Socket currentUserSocket = null;
            try {
                currentUserSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(currentUserSocket);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
