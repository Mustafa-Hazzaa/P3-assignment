package View;

import Model.Product;
import Model.ProductLine;
import Model.Task;
import Service.ProductLineService;
import Util.TaskStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductTask extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductTask(List<Task> tasks, List<Product> products, ProductLineService productLineService) {
        super(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbarPanel.setBackground(Color.WHITE);

        JComboBox<Object> productSelector = new JComboBox<>();
        productSelector.addItem("All");
        for (Product p : products) {
            productSelector.addItem(p.getName());
        }

        JComboBox<Object> statusBox = new JComboBox<>();
        statusBox.addItem("All");
        for(TaskStatus ts : TaskStatus.values()){
            statusBox.addItem(ts);
        }

        JTextField searchField = new JTextField(10);

        toolbarPanel.add(new JLabel("Product:"));
        toolbarPanel.add(productSelector);
        toolbarPanel.add(new JLabel("Status:"));
        toolbarPanel.add(statusBox);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(new JLabel("Search Task ID:"));
        toolbarPanel.add(searchField);

        model = new DefaultTableModel(new String[]{"Task ID", "Product", "Line", "Quantity", "Start", "End", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        applyStatusRenderer();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(toolbarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        populateTable(tasks, productLineService);

        Runnable filterAction = () -> applyFilters(productSelector, statusBox, searchField);
        productSelector.addActionListener(e -> filterAction.run());
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

    private void applyFilters(JComboBox<Object> productSelector, JComboBox<Object> statusBox, JTextField searchField) {
        String selectedProduct = productSelector.getSelectedItem().toString();
        String selectedStatus = statusBox.getSelectedItem().toString();
        String searchText = searchField.getText();

        RowFilter<DefaultTableModel, Integer> combinedFilter = new RowFilter<DefaultTableModel, Integer>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry){
            boolean productMatch = selectedProduct.equals("All") || entry.getValue(1).equals(selectedProduct);
                boolean statusMatch = selectedStatus.equals("All") || entry.getValue(6).equals(selectedStatus);
                boolean searchMatch = searchText.trim().isEmpty() || entry.getStringValue(0).toLowerCase().contains(searchText.toLowerCase());

                return productMatch && statusMatch && searchMatch;
            }
        };

        sorter.setRowFilter(combinedFilter);
    }

    private void applyStatusRenderer() {
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, isS, hasF, r, c);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setOpaque(true);

                if (v != null) {
                    String status = v.toString();
                    if (status.equals(TaskStatus.COMPLETED.toString())) {
                        l.setBackground(new Color(200, 230, 201));
                        l.setForeground(new Color(46, 125, 50));
                    } else if (status.equals(TaskStatus.IN_PROGRESS.toString())) {
                        l.setBackground(new Color(255, 243, 224));
                        l.setForeground(new Color(230, 81, 0));
                    } else {
                        l.setBackground(t.getBackground());
                        l.setForeground(t.getForeground());
                    }
                }
                return l;
            }
        });
    }
}
