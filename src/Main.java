import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Main {
    private static BufferedImage img;
    private static Stack<int[]> stack;
    private static boolean[][] processed;

    public static void main(String[] args) throws Exception {
        Scanner read = new Scanner(System.in);

        img = ImageIO.read(new File("src//test.png"));
        if (img == null) throw new Exception("Image not found");

        Color selectedColor = ColorChooser.showColorChooser();
        System.out.println("Cor escolhida: " + selectedColor);

        System.out.println("A partir de qual pixel x deseja preencher: ");
        int pixelX = read.nextInt();

        System.out.println("A partir de qual pixel y deseja preencher: ");
        int pixelY = read.nextInt();

        Color colorToFill = new Color(img.getRGB(pixelX, pixelY));

        processed = new boolean[img.getWidth()][img.getHeight()];
        stack = new Stack<>();

        stackFill(pixelX, pixelY, colorToFill.getRGB(), selectedColor.getRGB());
    }

    private static void stackFill(int x, int y, int targetColor, int fillColor) {
        if (targetColor == fillColor) return;

        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0], py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight() || processed[px][py]) {
                continue;
            }

            if (img.getRGB(px, py) == targetColor) {
                img.setRGB(px, py, fillColor);
                processed[px][py] = true;

                // Empilha vizinhos apenas se não processados
                pushIfValid(px + 1, py, targetColor);
                pushIfValid(px - 1, py, targetColor);
                pushIfValid(px, py + 1, targetColor);
                pushIfValid(px, py - 1, targetColor);
            }
        }
    }

    private static void pushIfValid(int x, int y, int targetColor) {
        if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight() && !processed[x][y] && img.getRGB(x, y) == targetColor) {
            stack.push(new int[]{x, y});
        }
    }
}
