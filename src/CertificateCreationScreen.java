import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField, signField, companyField;
    private JComboBox<String> styleComboBox, dayComboBox, monthComboBox, yearComboBox;
    private final Map<String, String> monthToNumberMap = new HashMap<>();
    private JLabel imagePreviewLabel;
    private ImagePreviewer imagePreviewer; // Assuming this is implemented for image preview
    private JPanel dynamicFieldsPanel;

    public CertificateCreationScreen(Runnable previewAction) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setupDynamicFieldsPanel(); // Initialize dynamicFieldsPanel first
        setupStyleComboBox(); // Now it's safe to call setupStyleComboBox
        setupImagePreview();
        setupGenerateAndPreviewButtons(previewAction);

        panel.setPreferredSize(new Dimension(650, 500));
    }


    private void setupImagePreview() {
        imagePreviewLabel = new JLabel(new ImageIcon(), SwingConstants.CENTER);
        // Set a preferred size for the image preview label, this might need to be adjusted
        imagePreviewLabel.setPreferredSize(new Dimension(600, 400));

        // Wrap the label in a JScrollPane to allow scrolling if the image is larger than the display area
        JScrollPane scrollPane = new JScrollPane(imagePreviewLabel);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Set the scroll pane's size
        scrollPane.setBorder(BorderFactory.createTitledBorder("Preview"));

        // Add the scrollPane to the panel instead of the label directly
        panel.add(scrollPane);

        imagePreviewer = new ImagePreviewer(imagePreviewLabel);
        // Placeholder Image Before Certificate Generates
        imagePreviewer.updateImageFromURL("http://localhost/CertificateGen/src/Placeholder.jpg");
    }


    private void setupDynamicFieldsPanel() {
        dynamicFieldsPanel = new JPanel();
        dynamicFieldsPanel.setLayout(new BoxLayout(dynamicFieldsPanel, BoxLayout.Y_AXIS));
        dynamicFieldsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        panel.add(dynamicFieldsPanel);
    }

    private void setupStyleComboBox() {
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stylePanel.add(new JLabel("Certificate Style:"));
        styleComboBox = new JComboBox<>(new String[]{"Appreciation Certificate", "Participation Certificate", "Best Employee Certificate"});
        styleComboBox.addActionListener(e -> updateDynamicFields());
        stylePanel.add(styleComboBox);
        panel.add(stylePanel, 0); // Adding at index 0 to ensure it's the first component

        updateDynamicFields(); // Initialize fields
    }

    private String buildCertificatePreviewApiUrl() {
        String selectedStyle = (String) styleComboBox.getSelectedItem();
        String name = URLEncoder.encode(nameField.getText(), StandardCharsets.UTF_8);
        String company = URLEncoder.encode(companyField.getText(), StandardCharsets.UTF_8);
        String signedBy = URLEncoder.encode(signField.getText(), StandardCharsets.UTF_8);
        String apiUrlBase = "http://localhost/CertificateGen/PHP%20API/preview.php?";

        switch (selectedStyle) {
            case "Appreciation Certificate":
                return apiUrlBase + "name=" + name + "&certname=appreciation_1" +
                        "&company=" + company + "&signedby=" + signedBy;
            case "Best Employee Certificate":
                String dateOfIssue = getDateFromComboBoxes();
                dateOfIssue = URLEncoder.encode(dateOfIssue, StandardCharsets.UTF_8);
                return apiUrlBase + "name=" + name + "&certname=bestemployee" +
                        "&company=" + company + "&signedby=" + signedBy +
                        "&dateOfIssue=" + dateOfIssue;
            // Add more cases for other certificate styles if needed
            default:
                JOptionPane.showMessageDialog(panel, "Preview is not available for the selected certificate style.", "Preview Unavailable", JOptionPane.ERROR_MESSAGE);
                return "";
        }
    }

    private String getDateFromComboBoxes() {
        String day = (String) dayComboBox.getSelectedItem();
        String monthAbbreviation = (String) monthComboBox.getSelectedItem();
        String month = monthToNumberMap.get(monthAbbreviation); // Convert abbreviation to number
        String year = (String) yearComboBox.getSelectedItem();

        // Format the date as dd/mm/yyyy
        return day + "/" + month + "/" + year;
    }



    private void setupGenerateAndPreviewButtons(Runnable previewAction) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> previewAction.run());
        buttonPanel.add(generateButton);

        JButton previewButton = new JButton("Preview");
        previewButton.addActionListener(e -> {
            String apiUrl = buildCertificatePreviewApiUrl();
            if (!apiUrl.isEmpty()) {
                imagePreviewer.updateImageFromURL(apiUrl);
            }
        });
        buttonPanel.add(previewButton);

        panel.add(buttonPanel);
    }

    private void updateDynamicFields() {
        dynamicFieldsPanel.removeAll();

        addTextFieldToPanel(dynamicFieldsPanel, "Name:", nameField = new JTextField(20));
        addTextFieldToPanel(dynamicFieldsPanel, "Sign:", signField = new JTextField(20));
        addTextFieldToPanel(dynamicFieldsPanel, "Company:", companyField = new JTextField(20));

        if ("Best Employee Certificate".equals(styleComboBox.getSelectedItem())) {
            addDatePickerToPanel(dynamicFieldsPanel);
        }

        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void addTextFieldToPanel(JPanel panel, String label, JTextField textField) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(new JLabel(label));
        fieldPanel.add(textField);
        panel.add(fieldPanel);
    }

    private void addDatePickerToPanel(JPanel panel) {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Date:"));

        // Day picker components
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.format("%02d", i); // Ensuring days are in two-digit format
        }
        dayComboBox = new JComboBox<>(days);
        datePanel.add(dayComboBox);

        // Month picker components with abbreviations
        String[] monthAbbreviations = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // Initialize the monthComboBox with abbreviations
        monthComboBox = new JComboBox<>(monthAbbreviations);
        datePanel.add(monthComboBox);

        // Populate the monthToNumberMap for converting month abbreviations to numbers
        for (int i = 0; i < monthAbbreviations.length; i++) {
            monthToNumberMap.put(monthAbbreviations[i], String.format("%02d", i + 1)); // Ensure months are in two-digit format
        }

        // Year picker components
        String[] years = new String[11];
        for (int i = 0; i < years.length; i++) {
            years[i] = Integer.toString(2020 + i);
        }
        yearComboBox = new JComboBox<>(years);
        datePanel.add(yearComboBox);

        panel.add(datePanel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Map<String, String> getCertificateDetails() {
        Map<String, String> details = new HashMap<>();
        details.put("name", nameField.getText());
        details.put("sign", signField.getText());
        details.put("company", companyField.getText());
        if ("Best Employee Certificate".equals(styleComboBox.getSelectedItem())) {
            String date = (String) yearComboBox.getSelectedItem() + "-" +
                    String.format("%02d", monthComboBox.getSelectedIndex() + 1) + "-" +
                    (String) dayComboBox.getSelectedItem();
            details.put("date", date);
        }
        details.put("style", (String) styleComboBox.getSelectedItem());
        return details;
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Certificate Creation Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Assuming previewAction is implemented elsewhere
            Runnable previewAction = () -> System.out.println("Previewing...");
            CertificateCreationScreen screen = new CertificateCreationScreen(previewAction);

            frame.setContentPane(screen.getPanel());
            frame.pack();
            frame.setVisible(true);
        });
    }
}

