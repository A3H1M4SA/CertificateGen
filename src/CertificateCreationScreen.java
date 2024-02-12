import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField;
    private JComboBox<String> styleComboBox;
    private JLabel imagePreviewLabel; // For showing the preview of the certificate or related image
    private ImagePreviewer imagePreviewer;

    public CertificateCreationScreen(Runnable generateAction) {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Layout configuration...

        imagePreviewLabel = new JLabel();
        imagePreviewer = new ImagePreviewer(imagePreviewLabel);

        // Assuming you want to update the preview based on a URL
        imagePreviewer.updateImageFromURL("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1200px-Google_2015_logo.svg.png");
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        panel.add(imagePreviewLabel, gbc);

        gbc.gridwidth = 1; // Reset to default for the following components
        gbc.gridy++; // Move to the next row

        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(10); // Define size to influence preferred width
        gbc.gridx = 1; // Next column
        panel.add(nameField, gbc);

        gbc.gridx = 0; // Reset column for next component
        gbc.gridy++; // Move to the next row for style
        panel.add(new JLabel("Style:"), gbc);

        styleComboBox = new JComboBox<>(new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        gbc.gridx = 1; // Next column for the combobox
        panel.add(styleComboBox, gbc);

        // Add more fields in similar fashion...

        // Add Generate button
        gbc.gridy++; // Move to the next row for the button
        gbc.gridx = 0; // Align to the first column
        gbc.gridwidth = 2; // Button spans two columns
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> generateAction.run());
        panel.add(generateButton, gbc);

        panel.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the panel
    }

    // Method to update the image preview based on a URL or file path
    private void updateImagePreview(String imagePath) {
        try {
            // If imagePath is a URL
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(new URL(imagePath)));
            // For local file use: new ImageIcon(ImageIO.read(new File(imagePath)));

            // Resize icon to fit the label if necessary
            Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            e.printStackTrace();
            imagePreviewLabel.setText("Image preview not available");
        }
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
