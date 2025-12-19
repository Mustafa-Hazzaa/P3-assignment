import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HRView extends JFrame {

    public JTextField nameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    public JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Manager", "Supervisor", "HR"});
    JButton addUserButton = new JButton("Add User");
    JButton removeUserButton = new JButton("Remove User");
    public DefaultListModel<String> userListModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(userListModel);

    HRView() {
        Color potatoBeige = new Color(243, 229, 195);
        Color capeRed = new Color(192, 57, 43);
        Color glassesGreen = new Color(26, 188, 156);
        Color textBrown = new Color(93, 64, 55);

        this.setTitle("Super Potato HR");
        this.setSize(1350, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("Images/imagep.jpg");
        Image image = icon.getImage().getScaledInstance(600, 750, Image.SCALE_SMOOTH);
        leftPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(potatoBeige);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("HR Management", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setForeground(capeRed);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(title, gbc);

        JLabel labelName = new JLabel("Name:");
        labelName.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelName.setForeground(textBrown);
        nameField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        nameField.setPreferredSize(new Dimension(300, 35));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        rightPanel.add(labelName, gbc);
        gbc.gridx = 1;
        rightPanel.add(nameField, gbc);

        JLabel labelPass = new JLabel("Password:");
        labelPass.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelPass.setForeground(textBrown);
        passwordField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        passwordField.setPreferredSize(new Dimension(300, 35));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(labelPass, gbc);
        gbc.gridx = 1;
        rightPanel.add(passwordField, gbc);

        JLabel labelRole = new JLabel("Role:");
        labelRole.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelRole.setForeground(textBrown);

        roleComboBox.setPreferredSize(new Dimension(300, 35));
        roleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        roleComboBox.setBackground(Color.WHITE);
//        roleComboBox.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(labelRole, gbc);
        gbc.gridx = 1;
        rightPanel.add(roleComboBox, gbc);


        addUserButton.setBackground(glassesGreen);
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        addUserButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 10, 15, 10); // extra vertical space
        rightPanel.add(addUserButton, gbc);

        userList.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        JScrollPane listScroll = new JScrollPane(userList);
        listScroll.setPreferredSize(new Dimension(300, 200));
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 10, 5, 10);
        rightPanel.add(listScroll, gbc);

        removeUserButton.setBackground(capeRed);
        removeUserButton.setForeground(Color.WHITE);
        removeUserButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        removeUserButton.setFocusPainted(false);
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        rightPanel.add(removeUserButton, gbc);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);


        this.setVisible(true);
    }

    public static void main(String[] args) {
        UserData model = new UserData();
        HRView view = new HRView();
        new HRController(model, view);
    }
}
