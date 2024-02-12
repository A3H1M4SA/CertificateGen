import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This line ensures the Swing components are created and updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CertificateApp(); // Replace CertificateApp with your main application class if it's different
            }
        });
    }
}
