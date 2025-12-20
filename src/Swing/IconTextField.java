package Swing;

import javax.swing.*;
import java.awt.*;

public class IconTextField extends RoundedTextField {

    private Icon icon;
    private static final int ICON_SIZE = 20;
    private static final int LEFT_PADDING = 35; // Total space for the icon + gap

    public IconTextField(int cols, Icon icon) {
        super(cols);
        this.icon = scale(icon);
        // Set a transparent cursor/text margin
        setMargin(new Insets(2, LEFT_PADDING, 2, 5));
    }

    private Icon scale(Icon icon) {
        if (icon == null) return null;
        try {
            Image img = ((ImageIcon) icon).getImage();
            Image scaled = img.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return icon;
        }
    }

    // This is the "Magic" fix:
    // It forces the internal text renderer to stay to the right
    @Override
    public Insets getInsets() {
        Insets insets = super.getInsets();
        insets.left += LEFT_PADDING;
        return insets;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 1. Paint the background and rounded shape (from parent)
        super.paintComponent(g);

        // 2. Paint the icon manually in the padding area
        if (icon != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Icon X position (centered in the LEFT_PADDING area)
            int iconX = (LEFT_PADDING - ICON_SIZE) / 2;
            // Icon Y position (vertically centered)
            int iconY = (getHeight() - ICON_SIZE) / 2;

            icon.paintIcon(this, g2, iconX, iconY);
            g2.dispose();
        }
    }
}