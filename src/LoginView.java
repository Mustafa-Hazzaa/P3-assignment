import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JRadioButton adminRadio = new JRadioButton("admin");
    JRadioButton userRadio = new JRadioButton("user");
    JButton submitButton = new JButton("Submit");

    public LoginView() {
        setTitle("Login Test");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(userRadio);
        JPanel rolePanel = new JPanel();
        rolePanel.add(adminRadio);
        rolePanel.add(userRadio);
        gbc.gridx = 1;
        add(rolePanel, gbc);

        // Submit
        gbc.gridx = 1; gbc.gridy = 3;
        add(submitButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
