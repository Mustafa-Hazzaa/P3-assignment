package management;

import Control.SupervisorController;
import Util.Category;
import Util.InventoryStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FilterPopUp {
    private final JPopupMenu menu;
    private final TableRowSorter<DefaultTableModel> sorter;
    private SupervisorController controller;
    private Set<InventoryStatus> selectedStatuses = new HashSet<>();
    private Set<String> selectedCategories = new HashSet<>();



    public FilterPopUp(TableRowSorter<DefaultTableModel> sorter, SupervisorController controller) {
        this.sorter = sorter;
        this.controller = controller;
        this.menu = new JPopupMenu();
        menu.setBackground(new Color(255, 250, 240));
        menu.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 140), 2));
        buildMenu();
    }

    private void buildMenu() {
        JMenu statusMenu = new JMenu("Status");
        JCheckBoxMenuItem av = new JCheckBoxMenuItem("Available");
        JCheckBoxMenuItem low = new JCheckBoxMenuItem("Low");
        JCheckBoxMenuItem out = new JCheckBoxMenuItem("Out of stock");

        av.addActionListener(e -> toggleStatus(av, InventoryStatus.AVAILABLE));
        low.addActionListener(e -> toggleStatus(low, InventoryStatus.LOW));
        out.addActionListener(e -> toggleStatus(out, InventoryStatus.OUT_OF_STOCK));

        statusMenu.add(av);
        statusMenu.add(low);
        statusMenu.add(out);

        JMenu categoryMenu = new JMenu("Category");

        for (Category category : Category.values()) {
            String categoryName = category.name();
            JCheckBoxMenuItem categoryItem = new JCheckBoxMenuItem(categoryName);
            categoryItem.addActionListener(e -> toggleCategory(categoryItem, categoryName));
            categoryMenu.add(categoryItem);
        }

        menu.add(statusMenu);
        menu.add(categoryMenu);

        JMenuItem clearItem = new JMenuItem("Clear filter");
        clearItem.addActionListener(e -> {
            selectedStatuses.clear();
            selectedCategories.clear();
            sorter.setRowFilter(null);
            controller.applyFilters();
        });
        menu.add(clearItem);
        menu.addSeparator();
    }
    private void toggleStatus(JCheckBoxMenuItem item , InventoryStatus status){
        if (item.isSelected())
            selectedStatuses.add(status);
        else selectedStatuses.remove(status);

        controller.applyFilters();
    }

    private void toggleCategory(JCheckBoxMenuItem item , String cat){
        if (item.isSelected())
            selectedCategories.add(cat);
        else selectedCategories.remove(cat);

        controller.applyFilters();
    }

    public Set <InventoryStatus> getSelectedStatuses(){
        return selectedStatuses;
    }
    public Set <String> getSelectedCategories(){
        return selectedCategories;
    }

    public void show(JComponent parent) {
        menu.show(parent, 0, parent.getHeight());
    }
}