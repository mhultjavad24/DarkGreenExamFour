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
import java.util.List;

public class GameUI extends JFrame implements ActionListener {
    private List<Category> categories;
    private Category currentCategory;
    private List<Question> questions;
    private JPanel lobbyPanel;
    private JPanel questionPanel;

    private JLabel scoreLabelMe;
    private JLabel scoreLabelOpponent;

    private Question currentQuestion;
    private ObjectOutputStream out;
    private int roundsPerGame;
    private int questionsPerRound;
    private String identifier;
    private String opponentIdentifier;
    private boolean selectsNextCategory = false;

    // Spåra aktuella rundor för poänguppdatering
    private int currentRound = 0;
    private int currentPlayer = 1; // 1 = Player 1, 2 = Player 2
    private int currentRoundScore = 0;
    private int scorePlayerMe = 0;
    private int scorePlayerOpponent = 0;

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

        scoreLabelMe = new JLabel("Score: " + scorePlayerMe);
        scoreLabelMe.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabelOpponent = new JLabel("Score: " + scorePlayerOpponent);
        scoreLabelOpponent.setHorizontalAlignment(SwingConstants.CENTER);

        northPanel.add(playerOneLabel);
        northPanel.add(playerTwoLabel);
        outerScorePanel.add(scoreLabelMe);
        outerScorePanel.add(scoreLabelOpponent);

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

    public void updateScore(int roundScore, int opponentScore) {
        scorePlayerMe = roundScore;
        scorePlayerOpponent = opponentScore;
        scoreLabelMe.setText("Score: " + scorePlayerMe);
        scoreLabelOpponent.setText("Score: " + scorePlayerOpponent);
        revalidate();
        repaint();
    }

    // NY Metod för att uppdatera poängtavlans rutor - detta inkl både player 1 å 2
    private void updateScorePanel(boolean isCorrect) {
        if (isCorrect) {
            scorePlayerMe++;
        }

        scoreLabelMe.setText("Score: " + scorePlayerMe);

        revalidate();
        repaint();
    }

    public void showQuestionPanel(Question question) {
        this.currentQuestion = question;
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);

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

    //Metod som kontrollerar svar och justerar knappfärger
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
            Timer timer = new Timer(1000, new ActionListener() {
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
    }


    public void setOut(ObjectOutputStream out) {
        this.out = out;
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