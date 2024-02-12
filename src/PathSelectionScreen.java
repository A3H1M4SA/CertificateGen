import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PathSelectionScreen {
    private JPanel panel;
    private JButton choosePathButton;
    private JLabel pathLabel;
    private String savePath; // To store the selected path
    private JButton nextButton; // Reference to the "Next" button if you need to adjust its visibility

    public PathSelectionScreen(ActionListener nextAction) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout for simplicity

        choosePathButton = new JButton("Choose Path to Save Certificate");
        pathLabel = new JLabel("No path selected");
        nextButton = new JButton("Next");

        // Configure the choosePathButton to open a JFileChooser when clicked
        choosePathButton.addActionListener(e -> choosePath());

        // Configure the nextButton to perform the next action
        nextButton.addActionListener(nextAction);

        // Add components to the panel
        panel.add(choosePathButton);
        panel.add(pathLabel);
        panel.add(nextButton);
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
