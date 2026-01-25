package Control;

import Service.InventoryService;
import Model.Item;
import Util.*;
import View.SupervisorRightPanel;
import View.FilterPopUp;
import raven.toast.Notifications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Set;

public class SupervisorController {
    private final InventoryService model;
    private final SupervisorRightPanel view;
    private FilterPopUp filterPopUp;

    public SupervisorController(InventoryService model, SupervisorRightPanel view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {

        view.addBtn.addActionListener(e -> handleAdd());
        view.editBtn.addActionListener(e -> handleEdit());
        view.deleteBtn.addActionListener(e -> handleDelete());
        view.saveBtn.addActionListener(e -> {model.saveChanges();JOptionPane.showMessageDialog(view, "Inventory file updated!");});

        filterPopUp = new FilterPopUp(view.getSorter(), this);
        view.filterBtn.addActionListener(e -> filterPopUp.show(view.filterBtn));
        setupSearchAndFilter();


        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (Item item : model.getAllItems()) {
            tableModel.addRow(new Object[]{
                    item.getId(),            // ID
                    item.getName(),          // Name
                    item.getQuantity(),      // Quantity
                    item.getPrice(),         // Price
                    item.getCategory(),      // Category
                    item.getMinStockLevel(), // Min Stock Level
                    getStatus(item)          // Status
            });
        }
    }

    private void handleAdd() {
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField minQtyField = new JTextField();
        JTextField priceField = new JTextField();
        JComboBox<Category> categoryBox = new JComboBox<>(Category.values());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        panel.add(new JLabel("Min Stock:"));
        panel.add(minQtyField);

        while (true) {
            int option = JOptionPane.showConfirmDialog(
                    view,
                    panel,
                    "Add Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option != JOptionPane.YES_OPTION) return;

            try {
                String name = Checker.isAlphabet(nameField.getText().trim().toLowerCase());
                int qty = Checker.parsePositiveInt(qtyField.getText().trim());
                double price = Checker.parsePositiveDouble(priceField.getText().trim());
                int minQty = Checker.parsePositiveInt(minQtyField.getText().trim());
                Category category = (Category) categoryBox.getSelectedItem();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Name cannot be empty");
                    continue;
                }

                model.addItem(name, qty, price, String.valueOf(category), minQty);
                refreshTable();
                return;

            } catch (PositiveIntegerException ex) {
                JOptionPane.showMessageDialog(view, "Quantity, Price, and Min Stock must be positive integers");
            } catch (IsAlphabet isAlphabet) {
                JOptionPane.showMessageDialog(view, "Name must only contain letters");
            } catch (PositiveDoubleException e) {
                 JOptionPane.showMessageDialog(view, "Price must be positive double");
            }
        }
    }

    private void handleEdit() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a product to edit.");
            return;
        }

        String oldName = view.getTableModel().getValueAt(row, 1).toString();
        String oldCategory = view.getTableModel().getValueAt(row, 4).toString();
        String oldQty = view.getTableModel().getValueAt(row, 2).toString();
        String oldPrice = view.getTableModel().getValueAt(row, 3).toString();
        String oldMin = view.getTableModel().getValueAt(row, 5).toString();

        JTextField qtyField = new JTextField(oldQty);
        JTextField priceField = new JTextField(oldPrice);
        JTextField minQtyField = new JTextField(oldMin);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Min Stock:"));
        panel.add(minQtyField);

        while (true) {
            int option = JOptionPane.showOptionDialog(
                    view,
                    panel,
                    "Edit " + oldName,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"Save", "Cancel"},
                    "Save"
            );

            if (option != 0) return; // Cancel

            try {
                int qty = Checker.parsePositiveInt(qtyField.getText().trim());
                int price = Checker.parsePositiveInt(priceField.getText().trim());
                int minQty = Checker.parsePositiveInt(minQtyField.getText().trim());

                Item item =model.getItemByName(oldName);
                item.setQuantity(qty);
                item.setMinStockLevel(minQty);
                item.setPrice(price);
                refreshTable();
                break;

            } catch (PositiveIntegerException ex) {
                JOptionPane.showMessageDialog(view, "Quantity, Price, and Min Stock must be positive integers");
        }
    }
    }

    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            String name = (String) view.getTableModel().getValueAt(row, 1);
            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to delete the item \"" + name + "\"?",
                    "Warning: Delete Item",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeItem(name);
                refreshTable();
                JOptionPane.showMessageDialog(view, "Item \"" + name + "\" deleted successfully.",
                        "Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a product to delete.");
            return;
        }
        refreshTable();
    }

    private InventoryStatus getStatus(Item item) {
        if (item.getQuantity() <= 0) return InventoryStatus.OUT_OF_STOCK;
        return item.getQuantity() < item.getMinStockLevel() ? InventoryStatus.LOW : InventoryStatus.AVAILABLE;
    }


    private void setupSearchAndFilter() {
        view.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
        });
    }

    public void applyFilters() {
        String text = view.searchField.getText().trim().toLowerCase();
        Set<InventoryStatus> selectedStatuses = filterPopUp.getSelectedStatuses();
        Set<String> selectedCategories = filterPopUp.getSelectedCategories();
        TableRowSorter<DefaultTableModel> sorter = view.getSorter();


        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {

                String name = entry.getStringValue(view.getTableModel().findColumn("Name")).toLowerCase();
                String category = entry.getStringValue(view.getTableModel().findColumn("Category"));
                InventoryStatus status = (InventoryStatus) entry.getValue(view.getTableModel().findColumn("Status"));


                boolean matchesSearch = name.contains(text);
                boolean matchesStatus = selectedStatuses.isEmpty() || selectedStatuses.contains(status);
                boolean matchesCategory = selectedCategories.isEmpty() || selectedCategories.stream()
                        .anyMatch(c -> c.equalsIgnoreCase(category));

                return matchesSearch && matchesStatus && matchesCategory;
            }
        });
    }

}