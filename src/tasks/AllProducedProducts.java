package tasks;

import javax.swing.*;
import java.awt.*;

public class AllProducedProducts extends BaseDetails {
    private JPanel cardsPanel;

    public AllProducedProducts() {
        super("Produced Products", "View all finished goods manufactured across all production lines");
        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All Types", "Salted", "Vinegar", "Chili", "Cheese"});
        toolbarPanel.add(new JLabel("Product Flavor: "));
        toolbarPanel.add(typeFilter);
        cardsPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        addFinishedProductCard("Chips Vinegar", "5000 Bags", "Line A", new Color(173, 216, 230));
        addFinishedProductCard("Chips Chili", "3200 Bags", "Line B", new Color(255, 182, 193));
        addFinishedProductCard("Chips Salt", "4100 Bags", "Line C", new Color(245, 245, 220));
        addFinishedProductCard("Chips Cheese", "2800 Bags", "Line B", new Color(255, 223, 186));
    }
    private void addFinishedProductCard(String name, String totalQty, String lines, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 180));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel icon = new JLabel("✨");
        icon.setFont(new Font("Serif", Font.PLAIN, 40));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel qtyLabel = new JLabel("Stock: " + totalQty);
        qtyLabel.setForeground(new Color(70, 70, 70));
        qtyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lineInfo = new JLabel("Produced by: " + lines);
        lineInfo.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lineInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel accentBar = new JPanel();
        accentBar.setBackground(color);
        accentBar.setMaximumSize(new Dimension(100, 4));

        card.add(icon);
        card.add(Box.createVerticalStrut(10));
        card.add(nameLabel);
        card.add(qtyLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(lineInfo);
        card.add(Box.createVerticalStrut(10));
        card.add(accentBar);

        cardsPanel.add(card);
    }
}