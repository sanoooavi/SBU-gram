package Server;

import Client.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    /**
     * welcome to sbu gram app
     * by this app you can communicate with your friend and family ba have a chat or sharing posts and picture every time
     * to use this you should first sign up and then you just get what to do
     * hope you enjoy!
     * @author sanaMousavi
     * @since May 2021
     */
    private static final int PORT = 2222;
    public static boolean isServerUp = true;
    public static Map<String, Profile> users;

    public static boolean isServerUp() {
        return isServerUp;
    }

    /**
     * first this method should be run so that the server get activates
     * @param args not use ful just to run the app
     */
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
