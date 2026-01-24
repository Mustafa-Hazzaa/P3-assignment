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

        // Buttons
        addBtn = new RoundedButton("Add", new Color(180, 220, 180));
        editBtn = new RoundedButton("Edit", new Color(255, 214, 153));
        deleteBtn = new RoundedButton("Delete", new Color(255, 170, 170));
        saveBtn = new RoundedButton("Save Inventory", new Color(180, 180, 220));


        filterBtn = new JButton(new ImageIcon("src/Images/Logo.png"));
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

    private JPanel createTaskPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(245, 245, 245));

        mainPanel.add(createDashboardCard("Tasks by Production Line", "View tasks assigned to each Production Line", () -> new ProductionLineTasks().setVisible(true)));
        mainPanel.add(createDashboardCard("Tasks by Product", "View tasks related to a specific Product", () -> new ProductTask().setVisible(true)));
        mainPanel.add(createDashboardCard("Production Lines for a Product", "View Production Lines that worked on a product", () -> new ProductionLinesByProduct().setVisible(true)));
        mainPanel.add(createDashboardCard("Products by Production Line", "View Products produced by a specific Line", () -> new ProductsByProductionLine().setVisible(true)));
        mainPanel.add(createDashboardCard("All produced Products", "View all Products produced by all Lines", () -> new AllProducedProducts().setVisible(true)));
        mainPanel.add(createDashboardCard("Most requested Product", "Find the most requested Product in a period", () -> new MostRequestedProduct().setVisible(true)));

        return mainPanel;
    }

    private JPanel createDashboardCard(String titleText, String descText, Runnable onOpen) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel desc = new JLabel("<html>" + descText + "</html>");
        desc.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JButton openBtn = new JButton("Open");
        openBtn.setBackground(new Color(200, 180, 140));
        openBtn.addActionListener(e -> onOpen.run());
        card.add(title, BorderLayout.NORTH);
        card.add(desc, BorderLayout.CENTER);
        card.add(openBtn, BorderLayout.SOUTH);
        return card;
    }


    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
    public TableRowSorter<DefaultTableModel> getSorter() { return sorter; }
}