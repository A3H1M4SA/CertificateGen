import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField;
    private JComboBox<String> styleComboBox;
    private JLabel imagePreviewLabel; // For showing the preview of the certificate or related image
    private ImagePreviewer imagePreviewer;
    private APIClient apiClient; // Assuming you have an APIClient class

    public CertificateCreationScreen(Runnable generateAction) {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4); // Margins for components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initializeComponents(gbc);
        addActionToGenerateButton(generateAction);

        // Setup API Client
        apiClient = new APIClient("http://localhost/CertificateGen/PHP%20API/api.php");

        // Setup and display the initial image preview
        imagePreviewer = new ImagePreviewer(imagePreviewLabel);
        imagePreviewer.updateImageFromURL("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1200px-Google_2015_logo.svg.png");

        panel.setPreferredSize(new Dimension(600, 400)); // Suggest a size for the panel
    }

    private void initializeComponents(GridBagConstraints gbc) {
        // Image Preview
        imagePreviewLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(imagePreviewLabel, gbc);

        // Reset to default for the next components
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Name Field
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(10);
        panel.add(nameField, gbc);

        // Style ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Style:"), gbc);
        gbc.gridx = 1;
        styleComboBox = new JComboBox<>(new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        panel.add(styleComboBox, gbc);
    }

    private void addActionToGenerateButton(Runnable generateAction) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3; // Adjust based on the actual layout
        gbc.gridwidth = 2; // Span two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> {
            generateAction.run();
            // Potentially call apiClient.makeRequest(...) here
        });
        panel.add(generateButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Map<String, String> getCertificateDetails() {
        Map<String, String> details = new HashMap<>();
        details.put("name", nameField.getText());
        details.put("style", (String) styleComboBox.getSelectedItem());
        // Add other details as needed...
        return details;
    }
}
