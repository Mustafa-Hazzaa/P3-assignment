package Swing;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private Color shadowColor = new Color(0,0,0,50);

    public RoundedPanel(Color bgColor, int radius) {
        setBackground(bgColor);
        cornerRadius = radius;
        setOpaque(false);
    }

    public void setShadowColor(Color c) { shadowColor = c; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(shadowColor);
        g2.fillRoundRect(5, 5, width - 10, height - 10, cornerRadius, cornerRadius);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 10, height - 10, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
