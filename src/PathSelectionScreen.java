import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PathSelectionScreen {
    private JPanel panel;
    private JButton choosePathButton;
    private JLabel pathLabel;
    private JLabel instructionLabel;
    private String savePath;
    private JButton nextButton;

    public PathSelectionScreen(ActionListener nextAction) {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        instructionLabel = new JLabel("<html><center>Please select a directory where the certificate will be saved.<br>This directory will be used to store the generated certificate file.</center></html>");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        choosePathButton = new JButton("Choose Path to Save Certificate");
        choosePathButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pathLabel = new JLabel("No path selected", SwingConstants.CENTER);
        pathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setEnabled(false); // Disable the button initially

        choosePathButton.addActionListener(e -> choosePath());
        nextButton.addActionListener(nextAction);

        panel.add(instructionLabel, gbc);
        panel.add(choosePathButton, gbc);
        panel.add(pathLabel, gbc);
        panel.add(nextButton, gbc);

        panel.setPreferredSize(new Dimension(600, 400));
    }

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
            nextButton.setEnabled(true); // Enable the next button only if a path is selected
        } else {
            nextButton.setEnabled(false); // Keep the next button disabled if no path is selected
        }

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
