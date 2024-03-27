import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImagePreviewer {
    private JLabel targetLabel;
    private BufferedImage currentImage;

    public ImagePreviewer(JLabel targetLabel) {
        this.targetLabel = targetLabel;
        // Add a component listener to handle resizing of the label
        this.targetLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (currentImage != null) {
                    updateLabelIcon(currentImage);
                }
            }
        });
    }

    public void updateImageFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            currentImage = ImageIO.read(url);
            updateLabelIcon(currentImage);
        } catch (IOException e) {
            targetLabel.setText("Image load failed");
            e.printStackTrace();
        }
    }

    public void updateImageFromFilePath(String filePath) {
        try {
            File file = new File(filePath);
            currentImage = ImageIO.read(file);
            updateLabelIcon(currentImage);
        } catch (IOException e) {
            targetLabel.setText("Image load failed");
            e.printStackTrace();
        }
    }

    private void updateLabelIcon(BufferedImage img) {
        if (img != null && targetLabel.getWidth() > 0 && targetLabel.getHeight() > 0) {
            ImageIcon icon = new ImageIcon(scaleImage(img, targetLabel.getWidth(), targetLabel.getHeight()));
            targetLabel.setIcon(icon);
            targetLabel.setText(""); // Clear any previous text
        }
    }

    private Image scaleImage(BufferedImage img, int targetWidth, int targetHeight) {
        double aspectRatio = (double) img.getWidth() / img.getHeight();
        int width = targetWidth;
        int height = (int) (targetWidth / aspectRatio);

        if (height > targetHeight) {
            height = targetHeight;
            width = (int) (targetHeight * aspectRatio);
        }

        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
