package client;

import shared.Category;
import shared.Question;
import shared.Response;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static String playerName;
    private static GameUI gameUI;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your name: ");
//        playerName = scanner.nextLine();

        try (Socket socket = new Socket("127.0.0.1", 55556);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            System.out.println("Connected to server.");

////             En endaste instans av GameUi
////            GameUI gameUI = new GameUI(List.of(category), null); //Här behöver vi mata in en del i konstruktorn
//            Question question = new Question("Vad är huvudstaden i Sverige?", category, new String[]{"Göteborg", "Stockholm", "Malmö", "Uppsala"}, 1);
//            GameUI gameUI = new GameUI(List.of(category), List.of(question), 3, 3, null, true);
//            gameUI.setOut(out);

            // Visar lobbyn
//            SwingUtilities.invokeLater(() -> {
//                gameUI.showLobbyPanel(true);
//                gameUI.setVisible(true);
//            });

            // Steg 4: Lyssna efter serverrespons
            Response inResponse;
            while ((inResponse = (Response) in.readObject()) != null) {
                // switch
                switch (inResponse.getType()) {
                    case WELCOME -> {
                        System.out.println("Welcome message received at Client");
                        gameUI = new GameUI(inResponse.getCategories(), 3, 3, null, true);
                        gameUI.setOut(out);
                        SwingUtilities.invokeLater(() -> {
                            gameUI.showLobbyPanel(true);
                            gameUI.setVisible(true);
                        });

                    }
                    case WAIT -> {
                        System.out.println("Welcome message received at Client");
                        gameUI = new GameUI(inResponse.getCategories(), 3, 3, null, false);
                        gameUI.setOut(out);
                        SwingUtilities.invokeLater(() -> {
                            gameUI.showLobbyPanel(false);
                            gameUI.setVisible(true);
                        });

                    }
                    case QUESTION -> {
                        System.out.println("Question message received at Client");
                    }
                    case RESULT -> {
                        System.out.println("Result message received at Client");
                        gameUI.startRound(inResponse.getCategory());
                    }

                }

//                if (inResponse instanceof Response quizResponse) {
//                    SwingUtilities.invokeLater(() -> {
//                        Category c = quizResponse.getCategories().get(0); // Hämtar första kategorin
//                        Question firstQuestion = c.getQuestions().get(0); // Hämtar första frågan
//
//                        // Uppdatera befintlig client.GameUI-instans och visar frågan
//                        gameUI.showGamePanel(firstQuestion);
//                    });
//                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}