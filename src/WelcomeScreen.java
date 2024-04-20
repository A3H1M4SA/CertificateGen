import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen {
    private JPanel panel;
    private JButton continueButton;

    public WelcomeScreen(ActionListener continueAction) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to stack labels and button vertically
        panel.setPreferredSize(new Dimension(800, 600)); // Standard Size

        // Labels for multiple lines of text
        JLabel welcomeLabel1 = new JLabel("Welcome To Certificate Generator", JLabel.CENTER);
        JLabel welcomeLabel2 = new JLabel("Developed By Student ID: 2287166", JLabel.CENTER);
        JLabel welcomeLabel3 = new JLabel("Click 'Continue' to start.", JLabel.CENTER);

        // Format labels for proper alignment
        formatLabel(welcomeLabel1);
        formatLabel(welcomeLabel2);
        formatLabel(welcomeLabel3);

        continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        continueButton.addActionListener(continueAction);

        // Add components to the panel
        panel.add(Box.createVerticalGlue()); // Add spacing
        panel.add(welcomeLabel1);
        panel.add(welcomeLabel2);
        panel.add(welcomeLabel3);
        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Add some space before the button
        panel.add(continueButton);
        panel.add(Box.createVerticalGlue()); // Add spacing
    }

    private void formatLabel(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure labels are centered
        label.setMaximumSize(new Dimension(800, 50)); // Set maximum size for consistent layout
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for better visibility
    }

    public JPanel getPanel() {
        return panel;
    }
}
