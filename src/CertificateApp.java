import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;
import java.net.URI;

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
        savePath = pathSelectionScreen.getSavePath();
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

        // Update savePath with the user's selected path from PathSelectionScreen
        savePath = pathSelectionScreen.getSavePath(); // This line is added here

        // Now savePath contains the directory chosen by the user
        // You can pass savePath to sendApiRequest or use it directly within that method
        sendApiRequest("http://localhost/CertificateGen/PHP%20API/api.php?name=Ahimsa&signedBy=Ron%20Kulkin&certname=participation_1&company=Red%20Gate&signedby=Mr.Rohan%20Singh", urlParameters);
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


    private void sendApiRequest(String apiUrl, String urlParameters) {
        try {
            URL url = new URL(apiUrl + "?" + urlParameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // Adjust based on your API requirement

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // The IOException can be thrown by getInputStream
                InputStream inputStream = conn.getInputStream();

                String filePath = savePath + File.separator + "test.pdf";
                File fileToSave = new File(filePath);

                // Ensure the directory exists
                File parentDir = fileToSave.getParentFile();
                if (parentDir != null) parentDir.mkdirs();

                // The IOException can also be thrown by FileOutputStream constructor and write method
                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                JOptionPane.showMessageDialog(mainFrame, "Certificate saved successfully as test.pdf.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to generate certificate. Server responded with status: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "An error occurred while trying to save the certificate.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(CertificateApp::new);
    }
}
