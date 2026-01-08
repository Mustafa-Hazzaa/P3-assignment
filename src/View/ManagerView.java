package View;

import Model.ProductLine;
import Service.ProductLineService;
import Util.LineStatus;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ManagerView extends JFrame {

    private static final ImageIcon IDLE = new ImageIcon(ManagerView.class.getResource("/Images/IdleFactory.gif"));
    private static final ImageIcon WORKING = new ImageIcon(ManagerView.class.getResource("/Images/factory.gif"));
    private static final ImageIcon BROKEN = new ImageIcon(ManagerView.class.getResource("/Images/brokenFactory.gif"));

    private static final ImageIcon activeIcon = new ImageIcon(ManagerView.class.getResource("/Images/activeIcon.png"));
    private static final ImageIcon maintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/maintenanceIcon.png"));
    private static final ImageIcon stoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/stopIcon.png"));

    private static final ImageIcon clickedActiveIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedActiveIcon.png"));
    private static final ImageIcon clickedMaintenanceIcon = new ImageIcon(ManagerView.class.getResource("/Images/clickedMaintenanceIcon.png"));
    private static final ImageIcon clickedStoppedIcon = new ImageIcon(ManagerView.class.getResource("/Images/ClickedStopIcon.png"));

    private final HashMap<Integer, ProductionLinePanel> allLines = new HashMap<>();

    public ManagerView() {
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1350, 750));
        setLayout(new BorderLayout());

        SideNavPanel sideNav = new SideNavPanel(
                "Manager Panel",
                "Factory Control",
                "src/Images/Logo.png",
                List.of("⚙️ Edit Production Line", "📊 Reports", "👥 Workers"),
                this::onMenuClick,
                () -> System.exit(0)
        );
        add(sideNav, BorderLayout.WEST);
    }

    private void onMenuClick(String item) {
        JOptionPane.showMessageDialog(this, item + " Clicked");
    }

    class ProductionLinePanel extends JPanel {

        private final ProductLine productLine;
        private int rating = 0;

        private JLabel statusLabel;
        private JProgressBar bar;

        public ProductionLinePanel(ProductLine line, ImageIcon gif) {
            this.productLine = line;

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
        }

        private void openDetails() {
            JDialog dialog = new JDialog(
                    ManagerView.this,
                    productLine.getName() + " Review",
                    true
            );
            dialog.setSize(450, 420);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new BorderLayout(10, 10));

            JLabel header = new JLabel(
                    productLine.getName() + " | ID: " + productLine.getId(),
                    SwingConstants.CENTER
            );

            header.setFont(new Font("SansSerif", Font.BOLD, 16));
            header.setBorder(new EmptyBorder(10, 10, 10, 10));
            dialog.add(header, BorderLayout.NORTH);

            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new CompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1, true),
                    new EmptyBorder(15, 15, 15, 15)
            ));
            card.setBackground(Color.WHITE);

            JLabel statusTitle = new JLabel("Line Status:");
            statusTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JRadioButton active = new JRadioButton();
            JRadioButton stopped = new JRadioButton();
            JRadioButton broken = new JRadioButton();


            active.setIcon(activeIcon);
            stopped.setIcon(stoppedIcon);
            broken.setIcon(maintenanceIcon);

            JRadioButton[] buttons = {active, stopped, broken};

            for (JRadioButton btn : buttons) {
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (btn == active) {
                            productLine.setStatus(LineStatus.ACTIVE);
                            active.setIcon(clickedActiveIcon);
                            stopped.setIcon(stoppedIcon);
                            broken.setIcon(maintenanceIcon);
                        } else if (btn == stopped) {
                            productLine.setStatus(LineStatus.STOPPED);
                            active.setIcon(activeIcon);
                            stopped.setIcon(clickedStoppedIcon);
                            broken.setIcon(maintenanceIcon);
                        } else if (btn == broken) {
                            productLine.setStatus(LineStatus.MAINTENANCE);
                            active.setIcon(activeIcon);
                            stopped.setIcon(stoppedIcon);
                            broken.setIcon(clickedMaintenanceIcon);
                        }
                    }
                });
            }

            ButtonGroup group = new ButtonGroup();
            group.add(active);
            group.add(stopped);
            group.add(broken);

            switch (productLine.getStatus()) {
                case ACTIVE -> {
                    active.setSelected(true);
                    active.setIcon(clickedActiveIcon);
                }
                case STOPPED -> {
                    stopped.setSelected(true);
                    stopped.setIcon(clickedStoppedIcon);
                }
                case MAINTENANCE -> {
                    broken.setSelected(true);
                    broken.setIcon(clickedMaintenanceIcon);
                }
            }

            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            statusPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            statusPanel.add(active);
            statusPanel.add(stopped);
            statusPanel.add(broken);

            JLabel rateLabel = new JLabel("Rate this line:");
            rateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel starsPanel = new JPanel();
            starsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel[] stars = new JLabel[5];

            for (int i = 0; i < 5; i++) {
                stars[i] = new JLabel("☆");
                stars[i].setFont(new Font("SansSerif", Font.PLAIN, 28));
                int idx = i;
                stars[i].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        rating = idx + 1;
                        for (int j = 0; j < 5; j++) {
                            stars[j].setText(j <= idx ? "★" : "☆");
                        }
                    }
                });
                starsPanel.add(stars[i]);
            }

            JTextArea notes = new JTextArea(5, 20);
            JScrollPane scroll = new JScrollPane(notes);
            scroll.setBorder(new TitledBorder("Notes"));

            card.add(statusTitle);
            card.add(statusPanel);
            card.add(Box.createVerticalStrut(10));
            card.add(rateLabel);
            card.add(starsPanel);
            card.add(Box.createVerticalStrut(10));
            card.add(scroll);

            dialog.add(card, BorderLayout.CENTER);

            JButton save = new JButton("Save Review");
            save.addActionListener(e -> {
                updateFromModel();
                dialog.dispose();
            });

            JPanel bottom = new JPanel();
            bottom.add(save);
            dialog.add(bottom, BorderLayout.SOUTH);

            dialog.setVisible(true);
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

    public void createPanelsForLines(Collection<ProductLine> lines) {
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
            allLines.put(line.getId(), p);
        }

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void refreshLine(ProductLine line) {
        ProductionLinePanel panel = allLines.get(line.getId());
        if (panel != null) panel.updateFromModel();
    }

    public static void main(String[] args) {

        UIManager.put("Button.pressedBackground", new Color(210, 210, 210));
        SwingUtilities.invokeLater(() -> {
            ProductLineService service = new ProductLineService();
            ManagerView view = new ManagerView();
            view.setVisible(true);
            view.createPanelsForLines(service.getAll());
        });
    }
}
