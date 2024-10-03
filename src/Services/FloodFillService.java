package Services;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Queue.DynamicQueue;
import Stack.DynamicStack;

public class FloodFillService {
    private BufferedImage img;
    private DynamicStack<int[]> stack;
    private DynamicQueue<int[]> queue;

    public FloodFillService(BufferedImage img) {
        this.img = img;
        this.stack = new DynamicStack<int[]>(4);
        this.queue = new DynamicQueue<int[]>(4);
    }

    public FloodFillService() {
        this.stack = new DynamicStack<int[]>(4);
        this.queue = new DynamicQueue<int[]>(4);
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getImgWidth() {
        return this.img.getWidth();
    }

    public int getImgHeight() {
        return this.img.getHeight();
    }

    public void saveImg(String fileType, String path) {
        if (img != null) {
            try {
                ImageIO.write(this.img, fileType, new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void queueFill(int x, int y, int fillColor) {
        int targetColor = img.getRGB(x, y);
        if (targetColor == fillColor) return;

        queue.store(new int[]{x, y});

        while (!queue.isEmpty()) {

            int[] point = queue.retrieve();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            }

            if (img.getRGB(px, py) == targetColor) {
                img.setRGB(px, py, fillColor);

                queue.store(new int[]{px + 1, py});
                queue.store(new int[]{px - 1, py});
                queue.store(new int[]{px, py + 1});
                queue.store(new int[]{px, py - 1});
            }
            
        }
    }

    public void stackFill(int x, int y, int fillColor) {
        int targetColor = img.getRGB(x, y);
        if (targetColor == fillColor) return;

        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            } 

            if (img.getRGB(px, py) == targetColor) {
                img.setRGB(px, py, fillColor);

                stack.push(new int[]{px + 1, py});
                stack.push(new int[]{px - 1, py});
                stack.push(new int[]{px, py + 1});
                stack.push(new int[]{px, py - 1});
            }
        }
    }

    public void queueFillWithAnimation(int x, int y, int fillColor, JPanel panel) {
        int targetColor = img.getRGB(x, y);
        if (targetColor == fillColor) return;

        queue.store(new int[]{x, y});
        int num = 0;
        while (!queue.isEmpty()) {
            int[] point = queue.retrieve();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            }

            if (img.getRGB(px, py) == targetColor) {
                img.setRGB(px, py, fillColor);

                queue.store(new int[]{px + 1, py});
                queue.store(new int[]{px - 1, py});
                queue.store(new int[]{px, py + 1});
                queue.store(new int[]{px, py - 1});

                SwingUtilities.invokeLater(() -> panel.repaint());
                if (num % 10 == 0) {
                    num = 0;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                num++;
            }
        }
    }

    public void stackFillWithAnimation(int x, int y, int fillColor, JPanel panel) {
        int targetColor = img.getRGB(x, y);
        if (targetColor == fillColor) return;

        stack.push(new int[]{x, y});
        int num = 0;
        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) {
                continue;
            }

            if (img.getRGB(px, py) == targetColor) {
                img.setRGB(px, py, fillColor);

                stack.push(new int[]{px + 1, py});
                stack.push(new int[]{px - 1, py});
                stack.push(new int[]{px, py + 1});
                stack.push(new int[]{px, py - 1});

                SwingUtilities.invokeLater(() -> panel.repaint());
                if (num % 10 == 0) {
                    num = 0;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                num++;
            }
        }
    }
}
