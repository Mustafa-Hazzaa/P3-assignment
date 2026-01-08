package View;

import Model.ProductLine;
import Util.LineStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ManagerView extends JFrame {

    // --- Assets ---
    private static final ImageIcon IDLE = new ImageIcon(ManagerView.class.getResource("/Images/IdleFactory.gif"));
    private static final ImageIcon WORKING = new ImageIcon(ManagerView.class.getResource("/Images/factory.gif"));
    private static final ImageIcon BROKEN = new ImageIcon(ManagerView.class.getResource("/Images/brokenFactory.gif"));

    public static final ImageIcon activeIcon = new ImageIcon(ManagerView.class.getResource("/Images/activeIcon.png"));
    public static final ImageIcon maintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/maintenanceIcon.png"));
    public static final ImageIcon stoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/stopIcon.png"));

    public static final ImageIcon clickedActiveIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedActiveIcon.png"));
    public static final ImageIcon clickedMaintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedMaintenanceIcon.png"));
    public static final ImageIcon clickedStoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/ClickedStopIcon.png"));

    // --- Event Callbacks ---
    private Consumer<ProductLine> onLineClicked;

    // --- Line Panels ---
    private final HashMap<Integer, ProductionLinePanel> linePanels = new HashMap<>();

    // --- Cards ---
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

        // --- Side Navigation ---
        SideNavPanel sideNav = new SideNavPanel(
                "Manager Panel",
                "Factory Control",
                "src/Images/Logo.png",
                List.of("⚙️ Edit Production Line", "📊 Reports"),
                item -> {
                    if (item.equals("⚙️ Edit Production Line")) {
                        cardLayout.show(cardPanel, PRODUCT_LINES_CARD);
                    } else {
                        cardLayout.show(cardPanel, REPORTS_CARD);
                    }
                },
                () -> System.exit(0)
        );
        add(sideNav, BorderLayout.WEST);

        // --- Cards ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel productLines = new JPanel(new BorderLayout());
        cardPanel.add(productLines, PRODUCT_LINES_CARD);

        JPanel reportsPanel = new JPanel(new BorderLayout());
        JLabel placeholder = new JLabel("Reports panel (empty)", SwingConstants.CENTER);
        placeholder.setFont(new Font("SansSerif", Font.BOLD, 20));
        reportsPanel.add(placeholder, BorderLayout.CENTER);
        cardPanel.add(reportsPanel, REPORTS_CARD);

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, PRODUCT_LINES_CARD);
    }

    public void setOnLineClicked(Consumer<ProductLine> callback) {
        this.onLineClicked = callback;
    }

    public void displayLines(Collection<ProductLine> lines) {
        JPanel productLines = (JPanel) cardPanel.getComponent(0);
        productLines.removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 3, 25, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(new Color(245, 220, 230));

        for (ProductLine line : lines) {
            ImageIcon gif = switch (line.getStatus()) {
                case ACTIVE -> WORKING;
                case STOPPED -> IDLE;
                case MAINTENANCE -> BROKEN;
            };

            ProductionLinePanel p = new ProductionLinePanel(line, gif);
            panel.add(p);
            linePanels.put(line.getId(), p);
        }

        productLines.add(panel, BorderLayout.CENTER);
        productLines.revalidate();
        productLines.repaint();
    }

    public void refreshLine(ProductLine line) {
        if (linePanels.containsKey(line.getId())) {
            linePanels.get(line.getId()).update();
        }
    }

    // ============= INNER PANEL CLASS =============
    private class ProductionLinePanel extends JPanel {

        private final ProductLine line;
        private final JLabel statusLabel = new JLabel();
        private final JProgressBar bar = new JProgressBar(0, 100);

        public ProductionLinePanel(ProductLine line, ImageIcon gif) {
            this.line = line;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            JLabel gifLabel = new JLabel(gif);
            gifLabel.setAlignmentX(CENTER_ALIGNMENT);
            gifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            gifLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (onLineClicked != null) onLineClicked.accept(line);
                }
            });

            JLabel title = new JLabel(line.getName());
            title.setFont(new Font("SansSerif", Font.BOLD, 15));
            title.setAlignmentX(CENTER_ALIGNMENT);

            bar.setStringPainted(true);
            bar.setMaximumSize(new Dimension(1000, 30));
            statusLabel.setAlignmentX(CENTER_ALIGNMENT);

            add(gifLabel);
            add(Box.createVerticalStrut(8));
            add(title);
            add(Box.createVerticalStrut(5));
            add(bar);
            add(Box.createVerticalStrut(8));
            add(statusLabel);

            new Timer(500, e -> update()).start();
            update();
        }

        public void update() {
            if (line.currentTask() != null) {
                bar.setValue(line.currentTask().getProgress());
            } else {
                bar.setValue(0);
            }

            switch (line.getStatus()) {
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
