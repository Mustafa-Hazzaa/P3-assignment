package View;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;

public class underline {

    // Creates an underline border with the given color
    public static Border underline(Color c) {
        return BorderFactory.createMatteBorder(0, 0, 2, 0, c);
    }

}
