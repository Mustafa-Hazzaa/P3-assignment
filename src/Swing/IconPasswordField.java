package Swing;

import javax.swing.*;
import java.awt.*;

public class IconPasswordField extends RoundedPasswordField {
    private Icon icon;

    public IconPasswordField(int cols, Icon icon) {
        super(cols);
        this.icon = icon;
        setMargin(new Insets(5 + icon.getIconHeight()/2, 25 + icon.getIconWidth(), 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(icon != null) {
            int y = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g, 5, y);
        }
    }
}
