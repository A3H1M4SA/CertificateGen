import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CertificateCreationScreen {
    private JPanel panel;
    private JTextField nameField, signField, companyField;
    private JComboBox<String> styleComboBox, dayComboBox, monthComboBox, yearComboBox;
    private JLabel imagePreviewLabel;
    private ImagePreviewer imagePreviewer; // Assuming this is implemented for image preview
    private JPanel dynamicFieldsPanel;

    public CertificateCreationScreen(Runnable previewAction) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setupImagePreview();
        setupDynamicFieldsPanel();
        setupStyleComboBox();
        setupGenerateAndPreviewButtons(previewAction);

        panel.setPreferredSize(new Dimension(650, 500));
    }

    private void setupImagePreview() {
        imagePreviewLabel = new JLabel();
        imagePreviewer = new ImagePreviewer(imagePreviewLabel);
        // Assuming a method to set a placeholder or initial image
        imagePreviewer.updateImageFromURL("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/800px-Google_2015_logo.svg.png");

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imagePreviewLabel, BorderLayout.CENTER);
        imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
        panel.add(imagePanel);
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
        panel.add(stylePanel);

        updateDynamicFields(); // Initialize fields
    }

    private void setupGenerateAndPreviewButtons(Runnable previewAction) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> {
            Map<String, String> details = getCertificateDetails();
            System.out.println("Generating certificate with details: " + details);
        });
        buttonPanel.add(generateButton);

        JButton previewButton = new JButton("Preview");
        previewButton.addActionListener(e -> previewAction.run());
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

        // Sample date picker components
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = Integer.toString(i);
        }
        dayComboBox = new JComboBox<>(days);
        datePanel.add(dayComboBox);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        datePanel.add(monthComboBox);

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

