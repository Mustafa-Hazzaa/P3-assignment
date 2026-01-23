package ui.tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductTask extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter <DefaultTableModel> sorter;

    public ProductTask(){
        setTitle("Tasks by Product");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel productLable = new JLabel("Product:");
        JComboBox<String> productSelector = new JComboBox<>(new String[]{
                "All" , "Product A" , "Product B" , "Product C"});
        topPanel.add(productLable);
        topPanel.add(productSelector);

        model = new DefaultTableModel(new String[]{
                "Task ID" , "Product" , "Line" , "Quantity" , "Start" , "End" , "Status"
        },0);

        table= new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        TaskControlPanel controlPanel = new TaskControlPanel(table , model , sorter);

        productSelector.addActionListener(e -> {
            String selectedProduct = productSelector.getSelectedItem().toString();
            if (selectedProduct.equals("All")){
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selectedProduct,1));
            }
        });
        add(topPanel , BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel,BorderLayout.SOUTH);
    }
}