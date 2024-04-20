import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        setupStyleComboBox();
        setupImagePreview();
        setupGenerateAndPreviewButtons(previewAction);

        panel.setPreferredSize(new Dimension(650, 500));
    }

    //JPanel For Image Preview Window
    private void setupImagePreview() {
        imagePreviewLabel = new JLabel(new ImageIcon(), SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(600, 400));
        JScrollPane scrollPane = new JScrollPane(imagePreviewLabel);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Preview"));
        panel.add(scrollPane);
        imagePreviewer = new ImagePreviewer(imagePreviewLabel);
        imagePreviewer.updateImageFromURL(SourceClass.THUMBNAIL_URL);
    }

    //Dynamic fields to auto adjust the Certificat Inputs
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

    //Calls The API from SourceClass to Preview the Certificate
    // Method to build the API URL for previewing certificates
    public  String buildCertificatePreviewApiUrl() {
        return buildCertificateApiUrl(SourceClass.PREVIEW_API_URL);
    }

    // Method to build the API URL for generating and saving certificates
    public  String buildCertificateSaveApiUrl() {
        return buildCertificateApiUrl(SourceClass.SAVE_API_URL);
    }

    // General method to construct API URL
    private String buildCertificateApiUrl(String apiUrl) {
        String selectedStyle = (String) styleComboBox.getSelectedItem();
        String name = URLEncoder.encode(nameField.getText(), StandardCharsets.UTF_8);
        String company = URLEncoder.encode(companyField.getText(), StandardCharsets.UTF_8);
        String signedBy = URLEncoder.encode(signField.getText(), StandardCharsets.UTF_8);

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?name=").append(name);
        urlBuilder.append("&signedBy=").append(signedBy);
        urlBuilder.append("&company=").append(company);

        switch (Objects.requireNonNull(selectedStyle)) {
            case "Appreciation Certificate":
                urlBuilder.append("&certname=appreciation_1");
                break;
            case "Best Employee Certificate":
                String dateOfIssue = getDateFromComboBoxes();
                urlBuilder.append("&certname=bestemployee");
                urlBuilder.append("&dateOfIssue=").append(URLEncoder.encode(dateOfIssue, StandardCharsets.UTF_8));
                break;
            case "Participation Certificate":
                urlBuilder.append("&certname=participation");
                break;
            default:
                JOptionPane.showMessageDialog(panel, "API URL construction failed. Invalid certificate style selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
        }
        return urlBuilder.toString();
    }


    //Function to return date
    private String getDateFromComboBoxes() {
        String day = (String) dayComboBox.getSelectedItem();
        String monthAbbreviation = (String) monthComboBox.getSelectedItem();
        String month = monthToNumberMap.get(monthAbbreviation); // Convert abbreviation to number
        String year = (String) yearComboBox.getSelectedItem();

        // Format the date as dd/mm/yyyy
        return day + "/" + month + "/" + year;
    }


    //Function To Preview the Certificate

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

    //Dynamic Fields for the 3 Styles of Certificates
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

    //Date picker to select date input field for the Certificate
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
