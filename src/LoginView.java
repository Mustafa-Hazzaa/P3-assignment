import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    JRadioButton ManagerButton = new JRadioButton("Manager");
    JRadioButton SupervisorButton = new JRadioButton("Supervisor");
    JRadioButton HRButton = new JRadioButton("HR");
    JButton signInButton = new JButton("Sign in");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    LoginView() {
        Color potatoBeige = new Color(243, 229, 195);
        Color capeRed = new Color(192, 57, 43);
        Color glassesGreen = new Color(26, 188, 156);
        Color textBrown = new Color(93, 64, 55);

        this.setTitle("Super Potato Login");
        this.setSize(1350, 750);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1350, 750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("Images/imagep.jpg");
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(600, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

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


        labelUser.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPass.setHorizontalAlignment(SwingConstants.RIGHT);

        Dimension fieldSize = new Dimension(260, 40);
        usernameField.setMaximumSize(fieldSize);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);

        usernameField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        passwordField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));

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

        ButtonGroup group = new ButtonGroup();
        group.add(ManagerButton);
        group.add(SupervisorButton);
        group.add(HRButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        radioPanel.setBackground(potatoBeige);
        radioPanel.setMaximumSize(new Dimension(400, 40));

        JRadioButton[] radios = {ManagerButton, SupervisorButton, HRButton};
        for (JRadioButton btn : radios) {
            btn.setBackground(potatoBeige);
            btn.setForeground(textBrown);
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            btn.setFocusPainted(false);
            radioPanel.add(btn);
        }

        signInButton.setBackground(capeRed);
        signInButton.setForeground(Color.WHITE);
        signInButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        signInButton.setMaximumSize(fieldSize);
        signInButton.setPreferredSize(fieldSize);
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setFocusPainted(false);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        rightPanel.add(userRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(passRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(radioPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        rightPanel.add(signInButton);
        rightPanel.add(Box.createVerticalGlue());


        getRootPane().setDefaultButton(signInButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        this.setVisible(true);



    }
}