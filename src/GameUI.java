import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    // NY - listor för att hålla referenser till rutorna
    private List<JPanel> scorePanelsPlayerOne = new ArrayList<>();
    private List<JPanel> scorePanelsPlayerTwo = new ArrayList<>();


    public GameUI(List<Category> categories, List<Question> questions) {
        this.categories = categories;
        this.game = game; //koppla game till gameUI
        this.questions = questions;
    }


    public static void main(String[] args) {

//        För att testa:
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Animals and Nature"));
        Category c1 = new Category("History");
        categories.add(c1);

        List<Question> historyQuestions = new ArrayList<>();
        String[] answers = {"1939", "1940", "1941", "1942"};
        Question question3 = new Question("When did second world war start?", c1, answers, 0);

        historyQuestions.add(question3);
        c1.setQuestions(historyQuestions);

        List<Question> questions = new ArrayList<>();
        questions.add(question3);

        GameUI gameUI = new GameUI(categories, questions);
        gameUI.showLobbyPanel(true);
        gameUI.setVisible(true);
//        gameUI.showGamePanel(question3);
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

    private JPanel createScorePanel(int rows, int col, Color color, List<JPanel> scorePanels) {
        JPanel panel = new JPanel(new GridLayout(rows, col, 10, 10));
        panel.setBackground(Color.WHITE);

        for (int i = 0; i < rows * col; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(color);
            scorePanels.add(cell); // Sparar 1 referens till varje cell
            panel.add(cell);
        }
        return panel;
    }
    //för att uppdatera färg på poängtavlans rutor - ny metod
    private void updateScorePanel(int player, int round, boolean isCorrect) {
        List<JPanel> scorePanels = (player == 1) ? scorePanelsPlayerOne : scorePanelsPlayerTwo;
        if (round >= 0 && round < scorePanels.size()) {
            JPanel cell = scorePanels.get(round);
            cell.setBackground(isCorrect ? Color.GREEN : Color.RED);
        }
    }


    //Här kanske man behöver ha kategori som inparameter istället för frågan?
    //Så att man sen kan iterera genom frågorna per kategori och inte behöver anropa
    //showGamePanel för varje fråga. Tankar?
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

    @Override
    public void actionPerformed(ActionEvent e) {

        /*
        To-do:
        Kolla av vad för typ av knapp som tryckts på
        Om kategori - Kolla vilken kategori som valts och gå vidare till GamePanel
        Om svarsknapp - Kolla vilket svar som angivits, se om det var rätt
        Ändra färg på knappen som tryckts på. Rött - fel. Grönt - rätt / done
        Visa nästa fråga
        Loop som avgör hur många frågor och rundor
        Ändra färger på rutor i Lobbyn baserat på poäng för rundan
        osv
         */

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
            boolean isCorrect = currentQuestion.isCorrectAnswer(selectedAnswer); // Kontroll om svaret är rätt lr fel

            clickedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);
            // Ändrar bakgrundsfärg på den knapp som användaren klickade på

            // Uppdatera poängtavlan: Spelare 1, runda 0
            updateScorePanel(1, 0, isCorrect); // Justera spelare och runda enligt logiken så de behöver nt va 0

            // Skapar en timer med en anonym inre klass för att vänta och efter då återgå till lobbyn
            Timer timer = new Timer(4000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    showLobbyPanel(true); // Visa lobbyn igen
                }
            });
            timer.setRepeats(false); // Kör bara en gång
            timer.start();
        }

//            if (currentQuestion.isCorrectAnswer(selectedAnswer)) {
//                clickedButton.setBackground(Color.GREEN);
//            } else {
//                clickedButton.setBackground(Color.RED);
//            }
//
//        }
//
        revalidate();
        repaint();

    }
}