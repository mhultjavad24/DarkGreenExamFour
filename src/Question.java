public class Question {
    protected String text;
    protected Category category;
    protected String[] answers;
    protected int correctOption;

    public Question(String text, Category category, String[] answers, int correctOption) {
        this.text = text;
        this.category = category;
        this.answers = answers;
        this.correctOption = correctOption;
    }

    public String getText() {
        return text;
    }

    public Category getCategory() {
        return category;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public boolean isCorrectAnswer(String answer) {
        return answers[correctOption].equals(answer);
    }
}

