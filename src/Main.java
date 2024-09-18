import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import Services.ColorPickerService;
import Services.FloodFillService;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("src//test.png"));
        if (img == null) {
            throw new Exception("Image not found");
        }

        FloodFillService floodFillService = new FloodFillService(img);

        Color selectedColor = ColorPickerService.showColorChooser();
        System.out.println("Cor escolhida: " + selectedColor);
        
        int[] starterPixel = getPixel();

        floodFillService.stackFill(starterPixel[0], starterPixel[1], selectedColor.getRGB());
        floodFillService.saveImg();
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
