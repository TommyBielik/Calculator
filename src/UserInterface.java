import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInterface extends JFrame {

    private JTextField display;
    private JPanel buttonsPanel;
    private Calculator calculator;

    UserInterface(){

        // FRAME
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.RED);

        // PANELS AND BUTTONS
        this.buttonsPanel = new JPanel();
        this.buttonsPanel.setLayout(new GridLayout(5, 4, 10, 10));
        this.buttonsPanel.setBackground(Color.decode("#BF1213"));

        String[] buttonLabels = {
            "C", "%", "^", "<-",
            "7", "8", "9", "*",
            "4", "5", "6", "/",
            "1", "2", "3", "+",
            ",", "0", "=", "-"
        };

        this.calculator = new Calculator(this);

        for(String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setFocusable(false);
            button.setActionCommand(label);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            button.addActionListener(calculator);
            this.buttonsPanel.add(button);
        }

        // DISPLAY
        this.display = new JTextField();
        this.display.setFont(new Font("Arial", Font.BOLD, 30));
        this.display.setEditable(false);
        this.display.setHorizontalAlignment(JTextField.RIGHT);   
        this.display.setPreferredSize(new Dimension(400, 200));  
        this.display.setBackground(Color.decode("#0E6118"));
        this.display.setForeground(Color.decode("#041F08"));
        this.display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4)); 

        this.add(display, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JTextField getDisplay() {
        return this.display;
    }
}
