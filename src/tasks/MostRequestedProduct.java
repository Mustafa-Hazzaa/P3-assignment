package tasks;

import javax.swing.*;
import java.awt.*;

public class MostRequestedProduct extends BaseDetails {
    private JLabel resultName;
    private JLabel resultQty;
    private JPanel resultCard;

    public MostRequestedProduct() {
        super("Most Requested Product", "Analysis of production demand across all lines");
        JTextField fromDateField = new JTextField("2026-01-01", 10);
        JTextField toDateField = new JTextField("2026-01-31", 10);
        JButton analyzeBtn = new JButton("Analyze Demand");

        analyzeBtn.setBackground(new Color(33, 150, 243));
        analyzeBtn.setForeground(Color.WHITE);
        analyzeBtn.setFocusPainted(false);

        toolbarPanel.add(new JLabel("From:"));
        toolbarPanel.add(fromDateField);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(new JLabel("To:"));
        toolbarPanel.add(toDateField);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(analyzeBtn);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        resultCard = new JPanel();
        resultCard.setLayout(new BoxLayout(resultCard, BoxLayout.Y_AXIS));
        resultCard.setPreferredSize(new Dimension(350, 200));
        resultCard.setBackground(new Color(245, 245, 245));
        resultCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        resultCard.setVisible(false);

        JLabel crownIcon = new JLabel("🏆");
        crownIcon.setFont(new Font("Serif", Font.PLAIN, 50));
        crownIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultName = new JLabel("Product Name");
        resultName.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultName.setForeground(new Color(33, 150, 243));
        resultName.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultQty = new JLabel("Total Requested: 0 Units");
        resultQty.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultQty.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultCard.add(Box.createVerticalStrut(20));
        resultCard.add(crownIcon);
        resultCard.add(Box.createVerticalStrut(10));
        resultCard.add(resultName);
        resultCard.add(Box.createVerticalStrut(5));
        resultCard.add(resultQty);

        centerPanel.add(resultCard);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeBtn.addActionListener(e -> {
            String from = fromDateField.getText();
            String to = toDateField.getText();
            analyzeMostRequestedProduct(from, to);
        });
    }
    private void analyzeMostRequestedProduct(String from, String to) {
        if (from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both dates", "Missing Date", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String winner = "Chips Vinegar (Family Pack)";
        String total = "15,400 Bags";

        resultName.setText(winner);
        resultQty.setText("Total Production Demand: " + total);
        resultCard.setVisible(true);
        resultCard.setBackground(new Color(240, 248, 255));
    }
}