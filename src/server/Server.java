package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 55556;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Quiz Server is running on port " + port);

        while (true) {
            try {

                System.out.println("Waiting for shared.Player");
                Socket player1Socket = serverSocket.accept();
                PlayerConnection player1 = new PlayerConnection(player1Socket, "Player 1");
                System.out.println("Player 1 connected");

                System.out.println("Waiting for shared.Player");
                Socket player2Socket = serverSocket.accept();
                PlayerConnection player2 = new PlayerConnection(player2Socket, "Player 2");
                System.out.println("Player 2 connected");


                Properties properties = new Properties();
                properties.loadProperties();
                ServerGame serverGame = new ServerGame(player1, player2, properties.getRoundsPerGame(), properties.getQuestionsPerRound());
                serverGame.start();


//                new Thread(() -> {
//                    try {
//                        System.out.println("Starting a new game for Player 1 and Player 2.");
//                        Thread player1Thread = new Thread(player1);
//                        Thread player2Thread = new Thread(player2);
//
//                        player1Thread.start();
//                        player2Thread.start();
//
//                        player1Thread.join();
//                        player2Thread.join();
//
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
