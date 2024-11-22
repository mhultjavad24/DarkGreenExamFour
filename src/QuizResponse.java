import java.io.Serializable;
import java.util.List;

public class QuizResponse implements Serializable {

    private List<String> categories;
    private List<String> questions;
    private Result result;

    public QuizResponse(List<String> categories, List<String> questions, Result result) {
        this.categories = categories;
        this.questions = questions;
        this.result = result;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


}
