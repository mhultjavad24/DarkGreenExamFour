import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Category> categories = List.of(
                    new Category("Programming"),
                    new Category("Animals and Nature"),
                    new Category("History")
            );

            Object input;
            while ((input = in.readObject()) != null) {
                if (input instanceof String request) {
                    if ("GET_CATEGORIES".equals(request)) {
                        out.writeObject(categories);
                        out.flush();
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
