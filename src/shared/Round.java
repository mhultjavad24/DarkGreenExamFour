package shared;

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


    public boolean nextQuestion() {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            return true;
        }
        return false;
    }

    //Kontroll om rundan är klar/
    public boolean isFinished() {
        return currentQuestionIndex >= questions.length - 1;
    }

    public void setPlayerResult(int playerIndex, boolean isCorrect) {
    }
}


