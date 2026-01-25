package View;

import Model.ProductLine;
import Model.Task;
import Service.ProductLineService;
import Util.TaskStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductionLineTasks extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLineTasks(List<ProductLine> productLines,List<Task> tasks, ProductLineService productLineService) {
        super(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Toolbar ---
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbarPanel.setBackground(Color.WHITE);

        JComboBox<String> lineBox = new JComboBox<>(new String[]{"All"});
        for (ProductLine p:productLines)
            lineBox.addItem(p.getName());

        DefaultComboBoxModel<Object> statusModel = new DefaultComboBoxModel<>();
        statusModel.addElement("All");
        for (TaskStatus status : TaskStatus.values()) {
            statusModel.addElement(status);
        }
        JComboBox<Object> statusBox = new JComboBox<>(statusModel);
        JTextField searchField = new JTextField(15);

        toolbarPanel.add(new JLabel("Production Line:"));
        toolbarPanel.add(lineBox);
        toolbarPanel.add(new JLabel("Status:"));
        toolbarPanel.add(statusBox);
        toolbarPanel.add(new JLabel("Search:"));
        toolbarPanel.add(searchField);

        // --- Table ---
        model = new DefaultTableModel(new String[]{"Task ID", "Product", "Line", "Quantity", "Start", "End", "Status"}, 0);
        table = new JTable(model);
        applyStatusRenderer();

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // --- Layout ---
        add(toolbarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- Populate and Filter ---
        populateTable(tasks, productLineService);

        Runnable filterAction = () -> applyFilters(lineBox, statusBox, searchField);
        lineBox.addActionListener(e -> filterAction.run());
        statusBox.addActionListener(e -> filterAction.run());
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filterAction.run();
            }
        });
    }

    private void populateTable(List<Task> tasks, ProductLineService productLineService) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Task task : tasks) {
            ProductLine line = productLineService.getById(task.getProductLineId());
            String lineName = (line != null) ? line.getName() : "Unknown";
            String startTimeStr = (task.getStartTime() != null) ? task.getStartTime().format(formatter) : "...";
            String endTimeStr = (task.getEndTime() != null) ? task.getEndTime().format(formatter) : "...";

            model.addRow(new Object[]{
                    task.getId(),
                    task.getProductName(),
                    lineName,
                    task.getQuantity(),
                    startTimeStr,
                    endTimeStr,
                    task.getStatus().toString()
            });
        }
    }

    private void applyFilters(JComboBox<String> l, JComboBox<Object> s, JTextField search) {
        String line = l.getSelectedItem().toString();
        String status = s.getSelectedItem().toString();
        String txt = search.getText().toLowerCase();

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean mLine = line.equals("All") || entry.getStringValue(2).equals(line);
                boolean mStatus = status.equals("All") || entry.getStringValue(6).equals(status);
                boolean mSearch = txt.isEmpty() || entry.getStringValue(1).toLowerCase().contains(txt);
                return mLine && mStatus && mSearch;
            }
        });
    }

    private void applyStatusRenderer() {
        table.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, isS, hasF, r, c);
                l.setHorizontalAlignment(JLabel.CENTER);
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
