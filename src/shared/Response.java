package shared;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    public enum ResponseType {
        WELCOME,
        QUESTION,
        RESULT,
        WAIT
    }

    private List<Category> categories;
    private Category category;
    private Result result;
    private ResponseType type;

    public Response(List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
    }

    public Response(ResponseType type, List<Category> categories, Result result) {
        this.categories = categories;
        this.result = result;
        this.type = type;
    }

    public Response(ResponseType type, Category category, Result result) {
        this.category = category;
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

    public Category getCategory() {
        return category;
    }



}
