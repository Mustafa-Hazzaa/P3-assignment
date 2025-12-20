package Swing;

import javax.swing.*;
import java.awt.*;

public class IconPasswordField extends RoundedPasswordField {

    private Icon icon;
    private static final int ICON_SIZE = 18;
    // Matching the 35px offset used in the TextField for alignment
    private static final int LEFT_PADDING = 35;

    public IconPasswordField(int cols, Icon icon) {
        super(cols);
        this.icon = scale(icon);
        // Standard margin (top, left, bottom, right)
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

    // This ensures the password dots/bullets start after the icon
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

        // 2. Paint the icon manually
        if (icon != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Center the icon horizontally within the LEFT_PADDING area
            int iconX = (LEFT_PADDING - ICON_SIZE) / 2;
            // Vertically center the icon
            int iconY = (getHeight() - ICON_SIZE) / 2;

            icon.paintIcon(this, g2, iconX, iconY);
            g2.dispose();
        }
    }
}