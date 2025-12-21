package View;

import Swing.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class HRView extends JFrame {

    public IconTextField nameField;
    public IconTextField emailField;
    public IconPasswordField passwordField;
    public JComboBox<String> roleComboBox;

    public DefaultListModel<String> userListModel;
    public JList<String> userList;
    public JButton addUserBtn;
    public JButton rmvBtn;
    List<String> menuItems;
    SideNavPanel sideNav;

    private CardLayout cardLayout = new CardLayout();
    private JPanel rightPanel;

    private final Color cardColor = new Color(45, 45, 45);

    public HRView() {
        Color glassesGreen = new Color(26, 188, 156);
        Color rmvRed = new Color(192, 57, 43);
        Color labelColor = new Color(220, 220, 220);

        setTitle("Super Potato HR");
        setSize(1350, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        //   Right Panel
        rightPanel = new BackgroundPanel("src/Images/background.jpg"); // use absolute classpath
        rightPanel.setLayout(cardLayout);
        setupCards(labelColor);
        add(rightPanel, BorderLayout.CENTER);

        menuItems = List.of("Add User", "Remove User");
        sideNav = new SideNavPanel(
                "SUPER POTATO",
                "HR MANAGER",
                "src/Images/logo.png",
                menuItems,
                item -> {
                    switch (item) {
                        case "Add User" -> cardLayout.show(rightPanel, "ADD");
                        case "Remove User" -> cardLayout.show(rightPanel, "REMOVE");
                    }
                },
                () -> System.exit(0)
        );
        add(sideNav, BorderLayout.WEST);

        setVisible(true);
    }

    private void setupCards(Color labelColor) {
        Color glassesGreen = new Color(26, 188, 156);
        Color rmvRed = new Color(192, 57, 43);

        //  Add User Card
        JPanel addUserCard = new JPanel(new GridBagLayout());
        addUserCard.setOpaque(false);

        //  init Card
        RoundedPanel formPanel = new RoundedPanel(cardColor, 30);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        formPanel.setPreferredSize(new Dimension(500, 520));
        formPanel.setShadowColor(new Color(0, 0, 0, 70));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 10);
        gbc.anchor = GridBagConstraints.WEST;
        Dimension labelSize = new Dimension(90, 30);

        //#######################   Title    ######################

        JLabel titleAdd = new JLabel("Add User", SwingConstants.CENTER);
        titleAdd.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleAdd.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(titleAdd, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 6, 6, 10);

        //  add icons
        Icon userIcon = new ImageIcon(getClass().getResource("/Images/user.png"));
        Icon emailIcon = new ImageIcon(getClass().getResource("/Images/email.png"));
        Icon passIcon = new ImageIcon(getClass().getResource("/Images/password.png"));

        //#######################   NAME    ######################
        //  nameLabel
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(labelColor);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setPreferredSize(labelSize);
        formPanel.add(nameLabel, gbc);
        //  nameField
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new IconTextField(10, userIcon);
        nameField.setPreferredSize(new Dimension(0, 40));
        nameField.setBorder(underline(new Color(70, 73, 75)));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(nameField, gbc);

        //#######################   Email   ######################
        //  EmailLabel
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(labelColor);
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        emailLabel.setPreferredSize(labelSize);
        formPanel.add(emailLabel, gbc);
        //  EmailField
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new IconTextField(10, emailIcon);
        emailField.setPreferredSize(new Dimension(0, 40));
        emailField.setBorder(underline(new Color(70, 73, 75)));
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(emailField, gbc);

        //#######################   Password    ######################
        //  PasswordLabel
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(labelColor);
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        passLabel.setPreferredSize(labelSize);
        formPanel.add(passLabel, gbc);
        //  PasswordField
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new IconPasswordField(10, passIcon);
        passwordField.setPreferredSize(new Dimension(0, 40));
        passwordField.setBorder(underline(new Color(70, 73, 75)));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(passwordField, gbc);

        //#######################   Role    ######################
        //  roleLabel
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(labelColor);
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        roleLabel.setPreferredSize(labelSize);
        formPanel.add(roleLabel, gbc);

        //  roleComboBox
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Supervisor", "HR"});
        roleComboBox.setPreferredSize(new Dimension(0, 35));
        formPanel.add(roleComboBox, gbc);

        //#######################   addButton    ######################
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        addUserBtn = new RoundedButton("Add User", glassesGreen);
        addUserBtn.setForeground(Color.WHITE);
        addUserBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addUserBtn.setPreferredSize(new Dimension(220, 45));
        formPanel.add(addUserBtn, gbc);

        addUserCard.add(formPanel);

        //                                       RemoveUser Card

        JPanel removeUserCard = new JPanel(new GridBagLayout());
        removeUserCard.setOpaque(false);

        RoundedPanel listPanel = new RoundedPanel(cardColor, 30);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(new EmptyBorder(20, 20, 20, 30));
        listPanel.setPreferredSize(new Dimension(500, 450));
        listPanel.setShadowColor(new Color(0,0,0,50));

        //#######################   Title    ######################
        JLabel titleRemove = new JLabel("Remove User", SwingConstants.CENTER);
        titleRemove.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleRemove.setForeground(Color.WHITE);
        titleRemove.setBorder(new EmptyBorder(0,0,15,0));

        //#######################   UserList    ######################
        userListModel = new DefaultListModel<>();
        userList= new JList<>(userListModel);
        userList.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(userList);

        //#######################   removeButton    ######################
        rmvBtn = new RoundedButton("Remove User", rmvRed);
        rmvBtn.setForeground(Color.WHITE);
        rmvBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        rmvBtn.setPreferredSize(new Dimension(150, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(rmvBtn);

        listPanel.add(titleRemove, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(bottomPanel, BorderLayout.SOUTH);

        removeUserCard.add(listPanel);
        rightPanel.add(addUserCard, "ADD");
        rightPanel.add(removeUserCard, "REMOVE");
    }

    // ==== Border helper ====
    public static Border underline(Color c) {
        return BorderFactory.createMatteBorder(0, 0, 2, 0, c);
    }

//    public void addEmailListener(KeyAdapter listener) {
//        emailField.addKeyListener(listener);
//    }
//
//    // Allow controller to listen to password typing
//    public void addPasswordListener(KeyAdapter listener) {
//        passwordField.addKeyListener(listener);
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HRView::new);
    }
}