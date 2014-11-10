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

public class ServeurThread extends Thread {
    private Socket socket = null;

    public ServeurThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    private Query reception(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        System.out.print("Reception ... \t\t");
        return (Query) in.readObject();
    }

    private void envoi(Reply reply, ObjectOutputStream out) throws IOException {
        System.out.print("Envoi ... \t\t");
        out.writeObject(reply);
        out.flush();
        System.out.println("[OK]");
    }

    private Reply execution(Query query) {
        System.out.println("Dépaquetage ...");
        int numeroService = query.getService();
        int codeErr = ErrorCode.IM_A_TEAPOT;
        Object obj = null;
        codeErr = appelService(query, obj);
        System.out.println("Paquetage ...");
        if (codeErr < 0) {
            return null;
        } else {
            return new Reply(numeroService, codeErr, obj, query.getQueryID());
        }
    }

    private int appelService(Query query, Object obj) {
        Data service = Data.getInstance();
        Set<String> set = new HashSet<String>();
        int codeErr = -1;
        switch (query.getService()) {
        case 1:
            codeErr = service.putSurname(query.getArg1(), query.getArg2());
            break;
        case 2:
            codeErr = service.postName(query.getArg1(), query.getArg2());
            break;
        case 3:
            codeErr = service.postSurname(query.getArg1(), query.getArg2());
            break;
        case 4:
            HashMap<String, List<String>> map = new HashMap<String, List<String>>();
            codeErr = service.getAll(map);
            obj = map;
            break;
        case 5:
            codeErr = service.getName(set);
            break;
        case 6:
            codeErr = service.getSurname(set, query.getArg1());
            break;
        case 7:
            codeErr = service.deleteSurname(query.getArg1());
            break;
        case 8:
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
        System.out.println("CLOSE");
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}