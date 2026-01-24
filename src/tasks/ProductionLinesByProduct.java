package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductionLinesByProduct extends BaseDetails {
    private JTable table;
    private DefaultTableModel model;

    public ProductionLinesByProduct() {
        super("Production Lines by Product", "Detailed view of tasks and lines for a specific product");

        JComboBox<String> productSelector = new JComboBox<>(new String[]{
                "All", "Product A", "Product B", "Product C"
        });

        toolbarPanel.add(new JLabel("Select Product: "));
        toolbarPanel.add(productSelector);

        model = new DefaultTableModel(new String[]{
                "Task ID", "Line Name", "Quantity", "Start Date", "Status"
        }, 0);

        table = new JTable(model);
        customizeTable(table);
        table.setDefaultEditor(Object.class, null);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        productSelector.addActionListener(e -> {
            String selected = (String) productSelector.getSelectedItem();
            updateTable(selected);
        });

        updateTable("All");
    }

    private void updateTable(String productName) {
        model.setRowCount(0);
        switch (productName) {
            case "Product A":
                model.addRow(new Object[]{"T-01", "Line A", "500", "20/01/2026", "COMPLETED"});
                model.addRow(new Object[]{"T-04", "Line C", "300", "22/01/2026", "IN_PROGRESS"});
                break;
            case "Product B":
                model.addRow(new Object[]{"T-02", "Line B", "1000", "21/01/2026", "COMPLETED"});
                model.addRow(new Object[]{"T-08", "Line B", "450", "23/01/2026", "IN_PROGRESS"});
                break;
            case "Product C":
                model.addRow(new Object[]{"T-05", "Line A", "200", "22/01/2026", "COMPLETED"});
                break;
            case "All":
                model.addRow(new Object[]{"T-01", "Line A", "500", "20/01/2026", "COMPLETED"});
                model.addRow(new Object[]{"T-02", "Line B", "1000", "21/01/2026", "COMPLETED"});
                model.addRow(new Object[]{"T-04", "Line C", "300", "22/01/2026", "IN_PROGRESS"});
                model.addRow(new Object[]{"T-05", "Line A", "200", "22/01/2026", "COMPLETED"});
                model.addRow(new Object[]{"T-08", "Line B", "450", "23/01/2026", "IN_PROGRESS"});
                break;
        }
    }
}