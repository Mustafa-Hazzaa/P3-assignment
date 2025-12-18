import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{
    JRadioButton MangerButton=new JRadioButton("Manger");
    JRadioButton SupervisorButton =new JRadioButton("ProductionSupervisor");
    JButton signInButton=new JButton("Sign in");
    JButton signUpButton=new JButton("Sign up");
    JTextField usernameField =new JTextField();

    JPasswordField passwordField=new JPasswordField();
    LoginView(){
        this.setTitle("Login");
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));



        JLabel labelUser = new JLabel("UserName:");
        JLabel labelPass = new JLabel("Password:");





        usernameField.setPreferredSize(new Dimension(200,40));
        passwordField.setPreferredSize(new Dimension(200,40));


        ButtonGroup group = new ButtonGroup();
        group.add(MangerButton);
        group.add(SupervisorButton);



        JPanel panel1=new JPanel();
        panel1.add(labelUser);
        panel1.add(usernameField);

        JPanel panel2=new JPanel();
        panel2.add(labelPass);
        panel2.add(passwordField);

        JPanel panel3 = new JPanel();
        panel3.add(MangerButton);
        panel3.add(SupervisorButton);

        JPanel panel4=new JPanel();
        panel4.add(signInButton);

        JPanel panel5=new JPanel();
        panel5.add(signUpButton);




        this.getContentPane().setBackground(new Color(255, 245, 200));
        labelUser.setForeground(new Color(255, 120, 0));
        usernameField.setBackground(Color.WHITE);
        usernameField.setForeground(Color.DARK_GRAY);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        labelPass.setForeground(new Color(255, 120, 0));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.DARK_GRAY);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        MangerButton.setBackground(new Color(255, 245, 200));
        MangerButton.setForeground(new Color(200, 80, 0));
        SupervisorButton.setBackground(new Color(255, 245, 200));
        SupervisorButton.setForeground(new Color(200, 80, 0));
        signInButton.setBackground(Color.darkGray);
        signInButton.setForeground(Color.WHITE);
        signUpButton.setBackground(Color.darkGray);
        signUpButton.setForeground(Color.WHITE);
        // signInButton.addActionListener(this);




        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.setVisible(true);

    }


    /*@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInButton) {
            String username = textField1.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!MangerButton.isSelected() && !ProductionSupervisorButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select a role", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (MangerButton.isSelected()) {
                new MangerSwing();
                dispose();
            } else {
                new ProductionSupervisoS();
                dispose();
            }*/

}