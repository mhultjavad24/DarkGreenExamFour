import java.util.List;

public class Game {
    protected List<Player> players;
    protected Round[] rounds;
    protected int currentRoundIndex;
    protected int playerSelectsNextCategoryIndex;
    protected Properties properties;

    public Game(List<Player> players, Round[] rounds, Properties properties) {
        this.players = players;
        this.rounds = rounds;
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
}

