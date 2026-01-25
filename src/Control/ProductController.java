package Control;

import Model.Item;
import Model.Product;
import Service.InventoryService;
import Util.Checker;
import Util.IsAlphabet;
import Util.PositiveIntegerException;
import View.ProductManagementPanel; // <-- Change the import
import raven.toast.Notifications;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    private final InventoryService inventoryService;
    private final ProductManagementPanel view;
    private final DefaultTableModel productTableModel;
    private final JTable productTable;
    private final TableRowSorter<DefaultTableModel> productSorter;


    public ProductController(InventoryService inventoryService, ProductManagementPanel view) {
        this.inventoryService = inventoryService;
        this.view = view;
        this.productTableModel = view.getProductTableModel();
        this.productTable = view.getProductTable();
        this.productSorter = view.getProductSorter();

        initListeners();
        loadProductData();
    }


    private void initListeners() {

        view.getProductAddBtn().addActionListener(e -> addProduct());
        view.getProductEditBtn().addActionListener(e -> editProduct());
        view.getProductDeleteBtn().addActionListener(e -> deleteProduct());
        view.getProductSaveBtn().addActionListener(e -> {inventoryService.saveChanges();Notifications.getInstance().show(Notifications.Type.SUCCESS, "Product inventory saved successfully.");});


        view.getProductSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterProducts(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterProducts(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterProducts(); }
        });
    }

    public void loadProductData() {
        productTableModel.setRowCount(0);
        List<Product> products = inventoryService.getAllProducts();
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    formatRequiredItems(product.getRequiredItems())
            });
        }
    }

    private String formatRequiredItems(Map<String, Integer> items) {
        if (items == null || items.isEmpty()) {
            return "None";
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            if (!result.isEmpty()) {
                result.append("   |   ");
            }
            result.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return result.toString();
    }


    private void filterProducts() {
        String searchText = view.getProductSearchField().getText();
        if (searchText.trim().isEmpty()) {
            productSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Integer> customFilter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String productName = entry.getStringValue(1);

                    String lowerCaseProductName = productName.toLowerCase();
                    String lowerCaseSearchText = searchText.toLowerCase();

                    return lowerCaseProductName.contains(lowerCaseSearchText);
                }
            };
            productSorter.setRowFilter(customFilter);
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        Map<String, Integer> requiredItemsMap = new HashMap<>();
        JTextArea requirementsDisplay = new JTextArea(4, 30);
        requirementsDisplay.setEditable(false);
        requirementsDisplay.setBorder(BorderFactory.createTitledBorder("Required Items"));

        Item[] allItemsArray = inventoryService.getAllItems().toArray(new Item[0]);
        if (allItemsArray.length == 0) {
            JOptionPane.showMessageDialog(view, "Cannot add a product because there are no items in inventory.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JComboBox<Item> itemComboBox = new JComboBox<>(allItemsArray);
        JTextField requiredQtyField = new JTextField(5);
        JButton plusButton = new JButton("+");
        plusButton.setBackground(new Color(149, 211, 138));


        JPanel panel = buildAddProductPanel(nameField, quantityField, requirementsDisplay, itemComboBox, requiredQtyField, plusButton);

        plusButton.addActionListener(e -> {
            Item selectedItem = (Item) itemComboBox.getSelectedItem();
            String qtyText = requiredQtyField.getText();

            try {
                int qty = Checker.parsePositiveInt(qtyText);
                requiredItemsMap.put(selectedItem.getName(), qty);
                updateRequirementsDisplay(requiredItemsMap, requirementsDisplay);
                requiredQtyField.setText("");

            } catch (PositiveIntegerException ex) {
                JOptionPane.showMessageDialog(panel, "Requirement quantity must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        while (true) {
            int option = JOptionPane.showConfirmDialog(view, panel, "Add New Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) return;

            try {
                String name = Checker.isAlphabet(nameField.getText().trim().toLowerCase());
                int quantity = Checker.parsePositiveInt(quantityField.getText().trim());

                if (requiredItemsMap.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "You must add at least one required item.");
                    continue;
                }

                inventoryService.addProduct(name, quantity, requiredItemsMap);
                loadProductData();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Product '" + name + "' added successfully.");
                return;

            } catch (PositiveIntegerException ex) {
                JOptionPane.showMessageDialog(view, "Product Quantity must be a valid number.");
            } catch (IsAlphabet e) {
                JOptionPane.showMessageDialog(view, "Product name must only have characters.");
            }
        }
    }

    private void editProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a product to edit.");
            return;
        }

        int modelRow = productTable.convertRowIndexToModel(selectedRow);
        int productId = (int) productTableModel.getValueAt(modelRow, 0);
        Product productToEdit = inventoryService.getProductById(productId);

        while (true) {
            String newQuantityStr = JOptionPane.showInputDialog(
                    view,
                    "Enter new quantity for '" + productToEdit.getName() + "':",
                    productToEdit.getQuantity()
            );

            if (newQuantityStr == null) {
                return;
            }

            try {
                int newQuantity = Checker.parsePositiveInt(newQuantityStr.trim());

                productToEdit.setQuantity(newQuantity);
                loadProductData();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Quantity updated for '" + productToEdit.getName() + "'.");
                return;

            } catch (PositiveIntegerException ex) {
                JOptionPane.showMessageDialog(view, "Please enter a valid, positive number for the quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = productTable.convertRowIndexToModel(selectedRow);
            String productName = (String) productTableModel.getValueAt(modelRow, 1);

            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete '" + productName + "'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                inventoryService.removeProduct(productName);
                loadProductData();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "'" + productName + "' has been deleted.");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a product to delete.");
        }
    }

    private JPanel buildAddProductPanel(JTextField nameField, JTextField quantityField, JTextArea requirementsDisplay, JComboBox<Item> itemComboBox, JTextField requiredQtyField, JButton plusButton, JButton clearButton) {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        topPanel.add(new JLabel("Product Name:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Current Quantity:"));
        topPanel.add(quantityField);
        panel.add(topPanel, BorderLayout.NORTH);

        panel.add(new JScrollPane(requirementsDisplay), BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBorder(BorderFactory.createTitledBorder("Modify Requirements"));
        addPanel.add(new JLabel("Item:"));
        addPanel.add(itemComboBox);
        addPanel.add(new JLabel("Qty:"));
        addPanel.add(requiredQtyField);
        addPanel.add(plusButton);
        addPanel.add(clearButton);
        panel.add(addPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildAddProductPanel(JTextField nameField, JTextField quantityField, JTextArea requirementsDisplay, JComboBox<Item> itemComboBox, JTextField requiredQtyField, JButton plusButton) {

        JPanel panel = new JPanel(new BorderLayout(10, 10));


        JPanel topPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        topPanel.add(new JLabel("Product Name:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Initial Quantity:"));
        topPanel.add(quantityField);
        panel.add(topPanel, BorderLayout.NORTH);


        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBorder(BorderFactory.createTitledBorder("Add a Requirement"));
        addPanel.add(new JLabel("Item:"));
        addPanel.add(itemComboBox);
        addPanel.add(new JLabel("Qty:"));
        addPanel.add(requiredQtyField);
        addPanel.add(plusButton);
        panel.add(addPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(requirementsDisplay), BorderLayout.SOUTH);

        return panel;
    }

    private void updateRequirementsDisplay(Map<String, Integer> map, JTextArea display) {
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            text.append(entry.getKey()).append(" (Qty: ").append(entry.getValue()).append(")\n");
        }
        display.setText(text.toString());
    }
}
