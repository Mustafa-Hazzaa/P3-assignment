package View;

import Swing.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class HRView extends JFrame {

    public IconTextField nameField;
    public IconTextField emailField;
    public IconPasswordField passwordField;
    public JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Manager", "Supervisor", "HR"});
    public DefaultListModel<String> userListModel = new DefaultListModel<>();
    public JList<String> userList = new JList<>(userListModel);

    private CardLayout cardLayout = new CardLayout();
    private JPanel rightPanel;

    private final Color navBackground = new Color(45, 45, 45);

    public HRView() {
        Color glassesGreen = new Color(26, 188, 156);
        Color rmvRed = new Color(192, 57, 43);
        Color labelColor = new Color(220, 220, 220);

        setTitle("Super Potato HR");
        setSize(1350, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Right Panel ----
        rightPanel = new BackgroundPanel("Images/background.jpg");
        rightPanel.setLayout(cardLayout);
        setupCards(glassesGreen, rmvRed, labelColor);
        add(rightPanel, BorderLayout.CENTER);

        // ---- Side Navigation Panel ----
        List<String> menuItems = List.of("Add User", "Remove User");
        SideNavPanel sideNav = new SideNavPanel(
                "SUPER POTATO",
                "HR MANAGER",
                "Images/logo.png",
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

    private void setupCards(Color glassesGreen, Color rmvRed, Color labelColor) {

        // ==== Add User Card ====
        JPanel addUserCard = new JPanel(new GridBagLayout());
        addUserCard.setOpaque(false);

        RoundedPanel formPanel = new RoundedPanel(navBackground, 30);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        formPanel.setPreferredSize(new Dimension(500, 520));
        formPanel.setShadowColor(new Color(0,0,0,70));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleAdd = new JLabel("Add User", SwingConstants.CENTER);
        titleAdd.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleAdd.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(titleAdd, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 5);

        // Icons
        Icon userIcon = new ImageIcon("Images/user.png");
        Icon emailIcon = new ImageIcon("Images/email.png");
        Icon passIcon = new ImageIcon("Images/password.png");

        // ---- Name ----
        gbc.gridy++; gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(labelColor);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new IconTextField(10, userIcon);
        nameField.setPreferredSize(new Dimension(0, 40));
        nameField.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(70,73,75)));
        formPanel.add(nameField, gbc);

        // ---- Email ----
        gbc.gridy++; gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setForeground(labelColor);
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new IconTextField(10, emailIcon);
        emailField.setPreferredSize(new Dimension(0, 40));
        emailField.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(70,73,75)));
        formPanel.add(emailField, gbc);

        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isValidEmail(emailField.getText())) {
                    emailField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.RED));
                } else {
                    emailField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.GREEN));
                }
            }
        });

        // ---- Password ----
        gbc.gridy++; gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password: ");
        passLabel.setForeground(labelColor);
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new IconPasswordField(10, passIcon);
        passwordField.setPreferredSize(new Dimension(0, 40));
        passwordField.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(70,73,75)));
        formPanel.add(passwordField, gbc);

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int strength = passwordCheck(passwordField.getText());
                Color color;
                if (strength <= 1) color = Color.RED;
                else if (strength == 2) color = Color.YELLOW;
                else color = Color.GREEN;
                passwordField.setBorder(BorderFactory.createMatteBorder(0,0,2,0,color));
            }
        });

        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // ---- Role ComboBox ----
        gbc.gridy++; gbc.gridx = 0;
        JLabel roleLabel = new JLabel("Role: ");
        roleLabel.setForeground(labelColor);
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        roleComboBox.setPreferredSize(new Dimension(0, 35));
        formPanel.add(roleComboBox, gbc);

        // ---- Add User Button ----
        JButton addUserBtn = new RoundedButton("Add User", glassesGreen);
        addUserBtn.setForeground(Color.WHITE);
        addUserBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        addUserBtn.setPreferredSize(new Dimension(220, 45));
        formPanel.add(addUserBtn, gbc);

        addUserCard.add(formPanel);

        // ==== Remove User Card ====
        JPanel removeUserCard = new JPanel(new GridBagLayout());
        removeUserCard.setOpaque(false);

        RoundedPanel listPanel = new RoundedPanel(navBackground, 30);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        listPanel.setPreferredSize(new Dimension(500, 450));
        listPanel.setShadowColor(new Color(0,0,0,50));

        JLabel titleRemove = new JLabel("Remove User", SwingConstants.CENTER);
        titleRemove.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleRemove.setForeground(Color.WHITE);
        titleRemove.setBorder(new EmptyBorder(0,0,15,0));

        userList.setFont(new Font("SansSerif", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(userList);

        JButton rmvBtn = new RoundedButton("Remove User", rmvRed);
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

    // ==== Email validation ====
    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{3,6}$");
    }

    // ==== Password strength 0=weak,1=weak,2=medium,3=strong ====
    public static int passwordCheck(String password) {
        if (password.length() < 6 || password.matches(".*\\s.*")) return 0;
        int score = 1;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/`~|\\\\].*")) score++;
        return Math.min(score, 3); // limit max to 3
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HRView::new);
    }
}
