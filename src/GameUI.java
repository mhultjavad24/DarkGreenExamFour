import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel CatagoryPanel = new JPanel(new GridLayout(4, 1));
    private JLabel scoreLabel;


    public GameUI() {
        setTitle("DarkGreen Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800, 600);
        pack();
        JButton Catagory1 = new JButton("Catagory 1");
        JButton Catagory2 = new JButton("Catagory 2");
        JButton Catagory3 = new JButton("Catagory 3");
        JButton ChooseCatagory = new JButton("Choose Catagory");
        CatagoryPanel.add(ChooseCatagory);
        CatagoryPanel.add(Catagory1);
        CatagoryPanel.add(Catagory2);
        CatagoryPanel.add(Catagory3);
        mainPanel.add(CatagoryPanel, BorderLayout.CENTER);
        add(mainPanel);

    }

}
