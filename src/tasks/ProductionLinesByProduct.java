package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        table.setRowHeight(40);
        table.getTableHeader().setBackground(new Color(188 , 170 , 145));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI" , Font.BOLD , 14));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(240 , 240 , 240));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.getColumnModel().getColumn(1).setCellRenderer(new StatusRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

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
    class StatusRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table , Object value , boolean isSelected , boolean hasFocus , int row , int column){
            JLabel label = (JLabel) super.getTableCellRendererComponent(table , value , isSelected , hasFocus , row , column);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setFont(new Font("Segoe Ui" , Font.BOLD , 12));

            if (value!= null){
                String status = value.toString().toUpperCase();
                if (status.equals("ACTIVE")){
                    label.setBackground(new Color(200 , 230 , 201));
                    label.setForeground(new Color(46,125,50));
                } else if (status.equals("STOPPED")) {
                    label.setBackground(new Color(255 , 205 , 210));
                }else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }
            }
            return label;
        }
    }
}