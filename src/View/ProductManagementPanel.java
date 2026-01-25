package View;

import Swing.RoundedButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import Util.RoundedTextField;



public class ProductManagementPanel extends JPanel {

    private final DefaultTableModel productModel;
    private final JTable productTable;
    private final TableRowSorter<DefaultTableModel> productSorter;
    private final JTextField productSearchField;
    private final RoundedButton productAddBtn;
    private final RoundedButton productEditBtn;
    private final RoundedButton productDeleteBtn;
    private final RoundedButton productSaveBtn;

    public ProductManagementPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        setBackground(new Color(255, 249, 230));

        productModel = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Required Items"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        productTable = new JTable(productModel);
        productSorter = new TableRowSorter<>(productModel);
        productTable.setRowSorter(productSorter);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        productTable.setDefaultRenderer(Object.class, centerRenderer);

        productTable.setShowGrid(false);
        productTable.setIntercellSpacing(new Dimension(0, 0));
        productTable.setSelectionBackground(new Color(220, 200, 170));
        productTable.setSelectionForeground(Color.BLACK);
        productTable.setRowHeight(50);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(productTable);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        productSearchField = new RoundedTextField(15);
        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(200, 180, 140));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(80, 40));
        header.setReorderingAllowed(false);

        Dimension buttonSize = new Dimension(100, 50);
        productAddBtn = new RoundedButton("Add", new Color(180, 220, 180));
        productAddBtn.setForeground(Color.BLACK);
        productAddBtn.setFont(new Font("segoe UI", Font.PLAIN, 18));
        productAddBtn.setPreferredSize(buttonSize);

        productEditBtn = new RoundedButton("Edit", new Color(255, 214, 153));
        productEditBtn.setForeground(Color.BLACK);
        productEditBtn.setFont(new Font("segoe UI", Font.PLAIN, 18));
        productEditBtn.setPreferredSize(buttonSize);

        productDeleteBtn = new RoundedButton("Delete", new Color(255, 170, 170));
        productDeleteBtn.setForeground(Color.BLACK);
        productDeleteBtn.setFont(new Font("segoe UI", Font.PLAIN, 18));
        productDeleteBtn.setPreferredSize(buttonSize);

        productSaveBtn = new RoundedButton("Save Products", new Color(180, 180, 220));
        productSaveBtn.setForeground(Color.BLACK);
        productSaveBtn.setFont(new Font("segoe UI", Font.PLAIN, 18));

        productSaveBtn.setIcon(new ImageIcon("src/Images/save.png"));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(255, 249, 230));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        left.setOpaque(false);
        left.add(productAddBtn);
        left.add(productEditBtn);
        left.add(productDeleteBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.setOpaque(false);
        right.add(new JLabel("Search:"));
        right.add(productSearchField);
        right.add(productSaveBtn);
        buttonPanel.add(left, BorderLayout.WEST);
        buttonPanel.add(right, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public DefaultTableModel getProductTableModel() { return productModel; }
    public JTable getProductTable() { return productTable; }
    public TableRowSorter<DefaultTableModel> getProductSorter() { return productSorter; }
    public JTextField getProductSearchField() { return productSearchField; }
    public RoundedButton getProductAddBtn() { return productAddBtn; }
    public RoundedButton getProductEditBtn() { return productEditBtn; }
    public RoundedButton getProductDeleteBtn() { return productDeleteBtn; }
    public RoundedButton getProductSaveBtn() { return productSaveBtn; }
}
