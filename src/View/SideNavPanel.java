package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import Control.AppRouter;
import Util.SimulatedClock;

public class SideNavPanel extends JPanel {

    private JLabel selectedLabel;
    private JLabel clockLabel;
    private final Color activeColor = new Color(255, 248, 0);
    private final Color idleColor = Color.WHITE;
    private final Color navBackground = new Color(45, 45, 45);
    private final Color hoverColor = new Color(60, 60, 60);
    private final Color separatorColor = new Color(70, 70, 70);
    private final SimulatedClock clock = SimulatedClock.getInstance();
    private JLabel logoutLabel;

    public SideNavPanel(String jobTitle, String subtitle, String logoPath,
                        List<String> menuItems, Consumer<String> onClick) {

        setLayout(new BorderLayout());
        setBackground(navBackground);
        setPreferredSize(new Dimension(260, 0));


        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, separatorColor),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        JLabel brandIcon = new JLabel();
        ImageIcon originalIcon = new ImageIcon(logoPath);
        Image scaledImg = originalIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        brandIcon.setIcon(new ImageIcon(scaledImg));

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

        JPanel menuContainer = new JPanel();
        menuContainer.setOpaque(false);
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.add(Box.createVerticalStrut(20));

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

            if (i < menuItems.size() - 1) {
                menuContainer.add(Box.createVerticalStrut(15));
            }
        }

        add(menuContainer, BorderLayout.CENTER);

        JPanel bottomNav = new JPanel(new BorderLayout());
        bottomNav.setOpaque(false);
        bottomNav.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, separatorColor));

        logoutLabel = createNavLabel("⏻   Log Out");
        logoutLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        logoutLabel.setForeground(new Color(255, 100, 100));
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 10));
        logoutLabel.addMouseListener(new MouseAdapter() {
        });

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.Y_AXIS));
        clockPanel.setOpaque(false);

        JLabel dateLabel = new JLabel("", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Inter", Font.PLAIN, 25));
        dateLabel.setForeground(Color.lightGray);

        JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Inter", Font.BOLD, 25));
        timeLabel.setForeground(Color.lightGray);

        dateLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);


        SimulatedClock clock = SimulatedClock.getInstance();

        dateLabel.setText(clock.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeLabel.setText(clock.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Timer t = new Timer(1000, e -> {
            dateLabel.setText(clock.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            timeLabel.setText(clock.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        t.start();

        clockPanel.add(dateLabel);
        clockPanel.add(timeLabel);

        bottomNav.add(logoutLabel, BorderLayout.NORTH);

        JPanel sepPanel = new JPanel();
        sepPanel.setLayout(new BoxLayout(sepPanel, BoxLayout.Y_AXIS));
        sepPanel.setOpaque(false);
        JSeparator clockSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        clockSeparator.setForeground(separatorColor);

        sepPanel.add(clockSeparator);
        sepPanel.add(Box.createVerticalStrut(5));

        bottomNav.add(sepPanel, BorderLayout.CENTER);
        bottomNav.add(clockPanel, BorderLayout.SOUTH);
        add(bottomNav, BorderLayout.SOUTH);

        for (Component comp : menuContainer.getComponents()) {
            if (comp instanceof JLabel lbl) {
                setSelected(lbl);
                break;
            }
        }

        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppRouter.getInstance().showLoginView();
            }
        });
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


}
