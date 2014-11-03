package Serveur;
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

import sds.Query;
import sds.Reply;

public class ServeurThread extends Thread {
    private Socket socket = null;

    public ServeurThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            boolean open = true;
            while (open) {
                Query query = (Query) in.readObject();
                int numeroService = query.getService();
                int codeErr = 418;
                Object obj = null;
                Data service = Data.getInstance();
                Set<String> set;
                switch (numeroService) {
                case 0:
                    open = false;
                    codeErr = 200;
                    break;
                case 1:
                    codeErr = service.putSurname(query.getArg1(),query.getArg2());
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
                   set = new HashSet<String>();
                    codeErr = service.getName(set);
                    break;
                case 6:
                    set = new HashSet<String>();
                    codeErr = service.getSurname(set, query.getArg1());
                    break;
                case 7:
                    codeErr = service.deleteSurname(query.getArg1());
                    break;
                case 8:
                    codeErr = service.deleteName(query.getArg1());
                    break;
                }
                Reply reply = new Reply(numeroService, codeErr, obj,
                        query.getQueryID());
                out.writeObject(reply);
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}