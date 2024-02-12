import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen {
    private JPanel panel;
    private JButton continueButton;

    public WelcomeScreen(ActionListener continueAction) {
        panel = new JPanel(new GridLayout(2, 1));
        JLabel welcomeLabel = new JLabel("Welcome To Certificate Generator", JLabel.CENTER);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(continueAction);

        panel.add(welcomeLabel);
        panel.add(continueButton);
    }


    public JPanel getPanel() {
        return panel;
    }
}
