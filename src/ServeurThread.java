import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                int codeErr;
                Object obj = null;
                switch (numeroService) {
                case 0:
                    // codeErr =
                    break;
                case 1:
                    // codeErr =
                    break;
                case 2:
                    // codeErr =
                    break;
                case 3:
                    // codeErr =
                    break;
                case 4:
                    // codeErr =
                    break;
                case 5:
                    // codeErr =
                    break;
                case 6:
                    // codeErr =
                    break;
                case 7:
                    // codeErr =
                    break;
                case 8:
                    // codeErr =
                    break;
                }
                Reply reply = new Reply(numeroService, codeErr, obj,
                        query.getQueryID());
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