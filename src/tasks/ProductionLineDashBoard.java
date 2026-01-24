package tasks;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class ProductionLineDashBoard extends JFrame {
    public ProductionLineDashBoard() {
        setTitle("Production Line Management");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 25, 25));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(245, 245, 245));

        mainPanel.add(createCard("Tasks by Production Line", "View tasks assigned to each Production Line", () -> new ProductionLineTasks().setVisible(true)));
        mainPanel.add(createCard("Tasks by Product", "View tasks related to a specific Product", () -> new ProductTask().setVisible(true)));
        mainPanel.add(createCard("Production Lines for a Product", "View Production Lines that worked on a product", () -> new ProductionLinesByProduct().setVisible(true)));
        mainPanel.add(createCard("Products by Production Line", "View Products produced by a specific Line", () ->new ProductsByProductionLine().setVisible(true)));
        mainPanel.add(createCard("All produced Products", "View all Products produced by all Lines", () -> new AllProducedProducts().setVisible(true)));
        mainPanel.add(createCard("Most requested Product", "Find the most requested Product in a period", () -> new MostRequestedProduct().setVisible(true)));

        add(mainPanel);
    }
    private JPanel createCard (String titleText , String descText , Runnable onOpen) {
        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI" , Font.BOLD,16));
        title.setForeground(new Color(60 , 60 , 60));

        JLabel desc = new JLabel("<html><span style='#777777'>" + descText + "</span></html>");
        desc.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JButton openBtn = new JButton("Open");

        openBtn.setFocusPainted(false);
        openBtn.setBackground(new Color(200 , 180 , 140));

        openBtn.addActionListener(e -> onOpen.run());

        card.add(title , BorderLayout.NORTH);
        card.add(desc , BorderLayout.CENTER);
        card.add(openBtn , BorderLayout.SOUTH);

        return card;
    }

    public static void main (String[]args){
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("ProgressBar.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        SwingUtilities.invokeLater(() -> new ProductionLineDashBoard().setVisible(true));
    }
}