package Swing;

import javax.swing.*;

public class SafeTextField extends JTextField {

    public SafeTextField() {
        super();
        initFocusListener();
    }

    public SafeTextField(int columns) {
        super(columns);
        initFocusListener();
    }

    private void initFocusListener() {
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                validateInput();
            }
        });
    }


    public void validateInput() {
        String text = getText();
        if (text.contains(",") || text.contains("\"") || text.contains("'")) {
            text = text.replace(",", "").replace("\"", "").replace("'", "");
            setText(text);

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid characters (comma, quotes) have been removed",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}

