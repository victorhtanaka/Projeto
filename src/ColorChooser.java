import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorChooser extends JPanel implements ChangeListener {

    protected JColorChooser tcc;
    protected Color selectedColor;
    protected JButton confirmButton;
    protected JFrame frame;

    public ColorChooser(JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;

        JPanel bannerPanel = new JPanel();
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Selecione a cor"));

        tcc = new JColorChooser(Color.white);
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder("Escolha a cor"));

        confirmButton = new JButton("Confirmar Cor");
        confirmButton.addActionListener(e -> {
            selectedColor = tcc.getColor();
            frame.dispose();
        });

        add(bannerPanel, BorderLayout.CENTER);
        add(tcc, BorderLayout.PAGE_START);
        add(confirmButton, BorderLayout.PAGE_END);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        selectedColor = tcc.getColor();
    }

    public static Color showColorChooser() {
        JFrame frame = new JFrame("Escolher Cor");
        ColorChooser chooser = new ColorChooser(frame);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(chooser);
        frame.pack();
        frame.setVisible(true);

        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return chooser.selectedColor;
    }
}
