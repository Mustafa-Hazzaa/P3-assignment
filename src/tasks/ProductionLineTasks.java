package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductionLineTasks extends BaseDetails {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLineTasks() {
        super("Tasks by Production Line", "View and manage tasks assigned to Production Lines");
        JComboBox<String> lineBox = new JComboBox<>(new String[]{
                "All", "Line A", "Line B", "Line C"});

        JComboBox<String> statusBox = new JComboBox<>(new String[]{
                "All", "IN_PROGRESS", "COMPLETED"
        });

        JTextField searchField = new JTextField(15);
        JButton addBtn = new JButton("Add Task");
        JButton deleteBtn = new JButton("Delete Task");

        addBtn.putClientProperty("JButton.buttonType", "roundRect");
        deleteBtn.putClientProperty("JButton.buttonType", "roundRect");

        toolbarPanel.add(addBtn);
        toolbarPanel.add(deleteBtn);

        toolbarPanel.add(new JLabel("Production Line:"));
        toolbarPanel.add(lineBox);

        toolbarPanel.add(new JLabel("Status:"));
        toolbarPanel.add(statusBox);

        toolbarPanel.add(new JLabel("Search:"));
        toolbarPanel.add(searchField);

        model = new DefaultTableModel(new String[]{
                "Task ID", "Product", "Line", "Quantity", "Start", "End", "Status"
        }, 0);

        table = new JTable(model);
        table.setRowHeight(36);

        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(BorderFactory.createCompoundBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableCard = new JPanel(new BorderLayout());

        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        tableCard.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(tableCard, BorderLayout.CENTER);

        model.addRow(new Object[]{"T-01", "Product A", "Line A", 120, "01/01", "05/01", "COMPLETED"});
        model.addRow(new Object[]{"T-02", "Product B", "Line B", 80, "03/01", "-", "IN_PROGRESS"});
        model.addRow(new Object[]{"T-03", "Product C", "Line A", 200, "02/01", "06/01", "COMPLETED"});

        Runnable applyFilter = () -> {
            String line = lineBox.getSelectedItem().toString();
            String status = statusBox.getSelectedItem().toString();
            String search = searchField.getText().toLowerCase();

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    boolean matchLine = line.equals("All") || entry.getStringValue(2).equals(line);
                    boolean matchStatus = status.equals("All") || entry.getStringValue(6).equals(status);
                    boolean matchSearch = search.isEmpty() || entry.getStringValue(0).toLowerCase().contains(search) ||
                            entry.getStringValue(1).toLowerCase().contains(search);

                    return matchLine && matchStatus && matchSearch;
                }
            });
        };

        lineBox.addActionListener(e -> applyFilter.run());
        statusBox.addActionListener(e -> applyFilter.run());
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                applyFilter.run();
            }
        });
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,"Add Task dialog here"));
        deleteBtn.addActionListener(e -> { int row = table.getSelectedRow();
        if (row== -1) return;

        model.removeRow(table.convertRowIndexToModel(row));
        });
    }
}
