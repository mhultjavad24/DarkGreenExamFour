import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class QuizServer {
    public static void main(String[] args) {
        int port = 55556;

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Server is running on port " + port);

            Object inputLine;

            while ((inputLine = in.readObject()) != null) {
                if (inputLine instanceof QuizResponse response) {
                    if (response.getResult() != null) {
                        System.out.println(response.getResult().getPlayer1RoundScores());
                    } else {
                        System.out.println(response.getCategories().getFirst().getName());

                        Question q = new Question("What data type is used to store text?", new Category("Programming"), new String[]{"Integer", "Float", "String", "Boolean"}, 2);
                        Category category = new Category("Programming", List.of(q));
                        response.setCategories(List.of(category));
                        out.writeObject(response);
                    }

                }
            }


//                while (true) {
//                    System.out.println("New client connected");
//
//
//                    new Thread(new ClientHandler(clientSocket)).start();
//                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
