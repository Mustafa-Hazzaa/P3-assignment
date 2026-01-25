package View;

import Model.Product;
import Model.ProductLine;
import Model.Task;
import Service.ProductLineService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ProductionLinesByProduct extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLinesByProduct(List<Task> tasks, List<Product> products, ProductLineService productLineService) {
        super(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbarPanel.setBackground(Color.WHITE);

        JComboBox<Object> productSelector = new JComboBox<>();
        productSelector.addItem("All Products");
        for (Product p : products) {
            productSelector.addItem(p.getName());
        }

        toolbarPanel.add(new JLabel("Select Product: "));
        toolbarPanel.add(productSelector);

        model = new DefaultTableModel(new String[]{"Task ID", "Product Name", "Line Name", "Quantity", "Status"}, 0) {
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
        populateTable(tasks, productLineService);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(toolbarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        productSelector.addActionListener(e -> {
            String selected = productSelector.getSelectedItem().toString();
            if (selected.equals("All Products")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        return entry.getStringValue(1).equals(selected);
                    }
                });
            }
        });
    }

    private void populateTable(List<Task> tasks, ProductLineService productLineService) {
        model.setRowCount(0);
        for (Task task : tasks) {
            ProductLine line = productLineService.getById(task.getProductLineId());
            String lineName = (line != null) ? line.getName() : "Unknown";

            model.addRow(new Object[]{
                    task.getId(),
                    task.getProductName(),
                    lineName,
                    task.getQuantity(),
                    task.getStatus().toString()
            });
        }
    }
}
