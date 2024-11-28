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
    private static String identifier;

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 55556);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            System.out.println("Connected to server.");
            Response inResponse;
            while ((inResponse = (Response) in.readObject()) != null) {
                switch (inResponse.getType()) {
                    case BOOTUP -> {
                        identifier = inResponse.getIdentifier();
                        gameUI = new GameUI(inResponse.getCategories(), inResponse.getRoundsPerGame(), inResponse.getQuestionsPerRound(), out, inResponse.getIdentifier());
                        gameUI.setOut(out);
                    }
                    case WELCOME -> {
                        SwingUtilities.invokeLater(() -> {
                            gameUI.showLobbyPanel(true);
                            gameUI.setVisible(true);
                        });
                    }
                    case WAIT -> {
                        SwingUtilities.invokeLater(() -> {
                            gameUI.showLobbyPanel(false);
                            gameUI.setVisible(true);
                        });
                    }
                    case WAIT_ROUND -> {


                    }
                    case QUESTION -> {
                        System.out.println("Question message received at Client");
                    }
                    case RESULT -> {
                        System.out.println("Result message received at Client");
                        gameUI.startRound(inResponse.getCategory());
                    }

                    case GAME_RESULT_WINNER -> {
                        JOptionPane.showMessageDialog(null, identifier + ": You won the game!");
                    }
                    case GAME_RESULT_LOSER -> {
                        JOptionPane.showMessageDialog(null, identifier + ": You lost the game!");
                    }
                    case GAME_RESULT_DRAW -> {
                        JOptionPane.showMessageDialog(null, identifier + ": It's a draw!");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}