package Swing;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    public RoundedTextField(int cols) {
        super(cols);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.dispose();
        super.paintComponent(g);
    }
}
