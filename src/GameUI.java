import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameUI extends JFrame implements ActionListener {
    private List<Category> categories;
    private List<Question> questions;
    private JPanel lobbyPanel;
    private JPanel gamePanel;
    private Question currentQuestion;
    private ObjectOutputStream out;
    private Game game;

    // NY listor för att hålla referenser till rutorna
    private List<JPanel> scorePanelsPlayerOne = new ArrayList<>();
    private List<JPanel> scorePanelsPlayerTwo = new ArrayList<>();

    // Spåra aktuella rundor för poänguppdatering
    private int currentRound = 0;
    private int currentPlayer = 1; // 1 = Player 1, 2 = Player 2

    public GameUI(List<Category> categories, List<Question> questions) {
        this.categories = categories;
        this.questions = questions;
    }

    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Animals and Nature"));
        Category c1 = new Category("History");
        categories.add(c1);

        List<Question> historyQuestions = new ArrayList<>();
        String[] answers = {"1939", "1940", "1941", "1942"};
        Question question3 = new Question("When did the second world war start?", c1, answers, 0);

        historyQuestions.add(question3);
        c1.setQuestions(historyQuestions);

        List<Question> questions = new ArrayList<>();
        questions.add(question3);

        GameUI gameUI = new GameUI(categories, questions);
        gameUI.showLobbyPanel(true);
        gameUI.setVisible(true);
    }

    public void showLobbyPanel(boolean chooseCategory) {
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);


        if (lobbyPanel == null) {
            lobbyPanel = new JPanel(new BorderLayout());
            JPanel categoryPanel = new JPanel(new GridLayout(4, 1));
            JPanel outerScorePanel = new JPanel(new GridLayout(1, 2));
            outerScorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JPanel northPanel = new JPanel(new GridLayout(1, 2));

            JLabel playerOneLabel = new JLabel("Player 1");
            playerOneLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel playerTwoLabel = new JLabel("Player 2");
            playerTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel chooseCategoryLabel = new JLabel("Player " + currentPlayer + ", choose a category"); // NY
            chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);


            if (chooseCategory) {
                categoryPanel.add(chooseCategoryLabel);
                for (Category category : categories) {
                    JButton button = new JButton(category.getName());
                    button.addActionListener(this);
                    categoryPanel.add(button);
                }
            } else {
                JLabel waitingLabel = new JLabel("Waiting for opponent...");
                waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
                categoryPanel.add(waitingLabel);
            }

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

            // Uppdaterad för att inkludera scorePanels
            JPanel scorePanelPlayerOne = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY, scorePanelsPlayerOne);
            scorePanelPlayerOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanel scorePanelPlayerTwo = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY, scorePanelsPlayerTwo);
            scorePanelPlayerTwo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            northPanel.add(playerOneLabel);
            northPanel.add(playerTwoLabel);

            outerScorePanel.add(scorePanelPlayerOne);
            outerScorePanel.add(scorePanelPlayerTwo);

            lobbyPanel.add(categoryPanel, BorderLayout.SOUTH);
            lobbyPanel.add(outerScorePanel, BorderLayout.CENTER);
            lobbyPanel.add(northPanel, BorderLayout.NORTH);

            add(lobbyPanel);
        }
        if (gamePanel != null) {
            gamePanel.setVisible(false);
        }
        lobbyPanel.setVisible(true);

        revalidate();
        repaint();
    }

    // NY skapa och spara referenser till poängtavlans rutor
    private JPanel createScorePanel(int rows, int col, Color color, List<JPanel> scorePanels) {
        JPanel panel = new JPanel(new GridLayout(rows, col, 10, 10));
        panel.setBackground(Color.WHITE);

        for (int i = 0; i < rows * col; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(color);
            scorePanels.add(cell); // Sparar referens till varje cell
            panel.add(cell);
        }
        return panel;
    }

    // NY Metod för att uppdatera poängtavlans rutor - detta inkl både player 1 å 2
    private void updateScorePanel(int player, int round, boolean isCorrect) {
        List<JPanel> scorePanels = (player == 1) ? scorePanelsPlayerOne : scorePanelsPlayerTwo;
        if (round >= 0 && round < scorePanels.size()) {
            JPanel cell = scorePanels.get(round);
            cell.setBackground(isCorrect ? Color.GREEN : Color.RED);
        }
    }

    public void showGamePanel(Question question, ObjectOutputStream out) {
        this.out = out;
        this.currentQuestion = question;
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        if (gamePanel == null) {
            gamePanel = new JPanel(new BorderLayout());
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

            JLabel questionLabel = new JLabel(question.getText());
            questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel categoryLabel = new JLabel(question.getCategory().getName());
            categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

            for (String answer : question.answers) {
                JButton button = new JButton(answer);
                button.setActionCommand(answer);
                button.addActionListener(this);
                buttonPanel.add(button);
            }

            gamePanel.add(questionLabel, BorderLayout.CENTER);
            gamePanel.add(categoryLabel, BorderLayout.NORTH);
            gamePanel.add(buttonPanel, BorderLayout.SOUTH);

            add(gamePanel);
        }

        if (lobbyPanel != null) {
            lobbyPanel.setVisible(false);
        }

        gamePanel.setVisible(true);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        for (Category category : categories) {
            if (category.getName().equals(e.getActionCommand())) {
                List<Question> categorySpecificQuestions = category.getQuestions();
                showGamePanel(categorySpecificQuestions.get(0), null);
                return;
            }
        }

        if (currentQuestion != null) {
            String selectedAnswer = e.getActionCommand();
            boolean isCorrect = currentQuestion.isCorrectAnswer(selectedAnswer);

            clickedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);

            updateScorePanel(currentPlayer, currentRound, isCorrect);

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    currentRound++;
                    if (currentRound >= questions.size()) {
                        // Återställ rundan och byt spelare om det är slut på frågor
                        currentRound = 0;
                        currentPlayer = (currentPlayer == 1) ? 2 : 1; // DETTA växlar spelare
                        showLobbyPanel(true);
                    } else {
                        showGamePanel(questions.get(currentRound), null);
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
}