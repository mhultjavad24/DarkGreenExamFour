package client;

import shared.Category;
import shared.Question;
import shared.Response;
import shared.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameUI extends JFrame implements ActionListener {
    private List<Category> categories;
    private Category currentCategory;
    private List<Question> questions;
    private JPanel lobbyPanel;
    private JPanel questionPanel;

    private JLabel scoreLabelOne;
    private JLabel scoreLabelTwo;

    private Question currentQuestion;
    private ObjectOutputStream out;
    private Game game; //Används inte
    private int roundsPerGame;
    private int questionsPerRound;
    private String identifier;
    private String opponentIdentifier;
    private boolean selectsNextCategory = false;

    // NY listor för att hålla referenser till rutorna
//    private List<JPanel> scorePanelsPlayerOne = new ArrayList<>();
//    private List<JPanel> scorePanelsPlayerTwo = new ArrayList<>();

    // Spåra aktuella rundor för poänguppdatering
    private int currentRound = 0;
    private int currentPlayer = 1; // 1 = Player 1, 2 = Player 2
    private int currentRoundScore = 0;
    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;

    public GameUI(List<Category> categories, int roundsPerGame, int questionsPerRound, ObjectOutputStream out, String identifier) {
        this.categories = categories;
        this.roundsPerGame = roundsPerGame;
        this.questionsPerRound = questionsPerRound;
        this.out = out;
        this.identifier = identifier;
        this.opponentIdentifier = (identifier.equals("Player 1")) ? "Player 2" : "Player 1";
        if (this.identifier.equals("Player 1")) {
            this.currentPlayer = 1;
        } else {
            this.currentPlayer = 2;
            this.selectsNextCategory = true;
        }
    }

    public void createLobbyPanel() {
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        lobbyPanel = new JPanel(new BorderLayout());
        JPanel outerScorePanel = new JPanel(new GridLayout(1, 2));
        outerScorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel northPanel = new JPanel(new GridLayout(1, 2));

        JLabel playerOneLabel = new JLabel(this.identifier);
        playerOneLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerOneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel playerTwoLabel = new JLabel(this.opponentIdentifier);
        playerTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabelOne = new JLabel("Score: " + scorePlayerOne);
        scoreLabelOne.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabelTwo = new JLabel("Score: " + scorePlayerTwo);
        scoreLabelTwo.setHorizontalAlignment(SwingConstants.CENTER);

//        JPanel scorePanelPlayerOne = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY, scorePanelsPlayerOne);
//        scorePanelPlayerOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        JPanel scorePanelPlayerTwo = createScorePanel(roundsPerGame, questionsPerRound, Color.GRAY, scorePanelsPlayerTwo);
//        scorePanelPlayerTwo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        northPanel.add(playerOneLabel);
        northPanel.add(playerTwoLabel);
        outerScorePanel.add(scoreLabelOne);
        outerScorePanel.add(scoreLabelTwo);

//        outerScorePanel.add(scorePanelPlayerOne);
//        outerScorePanel.add(scorePanelPlayerTwo);

        lobbyPanel.add(outerScorePanel, BorderLayout.CENTER);
        lobbyPanel.add(northPanel, BorderLayout.NORTH);

        add(lobbyPanel);
    }

    public void showLobbyPanel(boolean chooseCategory) {
        System.out.println("Showing lobby panel" + this.identifier + " " + chooseCategory);
        createLobbyPanel();
        JPanel categoryPanel = new JPanel(new GridLayout(4, 1));
        if (chooseCategory) {
            JLabel chooseCategoryLabel = new JLabel("Your turn to choose a category");
            chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            categoryPanel.add(chooseCategoryLabel);

            for (Category category : categories) {
                JButton button = new JButton(category.getName());
                button.addActionListener(e -> {
                    setCurrentCategory(category);
                    showQuestionPanel(category.getQuestions().get(0));
                });
                categoryPanel.add(button);
            }
        } else {
            JLabel waitingLabel = new JLabel("Waiting for opponent...");
            waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
            categoryPanel.add(waitingLabel);
        }

        Component[] categoryPanelComponents = categoryPanel.getComponents();
        for (Component component : categoryPanelComponents) {
            if (component != categoryPanel) {
                lobbyPanel.remove(component);
            }
        }

        lobbyPanel.add(categoryPanel, BorderLayout.SOUTH);

        if (questionPanel != null) {
            questionPanel.setVisible(false);
        }
        lobbyPanel.setVisible(true);

        revalidate();
        repaint();
        setVisible(true);
    }

    // NY skapa och spara referenser till poängtavlans rutor
//    private JPanel createScorePanel(int rows, int col, Color color, List<JPanel> scorePanels) {
//        JPanel panel = new JPanel(new GridLayout(rows, col, 10, 10));
//        panel.setBackground(Color.WHITE);
//
//        for (int i = 0; i < rows * col; i++) {
//            JPanel cell = new JPanel();
//            cell.setBackground(color);
//            scorePanels.add(cell); // Sparar referens till varje cell
//            panel.add(cell);
//        }
//        return panel;
//    }

    // NY Metod för att uppdatera poängtavlans rutor - detta inkl både player 1 å 2
    private void updateScorePanel(boolean isCorrect) {

        if (isCorrect) {
            if (currentPlayer == 1) {
                scorePlayerOne += currentRoundScore;
            } else if (currentPlayer == 2) {
                scorePlayerTwo += currentRoundScore;
            }
        }

        scoreLabelOne.setText("Score: " + scorePlayerOne);
        scoreLabelTwo.setText("Score: " + scorePlayerTwo);

        revalidate();
        repaint();

        //        List<JPanel> scorePanels = (player == 1) ? scorePanelsPlayerOne : scorePanelsPlayerTwo;
//        if (round >= 0 && round < scorePanels.size()) {
//            JPanel cell = scorePanels.get(round);
//            cell.setBackground(isCorrect ? Color.GREEN : Color.RED);
//        }


    }

    public void showQuestionPanel(Question question) {
        this.currentQuestion = question;
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
//        setLocationRelativeTo(null);

        if (questionPanel != null) {
            remove(questionPanel);
        }
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setVisible(false);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        JLabel questionLabel = new JLabel(question.getText());
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel categoryLabel = new JLabel(currentCategory.getName());
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        questionPanel.add(categoryLabel, BorderLayout.NORTH);
        for (String answer : question.getAnswers()) {
            JButton button = new JButton(answer);
            button.setActionCommand(answer);
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        questionPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(questionPanel);
        questionPanel.setVisible(true);

        if (lobbyPanel != null) {
            lobbyPanel.setVisible(false);
        }
        revalidate();
        repaint();
    }

    public void validateAnswer(JButton clickedButton, String selectedAnswer) {
        boolean isCorrect = currentQuestion.isCorrectAnswer(selectedAnswer);
        if (isCorrect) {
            currentRoundScore++;
        }
        System.out.println("Correct answer provided: " + isCorrect);
        clickedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);
        updateScorePanel(isCorrect);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clickedButton = (JButton) e.getSource();

        if (currentQuestion != null) {
            String selectedAnswer = e.getActionCommand();

            validateAnswer(clickedButton, selectedAnswer); //Kontrollerar svar


            // Tar en paus innan nästa fråga för att hinna se färgbyte
            // Set back to 1000 ms before submitting
            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    currentRound++;
                    if (currentRound >= questionsPerRound) {

                        List<Integer> p1Score = List.of(0, 0, 0);
                        List<Integer> p2Score = List.of(0, 0, 0);
                        Result result = new Result(p1Score, p2Score);

                        Response.ResponseType type = selectsNextCategory ? Response.ResponseType.RESULT_NO_ACTION : Response.ResponseType.RESULT;
                        Response response = new Response(type, currentCategory, result);
                        response.setIdentifier(identifier);
                        response.setRoundScore(currentRoundScore);


                        System.out.println("Sending response");
                        try {
                            out.writeObject(response);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        // Återställ rundan och byt spelare om det är slut på frågor
                        currentRoundScore = 0;
                        currentRound = 0;

                        decideNextView();
                    } else {
                        Question q = questions.get(currentRound);
                        showQuestionPanel(q);
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void decideNextView() {
        if (selectsNextCategory) {
            showLobbyPanel(true);
            this.selectsNextCategory = false;
            return;
        }

        currentPlayer = (currentPlayer == 1) ? 2 : 1; // DETTA växlar spelare
        showLobbyPanel(selectsNextCategory);
//        boolean chooseCategory = true;
//        if (identifier.equals("Player 2") && currentPlayer == 1) {
//            chooseCategory = false;
//        } else if (identifier.equals("Player 1") && currentPlayer == 2) {
//            chooseCategory = false;
//        }
//
//        showLobbyPanel(chooseCategory);

    }


    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
        this.questions = currentCategory.getQuestions();
    }


    public void startRound(Category category) {
        setCurrentCategory(category);
        showQuestionPanel(category.getQuestions().get(0));
        this.selectsNextCategory = true;
    }
}