import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HRView extends JFrame {

    // Fields for user input
    public JTextField nameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    public JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Manager", "Supervisor", "HR"});

    // Buttons
    JButton addUserButton = new JButton("Add User");
    JButton removeUserButton = new JButton("Remove User");

    // User display
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

        // Left Panel with Image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("Images/imagep.jpg");
        Image image = icon.getImage().getScaledInstance(600, 750, Image.SCALE_SMOOTH);
        leftPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(potatoBeige);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("HR Management");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setForeground(capeRed);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name Row
        JLabel labelName = new JLabel("Name:");
        labelName.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelName.setForeground(textBrown);
        nameField.setMaximumSize(new Dimension(260, 40));
        nameField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));

        JPanel nameRow = new JPanel();
        nameRow.setLayout(new BoxLayout(nameRow, BoxLayout.X_AXIS));
        nameRow.setBackground(potatoBeige);
        nameRow.add(labelName);
        nameRow.add(Box.createRigidArea(new Dimension(10, 0)));
        nameRow.add(nameField);
        nameRow.setMaximumSize(new Dimension(400, 45));

        // Password Row
        JLabel labelPass = new JLabel("Password:");
        labelPass.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelPass.setForeground(textBrown);
        passwordField.setMaximumSize(new Dimension(260, 40));
        passwordField.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));

        JPanel passRow = new JPanel();
        passRow.setLayout(new BoxLayout(passRow, BoxLayout.X_AXIS));
        passRow.setBackground(potatoBeige);
        passRow.add(labelPass);
        passRow.add(Box.createRigidArea(new Dimension(10, 0)));
        passRow.add(passwordField);
        passRow.setMaximumSize(new Dimension(400, 45));

        // Role Row
        JLabel labelRole = new JLabel("Role:");
        labelRole.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelRole.setForeground(textBrown);
        roleComboBox.setMaximumSize(new Dimension(260, 40));

        JPanel roleRow = new JPanel();
        roleRow.setLayout(new BoxLayout(roleRow, BoxLayout.X_AXIS));
        roleRow.setBackground(potatoBeige);
        roleRow.add(labelRole);
        roleRow.add(Box.createRigidArea(new Dimension(10, 0)));
        roleRow.add(roleComboBox);
        roleRow.setMaximumSize(new Dimension(400, 45));

        // Buttons Row
        addUserButton.setBackground(glassesGreen);
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        addUserButton.setFocusPainted(false);

        removeUserButton.setBackground(capeRed);
        removeUserButton.setForeground(Color.WHITE);
        removeUserButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        removeUserButton.setFocusPainted(false);

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(potatoBeige);
        buttonRow.add(addUserButton);
        buttonRow.add(removeUserButton);

        // User List
        userList.setBorder(BorderFactory.createLineBorder(glassesGreen, 2));
        JScrollPane listScroll = new JScrollPane(userList);
        listScroll.setPreferredSize(new Dimension(400, 200));
        listScroll.setMaximumSize(new Dimension(400, 200));

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        rightPanel.add(nameRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(passRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(roleRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(buttonRow);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(listScroll);
        rightPanel.add(Box.createVerticalGlue());

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);




        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1) {
                    userListModel.remove(selectedIndex);
                }
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        UserData model = new UserData();
        HRView view = new HRView();
        new HRController(model,view);
    }
}
