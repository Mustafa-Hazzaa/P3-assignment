package Swing;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private final Color bgColor;

    public RoundedButton(String text, Color color) {
        super(text);
        bgColor = color;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(bgColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(bgColor.brighter());
        } else {
            g2.setColor(bgColor);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
    }
}
