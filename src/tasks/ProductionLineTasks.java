package ui.tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductionLineTasks extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLineTasks(){
        setTitle("Tasks by Production Line");
        setSize(900 , 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lineLable = new JLabel("Production Line:");
        JComboBox<String> lineSelector = new JComboBox<>(new String[]{
                "All" , "Line A" , "Line B" , "Line C"});
        topPanel.add(lineLable);
        topPanel.add(lineSelector);

        model = new DefaultTableModel(new String[]{
                "Task ID" , "Product" , "Line" , "Quantity" , "Start" , "End" , "Status"
        },0);

        table= new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        TaskControlPanel controlPanel = new TaskControlPanel(table , model , sorter);

        lineSelector.addActionListener(e -> {
            String selectedLine = lineSelector.getSelectedItem().toString();
            if (selectedLine.equals("All")){
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selectedLine,2));
            }
        });

        add(topPanel , BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel,BorderLayout.SOUTH);
    }
}
