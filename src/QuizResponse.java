import java.io.Serializable;
import java.util.List;

public class QuizResponse implements Serializable {

    private List<Category> categories;
    private Result result;

    public QuizResponse(List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
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


}
