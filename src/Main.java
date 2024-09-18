import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

import Queue.DynamicQueue;
import Stack.DynamicStack;

public class Main {
    private static BufferedImage img;
    private static DynamicStack<int[]> stack;
    private static DynamicQueue<int[]> queue;
    public static void main(String[] args) throws Exception {

        img = ImageIO.read(new File("src//test.png"));
        if (img == null) {
            throw new Exception("Image not found");
        }

        Color selectedColor = ColorChooser.showColorChooser();
        System.out.println("Cor escolhida: " + selectedColor);
        
        int[] starterPixel = getPixel();

        stack = new DynamicStack<int[]>(4);
        queue = new DynamicQueue<int[]>(4);

        /* stackFill(starterPixel[0], starterPixel[1], img.getRGB(starterPixel[0], starterPixel[1]), selectedColor.getRGB()); */
        queueFill(starterPixel[0], starterPixel[1], img.getRGB(starterPixel[0], starterPixel[1]), selectedColor.getRGB());
        ImageIO.write(img, "png", new File("src//output.png"));
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

        queue.store(new int[]{x, y});

        while (!queue.isEmpty()) {

            int[] point = queue.retrieve();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            } else {
                if (img.getRGB(px, py) == targetColor) {
                    System.out.println("Ponto: " + px + ", " + py);
                    img.setRGB(px, py, fillColor);

                    queue.store(new int[]{px + 1, py});
                    queue.store(new int[]{px - 1, py});
                    queue.store(new int[]{px, py + 1});
                    queue.store(new int[]{px, py - 1});
                }
            }
        }
    }

    private static int[] getPixel() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a coordenada X do pixel: ");
        int x = scanner.nextInt();

        System.out.println("Digite a coordenada Y do pixel: ");
        int y = scanner.nextInt();

        scanner.close();

        return new int[]{x, y};
    }
}
