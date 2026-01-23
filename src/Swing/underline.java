package Swing;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;

public class underline {

    public static Border underline(Color c) {
        return BorderFactory.createMatteBorder(0, 0, 2, 0, c);
    }

}
