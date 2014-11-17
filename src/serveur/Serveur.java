package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur {
    public static void main(String[] args) throws IOException {
        // Création du socket d'écoute sur le port 1234
        int portEcoute = 1234;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portEcoute);
            System.out.println("Ecoute sur le port 1234");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(-1);
        }
        // Attente de connexions
        boolean listening = true;
        System.out.println("En attente de connexions...");
        while (listening) {
            // Création d'une thread pour gérer le client
            new ServeurThread(serverSocket.accept()).start();
        }
        // Fermeture du socket d'écoute
        serverSocket.close();
    }
}