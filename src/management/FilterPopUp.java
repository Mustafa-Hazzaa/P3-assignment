package management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FilterPopUp {
    private final JPopupMenu menu;
    private final TableRowSorter<DefaultTableModel> sorter;
    private ProductionSupervisorUI parent;
    private Set<InventoryStatus> selectedStatuses = new HashSet<>();
    private Set<String> selectedCategories = new HashSet<>();



    public FilterPopUp(TableRowSorter<DefaultTableModel> sorter, ProductionSupervisorUI parent) {
        this.sorter = sorter;
        this.parent = parent;
        this.menu = new JPopupMenu();
        menu.setBackground(new Color(255, 250, 240));
        menu.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 140), 2));
        buildMenu();
    }

    private void buildMenu() {
        JMenu statusMenu = new JMenu("Status");
        JMenu categoryMenu = new JMenu("Category");
        JCheckBoxMenuItem av = new JCheckBoxMenuItem("Available");
        JCheckBoxMenuItem low = new JCheckBoxMenuItem("Low");
        JCheckBoxMenuItem out = new JCheckBoxMenuItem("Out of stock");

        av.addActionListener(e -> toggleStatus(av, InventoryStatus.AVAILABLE));
        low.addActionListener(e -> toggleStatus(low, InventoryStatus.LOW));
        out.addActionListener(e -> toggleStatus(out, InventoryStatus.OUT_OF_STOCK));

        statusMenu.add(av);
        statusMenu.add(low);
        statusMenu.add(out);

        JCheckBoxMenuItem rawItem = new JCheckBoxMenuItem("Raw Material");
        JCheckBoxMenuItem prodItem = new JCheckBoxMenuItem("Product");

        rawItem.addActionListener(e -> toggleCategory(rawItem,"Raw Material"));
        prodItem.addActionListener(e -> toggleCategory(prodItem,"Product"));

        categoryMenu.add(rawItem);
        categoryMenu.add(prodItem);

        menu.add(statusMenu);
        menu.add(categoryMenu);

        JMenuItem clearItem = new JMenuItem("Clear filter");
        clearItem.addActionListener(e -> {
           selectedStatuses.clear();
           selectedCategories.clear();
            sorter.setRowFilter(null);
            parent.applyCombinedFilter();
        });
        menu.add(clearItem);
        menu.addSeparator();
    }
    private void toggleStatus(JCheckBoxMenuItem item , InventoryStatus status){
        if (item.isSelected())
            selectedStatuses.add(status);
        else selectedStatuses.remove(status);

        parent.applyCombinedFilter();
    }

    private void toggleCategory(JCheckBoxMenuItem item , String cat){
        if (item.isSelected())
            selectedCategories.add(cat);
        else selectedCategories.remove(cat);

        parent.applyCombinedFilter();
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