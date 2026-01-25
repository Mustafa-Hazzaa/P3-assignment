package Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class RoundedButton extends JButton {

    private final Color normalColor;
    private final Color hoverColor;
    private boolean hovering = false;

    public RoundedButton(String text, Color normalColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = normalColor.darker();

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        setFont(new Font("segoe UI", Font.BOLD, 13));
        setForeground(new Color(80, 60, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(hovering ? hoverColor : normalColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        g2.dispose();
        super.paintComponent(g);
    }
}
