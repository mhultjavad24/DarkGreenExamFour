package client;

import shared.Category;
import shared.Player;
import shared.Question;
import shared.Round;
import server.Properties;

import java.util.List;

public class Game {
    protected List<Player> players;
    protected Round[] rounds;
    protected int currentRoundIndex;
    protected int playerSelectsNextCategoryIndex;
    protected Properties properties;



    public Game(List<Player> players, Round[] rounds, Properties properties) {
        this.players = players;
        this.rounds = new Round[properties.getRoundsPerGame()]; //skapar rundor pga antalet i prop
        this.currentRoundIndex = 0;
        this.playerSelectsNextCategoryIndex = 0;
        this.properties = properties;
    }

    public Round getCurrentRound() {
        return rounds[currentRoundIndex];
    }

    public void nextRound() {
        currentRoundIndex++;
    }

    public boolean hasFinished() {
        return currentRoundIndex > properties.getRoundsPerGame();
    }

    public Player getCurrentPlayer() {
        return players.get(playerSelectsNextCategoryIndex);
    }

    public void switchPlayer() {
        playerSelectsNextCategoryIndex = (playerSelectsNextCategoryIndex + 1) % players.size();
    }

    public void selectCategory(Category category, List<Question> questions) { //tänkte list ist för array kan d va bättre/sämre?
        rounds[currentRoundIndex] = new Round(category, questions);
    }
    public void AnswerCurrentQuestion(String answer) {
        Round currentRound = getCurrentRound();
        Player currentPlayer = getCurrentPlayer();

        Question currentQuestion = currentRound.getQuestion();
        boolean isCorrectAnswer = currentQuestion.isCorrectAnswer(answer);

        int playerIndex = players.indexOf(currentPlayer);
        currentRound.setPlayerResult(playerIndex,isCorrectAnswer);

        if (isCorrectAnswer) { //poäng t spelare
            currentPlayer.increaseScore();
        }
        if (!currentRound.nextQuestion() && currentRound.isFinished()) { //gå vidare till nästa fråga/avsluta
            nextRound();
            switchPlayer();
        }
    }
    public Player getWinner() { //metod för att hitta vinnaren ?
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }
}


