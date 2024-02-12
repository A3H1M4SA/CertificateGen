import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CertificateApp {
    private JFrame mainFrame;
    private WelcomeScreen welcomeScreen;
    private PathSelectionScreen pathSelectionScreen;
    private CertificateCreationScreen certificateCreationScreen;
    private String savePath;

    public CertificateApp() {
        mainFrame = new JFrame("Certificate Generator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);

        welcomeScreen = new WelcomeScreen(e -> showPathSelectionScreen());
        pathSelectionScreen = new PathSelectionScreen(e -> showCertificateCreationScreen());
        certificateCreationScreen = new CertificateCreationScreen(this::generateCertificate);

        showWelcomeScreen();
        mainFrame.setVisible(true);
    }

    private void showWelcomeScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(welcomeScreen.getPanel(), BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
    }

    private void showPathSelectionScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(pathSelectionScreen.getPanel(), BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
    }

    private void showCertificateCreationScreen() {
        savePath = pathSelectionScreen.getSelectedPath(); // Get the save path from the previous screen
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(certificateCreationScreen.getPanel(), BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
    }

    private void generateCertificate() {
        // Here you can implement the logic to call the API and generate the certificate
        // The savePath variable holds the path where the user wants to save the certificate
        // You can retrieve input values from certificateCreationScreen and use them for the API call
        System.out.println("Generating certificate...");
        // Example: Use savePath and other data to generate and save the certificate
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CertificateApp::new);
    }
}

