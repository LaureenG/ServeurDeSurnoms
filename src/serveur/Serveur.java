package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur {
    public static void main(String[] args) throws IOException {
        int portEcoute = 1234;
        ServerSocket serverSocket = null;
        boolean listening = true;
        try {
            serverSocket = new ServerSocket(portEcoute);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(-1);
        }
        while (listening) {
            new ServeurThread(serverSocket.accept()).start();
        }
        serverSocket.close();
    }
}