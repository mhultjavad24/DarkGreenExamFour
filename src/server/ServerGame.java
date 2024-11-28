package server;

import server.Database;
import shared.Category;
import shared.Response;

import java.util.ArrayList;
import java.util.List;

public class ServerGame extends Thread {
    private PlayerConnection player1;
    private PlayerConnection player2;
    private PlayerConnection currentPlayer;
    private int currentRound = 0;
    private int roundsPerGame;
    private int questionsPerRound;
    private int resultsThisRound = 0;
    private List<Integer> player1Results = new ArrayList<>();
    private List<Integer> player2Results = new ArrayList<>();

    public ServerGame(PlayerConnection player1, PlayerConnection player2, int roundsPerGame, int questionsPerRound) {
        this.player1 = player1;
        this.player2 = player2;
        this.roundsPerGame = roundsPerGame;
        this.questionsPerRound = questionsPerRound;
        this.currentPlayer = player1;

        List<Category> categories = Database.getAllCategories();
        this.bootup(categories);
        Response welcomeResponse = new Response(Response.ResponseType.WELCOME, categories, null);
        Response welcomeResponseTwo = new Response(Response.ResponseType.WAIT, categories, null);
        this.player1.sendResponse(welcomeResponse);
        this.player2.sendResponse(welcomeResponseTwo);
    }

    public PlayerConnection getOpponent() {
        return currentPlayer == player1 ? player2 : player1;
    }

    public void reportScore() {
        Response scoreResponse = new Response(Response.ResponseType.SCORE_UPDATE, (List<Category>) null, null);
        int player1Total = player1Results.stream().mapToInt(Integer::intValue).sum();
        int player2Total = player2Results.stream().mapToInt(Integer::intValue).sum();
        scoreResponse.setTotalScorePlayer1(player1Total);
        scoreResponse.setTotalScorePlayer2(player2Total);
        player1.sendResponse(scoreResponse);
        player2.sendResponse(scoreResponse);
    }

    public void checkWinState() {
        if (player1Results.size() >= roundsPerGame && player2Results.size() >= roundsPerGame) {
            int player1Total = player1Results.stream().mapToInt(Integer::intValue).sum();
            int player2Total = player2Results.stream().mapToInt(Integer::intValue).sum();
            if (player1Total > player2Total) {
                player1.sendResponse(new Response(Response.ResponseType.GAME_RESULT_WINNER, (List<Category>) null, null));
                player2.sendResponse(new Response(Response.ResponseType.GAME_RESULT_LOSER, (List<Category>) null, null));
            } else if (player1Total < player2Total) {
                player1.sendResponse(new Response(Response.ResponseType.GAME_RESULT_LOSER, (List<Category>) null, null));
                player2.sendResponse(new Response(Response.ResponseType.GAME_RESULT_WINNER, (List<Category>) null, null));
            } else {
                player1.sendResponse(new Response(Response.ResponseType.GAME_RESULT_DRAW, (List<Category>) null, null));
                player2.sendResponse(new Response(Response.ResponseType.GAME_RESULT_DRAW, (List<Category>) null, null));
            }

        }

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
                case WELCOME -> {
                    System.out.println("Welcome message received at ServerGame");
                }
                case QUESTION -> {
                }
                case RESULT_NO_ACTION -> {
                    if (response.getIdentifier().equals("Player 1")) {
                        player1Results.add(response.getRoundScore());
                    } else {
                        player2Results.add(response.getRoundScore());
                    }

                }
                case RESULT -> {
                    if (response.getIdentifier().equals("Player 1")) {
                        player1Results.add(response.getRoundScore());
                    } else {
                        player2Results.add(response.getRoundScore());
                    }
                    resultsThisRound++;
                    if (resultsThisRound >= 2) {
                        resultsThisRound = 0;
                        currentRound++;
                    }
                    getOpponent().sendResponse(response);
                    currentPlayer = getOpponent();
                }
            }


            System.out.println("Player 1 results: " + player1Results);
            System.out.println("Player 2 results: " + player2Results);

            this.reportScore();
            this.checkWinState();

        }
    }

    public void bootup(List<Category> categories) {
        Response bootResponseOne = new Response(Response.ResponseType.BOOTUP, categories, null);
        bootResponseOne.setIdentifier("Player 1");
        bootResponseOne.setRoundsPerGame(roundsPerGame);
        bootResponseOne.setQuestionsPerRound(questionsPerRound);
        Response bootResponseTwo = new Response(Response.ResponseType.BOOTUP, categories, null);
        bootResponseTwo.setRoundsPerGame(roundsPerGame);
        bootResponseTwo.setQuestionsPerRound(questionsPerRound);
        bootResponseTwo.setIdentifier("Player 2");
        this.player1.sendResponse(bootResponseOne);
        this.player2.sendResponse(bootResponseTwo);

    }

}
