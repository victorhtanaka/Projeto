import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import Services.ColorPickerService;
import Services.FloodFillService;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    private static BufferedImage img;
    private static Color selectedColor;
    private static int[] starterPixel;

    public static void main(String[] args) {
        try {
            img = ImageIO.read(new File("src//test2.png"));
            if (img == null) {
                throw new IOException("Image not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        selectedColor = ColorPickerService.showColorChooser();
        starterPixel = new int[]{-1, -1};

        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(img.getWidth(), img.getHeight());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                starterPixel[0] = e.getX();
                starterPixel[1] = e.getY();
                floodFill();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void floodFill() {
        if (starterPixel[0] == -1 || starterPixel[1] == -1) {
            System.out.println("Please select a pixel to start the flood fill.");
            return;
        }

        FloodFillService floodFillService = new FloodFillService(img);
        floodFillService.stackFill(starterPixel[0], starterPixel[1], selectedColor.getRGB());
        floodFillService.saveImg("png", "src//test2.png");
        
        System.out.println("Flood fill completed.");
    }
}
