import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

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
        switchToScreen(welcomeScreen.getPanel());
    }

    private void showPathSelectionScreen() {
        switchToScreen(pathSelectionScreen.getPanel());
    }

    private void showCertificateCreationScreen() {
        savePath = pathSelectionScreen.getSelectedPath();
        switchToScreen(certificateCreationScreen.getPanel());
    }

    private void switchToScreen(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
    }

    private void generateCertificate() {
        // Collect user inputs (Implement these methods in CertificateCreationScreen)
        Map<String, String> certificateDetails = certificateCreationScreen.getCertificateDetails();

        // Construct API parameters
        String urlParameters = constructQueryParameters(certificateDetails);

        try {
            // Your API endpoint
            String apiUrl = "http://localhost/CertificateGen/PHP%20API/api.php";
            sendApiRequest(apiUrl, urlParameters);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Failed to generate certificate.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String constructQueryParameters(Map<String, String> details) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : details.entrySet()) {
            if (builder.length() > 0) builder.append('&');
            try {
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // This should never happen for UTF-8 encoding
                throw new RuntimeException("UTF-8 encoding not supported", e);
            }
        }
        return builder.toString();
    }


    private void sendApiRequest(String apiUrl, String urlParameters) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.writeBytes(urlParameters);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Handle successful response
            JOptionPane.showMessageDialog(mainFrame, "Certificate generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Handle server errors
            JOptionPane.showMessageDialog(mainFrame, "Failed to generate certificate. Server error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CertificateApp::new);
    }
}
