package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductsByProductionLine extends BaseDetails {
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductsByProductionLine() {
        super("Products by Production Line", "View products filtered by line");

        JComboBox<String> lineBox = new JComboBox<>(new String[]{"All Lines", "Line A", "Line B", "Line C"});
        toolbarPanel.add(new JLabel("Select Line: "));
        toolbarPanel.add(lineBox);

        model = new DefaultTableModel(new String[]{"Product Name", "Total Quantity", "Production Line", "Status"}, 0);
        JTable table = new JTable(model);
        customizeTable(table);


        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        addDummyData();

        contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        lineBox.addActionListener(e -> {
            String selected = (String) lineBox.getSelectedItem();
            if (selected.equals("All Lines")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("^" + selected + "$", 2));
            }
        });
    }

    private void addDummyData() {
        model.addRow(new Object[]{"Product A", "1200 Units", "Line A", "ACTIVE"});
        model.addRow(new Object[]{"Product B", "2000 Units", "Line B", "ACTIVE"});
        model.addRow(new Object[]{"Product C", "450 Units", "Line A", "COMPLETED"});
        model.addRow(new Object[]{"Product A", "300 Units", "Line C", "ACTIVE"});
    }
}