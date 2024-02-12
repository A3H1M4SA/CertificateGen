import javax.swing.*;
import java.awt.event.*;
import java.io.File;


public class PathSelectionScreen {
    private JPanel panel;
    private JButton choosePathButton;
    private JLabel pathLabel;

    public PathSelectionScreen(ActionListener nextAction) {
        panel = new JPanel();
        choosePathButton = new JButton("Choose Path to Save Certificate");
        pathLabel = new JLabel("No path selected", JLabel.CENTER);

        choosePathButton.addActionListener(e -> choosePath());
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(nextAction);

        panel.add(choosePathButton);
        panel.add(pathLabel);
        panel.add(nextButton);
    }


    private void choosePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(panel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            pathLabel.setText("Selected path: " + fileToSave.getAbsolutePath());
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getSelectedPath() {
        return pathLabel.getText().replace("Selected path: ", "");
    }
}

