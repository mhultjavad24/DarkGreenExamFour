import javax.swing.SwingUtilities;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class QuizClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 55555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server.");


            out.writeObject("GET_CATEGORIES");
            List<Category> categories = (List<Category>) in.readObject();


            SwingUtilities.invokeLater(() -> {
                GameUI gameUI = new GameUI(categories, null);
                gameUI.showLobbyPanel(true);
                gameUI.setVisible(true);
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
