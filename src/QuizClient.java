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

        try (Socket socket = new Socket("127.0.0.1", 55556);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server.");

            // detta är en kategori och skicka välkomstmeddelande
            Category category = new Category("Programming");
            QuizResponse response = new QuizResponse(QuizResponse.ResponseType.WELCOME, List.of(category), null);
//            out.writeObject(response);
//
//            // En endaste instans av GameUi
//            GameUI gameUI = new GameUI(List.of(category), null); //Här behöver vi mata in en del i konstruktorn
//
//            // Visar lobbyn
//            SwingUtilities.invokeLater(() -> {
//                gameUI.showLobbyPanel(true);
//                gameUI.setVisible(true);
//            });

            // Steg 4: Lyssna efter serverrespons
            Object inResponse;
            while ((inResponse = in.readObject()) != null) {
                System.out.println("Received response from server: " + inResponse);

                if (inResponse instanceof QuizResponse quizResponse) {
                    SwingUtilities.invokeLater(() -> {
                        Category c = quizResponse.getCategories().get(0); // Hämtar första kategorin
                        Question firstQuestion = c.getQuestions().get(0); // Hämtar första frågan

                        // Uppdatera befintlig GameUI-instans och visar frågan
//                        gameUI.showGamePanel(firstQuestion);
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