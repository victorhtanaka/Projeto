package Views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Services.FloodFillService;

public class MainView {
    FloodFillService FFS;
    private double zoomFactor = 1.0;
    private Color selectedColor = Color.WHITE;
    private boolean useQueueFill = true;
    private boolean useAnimatedFill = true;
    private String imagePath;

    public void showMainView(int[] starterPixel, FloodFillService floodFillService) {
        this.FFS = floodFillService;

        JFrame frame = new JFrame("Paint 8000 2025 edition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (FFS.getImg() != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    Image scaledImage = FFS.getImg().getScaledInstance(
                        (int) (FFS.getImgWidth() * zoomFactor), 
                        (int) (FFS.getImgHeight() * zoomFactor), 
                        Image.SCALE_SMOOTH
                    );
                    int x = (getWidth() - scaledImage.getWidth(null)) / 2;
                    int y = (getHeight() - scaledImage.getHeight(null)) / 2;
                    g2d.drawImage(scaledImage, x, y, null);
                }
            }
        };

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (FFS.getImg() == null) return;

                int scaledImgWidth = (int) (FFS.getImgWidth() * zoomFactor);
                int scaledImgHeight = (int) (FFS.getImgHeight() * zoomFactor);
                int offsetX = (mainPanel.getWidth() - scaledImgWidth) / 2;
                int offsetY = (mainPanel.getHeight() - scaledImgHeight) / 2;
                int mouseX = e.getX(), mouseY = e.getY();

                if (mouseX >= offsetX && mouseX < offsetX + scaledImgWidth &&
                    mouseY >= offsetY && mouseY < offsetY + scaledImgHeight) {
                    
                    starterPixel[0] = (int) ((mouseX - offsetX) / zoomFactor);
                    starterPixel[1] = (int) ((mouseY - offsetY) / zoomFactor);
                    new Thread(() -> animateFloodFill(starterPixel, selectedColor, mainPanel)).start();
                }
            }
        });

        mainPanel.addMouseWheelListener(e -> {
            zoomFactor *= (e.getPreciseWheelRotation() < 0) ? 1.1 : 0.9;
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setPreferredSize(new Dimension(400, 400));
        overlayPanel.setOpaque(false);
        ColorPickerView colorPicker = new ColorPickerView();
        overlayPanel.add(colorPicker, BorderLayout.CENTER);

        JButton uploadButton = new JButton("Upload de Imagem");
        uploadButton.addActionListener(e -> uploadImage(mainPanel));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(uploadButton);

        JButton animatedFillButton = new JButton("Ativar Animação");
        JButton nonAnimatedFillButton = new JButton("Desativar Animação");

        animatedFillButton.addActionListener(e -> useAnimatedFill = true);
        nonAnimatedFillButton.addActionListener(e -> useAnimatedFill = false);

        buttonPanel.add(animatedFillButton);
        buttonPanel.add(nonAnimatedFillButton);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(overlayPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void uploadImage(JPanel panel) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                FFS.setImg(img);
                panel.repaint();
                imagePath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "Imagem carregada com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem.");
            }
        }
    }

    private void animateFloodFill(int[] starterPixel, Color selectedColor, JPanel panel) {
        if (starterPixel[0] == -1 || starterPixel[1] == -1) return;

        if (useAnimatedFill) {
            if (useQueueFill) {
                FFS.queueFillWithAnimation(starterPixel[0], starterPixel[1], selectedColor.getRGB(), panel);
            } else {
                FFS.stackFillWithAnimation(starterPixel[0], starterPixel[1], selectedColor.getRGB(), panel);
            }
        } else {
            if (useQueueFill) {
                FFS.queueFill(starterPixel[0], starterPixel[1], selectedColor.getRGB());
                panel.repaint();
            } else {
                FFS.stackFill(starterPixel[0], starterPixel[1], selectedColor.getRGB());
                panel.repaint();
            }
        }

        if (imagePath != null && !imagePath.isEmpty()) {
            String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
            FFS.saveImg(fileExtension, imagePath);
        }
    }

    public class ColorPickerView extends JPanel implements ChangeListener {
        private JColorChooser tcc;
        private JButton queueFillButton, stackFillButton;

        public ColorPickerView() {
            setLayout(new BorderLayout());
            tcc = new JColorChooser(selectedColor);
            tcc.getSelectionModel().addChangeListener(this);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            queueFillButton = new JButton("Usar Fila");
            stackFillButton = new JButton("Usar Pilha");

            queueFillButton.addActionListener(e -> useQueueFill = true);
            stackFillButton.addActionListener(e -> useQueueFill = false);

            buttonPanel.add(queueFillButton);
            buttonPanel.add(stackFillButton);

            add(tcc, BorderLayout.PAGE_START);
            add(buttonPanel, BorderLayout.CENTER);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            selectedColor = tcc.getColor();
        }
    }
}
