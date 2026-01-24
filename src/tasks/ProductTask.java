package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductTask extends BaseDetails {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductTask() {
        super("Tasks by Product", "Browse all tasks related to a specific product");

        JComboBox<String> productSelector = new JComboBox<>(new String[]{
                "All", "Product A", "Product B", "Product C"
        });
        JTextField searchField = new JTextField(15);

        toolbarPanel.add(new JLabel("Product:"));
        toolbarPanel.add(productSelector);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{
                "All" , "IN_PROGRESS" , "COMPLETED"});
        toolbarPanel.add(new JLabel("Status:"));
        toolbarPanel.add(statusBox);

        Runnable applyFilters = () -> {String prod =productSelector.getSelectedItem().toString();
            String stat = statusBox.getSelectedItem().toString();
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    boolean matchesProd = prod.equals("All") || entry.getStringValue(1).equals(prod);
                    boolean matchesStat = stat.equals("All") || entry.getStringValue(6).equals(stat);
                    return matchesProd && matchesStat;
                }
            });
        };
        productSelector.addActionListener(e -> applyFilters.run());
        statusBox.addActionListener(e -> applyFilters.run());

        toolbarPanel.add(Box.createHorizontalStrut(20));

        toolbarPanel.add(new JLabel("Search ID:"));
        toolbarPanel.add(searchField);

        model = new DefaultTableModel(new String[]{
                "Task ID", "Product", "Line", "Quantity", "Start", "End", "Status"
        }, 0);

        table = new JTable(model);
        customizeTable(table);
        applyStatusRenderer();

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(tableWrapper , BorderLayout.CENTER);

        //data
        model.addRow(new Object[]{
                "T-01" , "Product A" , "Line A" , 120 , "01/01" , "05/01" , "COMPLETED"
        });

        productSelector.addActionListener(e -> {
            String selected = productSelector.getSelectedItem().toString();
            if (selected.equals("All")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selected, 1));
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String txt = searchField.getText();
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt, 0));
            }
        });
    }

    private void applyStatusRenderer() {
        table.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, isS, hasF, r, c);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setOpaque(true);

                if (v != null) {
                    if (v.toString().equals("COMPLETED")) {
                        l.setBackground(new Color(200, 230, 201));
                        l.setForeground(new Color(46, 125, 50));
                    } else {
                        l.setBackground(new Color(255, 243, 224));
                        l.setForeground(new Color(230, 81, 0));
                    }
                }
                return l;
            }
        });
    }
}