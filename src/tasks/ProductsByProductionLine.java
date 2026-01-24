package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductsByProductionLine extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductsByProductionLine(){
        setTitle("Products by Production Line");
        setSize(750 , 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT , 15 , 10));

        JComboBox<String> lineBox = new JComboBox<>(new String[] {
                "Line A" , "Line B" , "Line C"
        });
        JTextField searchField = new JTextField(15);

        topPanel.add(new JLabel("Production Line:"));
        topPanel.add(lineBox);

        topPanel.add(new JLabel("Search Product:"));
        topPanel.add(searchField);

        model = new DefaultTableModel(new String[] {
                "Product Name" , "Produced Quantity"},0);
        table = new JTable(model);
        table.setRowHeight(30);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        //dummy data
        model.addRow(new Object[]{"Product A" ,120});
        model.addRow(new Object[]{"Product B" ,80});
        model.addRow(new Object[]{"Product C" ,200});

        Runnable applyFilter = () -> {
            String search = searchField.getText().toLowerCase();

            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    return entry.getStringValue(0).toLowerCase().contains(search);
                }
            });
        };
        searchField.addKeyListener(new java.awt.event.KeyAdapter(){
            @Override
            public void keyReleased(java.awt.event.KeyEvent e){
                applyFilter.run();
            }
        });
        add(topPanel , BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}