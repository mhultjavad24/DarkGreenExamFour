import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameUI extends JFrame {
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel CatagoryPanel = new JPanel(new GridLayout(4, 1));
    private JLabel scoreLabel;
    private Game game;


    public GameUI() {
//        setTitle("DarkGreen Quiz");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        JButton Catagory1 = new JButton("Catagory 1");
//        JButton Catagory2 = new JButton("Catagory 2");
//        JButton Catagory3 = new JButton("Catagory 3");
//        JButton ChooseCatagory = new JButton("Choose Catagory");
//        CatagoryPanel.add(ChooseCatagory);
//        CatagoryPanel.add(Catagory1);
//        CatagoryPanel.add(Catagory2);
//        CatagoryPanel.add(Catagory3);
//        mainPanel.add(CatagoryPanel, BorderLayout.CENTER);
//        add(mainPanel);
//        setLocationRelativeTo(null);
//        setVisible(true);

    }

    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
    }

    public void renderCategorySelection(List<Category> categories) {
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        JButton ChooseCatagory = new JButton("Choose Catagory");
        CatagoryPanel.add(ChooseCatagory);
        for (Category category : categories) {
            JButton button = new JButton(category.getName());
            CatagoryPanel.add(button);
        }
        mainPanel.add(CatagoryPanel, BorderLayout.CENTER);
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void renderQuestion(Question question) {
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        JButton ChooseCatagory = new JButton(question.getText());
        CatagoryPanel.add(ChooseCatagory);

        for (String answer : question.answers) {
            JButton button = new JButton(answer);
            CatagoryPanel.add(button);
        }
        mainPanel.add(CatagoryPanel, BorderLayout.CENTER);
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
