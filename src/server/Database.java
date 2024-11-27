package server;

import java.util.ArrayList;
import java.util.List;

import shared.Category;
import shared.Question;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Database {

    public Database() {

        Properties properties = new Properties();
        properties.loadProperties();
        Category category = new Category("General");
        Question question = new Question("What is the capital of Sweden?", category, new String[]{"Stockholm", "London", "Berlin", "Madrid"}, 0);
        Question[] questions = new Question[]{question};
//        List<shared.Player> players = List.of(new shared.Player("Player 1"), new shared.Player("Player 2"));
//        shared.Round round = new shared.Round(category, List.of(questions));
//        Game game = new Game(players, new shared.Round[]{new shared.Round(category, List.of(questions))}, properties);

        List<Category> categories2 = getCategories();
        System.out.println(categories2.toString());
        List<Question> allQuestions = getAllQuestions();
        System.out.println(allQuestions.toString());

        List<Question> programmingQuestions = getQuestionsByCategory("Programming");
        System.out.println(programmingQuestions.toString());

         // client.GameUI gameUI = new client.GameUI();

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Animals and Nature"));
        Category c1 = new Category("History");
        categories.add(c1);

        String[] answers = {"1939", "1940", "1941", "1942"};
        Question question3 = new Question("When did second world war start?", c1, answers, 0);

//        gameUI.renderCategorySelection(categories);
//
      //  gameUI.renderQuestion(question3);
    }

    public static void main(String[] args) {
        Database m = new Database();
    }

    public static List<Category> getAllCategories() {
        List<Question> programmingQuestions = List.of(
                new Question("What data structure follows the LIFO (Last In, First Out)  principle?", new Category("Programming"), new String[]{"Queue", "Linked List", "Stack", "Array"}, 2),
                new Question("What does PI in API mean?", new Category("Programming"), new String[]{"Programming Interface", "Programming Instructions", "Processing Interface", "Processing Instructions"}, 0),
                new Question("What data type is used to store text?", new Category("Programming"), new String[]{"Integer", "Float", "String", "Boolean"}, 2)
        );
        List<Question> animalsAndNatureQuestions = List.of(
                new Question("What is the largest living animal on Earth?", new Category("Animals and nature"), new String[]{"African Elephant", "Blue Whale", "Saltwater Crocodile", "Polar Bear"}, 1),
                new Question("Which color is a ripe banana?", new Category("Animals and nature"), new String[]{"Green", "Orange", "Purple", "Yellow"}, 3),
                new Question("Which animal is known as the \"King of the Jungle\"?", new Category("Animals and nature"), new String[]{"Tiger", "Bear", "Elephant", "Lion"}, 3)
        );
        List<Question> historyQuestions = List.of(
                new Question("What year did World War 1 begin?", new Category("History"), new String[]{"1914", "1917", "1919", "1923"}, 0),
                new Question("Which ancient civilization built the pyramids of Giza?", new Category("History"), new String[]{"Romans", "Greeks", "Egyptians", "Mayans"}, 2),
                new Question("Who painted the Mona Lisa?", new Category("History"), new String[]{"Michelangelo", "da Vinci", "Raphael", "Donatello"}, 1)
        );
        List<Category> categories = new ArrayList<>();
        Category programmingCategory = new Category("Programming", programmingQuestions);
        Category animalsAndNatureCategory = new Category("Animals and nature", animalsAndNatureQuestions);
        Category historyCategory = new Category("History", historyQuestions);
        categories.add(programmingCategory);
        categories.add(animalsAndNatureCategory);
        categories.add(historyCategory);
        return categories;
    }

    public static List<Question> getAllQuestions() {
        List<Question> programmingQuestions = List.of(
                new Question("What data structure follows the LIFO (Last In, First Out)  principle?", new Category("Programming"), new String[]{"Queue", "Linked List", "Stack", "Array"}, 2),
                new Question("What does API stand for?", new Category("Programming"), new String[]{"Application Programming Interface", "Advanced Programming Insturctions", "Application Processding Interface", "Advanced Processing Instructions"}, 0),
                new Question("What data type is used to store text?", new Category("Programming"), new String[]{"Integer", "Float", "String", "Boolean"}, 2)
        );
        List<Question> animalsAndNatureQuestions = List.of(
                new Question("What is the largest living animal on Earth?", new Category("Animals and nature"), new String[]{"African Elephant", "Blue Whale", "Saltwater Crocodile", "Polar Bear"}, 1),
                new Question("Which color is a ripe banana?", new Category("Animals and nature"), new String[]{"Green", "Orange", "Purple", "Yellow"}, 3),
                new Question("Which animal is known as the \"King of the Jungle\"?", new Category("Animals and nature"), new String[]{"Tiger", "Bear", "Elephant", "Lion"}, 3)
        );
        List<Question> historyQuestions = List.of(
                new Question("What year did World War 1 begin?", new Category("History"), new String[]{"1914", "1917", "1919", "1923"}, 0),
                new Question("Which ancient civilization built the pyramids of Giza?", new Category("History"), new String[]{"Romans", "Greeks", "Egyptians", "Mayans"}, 2),
                new Question("Who painted the Mona Lisa?", new Category("History"), new String[]{"Michelangelo", "da Vinci", "Raphael", "Donatello"}, 1)
        );
        List<Question> questions = new ArrayList<>();
        questions.addAll(programmingQuestions);
        questions.addAll(animalsAndNatureQuestions);
        questions.addAll(historyQuestions);
        return questions;
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = getAllQuestions();
        List<Question> questionsByCategory = new ArrayList<>();
        for (Question question : questions) {
            if (question.getCategory().getName().equals(category)) {
                questionsByCategory.add(question);
            }
        }
        return questionsByCategory;
    }

    public List<Category> getCategories() {
        return List.of(new Category("Programming"), new Category("Animals and nature"), new Category("History"));
    }

}