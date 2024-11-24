import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizClient { // denna klass bör uppdateras
    private static String playerName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine();
        try (Socket socket = new Socket("127.0.0.1", 55555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server.");


            Result result = null;
            List<Question> questions = new ArrayList<>();
            Category category = new Category("Programming");
            QuizResponse response = new QuizResponse(List.of(category), null);
            out.writeObject(response);

            Object inResponse;
            while ((inResponse = in.readObject()) != null) {
                System.out.println(response);
                if (inResponse instanceof QuizResponse quizResponse) {
                    SwingUtilities.invokeLater(() -> {
                        Category c = quizResponse.getCategories().getFirst();


                        GameUI gameUI = new GameUI(List.of(c), null);
                        gameUI.showGamePanel(c.getQuestions().getFirst(), out);
//                        gameUI.showLobbyPanel(true);
                        gameUI.setVisible(true);


                    });

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
