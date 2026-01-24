package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductionLinesByProduct extends BaseDetails {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLinesByProduct() {
        super("Production Lines by Product", "Detailed view of tasks for a specific product");
        JComboBox<String> productSelector = new JComboBox<>(new String[]{"All Products", "Product A", "Product B", "Product C"});
        toolbarPanel.add(new JLabel("Select Product: "));
        toolbarPanel.add(productSelector);

        model = new DefaultTableModel(new String[]{
                "Task ID", "Product Name", "Line Name", "Quantity", "Status"
        }, 0);

         table= new JTable(model);
        customizeTable(table);
        table.setDefaultEditor(Object.class, null);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        addFullTaskData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        productSelector.addActionListener(e -> {
            String selected = (String) productSelector.getSelectedItem();
            if (selected.equals("All Products")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("^" + selected + "$", 1));
            }
        });
    }
    private void addFullTaskData() {
        model.addRow(new Object[]{"T-01", "Product A", "Line A", "500", "COMPLETED"});
        model.addRow(new Object[]{"T-02", "Product B", "Line B", "1000", "COMPLETED"});
        model.addRow(new Object[]{"T-04", "Product A", "Line C", "300", "IN_PROGRESS"});
        model.addRow(new Object[]{"T-05", "Product C", "Line A", "200", "COMPLETED"});
        model.addRow(new Object[]{"T-08", "Product B", "Line B", "450", "IN_PROGRESS"});
    }
}