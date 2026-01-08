package View;

import Control.ManagerController;
import Model.ProductLine;


import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ManagerView extends JFrame {

    private static final ImageIcon IDLE = new ImageIcon(ManagerView.class.getResource("/Images/IdleFactory.gif"));
    private static final ImageIcon WORKING = new ImageIcon(ManagerView.class.getResource("/Images/factory.gif"));
    private static final ImageIcon BROKEN = new ImageIcon(ManagerView.class.getResource("/Images/brokenFactory.gif"));

    public static final ImageIcon activeIcon = new ImageIcon(ManagerView.class.getResource("/Images/activeIcon.png"));
    public static final ImageIcon maintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/maintenanceIcon.png"));
    public static final ImageIcon stoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/stopIcon.png"));

    public static final ImageIcon clickedActiveIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedActiveIcon.png"));
    public static final ImageIcon clickedMaintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedMaintenanceIcon.png"));
    public static final ImageIcon clickedStoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/ClickedStopIcon.png"));

    private Consumer<String> menuClickListener;
    private final HashMap<Integer, ProductionLinePanel> allLines = new HashMap<>();

    // --- CardLayout fields ---
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final String PRODUCT_LINES_CARD = "PRODUCT_LINES";
    private final String REPORTS_CARD = "REPORTS";

    public ManagerView() {
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1350, 750));
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        // --- Side navigation ---
        SideNavPanel sideNav = new SideNavPanel(
                "Manager Panel",
                "Factory Control",
                "src/Images/Logo.png",
                List.of("⚙️ Edit Production Line", "📊 Reports"),
                item -> {
                    if (menuClickListener != null) menuClickListener.accept(item);
                    // Switch cards when menu clicked
                    if (item.equals("⚙️ Edit Production Line")) {
                        cardLayout.show(cardPanel, PRODUCT_LINES_CARD);
                    } else if (item.equals("📊 Reports")) {
                        cardLayout.show(cardPanel, REPORTS_CARD);
                    }
                },
                () -> System.exit(0)
        );
        add(sideNav, BorderLayout.WEST);

        // --- CardLayout for center content ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Product lines panel (empty for now, filled in createPanelsForLines)
        JPanel productLinesContainer = new JPanel(new BorderLayout());
        cardPanel.add(productLinesContainer, PRODUCT_LINES_CARD);

        // Reports panel (empty placeholder)
        JPanel reportsPanel = new JPanel();
        reportsPanel.setBackground(new Color(220, 220, 250));
        JLabel placeholder = new JLabel("Reports panel (empty)", SwingConstants.CENTER);
        placeholder.setFont(new Font("SansSerif", Font.BOLD, 20));
        reportsPanel.setLayout(new BorderLayout());
        reportsPanel.add(placeholder, BorderLayout.CENTER);
        cardPanel.add(reportsPanel, REPORTS_CARD);

        add(cardPanel, BorderLayout.CENTER);

        // Show product lines card by default
        cardLayout.show(cardPanel, PRODUCT_LINES_CARD);
    }

    public void setMenuClickListener(Consumer<String> listener) {
        this.menuClickListener = listener;
    }

    public void createPanelsForLines(Collection<ProductLine> lines, ManagerController controller) {
        // Get the card panel for Product Lines
        JPanel productLinesContainer = (JPanel) cardPanel.getComponent(0);

        productLinesContainer.removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 3, 25, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(new Color(245, 220, 230));

        for (ProductLine line : lines) {
            ImageIcon gif = switch (line.getStatus()) {
                case ACTIVE -> WORKING;
                case STOPPED -> IDLE;
                case MAINTENANCE -> BROKEN;
            };

            ProductionLinePanel p = new ProductionLinePanel(line, gif,controller);
            panel.add(p);
            allLines.put(line.getId(), p);
        }

        productLinesContainer.add(panel, BorderLayout.CENTER);
        productLinesContainer.revalidate();
        productLinesContainer.repaint();
    }

    public void refreshLine(ProductLine line) {
        ProductionLinePanel panel = allLines.get(line.getId());
        if (panel != null) panel.updateFromModel();
    }

    // ------------------ Inner Class ------------------
    public class ProductionLinePanel extends JPanel {

        private final ProductLine productLine;
        private final ManagerController controller;

        private int rating = 0;

        private JLabel statusLabel;
        private JProgressBar bar;

        public ProductionLinePanel(ProductLine line, ImageIcon gif,ManagerController controller) {
            this.productLine = line;
            this.controller = controller;


            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            JLabel gifLabel = new JLabel(gif);
            gifLabel.setAlignmentX(CENTER_ALIGNMENT);
            gifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            gifLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    openDetails();
                }
            });

            JLabel title = new JLabel(line.getName());
            title.setFont(new Font("SansSerif", Font.BOLD, 15));
            title.setAlignmentX(CENTER_ALIGNMENT);

            bar = new JProgressBar(0, 100);
            bar.setStringPainted(true);
            bar.setMaximumSize(new Dimension(1000, 30));

            statusLabel = new JLabel();
            statusLabel.setAlignmentX(CENTER_ALIGNMENT);

            add(gifLabel);
            add(Box.createVerticalStrut(8));
            add(title);
            add(Box.createVerticalStrut(5));
            add(bar);
            add(Box.createVerticalStrut(8));
            add(statusLabel);

            updateFromModel();

            Timer timer = new Timer(500, e -> updateFromModel());
            timer.start();
        }

        private void openDetails() {
            controller.openLineDetails(ManagerView.this, productLine);
        }

        public void updateFromModel() {
            if (productLine.currentTask() != null)
                bar.setValue(productLine.currentTask().getProgress());
            else
                bar.setValue(0);

            switch (productLine.getStatus()) {
                case ACTIVE -> setStatus("RUNNING", new Color(0, 150, 0));
                case STOPPED -> setStatus("PAUSED", new Color(180, 140, 0));
                case MAINTENANCE -> setStatus("BROKEN", Color.RED);
            }
        }

        private void setStatus(String text, Color color) {
            statusLabel.setText(text);
            statusLabel.setForeground(color);
        }



    }
}
