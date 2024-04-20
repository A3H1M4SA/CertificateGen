import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PathSelectionScreen {
    private JPanel panel;
    private JButton choosePathButton;
    private JLabel pathLabel;
    private JLabel instructionLabel; // New label for instructions
    private String savePath; // To store the selected path
    private JButton nextButton; // Reference to the "Next" button

    public PathSelectionScreen(ActionListener nextAction) {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for more control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margins for components

        // Instruction label explaining what the path is for
        instructionLabel = new JLabel("<html><center>Please select a directory where the certificate will be saved.<br>This directory will be used to store the generated certificate file.</center></html>");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        choosePathButton = new JButton("Choose Path to Save Certificate");
        choosePathButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pathLabel = new JLabel("No path selected", SwingConstants.CENTER);
        pathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Configure the choosePathButton to open a JFileChooser when clicked
        choosePathButton.addActionListener(e -> choosePath());

        // Configure the nextButton to perform the next action
        nextButton.addActionListener(nextAction);

        // Add components to the panel with GridBagConstraints
        panel.add(instructionLabel, gbc);
        panel.add(choosePathButton, gbc);
        panel.add(pathLabel, gbc);
        panel.add(nextButton, gbc);

        // Set the preferred size of the panel to ensure consistency
        panel.setPreferredSize(new Dimension(600, 400));
    }

    //Function to choose path to save the generated Certificate
    private void choosePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a directory to save the certificate");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showSaveDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            savePath = selectedFile.getAbsolutePath();
            pathLabel.setText("Selected path: " + savePath);
        }

        // Update UI to reflect changes
        panel.revalidate();
        panel.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getSavePath() {
        return savePath;
    }
}
