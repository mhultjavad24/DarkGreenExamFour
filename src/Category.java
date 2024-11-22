import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

    protected String name;
    protected List<Question> questions;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
