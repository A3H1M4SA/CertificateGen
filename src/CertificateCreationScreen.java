import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField;
    private JComboBox<String> styleComboBox;
    // Add other fields as necessary

    public CertificateCreationScreen(Runnable generateAction) {
        panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Style:"));
        styleComboBox = new JComboBox<>(new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        panel.add(styleComboBox);

        // Initialize and add other components...

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> generateAction.run());
        panel.add(generateButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Map<String, String> getCertificateDetails() {
        Map<String, String> details = new HashMap<>();
        details.put("name", nameField.getText());
        details.put("style", (String) styleComboBox.getSelectedItem());
        // Add other details similarly...
        return details;
    }
}
