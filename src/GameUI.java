import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameUI extends JFrame implements ActionListener {
//    private JPanel mainPanel = new JPanel(new BorderLayout());
//    private JPanel CatagoryPanel = new JPanel(new GridLayout(4, 1));
//    private JLabel scoreLabel;
//    private Game game; //ny referens för att kunna itiera med game


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

//        För att testa:
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Animals and Nature"));
        Category c1 = new Category("History");
        categories.add(c1);

        String[] answers = {"1939", "1940", "1941", "1942"};
        Question question3 = new Question("When did second world war start?", c1, answers, 0);

        GameUI gameUI = new GameUI();
//        gameUI.showLobbyPanel(categories, false);
//        gameUI.showGamePanel(question3);
    }

    public void showLobbyPanel(List<Category> categories, boolean chooseCategory) {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel categoryPanel = new JPanel(new GridLayout(4, 1));
        JPanel outerScorePanel = new JPanel(new GridLayout(1, 2));
        outerScorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel northPanel = new JPanel(new GridLayout(1,2));
        JPanel scorePanelPlayerOne;
        JPanel scorePanelPlayerTwo;

        JLabel playerOneLabel = new JLabel("Player 1");
        playerOneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel playerTwoLabel = new JLabel("Player 2");
        playerTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel waitingLabel = new JLabel("Waiting for opponent...");
        waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        java.util.Properties properties = new java.util.Properties();
        int roundsPerGame = 0;
        int questionsPerRound = 0;

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            roundsPerGame = Integer.parseInt(properties.getProperty("roundsPerGame"));
            questionsPerRound = Integer.parseInt(properties.getProperty("questionsPerRound"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scorePanelPlayerOne = createScorePanel(roundsPerGame, questionsPerRound,Color.GRAY);
        scorePanelPlayerOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scorePanelPlayerTwo = createScorePanel(roundsPerGame, questionsPerRound,Color.GRAY);
        scorePanelPlayerTwo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        JLabel ChooseCatagory = new JLabel("Choose Catagory");
        ChooseCatagory.setHorizontalAlignment(SwingConstants.CENTER);

        if (chooseCategory) {
            categoryPanel.add(ChooseCatagory);
            for (Category category : categories) {
                JButton button = new JButton(category.getName());
                button.addActionListener(this);
                categoryPanel.add(button);
            }
        } else {
            categoryPanel.add(waitingLabel);
        }

        northPanel.add(playerOneLabel);
        northPanel.add(playerTwoLabel);

        outerScorePanel.add(scorePanelPlayerOne);
        outerScorePanel.add(scorePanelPlayerTwo);

        mainPanel.add(categoryPanel, BorderLayout.SOUTH);
        mainPanel.add(outerScorePanel, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createScorePanel (int rows, int col, Color color){
        JPanel panel = new JPanel(new GridLayout(rows, col, 10, 10));
        panel.setBackground(Color.WHITE);

        for (int i = 0; i < rows * col; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(color);

            panel.add(cell);
        }
        return panel;
    }


    public void showGamePanel (Question question) {

        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

        JLabel questionLabel = new JLabel(question.getText());
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel categoryLabel = new JLabel(question.getCategory().getName());
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);


        for (String answer : question.answers) {
            JButton button = new JButton(answer);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(mainPanel);

        mainPanel.add(questionLabel, BorderLayout.CENTER);
        mainPanel.add(categoryLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
     //   if (e.getActionCommand()== "programmering")
    }

//    public void renderQuestion(Question question) {
//        setTitle("DarkGreen Quiz");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        JButton ChooseCatagory = new JButton(question.getText());
//        CatagoryPanel.add(ChooseCatagory);
//
//        for (String answer : question.answers) {
//            JButton button = new JButton(answer);
//            CatagoryPanel.add(button);
//        }
//        mainPanel.add(CatagoryPanel, BorderLayout.CENTER);
//        add(mainPanel);
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
}
