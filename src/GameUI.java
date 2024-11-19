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

    }

}
