import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class QuizClient {
    private static String playerName;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine();
        try (Socket socket = new Socket("127.0.0.1", 55555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server.");

            Category category = new Category("Programming");
            QuizResponse response = new QuizResponse(List.of(category), null);
            out.writeObject(response);
            GameUI gameUI = new GameUI(List.of(category), null);

            SwingUtilities.invokeLater(() -> {
                gameUI.showLobbyPanel(true);
                gameUI.setVisible(true);
            });

            Object inResponse;
            while ((inResponse = in.readObject()) != null) {
                System.out.println(inResponse);
                if (inResponse instanceof QuizResponse quizResponse) {
                    SwingUtilities.invokeLater(() -> {
                        Category c = quizResponse.getCategories().getFirst();
                        gameUI.showLobbyPanel(true);

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