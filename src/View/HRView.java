package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HRView extends JFrame {

    List<String> menuItems;
    public SideNavPanel sideNav;
    public HrRightPanel rightPanel;

    public HRView() {
        // Colors
        Color glassesGreen = new Color(26, 188, 156);
        Color rmvRed = new Color(192, 57, 43);
        Color labelColor = new Color(220, 220, 220);

        //#######################   Frame setup    ######################
        setTitle("Super Potato HR");
        setSize(1350, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        //#######################   Right Panel    ######################
        rightPanel = new HrRightPanel();
        add(rightPanel, BorderLayout.CENTER);

        //#######################   Side Navigation    ######################
        menuItems = List.of("Add User", "Remove User");
        sideNav = new SideNavPanel(
                "SUPER POTATO",
                "HR MANAGER",
                "src/Images/logo.png",
                menuItems,
                item -> {
                    switch (item) {
                        case "Add User" -> ((HrRightPanel) rightPanel).showCard("ADD");
                        case "Remove User" -> ((HrRightPanel) rightPanel).showCard("REMOVE");
                    }
                }
        );
        add(sideNav, BorderLayout.WEST);

        setVisible(true);
    }

    //#######################   Main Method    ######################
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HRView::new);
    }
}
