import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import Stack.DynamicStack;

public class Main {
    private static BufferedImage img;
    private static DynamicStack<int[]> stack;

    public static void main(String[] args) throws Exception {
        Scanner read = new Scanner(System.in);

        img = ImageIO.read(new File("src//test.png"));
        if (img == null) {
            read.close();
            throw new Exception("Image not found");
        }

        Color selectedColor = ColorChooser.showColorChooser();
        System.out.println("Cor escolhida: " + selectedColor);

        System.out.println("A partir de qual pixel x deseja preencher: ");
        int pixelX = read.nextInt();

        System.out.println("A partir de qual pixel y deseja preencher: ");
        int pixelY = read.nextInt();

        read.close();

        stack = new DynamicStack<int[]>(4);

        stackFill(pixelX, pixelY, img.getRGB(pixelX, pixelY), selectedColor.getRGB());
        queueFill(pixelX, pixelY, img.getRGB(pixelX, pixelY), selectedColor.getRGB());
    }

    private static void stackFill(int x, int y, int targetColor, int fillColor) {
        if (targetColor == fillColor) return;

        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            } else {
                if (img.getRGB(px, py) == targetColor) {
                    System.out.println("Ponto: " + px + ", " + py);
                    img.setRGB(px, py, fillColor);

                    stack.push(new int[]{px + 1, py});
                    stack.push(new int[]{px - 1, py});
                    stack.push(new int[]{px, py + 1});
                    stack.push(new int[]{px, py - 1});
                }
            }
        }
    }

    private static void queueFill(int x, int y, int targetColor, int fillColor) {
        if (targetColor == fillColor) return;

        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            } else {
                if (img.getRGB(px, py) == targetColor) {
                    System.out.println("Ponto: " + px + ", " + py);
                    img.setRGB(px, py, fillColor);

                    stack.push(new int[]{px + 1, py});
                    stack.push(new int[]{px - 1, py});
                    stack.push(new int[]{px, py + 1});
                    stack.push(new int[]{px, py - 1});
                }
            }
        }
    }
}
