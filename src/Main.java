import java.util.List;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.loadProperties();

        Category category = new Category("General");
        Question question = new Question("What is the capital of Sweden?", category, new String[] {"Stockholm", "London", "Berlin", "Madrid"}, 0);
        Question[] questions = new Question[] {question};
        List<Player> players = List.of(new Player("Player 1"), new Player("Player 2"));
        Round round = new Round(category, List.of(questions));
        Game game = new Game(players, new Round[] {new Round(category, List.of(questions))}, properties);
        System.out.println(game);
    }
}