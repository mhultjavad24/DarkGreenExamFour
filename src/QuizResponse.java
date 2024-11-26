import java.io.Serializable;
import java.util.List;

public class QuizResponse implements Serializable {

    public enum ResponseType {
        WELCOME,
        QUESTION,
        RESULT
    }

    private List<Category> categories;
    private Result result;
    private ResponseType type;

    public QuizResponse(List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
    }

    public QuizResponse(ResponseType type, List<Category> categories, Result result) {
        this.categories = categories;
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


}
