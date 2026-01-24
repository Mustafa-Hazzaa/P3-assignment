package View;

import Control.SupervisorController;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SupervisorView extends JFrame {

    List<String> menuItems;
    public SideNavPanel sideNav;
    public SupervisorRightPanel rightPanel;

    public SupervisorView() {
        setTitle("Super Potato Supervisor");
        setMinimumSize(new Dimension(1350, 750));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        rightPanel = new SupervisorRightPanel();
        add(rightPanel, BorderLayout.CENTER);

        // Side Navigation logic
        menuItems = List.of("ITEMS","PRODUCTS", "TASK MANAGEMENT");
        sideNav = new SideNavPanel(
                "SUPER POTATO",
                "SUPERVISOR",
                "src/Images/logo.png",
                menuItems,
                item -> {
                    switch (item) {
                        case "ITEMS" -> rightPanel.showCard("ITEMS");
                        case "PRODUCTS" -> rightPanel.showCard("PRODUCTS");
                        case "TASK MANAGEMENT" -> rightPanel.showCard("TASK MANAGEMENT");
                    }
                }
        );
        add(sideNav, BorderLayout.WEST);

        setVisible(true);
    }

}