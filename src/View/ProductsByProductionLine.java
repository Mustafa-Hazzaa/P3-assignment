package View;

import Model.ProductLine;
import Model.Task;
import Util.TaskStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsByProductionLine extends JPanel {
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductsByProductionLine(List<Task> tasks, List<ProductLine> productLines) {
        super(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbarPanel.setBackground(Color.WHITE);

        JComboBox<Object> lineBox = new JComboBox<>();
        lineBox.addItem("All Lines");
        for (ProductLine pl : productLines) {
            lineBox.addItem(pl.getName());
        }

        toolbarPanel.add(new JLabel("Select Line: "));
        toolbarPanel.add(lineBox);

        model = new DefaultTableModel(new String[]{"Product Name", "Total Quantity", "Production Line", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        Map<Integer, String> lineIdToNameMap = productLines.stream()
                .collect(Collectors.toMap(ProductLine::getId, ProductLine::getName));

        populateTable(tasks, lineIdToNameMap);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(toolbarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        lineBox.addActionListener(e -> {
            String selected = lineBox.getSelectedItem().toString();
            if (selected.equals("All Lines")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        return entry.getStringValue(2).equals(selected);
                    }
                });
            }
        });
    }

    private void populateTable(List<Task> tasks, Map<Integer, String> lineIdToNameMap) {
        Map<String, List<Task>> groupedTasks = new HashMap<>();

        for (Task task : tasks) {
            String key = task.getProductName() + "::" + task.getProductLineId();

            if (!groupedTasks.containsKey(key)) {
                groupedTasks.put(key, new ArrayList<>());
            }

            groupedTasks.get(key).add(task);
        }

        model.setRowCount(0);

        for (Map.Entry<String, List<Task>> entry : groupedTasks.entrySet()) {
            List<Task> tasksInGroup = entry.getValue();
            if (tasksInGroup.isEmpty()) continue;

            Task firstTask = tasksInGroup.get(0);
            String productName = firstTask.getProductName();
            int lineId = firstTask.getProductLineId();
            String lineName = lineIdToNameMap.getOrDefault(lineId, "Unknown");

            int totalQuantity = 0;
            for (Task task : tasksInGroup) {
                totalQuantity += task.getTotalQuantity();
            }
            boolean anyInProgress = false;
            for (Task t : tasksInGroup) {
                if (t.getStatus() != TaskStatus.COMPLETED) {
                    anyInProgress = true;
                    break;
                }
            }
            String status = anyInProgress ? "ACTIVE" : "COMPLETED";

            model.addRow(new Object[]{
                    productName,
                    totalQuantity + " Units",
                    lineName,
                    status
            });
        }
    }
}
