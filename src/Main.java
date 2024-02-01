import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        // Frame Title
        setTitle("Certificate Generator - Alpha V1.0");

        // Main Panel with GridBagLayout for flexibility
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Components
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField(20);
        JLabel styleLabel = new JLabel("Certificate Style:");
        JComboBox<String> styleComboBox = new JComboBox<>(new String[]{"Style 1", "Style 2", "Style 3", "Style 4", "Style 5"});
        JLabel companyLabel = new JLabel("Company Name:");
        JTextField companyTextField = new JTextField(20);
        JLabel signedByLabel = new JLabel("Signed By:");
        JTextField signedByTextField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date of Issue:");
        JTextField dateTextField = new JTextField(20);
        JButton generateButton = new JButton("Generate");

        // Adding components to the main panel
        addToPanel(mainPanel, nameLabel, constraints, 0, 0);
        addToPanel(mainPanel, nameTextField, constraints, 1, 0);
        addToPanel(mainPanel, styleLabel, constraints, 0, 1);
        addToPanel(mainPanel, styleComboBox, constraints, 1, 1);
        addToPanel(mainPanel, companyLabel, constraints, 0, 2);
        addToPanel(mainPanel, companyTextField, constraints, 1, 2);
        addToPanel(mainPanel, signedByLabel, constraints, 0, 3);
        addToPanel(mainPanel, signedByTextField, constraints, 1, 3);
        addToPanel(mainPanel, dateLabel, constraints, 0, 4);
        addToPanel(mainPanel, dateTextField, constraints, 1, 4);
        addToPanel(mainPanel, generateButton, constraints, 0, 5, 2, 1);

        // Adding main panel to the frame
        add(mainPanel);

        // Setting Frame properties
        pack();
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        panel.add(component, constraints);
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints, int x, int y, int width, int height) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        panel.add(component, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
