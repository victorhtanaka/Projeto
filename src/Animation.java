import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Animation {
    public static void main(String[] args) {
        String folderPath = "src//Frames";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(20, 20);
        frame.setVisible(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (File file : files) {
                    try {
                        BufferedImage image = ImageIO.read(file);
                        g.drawImage(image, 0, 0, null);
                        Thread.sleep(100);
                        repaint();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        frame.add(panel);
    }
}
