package View;

import Model.ProductLine;
import Service.ProductLineService;
import Swing.SafeTextField;
import Util.LineStatus;
import Util.raven.floating.FloatingButtonUI;

import javax.swing.*;
import java.awt.*;
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

    private final ProductLineService productLineService;
    private Consumer<ProductLine> onLineClicked;

    private final HashMap<Integer, ProductionLinePanel> linePanels = new HashMap<>();

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final String PRODUCT_LINES_CARD = "PRODUCT_LINES";
    private final String REPORTS_CARD = "REPORTS";

    private JPanel productLinesPanel;

    public ManagerView(ProductLineService productLineService) {
        this.productLineService = productLineService;
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1350, 750));
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());

        // SIDE NAV
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
                }
        );
        add(sideNav, BorderLayout.WEST);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        productLinesPanel = new JPanel();
        productLinesPanel.setBackground(new Color(245, 220, 230));

        JScrollPane scrollPane = new JScrollPane(productLinesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        JPanel linesContainerPanel = new JPanel(new BorderLayout());
        linesContainerPanel.add(scrollPane, BorderLayout.CENTER);

        JLayer<JPanel> layer = new JLayer<>(linesContainerPanel, new FloatingButtonUI(this::addProductLine));
        cardPanel.add(layer, PRODUCT_LINES_CARD);

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
        productLinesPanel.removeAll();
        linePanels.clear();

        productLinesPanel.setLayout(new GridLayout(0, 3, 25, 25));
        productLinesPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        productLinesPanel.setBackground(new Color(245, 220, 230));

        for (ProductLine line : lines) {
            ImageIcon gif = switch (line.getStatus()) {
                case ACTIVE -> WORKING;
                case STOPPED -> IDLE;
                case MAINTENANCE -> BROKEN;
            };

            ProductionLinePanel p = new ProductionLinePanel(line, gif);
            productLinesPanel.add(p);
            linePanels.put(line.getId(), p);
        }

        productLinesPanel.revalidate();
        productLinesPanel.repaint();
    }

    public void refreshLine(ProductLine line) {
        if (linePanels.containsKey(line.getId())) {
            linePanels.get(line.getId()).update();
        }
    }

    private void addProductLine() {
        SafeTextField nameField = new SafeTextField();
        String[] statuses = {"ACTIVE", "STOPPED", "MAINTENANCE"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setSelectedIndex(0);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    this, panel, "Add ProductLine", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String name = nameField.getText().trim();


            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (name.contains(",") || name.contains("\"") || name.contains("'")) {
                continue;
            }

            LineStatus status = LineStatus.valueOf((String) statusBox.getSelectedItem());
            ProductLine newLine = new ProductLine(name, status);
            productLineService.add(newLine);
            displayLines(productLineService.getAll());
            break;
        }
    }

    private class ProductionLinePanel extends JPanel {

        private final ProductLine line;
        private final JLabel statusLabel = new JLabel();
        private final JProgressBar bar = new JProgressBar(0, 100);
        JLabel gifLabel;

        public ProductionLinePanel(ProductLine line, ImageIcon gif) {
            this.line = line;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            gifLabel = new JLabel(gif);
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
            bar.setPreferredSize(new Dimension(425, 25));
            bar.setMaximumSize(new Dimension(425, 25));
            bar.setMinimumSize(new Dimension(425, 25));
            bar.setStringPainted(true);
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

            LineStatus status = line.getStatus();
            if (status == null) status = LineStatus.STOPPED;

            switch (status) {
                case ACTIVE -> setStatus("RUNNING", new Color(0, 150, 0), WORKING);
                case STOPPED -> setStatus("PAUSED", new Color(180, 140, 0), IDLE);
                case MAINTENANCE -> setStatus("BROKEN", Color.RED, BROKEN);
            }
        }

        private void setStatus(String text, Color color, ImageIcon image) {
            statusLabel.setText(text);
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            statusLabel.setForeground(color);
            gifLabel.setIcon(image);
        }
    }
}
