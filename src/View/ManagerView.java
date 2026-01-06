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

public class ManagerView extends JFrame {
    private static final ImageIcon IDLE = new ImageIcon(ManagerView.class.getResource("/Images/IdleFactory.gif"));
    private static final ImageIcon WORKING = new ImageIcon(ManagerView.class.getResource("/Images/workingFactory.gif"));
    private static final ImageIcon BROKEN = new ImageIcon(ManagerView.class.getResource("/Images/brokenFactory.gif"));

    public HashMap<Integer, ProductionLinePanel> allLines;

    public ManagerView() {
        allLines = new HashMap<>();
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(1350, 750));
        setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon("src/Images/Logo.png");
        this.setIconImage(logo.getImage());


        // Side nav
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
        switch (item) {
            case "⚙️ Edit Production Line" -> JOptionPane.showMessageDialog(this,"Edit Production Line Clicked");
            case "📊 Reports" -> JOptionPane.showMessageDialog(this,"Reports Clicked");
            case "👥 Workers" -> JOptionPane.showMessageDialog(this,"Workers Clicked");
        }
    }

    class ProductionLinePanel extends JPanel {
        private final ProductLine productLine;
        public JProgressBar bar;

        public ProductionLinePanel(ProductLine productLine, ImageIcon gif) {
            this.productLine = productLine;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            JLabel gifLabel = new JLabel(gif);
            gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            gifLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    openDetails(productLine);
                }
            });

            JLabel title = new JLabel(productLine.getName());
            title.setFont(new Font("SansSerif", Font.BOLD, 14));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            bar = new JProgressBar(0, 100);
            if (productLine.currentTask() != null) {
                bar.setValue(productLine.currentTask().getProgress());
            }
            bar.setStringPainted(true);
            bar.setMaximumSize(new Dimension(200, 25));

            add(gifLabel);
            add(Box.createVerticalStrut(10));
            add(title);
            add(Box.createVerticalStrut(5));
            add(bar);
        }

        private void openDetails(ProductLine productLine) {
            JDialog dialog = new JDialog(ManagerView.this, productLine.getName() + " Details", true);
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(null);
            dialog.add(new JLabel("Details for " + productLine.getName(), SwingConstants.CENTER));
            dialog.setVisible(true);
        }

        public void updateFromModel() {
            if (productLine.currentTask() != null) {
                bar.setValue(productLine.currentTask().getProgress());
            }else {bar.setValue(0);}
        }
    }

    public void createPanelsForLines(Collection<ProductLine> lines) {
//        if (getContentPane().getComponentCount() > 1) {
//            getContentPane().remove(1);
//        }

        JPanel linePanel = new JPanel(new GridLayout(0, 3, 25, 0));
        linePanel.setBackground(new Color(255, 200, 220));
        linePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        ImageIcon gif = IDLE;
        for (ProductLine line : lines) {
            switch (line.getStatus()){
                case ACTIVE -> gif = WORKING;
                case STOPPED -> gif = IDLE;
                case MAINTENANCE -> gif = BROKEN;
            }
            ProductionLinePanel panel = new ProductionLinePanel(line, gif);
            linePanel.add(panel);
            allLines.put(line.getId(), panel);
        }

        add(linePanel, BorderLayout.CENTER);
        revalidate();
        repaint();


        setVisible(true);
    }

    public void refreshLine(ProductLine line) {
        ProductionLinePanel panel = allLines.get(line.getId());
        if (panel != null) panel.updateFromModel();
    }
}
