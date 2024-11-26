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
    private JPanel lobbyPanel;
    private JPanel gamePanel;
    private Question currentQuestion;
    private ObjectOutputStream out;
    private int roundsPerGame;
    private int questionsPerRound;
    private int questionAskedInCurrentRound = 0;


    public GameUI(List<Category> categories, ObjectOutputStream out, int roundsPerGame, int questionsPerRound) {
        this.categories = categories;
        this.out = out;
        this.roundsPerGame = roundsPerGame;
        this.questionsPerRound = questionsPerRound;
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
                JLabel waitingLabel = new JLabel("Waiting for opponent...");
                waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
                categoryPanel.add(waitingLabel);
            }

            JPanel scorePanelPlayerOne = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY);
            scorePanelPlayerOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanel scorePanelPlayerTwo = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY);
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

    private JPanel createScorePanel(int rows, int col, Color color) {
        JPanel panel = new JPanel(new GridLayout(rows, col, 10, 10));
        panel.setBackground(Color.WHITE);

        for (int i = 0; i < rows * col; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(color);

            panel.add(cell);
        }
        return panel;
    }


    //Här kanske man behöver ha kategori som inparameter istället för frågan?
    //Så att man sen kan iterera genom frågorna per kategori och inte behöver anropa
    //showGamePanel för varje fråga. Tankar?
    public void showGamePanel(Question question) {
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
                button.setActionCommand(String.valueOf(answer));
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

    public void validateAnswer(ActionEvent e) {

        if (e.getSource() instanceof JButton clickedButton) {
            String selectedAnswer = e.getActionCommand();
            boolean isCorrect = currentQuestion.isCorrectAnswer(selectedAnswer);

            if (isCorrect) {
                clickedButton.setBackground(Color.GREEN);
                //Plussa på score
            } else {
                clickedButton.setBackground(Color.RED);
            }

            Timer timer = new Timer(1000, evt -> {

                questionAskedInCurrentRound++;

                if (questionAskedInCurrentRound < questionsPerRound) {

                    Category currentCategory = currentQuestion.getCategory();
                    List<Question> questions = currentCategory.getQuestions();
                    int currentIndex = questions.indexOf(currentQuestion);

                    if (currentIndex + 1 < questions.size()) {
                        currentQuestion = questions.get(currentIndex + 1);
                        showGamePanel(currentQuestion);
                    } else {
                        showLobbyPanel(false);
                    }
                } else {
                    questionAskedInCurrentRound = 0;
                    showLobbyPanel(false);
                }

            });

            timer.setRepeats(false);
            timer.start();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.out != null) {
            // sdsd
            List<Integer> p1RoundScore = new ArrayList<>();
            List<Integer> p2RoundScore = new ArrayList<>();

            p1RoundScore.add(1);
            p1RoundScore.add(2);
            p1RoundScore.add(3);

            p2RoundScore.add(2);
            p2RoundScore.add(1);
            p2RoundScore.add(3);

            Result r = new Result(p1RoundScore, p2RoundScore);
            QuizResponse quizResponse1 = new QuizResponse(List.of(), r);
            try {
                this.out.writeObject(quizResponse1);
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }

        for (Category category : categories) {
            if (category.getName().equals(e.getActionCommand())) {
                List<Question> categorySpecificQuestions = category.getQuestions();
                //Skicka kategorin här...
                showGamePanel(categorySpecificQuestions.get(0), null);
                return;
            }
        }

        if (currentQuestion != null) {
            validateAnswer(e);
        }

    }
}
