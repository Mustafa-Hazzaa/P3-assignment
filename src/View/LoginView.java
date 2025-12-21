package View;

import javax.swing.*;
import java.awt.*;
import Swing.*;

public class LoginView extends JFrame {
    public RoundedButton signInButton;
    public IconTextField usernameField;
    public IconPasswordField passwordField;
    private CardLayout cardLayout;

    public LoginView() {
        Color potatoBeige = new Color(243, 229, 195);
        Color capeRed = new Color(192, 57, 43);
        Color glassesGreen = new Color(26, 188, 156);
        Color textBrown = new Color(93, 64, 55);
        Icon userIcon = new ImageIcon(getClass().getResource("/Images/user.png"));
        Icon passIcon = new ImageIcon(getClass().getResource("/Images/password.png"));

        //  Frame Settings
        this.setTitle("Super Potato Login");
        this.setSize(1350, 750);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1350, 750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        //  Left Panel Settings
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("src/Images/imagep.jpg");
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(600, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        //  Right Panel Settings
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(potatoBeige);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to Super Potato");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setForeground(capeRed);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelUser = new JLabel("UserName:");
        JLabel labelPass = new JLabel("Password:");
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);

        labelUser.setFont(labelFont);
        labelPass.setFont(labelFont);
        labelUser.setForeground(textBrown);
        labelPass.setForeground(textBrown);

        Dimension labelDim = new Dimension(100, 30);
        labelUser.setPreferredSize(labelDim);
        labelPass.setPreferredSize(labelDim);

        labelUser.setMaximumSize(labelDim);
        labelPass.setMaximumSize(labelDim);

        //make the label next to the fields
        labelUser.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPass.setHorizontalAlignment(SwingConstants.RIGHT);

        Dimension fieldSize = new Dimension(260, 40);
        usernameField = new IconTextField(10, userIcon);
        passwordField = new IconPasswordField(10, passIcon);
        usernameField.setMaximumSize(fieldSize);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);

        usernameField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        passwordField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        usernameField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passwordField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JPanel userRow = new JPanel();
        userRow.setLayout(new BoxLayout(userRow, BoxLayout.X_AXIS));
        userRow.setBackground(potatoBeige);
        userRow.setMaximumSize(new Dimension(400, 45));
        userRow.add(labelUser);
        userRow.add(Box.createRigidArea(new Dimension(10, 0)));
        userRow.add(usernameField);
        userRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel passRow = new JPanel();
        passRow.setLayout(new BoxLayout(passRow, BoxLayout.X_AXIS));
        passRow.setBackground(potatoBeige);
        passRow.setMaximumSize(new Dimension(400, 45));
        passRow.add(labelPass);
        passRow.add(Box.createRigidArea(new Dimension(10, 0)));
        passRow.add(passwordField);
        passRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension signInSize = new Dimension(360, 40);
        signInButton= new RoundedButton("Sign in",capeRed);
//        signInButton.setBackground(capeRed);
        signInButton.setForeground(Color.WHITE);
        signInButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        signInButton.setMaximumSize(signInSize);
        signInButton.setPreferredSize(signInSize);
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setFocusPainted(false);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        rightPanel.add(userRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(passRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(signInButton);
        rightPanel.add(Box.createVerticalGlue());


        getRootPane().setDefaultButton(signInButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        this.setVisible(true);



    }
}