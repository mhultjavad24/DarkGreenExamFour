package shared;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    public enum ResponseType {
        BOOTUP,
        WELCOME,
        QUESTION,
        RESULT,
        RESULT_NO_ACTION,
        SCORE_UPDATE,
        WAIT,
        WAIT_ROUND,
        GAME_RESULT_WINNER,
        GAME_RESULT_LOSER,
        GAME_RESULT_DRAW,
    }

    private List<Category> categories;
    private Category category;
    private Result result;
    private ResponseType type;
    private String identifier;
    private int totalScorePlayer1;
    private int totalScorePlayer2;
    private int roundScore;
    private int roundsPerGame;
    private int questionsPerRound;

    public Response(List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
    }

    public Response(ResponseType type, List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
        this.type = type;
    }

    public Response(ResponseType type, Category category, Result result) {
        this.category = category;
        this.result = result;
        this.type = type;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public void setRoundsPerGame(int roundsPerGame) {
        this.roundsPerGame = roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }

    public void setQuestionsPerRound(int questionsPerRound) {
        this.questionsPerRound = questionsPerRound;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public int getTotalScorePlayer2() {
        return totalScorePlayer2;
    }

    public void setTotalScorePlayer2(int totalScorePlayer2) {
        this.totalScorePlayer2 = totalScorePlayer2;
    }

    public int getTotalScorePlayer1() {
        return totalScorePlayer1;
    }

    public void setTotalScorePlayer1(int totalScorePlayer1) {
        this.totalScorePlayer1 = totalScorePlayer1;
    }
}
