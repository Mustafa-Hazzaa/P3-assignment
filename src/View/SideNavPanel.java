package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public class SideNavPanel extends JPanel {

    private JLabel selectedLabel;
    private final Color activeColor = Color.YELLOW;
    private final Color idleColor = Color.WHITE;
    private final Color navBackground = new Color(45, 45, 45);
    private final Color hoverColor = new Color(60, 60, 60);
    private final Color separatorColor = new Color(70, 70, 70);

    public SideNavPanel(String jobTitle, String subtitle, String logoPath,
                        List<String> menuItems, Consumer<String> onClick, Runnable onLogout) {

        setLayout(new BorderLayout());
        setBackground(navBackground);
        setPreferredSize(new Dimension(260, 0));

        // -------- Header --------
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, separatorColor),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        JLabel brandIcon = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(logoPath);
            brandIcon.setIcon(createSquareLogo(originalIcon.getImage(), 70));
        } catch (Exception e) {
            brandIcon.setText("🥔");
            brandIcon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        }

        JPanel textContainer = new JPanel();
        textContainer.setOpaque(false);
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));

        JLabel brandTitle = new JLabel(jobTitle);
        brandTitle.setForeground(Color.WHITE);
        brandTitle.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel brandSubTitle = new JLabel(subtitle);
        brandSubTitle.setForeground(new Color(150, 150, 150));
        brandSubTitle.setFont(new Font("SansSerif", Font.BOLD, 11));

        textContainer.add(brandTitle);
        textContainer.add(brandSubTitle);

        headerPanel.add(brandIcon);
        headerPanel.add(textContainer);
        add(headerPanel, BorderLayout.NORTH);

        // -------- Menu Items --------
        JPanel menuContainer = new JPanel();
        menuContainer.setOpaque(false);
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.add(Box.createVerticalStrut(20)); // top spacing

        for (int i = 0; i < menuItems.size(); i++) {
            String item = menuItems.get(i);
            JLabel lbl = createNavLabel(item);
            lbl.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    setSelected(lbl);
                    onClick.accept(item);
                }
            });
            menuContainer.add(lbl);

            // Add vertical spacing between menu items
            if (i < menuItems.size() - 1) {
                menuContainer.add(Box.createVerticalStrut(15)); // 15 px spacing
            }
        }

        add(menuContainer, BorderLayout.CENTER);

        // -------- Footer Log Out --------
        JPanel bottomNav = new JPanel(new BorderLayout());
        bottomNav.setOpaque(false);
        bottomNav.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, separatorColor));

        JLabel logoutLabel = createNavLabel("\u23FB   Log Out");
        logoutLabel.setForeground(new Color(255, 100, 100));
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 10));
        logoutLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                onLogout.run();
            }
        });

        bottomNav.add(logoutLabel, BorderLayout.SOUTH);
        add(bottomNav, BorderLayout.SOUTH);

        // -------- Select first actual JLabel menu item --------
        for (Component comp : menuContainer.getComponents()) {
            if (comp instanceof JLabel lbl) {
                setSelected(lbl);
                break;
            }
        }
    }

    private JLabel createNavLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(idleColor);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setOpaque(true);
        label.setBackground(navBackground);
        label.setMaximumSize(new Dimension(260, 50));
        label.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 10));

        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (label != selectedLabel) label.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent evt) {
                if (label != selectedLabel) label.setBackground(navBackground);
            }
        });

        return label;
    }

    private void setSelected(JLabel label) {
        if (selectedLabel != null) {
            selectedLabel.setForeground(idleColor);
            selectedLabel.setBackground(navBackground);
            selectedLabel.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 10));
        }
        selectedLabel = label;
        selectedLabel.setForeground(activeColor);
        selectedLabel.setBackground(hoverColor);
        selectedLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, activeColor),
                BorderFactory.createEmptyBorder(12, 20, 12, 10)
        ));
    }

    private ImageIcon createSquareLogo(Image img, int size) {
        Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

}
