import java.io.FileInputStream;
import java.io.IOException;

public class Properties {
    protected int roundsPerGame;
    protected int questionsPerRound;

    public Properties() {
    }

    public void loadProperties() {
        java.util.Properties properties = new java.util.Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            this.roundsPerGame = Integer.parseInt(properties.getProperty("roundsPerGame"));
            this.questionsPerRound = Integer.parseInt(properties.getProperty("questionsPerRound"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }
}
