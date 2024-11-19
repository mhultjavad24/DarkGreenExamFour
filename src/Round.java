import java.util.List;

public class Round {
    protected Category category;
    protected Question[] questions;
    protected boolean[] player1Results;
    protected boolean[] player2Results;
    protected int currentQuestionIndex;

    public Round(Category category, List<Question> questions) {
        this.category = category;
        this.questions = questions.toArray(new Question[0]);
        this.player1Results = new boolean[questions.size()];
        this.player2Results = new boolean[questions.size()];
        this.currentQuestionIndex = 0;
    }

    public Question getQuestion() {
        return questions[currentQuestionIndex];
    }
}
