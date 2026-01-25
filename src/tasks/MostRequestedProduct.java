package tasks;

import Model.Task;
import Service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MostRequestedProduct extends JPanel {

    private final TaskService taskService;

    private JLabel resultName;
    private JLabel resultQty;
    private JPanel resultCard;

    public MostRequestedProduct(TaskService taskService) {
        super(new BorderLayout());
        this.taskService = taskService;

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setBackground(Color.WHITE);

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

        add(toolbarPanel, BorderLayout.NORTH);

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(resultCard, gbc);

        add(centerPanel, BorderLayout.CENTER);

        analyzeBtn.addActionListener(e -> analyzeMostRequestedProduct(fromDateField.getText(), toDateField.getText()));

        if (taskService.getAllTasks().isEmpty()) {
            showSampleProduct();
        }
    }

    private void showSampleProduct() {
        resultName.setText("Sample Product");
        resultQty.setText("Total Production Demand: 100 Units");
        resultCard.setVisible(true);
        resultCard.setBackground(new Color(240, 248, 255));
        resultCard.revalidate();
        resultCard.repaint();
        SwingUtilities.getWindowAncestor(this).pack();
    }

    private void analyzeMostRequestedProduct(String from, String to) {
        LocalDate fromDate;
        LocalDate toDate;

        try {
            fromDate = LocalDate.parse(from);
            toDate = LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Date Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            showSampleProduct();
            JOptionPane.showMessageDialog(this, "No tasks found. Showing sample data.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Map<String, Integer> demandMap = new HashMap<String, Integer>();

        for (Task t : tasks) {
            LocalDate taskStart = t.getStartTime().toLocalDate();

            if (taskStart.isBefore(fromDate) || taskStart.isAfter(toDate)) {
                continue;
            }

            String productName = t.getProductName();
            int quantity = t.getQuantity();

            if (demandMap.containsKey(productName)) {
                int oldValue = demandMap.get(productName);
                demandMap.put(productName, oldValue + quantity);
            } else {
                demandMap.put(productName, quantity);
            }
        }

        String topProduct = null;
        int maxQuantity = 0;
        for (Map.Entry<String, Integer> entry : demandMap.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                topProduct = entry.getKey();
            }
        }

        if (topProduct != null) {
            resultName.setText(topProduct);
            resultQty.setText("Total Production Demand: " + maxQuantity + " Units");
            resultCard.setVisible(true);
            resultCard.setBackground(new Color(240, 248, 255));
            resultCard.revalidate();
            resultCard.repaint();
            SwingUtilities.getWindowAncestor(this).pack();
        }
    }
}
