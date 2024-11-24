import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Object inputLine;
            while ((inputLine = in.readObject()) != null) {
                if (inputLine instanceof QuizResponse response) {
                    if (response.getResult() != null) {
                        System.out.println(response.getResult().getPlayer1RoundScores());
                    } else {
                        System.out.println(response.getCategories().getFirst().getName());

                        Question q = new Question(
                                "Vad är huvudstaden i Sverige?",
                                new Category("Geografi"),
                                new String[]{"Göteborg", "Stockholm", "Malmö", "Uppsala"},
                                1  // Stockholm , index 1
                        );
                    }
                } else if (inputLine instanceof String request) {
                    if ("GET_CATEGORIES".equals(request)) {
                        List<Category> categories = List.of(
                                new Category("Programming"),
                                new Category("Animals and Nature"),
                                new Category("History")
                        );
                        out.writeObject(categories);
                        out.flush();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                QuizServer.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}