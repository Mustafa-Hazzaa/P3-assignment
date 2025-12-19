import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{
    JRadioButton MangerButton=new JRadioButton("Manger");
    JRadioButton SupervisorButton =new JRadioButton("Supervisor");
    JButton signInButton=new JButton("Sign in");
    JButton signUpButton=new JButton("Sign up");
    JTextField usernameField =new JTextField();
    JPasswordField passwordField=new JPasswordField();



    LoginView(){
        Color potatoBeige =new Color(243,229,195);
        Color capeRed = new Color(192,57,43);
        Color glassesGreen = new Color(26,188,156);
        Color textBrown = new Color(93,64,55);





        this.setTitle("Super Potato Login");
        this.setSize(1350,750);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1350,750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel =new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        ImageIcon icon =new ImageIcon("imagep.jpg");
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(600,750,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon =new ImageIcon(scaledImg);
        JLabel imageLabel =new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel rightPanel =new JPanel();
        rightPanel.setBackground(potatoBeige);
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

        JLabel title =new JLabel("Welcome to Super Potato");
        title.setFont(new Font("Comic Sans MS",Font.BOLD, 24));
        title.setForeground(capeRed);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelUser = new JLabel("UserName:");
        JLabel labelPass = new JLabel("Password:");
        labelUser.setForeground(textBrown);
        labelPass.setForeground(textBrown);

        Dimension fieldSize =new Dimension(260,40);
        usernameField.setMaximumSize(fieldSize);
        usernameField.setPreferredSize(fieldSize);
      usernameField.setHorizontalAlignment(JTextField.LEFT);


        passwordField.setMaximumSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        passwordField.setHorizontalAlignment(JTextField.LEFT);

        usernameField.setBorder(BorderFactory.createLineBorder(glassesGreen,2));
        passwordField.setBorder(BorderFactory.createLineBorder(glassesGreen,2));


        ButtonGroup group = new ButtonGroup();
        group.add(MangerButton);
        group.add(SupervisorButton);

        MangerButton.setBackground(potatoBeige);
        SupervisorButton.setBackground(potatoBeige);
        MangerButton.setForeground(textBrown);
        SupervisorButton.setForeground(textBrown);
        signInButton.setBackground(capeRed);
        signInButton.setForeground(Color.WHITE);
        signUpButton.setBackground(glassesGreen);
        signUpButton.setForeground(Color.WHITE);

        Dimension buttonSize =new Dimension(200,40);
        signInButton.setMaximumSize(buttonSize);
        signUpButton.setMaximumSize(buttonSize);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0,25)));

        rightPanel.add(labelUser);
        rightPanel.add(usernameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,15)));

        rightPanel.add(labelPass);
        rightPanel.add(passwordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,15)));

        rightPanel.add(MangerButton);
        rightPanel.add(SupervisorButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0,20)));

        rightPanel.add(signInButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0,10)));
        rightPanel.add(signUpButton);
        rightPanel.add(Box.createVerticalGlue());

        add(leftPanel,BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);
        this.setVisible(true);

    }

}