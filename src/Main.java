import Services.FloodFillService;
import Views.MainView;
import java.io.IOException;

public class Main {
    private static int[] starterPixel;

    public static void main(String[] args) throws IOException {
        FloodFillService floodFillService = new FloodFillService();

        starterPixel = new int[]{-1, -1};

        MainView mainView = new MainView();
        mainView.showMainView(starterPixel, floodFillService);
    }
}
