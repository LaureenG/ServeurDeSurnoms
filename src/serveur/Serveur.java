package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur {
	public static final int PORT = 8080;
	
    public static void main(String[] args) throws IOException {
        // Création du socket d'écoute sur le port 1234
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ecoute sur le port "+PORT);
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("Could not listen on port: "+ PORT);
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