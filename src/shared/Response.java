package shared;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    public enum ResponseType {
        WELCOME,
        QUESTION,
        RESULT,
        WAIT,
        MESSAGE // Ny typ för meddelanden
    }

    private List<Category> categories;
    private Category category;
    private Result result;
    private ResponseType type;
    private String message; // Nytt fält för textmeddelanden

    // Konstruktorer
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

    public Response(ResponseType type, String message) {
        this.type = type;
        this.message = message;
    }

    // Getters och Setters
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    // t-S för debugging
    @Override
    public String toString() {
        return "Response{" +
                "categories=" + categories +
                ", category=" + category +
                ", result=" + result +
                ", type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}