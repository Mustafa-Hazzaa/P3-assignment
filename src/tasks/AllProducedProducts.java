package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class AllProducedProducts extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public AllProducedProducts(){
        setTitle("All Produced Products");
        setSize(700 , 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT , 15 , 10));
        JTextField searchField = new JTextField(18);

        JComboBox<String> sortBox = new JComboBox<>(new String[] {
                "Default" , "Quantity asc" , "Quantity desc"
        });

        topPanel.add(new JLabel("Search Product"));
        topPanel.add(searchField);

        topPanel.add(new JLabel("Sort:"));
        topPanel.add(sortBox);

        model = new DefaultTableModel(new String[] {
                "Product Name" , "Total Quantity"},0);
        table = new JTable(model);
        table.setRowHeight(30);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        //dummy data
        model.addRow(new Object[]{"Product A" ,500});
        model.addRow(new Object[]{"Product B" ,320});
        model.addRow(new Object[]{"Product C" ,780});

            searchField.addKeyListener(new java.awt.event.KeyAdapter(){
                @Override
                public void keyReleased(java.awt.event.KeyEvent e){
                    String search = searchField.getText().toLowerCase();
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search ,0));
                }
            });
            sortBox.addActionListener(e -> {
                String selected = sortBox.getSelectedItem().toString();
                if (selected.equals("Default")){
                    sorter.setSortKeys(null);
                } else if (selected.equals("Quantity Asc")) {
                    sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(1,SortOrder.ASCENDING)));
                } else if (selected.equals("Quantity Desc")) {
                    sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(1,SortOrder.DESCENDING)));
                }
            });
        add(topPanel , BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
