package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class TaskControlPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter <DefaultTableModel> sorter;

    private JComboBox<String> lineFilter;
    private JComboBox<String> statusFilter;
    private JTextField searchField;

    public TaskControlPanel(JTable table , DefaultTableModel model , TableRowSorter<DefaultTableModel> sorter){
        this.table = table;
        this.model = model;
        this.sorter = sorter;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        lineFilter = new JComboBox<>(new String[]{
                "All" , "Line A" , "Line B" , "Line C"
        });

        statusFilter = new JComboBox<>(new String[]{
                "All" , "IN_PROGRESS" , "COMPLETED"
        });
        searchField = new JTextField(15);
        add(addBtn);
        add(deleteBtn);

        add(new JLabel("Line:"));
        add(lineFilter);

        add(new JLabel("Status:"));
        add(statusFilter);

        add(new JLabel("Search:"));
        add(searchField);

        addBtn.addActionListener(e -> addTask());
        deleteBtn.addActionListener(e -> deleteTask());
        lineFilter.addActionListener(e -> applyFilters());
        statusFilter.addActionListener(e -> applyFilters());
        searchField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent e){
                applyFilters();
            }
        });
    }
    private void addTask(){
        JTextField id = new JTextField();
        JTextField product = new JTextField();
        JTextField line = new JTextField();
        JTextField qty = new JTextField();

        JComboBox<String>status = new JComboBox<>(new String[]{
                "IN_PROGRESS" , "COMPLETED"
        });
        Object[] from = {"Task ID:" , id , "Product:" , product , "Production Line:" , line , "Quantity:" , qty , "Status" , status};

        int result = JOptionPane.showConfirmDialog(this,from , "Add Task" , JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_CANCEL_OPTION){
            model.addRow(new Object[]{
                    id.getText() , product.getText() , line.getText() , qty.getText() , "" , "" , status.getSelectedItem()
            });
        }
    }
    private void deleteTask() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.removeRow(table.convertRowIndexToModel(row));
    }

    private void applyFilters(){
        String line = lineFilter.getSelectedItem().toString();
        String status = statusFilter.getSelectedItem().toString();
        String search = searchField.getText().toLowerCase();

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean okLine = line.equals("All") || entry.getStringValue(2).equals(line);
                boolean okStatus = status.equals("All") || entry.getStringValue(6).equals(status);
                boolean okSearch = search.isEmpty() || entry.getStringValue(0).toLowerCase().contains(search) ||
                        entry.getStringValue(1).toLowerCase().contains(search);

                return okLine && okStatus && okSearch;
            }
        });

    }
}