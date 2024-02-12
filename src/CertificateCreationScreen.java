import javax.swing.*;
import java.awt.*;


public class CertificateCreationScreen {
    private JPanel panel;

    public CertificateCreationScreen(Runnable generateAction) {
        panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Style:"));
        JComboBox<String> styleComboBox = new JComboBox<>(
                new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        panel.add(styleComboBox);

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> generateAction.run());

        panel.add(generateButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
