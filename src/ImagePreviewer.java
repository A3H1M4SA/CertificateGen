import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImagePreviewer {
    private JLabel targetLabel;

    public ImagePreviewer(JLabel targetLabel) {
        this.targetLabel = targetLabel;
    }

    public void updateImageFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 100, Image.SCALE_SMOOTH));
            targetLabel.setIcon(icon);
            targetLabel.setText(""); // Clear any previous text
        } catch (IOException e) {
            targetLabel.setText("Image load failed");
            e.printStackTrace();
        }
    }

    public void updateImageFromFilePath(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 100, Image.SCALE_SMOOTH));
            targetLabel.setIcon(icon);
            targetLabel.setText(""); // Clear any previous text
        } catch (IOException e) {
            targetLabel.setText("Image load failed");
            e.printStackTrace();
        }
    }
}
