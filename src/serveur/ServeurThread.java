package serveur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.Data;

import sds.ErrorCode;
import sds.Query;
import sds.Reply;
import sds.Service;

public class ServeurThread extends Thread {
	private static int nombreConnexion = 0;
	private int numeroConnexion;
    private Socket socket = null;
    private Object obj = null;

    public ServeurThread(Socket socket) {
        super("MultiServerThread");
        this.numeroConnexion = ++nombreConnexion;
        this.socket = socket;
    }

    private Query reception(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        System.out.print(numeroConnexion + " > Reception ... \t\t");
        return (Query) in.readObject();
    }

    private void envoi(Reply reply, ObjectOutputStream out) throws IOException {
        System.out.print(numeroConnexion + " > Envoi ... \t\t");
        out.writeObject(reply);
        out.flush();
        System.out.println("[OK]");
    }

    private Reply execution(Query query) {
        System.out.println(numeroConnexion + " > Dépaquetage ...");
        int numeroService = query.getService();
        int codeErr = ErrorCode.IM_A_TEAPOT;
        obj = null;
        codeErr = appelService(query);
        System.out.println(numeroConnexion + " > Paquetage ...");
        if (codeErr < 0) {
            return null;
        } else {
            return new Reply(numeroService, codeErr, obj, query.getQueryID());
        }
    }

    private int appelService(Query query) {
        Data service = Data.getInstance();
        Set<String> set = new HashSet<String>();
        int codeErr = -1;
        switch (query.getService()) {
        case Service.PUT_SURNAME:
            codeErr = service.putSurname(query.getArg1(), query.getArg2());
            break;
        case Service.POST_NAME:
            codeErr = service.postName(query.getArg1(), query.getArg2());
            break;
        case Service.POST_SURNAME:
            codeErr = service.postSurname(query.getArg1(), query.getArg2());
            break;
        case Service.GET_ALL:
            HashMap<String, List<String>> map = new HashMap<String, List<String>>();
            codeErr = service.getAll(map);
            obj = map;
            break;
        case Service.GET_NAME:
            codeErr = service.getName(set);
            obj = set;
            break;
        case Service.GET_SURNAME:
            codeErr = service.getSurname(set, query.getArg1());
            obj = set;
            break;
        case Service.DELETE_SURNAME:
            codeErr = service.deleteSurname(query.getArg1());
            break;
        case Service.DELETE_NAME:
            codeErr = service.deleteName(query.getArg1());
            break;
        }
        return codeErr;
    }

    public void run() {
        System.out.println("Nouveau client!");
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    socket.getOutputStream()));
            in = new ObjectInputStream(new BufferedInputStream(
                    socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        boolean open = true;
        while (open) {
            Query query;
            try {
                query = reception(in);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Reply reply = execution(query);
            if (reply != null) {
                try {
                    envoi(reply, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                open = false;
            }
        }
        System.out.println(numeroConnexion + " > Fermeture de la connexion ...");
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}