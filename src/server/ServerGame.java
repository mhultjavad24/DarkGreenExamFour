package server;

import server.Database;
import shared.Category;
import shared.Response;

import java.util.List;

public class ServerGame extends Thread {
    private PlayerConnection player1;
    private PlayerConnection player2;
    private PlayerConnection currentPlayer;
    private int roundsPerGame;
    private int questionsPerRound;
    private boolean isPlayerOneTurn = true;

    public ServerGame(PlayerConnection player1, PlayerConnection player2, int roundsPerGame, int questionsPerRound) {
        this.player1 = player1;
        this.player2 = player2;
        this.roundsPerGame = roundsPerGame;
        this.questionsPerRound = questionsPerRound;
        this.currentPlayer = player1;

        List<Category> categories = Database.getAllCategories();



        Response welcomeResponse = new Response(Response.ResponseType.WELCOME, categories, null);
        Response welcomeResponseTwo = new Response(Response.ResponseType.WAIT, categories, null);
        this.player1.sendResponse(welcomeResponse);
        this.player2.sendResponse(welcomeResponseTwo);
    }

    @Override
    public void run() {
        while (true) {
            Response response = currentPlayer.receiveResponse();
            if (response == null) {
                System.out.println("Player disconnected");
                break;
            }

            switch (response.getType()) {
                case RESULT -> {
                    // Skicka resultat till båda spelarna
                    player1.sendResponse(response);
                    player2.sendResponse(response);

                    // Växla spelare
                    isPlayerOneTurn = !isPlayerOneTurn;
                    currentPlayer = isPlayerOneTurn ? player1 : player2;

                    // Skicka meddelande om vems tur det är
                    player1.sendResponse(new Response(Response.ResponseType.MESSAGE,
                            isPlayerOneTurn ? "Your turn!" : "Waiting for opponent..."));
                    player2.sendResponse(new Response(Response.ResponseType.MESSAGE,
                            !isPlayerOneTurn ? "Your turn!" : "Waiting for opponent..."));
                }
            }
        }
    }



//        for (int i = 0; i < roundsPerGame; i++) {
//            player1.sendWelcome();
//            player2.sendWelcome();
//
//            for (int j = 0; j < questionsPerRound; j++) {
//                player1.sendQuestion();
//                player2.sendQuestion();
//
//                player1.sendResult();
//                player2.sendResult();
//            }
//        }
    }


