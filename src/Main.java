import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        GameUI gameUI = new GameUI();

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Animals and Nature"));
        Category c1 = new Category("History");
        categories.add(c1);

        String[] answers = {"1939", "1940", "1941", "1942"};
        Question question = new Question("When did second world war start?", c1, answers, 0);

//        gameUI.renderCategorySelection(categories);
//
        gameUI.renderQuestion(question);
    }
}