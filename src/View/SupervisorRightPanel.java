package View;

import Util.InventoryStatus;
import Util.InventoryStatusRenderer;
import management.*;
import tasks.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class SupervisorRightPanel extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;
    public JTextField searchField;
    private String searchText;
    public JButton filterBtn;
    public RoundedButton addBtn;
    public RoundedButton editBtn;
    public RoundedButton deleteBtn;
    public RoundedButton saveBtn;

    public SupervisorRightPanel( ) {
        setLayout(cardLayout);
        setOpaque(false);
        setupCards();
    }

    private void setupCards() {
        // ####################### CARD #######################
        JPanel stockCard = createStockPanel();
        JPanel taskCard = new ProductionLineDashBoard();
        add(stockCard, "INVENTORY");
        add(taskCard, "TASK MANAGEMENT");
    }

    private JPanel createStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        panel.setBackground(new Color(255, 249, 230));

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price", "Category", "Min Stock Level", "Status"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (!table.getColumnName(i).equals("Status")) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        int statusCol = table.getColumnModel().getColumnIndex("Status");
        table.getColumnModel().getColumn(statusCol).setCellRenderer(new InventoryStatusRenderer());

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(220, 200, 170));
        table.setSelectionForeground(Color.BLACK);
        table.setRowHeight(50);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setBorder(null);
        panel.add(scroll, BorderLayout.CENTER);

        searchField = new RoundedTextField(15);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(200, 180, 140));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 40));
        header.setReorderingAllowed(false);

        // Buttons
        addBtn = new RoundedButton("Add", new Color(180, 220, 180));
        editBtn = new RoundedButton("Edit", new Color(255, 214, 153));
        deleteBtn = new RoundedButton("Delete", new Color(255, 170, 170));
        saveBtn = new RoundedButton("Save Inventory", new Color(180, 180, 220));
        saveBtn.setIcon(new ImageIcon("src/Images/save.png"));

        filterBtn = new JButton(new ImageIcon("src/Images/filter.png"));
        filterBtn.setBorderPainted(false);
        filterBtn.setContentAreaFilled(false);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(255, 249, 230));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        left.setOpaque(false);
        left.add(addBtn); left.add(editBtn); left.add(deleteBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.setOpaque(false);
        right.add(filterBtn); right.add(new JLabel("Search:")); right.add(searchField); right.add(saveBtn);

        buttonPanel.add(left, BorderLayout.WEST);
        buttonPanel.add(right, BorderLayout.EAST);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }


    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
    public TableRowSorter<DefaultTableModel> getSorter() { return sorter; }
}