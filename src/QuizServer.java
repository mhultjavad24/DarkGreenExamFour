import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    public static void main(String[] args) throws IOException {
        int port = 55556;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Quiz Server is running on port " + port);

        while (true) {
            try {

                System.out.println("Waiting for Player");
                Socket player1Socket = serverSocket.accept();
                ClientHandler player1 = new ClientHandler(player1Socket);
                System.out.println("Player 1 connected");

                System.out.println("Waiting for Player");
                Socket player2Socket = serverSocket.accept();
                ClientHandler player2 = new ClientHandler(player2Socket);
                System.out.println("Player 2 connected");


                new Thread(() -> {
                    try {
                        System.out.println("Starting a new game for Player 1 and Player 2.");
                        Thread player1Thread = new Thread(player1);
                        Thread player2Thread = new Thread(player2);

                        player1Thread.start();
                        player2Thread.start();

                        player1Thread.join();
                        player2Thread.join();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
