import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    private List<Integer> player1RoundScores;
    private List<Integer> player2RoundScores;

    public Result(List<Integer> player1RoundScores, List<Integer> player2RoundScores) {
        this.player1RoundScores = player1RoundScores;
        this.player2RoundScores = player2RoundScores;
    }

    public List<Integer> getPlayer1RoundScores() {
        return player1RoundScores;
    }

    public void setPlayer1RoundScores(List<Integer> player1RoundScores) {
        this.player1RoundScores = player1RoundScores;
    }

    public List<Integer> getPlayer2RoundScores() {
        return player2RoundScores;
    }

    public void setPlayer2RoundScores(List<Integer> player2RoundScores) {
        this.player2RoundScores = player2RoundScores;
    }
}


