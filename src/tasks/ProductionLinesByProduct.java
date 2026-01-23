package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductionLinesByProduct extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductionLinesByProduct(){
        setTitle("Production Lines by Product");
        setSize(800 , 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT , 15 , 10));

        JComboBox<String> productBox = new JComboBox<>(new String[]{
                "Product A" , "Product B" , "Product C"
        });
        JComboBox<String> statusBox = new JComboBox<>(new String[] {
                "All" , "IN_PROGRESS" , "COMPLETED"
        });

        JTextField searchField = new JTextField(15);

        topPanel.add(new JLabel("Product:"));
        topPanel.add(productBox);

        topPanel.add(new JLabel("Task Status:"));
        topPanel.add(statusBox);

        topPanel.add(new JLabel("Search Line:"));
        topPanel.add(searchField);

        model = new DefaultTableModel(new String[] {
                "Production Line" , "Line Status" , "Tasks Count"
        },0);
        table = new JTable(model);
        table.setRowHeight(30);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        // dummy data
        model.addRow(new Object[]{"Line A" , "Active" ,3});
        model.addRow(new Object[]{"Line B" , "Active" ,1});
        model.addRow(new Object[]{"Line C" , "Stopped" ,2});

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
