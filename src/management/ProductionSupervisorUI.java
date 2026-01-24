//package management;
//
//import com.formdev.flatlaf.FlatLightLaf;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;
//import javax.swing.table.TableRowSorter;
//import java.awt.*;
//
//public class ProductionSupervisorUI extends JFrame {
//    private DefaultTableModel model;
//    private JTable table;
//    private JButton filterBtn;
//    private TableRowSorter<DefaultTableModel> sorter;
//    private JTextField searchField;
//    private String searchText;
//    private FilterPopUp filterPopUp;
//    private RoundedButton saveBtn;
//
//    public ProductionSupervisorUI(){
//        setTitle("Production Supervisor Dashboard");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(new Dimension(1300,760));
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        model = new DefaultTableModel(new Object[]{"Item Name" , "Category" ,
//                "Quantity" , "Min Qty" , "Status"},0);
//        table = new JTable(model);
//        sorter = new TableRowSorter<>(model);
//        table.setRowSorter(sorter);
//
//        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value , boolean isSelected , boolean hasFocus , int row , int column){
//                Component c = super.getTableCellRendererComponent(table , value , isSelected , hasFocus , row , column);
//                if (!isSelected){
//                    if (row % 2 == 0 )
//                        c.setBackground(Color.WHITE);
//                    else
//                        c.setBackground(new Color(245 , 245 , 245));
//                }
//                return c;
//            }
//        });
//
//        table.setShowGrid(false);
//        table.setIntercellSpacing(new Dimension(0,0));
//        table.setSelectionBackground(new Color(220 , 200 , 170));
//        table.setSelectionForeground(Color.BLACK);
//        table.setOpaque(false);
//        JScrollPane scroll = new JScrollPane(table);
//        scroll.getViewport().setOpaque(false);
//        scroll.setOpaque(false);
//
//        add(scroll , BorderLayout.CENTER);
//
//        searchField = new RoundedTextField(15);
//        searchField.setFont(new Font("segoe UI", Font.PLAIN,13));
//        searchField.setToolTipText("Search by item name");
//
//        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            public void insertUpdate(javax.swing.event.DocumentEvent e ){applySearch();}
//            public void removeUpdate (javax.swing.event.DocumentEvent e){applySearch();}
//            public void changedUpdate(javax.swing.event.DocumentEvent e) {applySearch();}
//        });
//
//        JTableHeader header = table.getTableHeader();
//        header.setFont(new Font("segoe UI",Font.BOLD,15));
//        header.setBackground(new Color(200 , 180 , 140));
//        header.setForeground(Color.WHITE);
//        header.setPreferredSize(new Dimension(100 , 40));
//
//        JPanel buttonPanel = new JPanel(new BorderLayout());
//        buttonPanel.setBackground(new Color(255 , 249 , 230));
//
//        RoundedButton addBtn = new RoundedButton("Add" , new Color(180 , 220 , 180));
//        RoundedButton editBtn = new RoundedButton("Edit" , new Color(255 , 214 , 153));
//        RoundedButton deleteBtn = new RoundedButton("Delete" , new Color(255 , 170 , 170));
//
//        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT ,15 , 10));
//        leftPanel.setOpaque(false);
//
//        leftPanel.add(addBtn);
//        leftPanel.add(editBtn);
//        leftPanel.add(deleteBtn);
//
//        Font btnFont = new Font("segoe UI" , Font.BOLD,13);
//        addBtn.setFont(btnFont);
//        editBtn.setFont(btnFont);
//        deleteBtn.setFont(btnFont);
//
//        addBtn.addActionListener(e -> {
//            JTextField nameField = new JTextField();
//            JTextField categoryField = new JTextField();
//            JTextField qtyField = new JTextField();
//            JTextField minQtyField = new JTextField();
//
//            Object[] messege = {"Item name" , nameField , "Category" , categoryField , "Quantity"
//                    ,qtyField , "Min Quantity" , minQtyField};
//
//            int option = JOptionPane.showConfirmDialog(this , messege , "Add Item",
//                    JOptionPane.OK_CANCEL_OPTION);
//            if (option == JOptionPane.OK_OPTION){
//                if(nameField.getText().trim().isEmpty() || categoryField.getText().trim().isEmpty() || qtyField.getText().trim().isEmpty() || minQtyField.getText().trim().isEmpty()){
//                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Warning" , JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//                try{
//                    String name = nameField.getText();
//                    String category = categoryField.getText();
//                    int qty = Integer.parseInt(qtyField.getText());
//                    int minQty = Integer.parseInt(minQtyField.getText());
//
//                    InventoryStatus status;
//
//                    if (qty == 0)
//                        status = InventoryStatus.OUT_OF_STOCK;
//                    else if (qty < minQty)
//                        status = InventoryStatus.LOW;
//                    else
//                        status = InventoryStatus.AVAILABLE;
//
//                    model.addRow(new Object[] {name , category , qty , minQty, status});
//                }
//                catch (NumberFormatException ex){
//                    JOptionPane.showMessageDialog(this , "Invalid numbers");
//                }
//            }
//        });
//        deleteBtn.addActionListener(e -> {int selectedRow = table.getSelectedRow();
//            if (selectedRow == -1){
//                JOptionPane.showMessageDialog(this, "Please select a row first" ,
//                        "Warning" , JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Item?" , "Confirm delete" ,JOptionPane.YES_NO_OPTION);
//            if (confirm == JOptionPane.YES_NO_OPTION) {
//                model.removeRow(selectedRow);
//            }
//        });
//
//        editBtn.addActionListener(e -> {int Row = table.getSelectedRow();
//            if (Row == -1) {
//                JOptionPane.showMessageDialog(this , "Select a row to edit");
//                return;
//            }
//            String category = model.getValueAt(Row , 1).toString();
//            int qty = (int) model.getValueAt(Row , 2);
//            int minQty = (int) model.getValueAt(Row , 3);
//
//            JTextField categoryField = new JTextField(category);
//            JTextField qtyField = new JTextField(String.valueOf(qty));
//            JTextField minQtyField = new JTextField(String.valueOf(minQty));
//
//            Object[] message = {"Category:", categoryField , "Quantity:" , qtyField ,
//                    "Min Quantity:", minQtyField};
//
//            int option = JOptionPane.showConfirmDialog(this, message , "Edit Item", JOptionPane.OK_CANCEL_OPTION);
//            if (option == JOptionPane.OK_OPTION) {
//                try {
//                    int newQty = Integer.parseInt(qtyField.getText());
//                    int newMinQty = Integer.parseInt(minQtyField.getText());
//                    InventoryStatus status;
//                    if (newQty == 0)
//                        status = InventoryStatus.OUT_OF_STOCK;
//                    else if (newQty < newMinQty)
//                        status = InventoryStatus.LOW;
//                    else
//                        status = InventoryStatus.AVAILABLE;
//
//                    model.setValueAt(categoryField.getText(), Row, 1);
//                    model.setValueAt(newQty, Row, 2);
//                    model.setValueAt(newMinQty, Row, 3);
//                    model.setValueAt(status, Row, 4);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(this, "Invalid numbers");
//                }
//            }
//        });
//
//        saveBtn = new RoundedButton("Save Inventory" , new Color(180,180,220));
//        saveBtn.setIcon(new ImageIcon("icons/save.png"));
//        saveBtn.setIconTextGap(8);
//
//        saveBtn.addActionListener(e -> {
//            try{
//                InventoryFileManager.saveWithChooser(this,model);
//                JOptionPane.showMessageDialog(this,"Inventory saved successfully" , "save" , JOptionPane.INFORMATION_MESSAGE);
//            }
//            catch (Exception ex){
//                JOptionPane.showMessageDialog(this,"Error happened while saving file","Error",JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        filterBtn = new JButton(new ImageIcon("icons/filter.png"));
//        filterBtn.setBorderPainted(false);
//        filterBtn.setContentAreaFilled(false);
//        filterBtn.setFocusPainted(false);
//        buttonPanel.add(filterBtn);
//
//        filterPopUp = new FilterPopUp(sorter , this);
//        filterBtn.addActionListener(e -> {
//                    filterPopUp.show(filterBtn);
//                    applyCombinedFilter();
//                });
//
//        add(buttonPanel, BorderLayout.NORTH);
//        add(new JScrollPane(table) , BorderLayout.CENTER);
//
//        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT , 10 , 10));
//        rightPanel.setOpaque(false);
//
//        rightPanel.add(new JLabel("Search:"));
//        rightPanel.add(searchField);
//        rightPanel.add(filterBtn);
//        rightPanel.add(saveBtn);
//
//        buttonPanel.add(leftPanel , BorderLayout.WEST);
//        buttonPanel.add(rightPanel , BorderLayout.EAST);
//
//        table.setRowHeight(50);
//
//        model.addRow(new Object[]{
//                "potato" , "Raw Material" , 500 , 200 , InventoryStatus.AVAILABLE
//        });
//
//        model.addRow(new Object[]{
//                "oil" , "Raw Material" , 50 , 100 , InventoryStatus.LOW
//        });
//
//        model.addRow(new Object[]{
//                "Salt" , "Raw Material" , 0 , 30 , InventoryStatus.OUT_OF_STOCK
//        });
//
//        model.addRow(new Object[]{
//                "Chips", "product", 20, 90, InventoryStatus.LOW
//        });
//
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//
//
//        for (int i = 0 ; i< table.getColumnCount(); i++){
//            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//        }
//        table.getColumnModel().getColumn(4).setCellRenderer(new InventoryStatusRenderer());
//    }
//
//    public void applyCombinedFilter(){
//        sorter.setRowFilter(new RowFilter<>() {
//            @Override
//            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
//                boolean matchesSearch =searchText == null || entry.getStringValue(0).toLowerCase().contains(searchText);
//                InventoryStatus rowStatus = (InventoryStatus) entry.getValue(4);
//                String rowCategory = entry.getStringValue(1);
//                boolean matchesStatus = filterPopUp.getSelectedStatuses().isEmpty() || filterPopUp.getSelectedStatuses().contains(rowStatus);
//                boolean matchesCategory = filterPopUp.getSelectedCategories().isEmpty() || filterPopUp.getSelectedCategories().stream().anyMatch(c -> c.equalsIgnoreCase(rowCategory));
//
//                return matchesSearch && matchesStatus && matchesCategory;
//            }
//        });
//    }
//
//    private void applySearch() {
//        searchText = searchField.getText().trim().toLowerCase();
//        applyCombinedFilter();
//        }
//
//    public static void main(String[]args){
//        FlatLightLaf.setup();
//        UIManager.put("Button.arc", 12);
//        UIManager.put("Component.arc", 12);
//        UIManager.put("ProgressBar.arc", 12);
//        UIManager.put("TextComponent.arc", 10);
//        SwingUtilities.invokeLater(() -> new ProductionSupervisorUI().setVisible(true));
//    }
//}
