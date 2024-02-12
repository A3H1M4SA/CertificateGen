import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField;
    private JComboBox<String> styleComboBox;
    private JLabel imagePreviewLabel; // For showing the preview of the certificate or related image
    private ImagePreviewer imagePreviewer; // Assuming you have this class implemented

    public CertificateCreationScreen(Runnable generateAction) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical box layout for the main panel

        // Image Preview
        imagePreviewLabel = new JLabel();
        imagePreviewer = new ImagePreviewer(imagePreviewLabel);
        imagePreviewer.updateImageFromURL("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1200px-Google_2015_logo.svg.png");

        // Use GridBagLayout for precise control over the image label
        JPanel imagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        gbc.gridx = GridBagConstraints.RELATIVE; // Position relative to other components, if any
        imagePanel.add(imagePreviewLabel, gbc);

        // Add the imagePanel to the main panel
        panel.add(imagePanel);


        // Name input setup
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(new JLabel("Name:"));
        nameField = new JTextField(20); // Increased field size for better appearance
        namePanel.add(nameField);
        panel.add(namePanel);

        // Style selection setup
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stylePanel.add(new JLabel("Style:"));
        styleComboBox = new JComboBox<>(new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        stylePanel.add(styleComboBox);
        panel.add(stylePanel);

        // Generate button setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> generateAction.run());
        buttonPanel.add(generateButton);
        panel.add(buttonPanel);

        // Set preferred size for the main panel to maintain a decent size
        panel.setPreferredSize(new Dimension(600, 400));
    }

    public JPanel getPanel() {
        return panel;
    }

    public Map<String, String> getCertificateDetails() {
        Map<String, String> details = new HashMap<>();
        details.put("name", nameField.getText());
        details.put("style", (String) styleComboBox.getSelectedItem());
        return details;
    }
}
