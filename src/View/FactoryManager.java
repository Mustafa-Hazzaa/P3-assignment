package View;

import View.SideNavPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FactoryManager extends JFrame {

    public FactoryManager() {
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        SideNavPanel sideNav = new SideNavPanel("Manager Panel","Factory Control","src/Images/Logo.png",List.of("⚙️ Edit Production Line", "📊 Reports", "👥 Workers"),this::onMenuClick,() -> System.exit(0));

        add(sideNav,BorderLayout.WEST);

        JPanel rightPanel=new JPanel(new GridLayout(1,3,25,0));
        rightPanel.setBackground(new Color(255,200,220));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        ImageIcon gif=new ImageIcon(getClass().getResource("/Images/factory.gif"));
        rightPanel.add(new ProductionLinePanel("Line 1",gif));
        rightPanel.add(new ProductionLinePanel("Line 2",gif));
        rightPanel.add(new ProductionLinePanel("Line 3",gif));

        add(rightPanel,BorderLayout.CENTER);
        setVisible(true);
    }
    private void onMenuClick(String item) {
        switch (item){
            case "⚙️ Edit Production Line" ->{
                JOptionPane.showMessageDialog(this,"Edit Production Line Clicked");
            }
            case "📊 Reports" ->{
                JOptionPane.showMessageDialog(this,"Reports Clicked");
            }
            case "👥 Workers" ->{
                JOptionPane.showMessageDialog(this,"Workers Clicked");
            }
        }
    }


    class ProductionLinePanel extends JPanel {

        public ProductionLinePanel(String name, ImageIcon gif) {
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setOpaque(false);

            JLabel gifLabel=new JLabel(gif);
            gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            gifLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    openDetails(name);
                }
            });

            JLabel title=new JLabel(name);
            title.setFont(new Font("SansSerif", Font.BOLD, 14));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JProgressBar bar=new JProgressBar(0,100);
            bar.setValue(0);
            bar.setStringPainted(true);
            bar.setMaximumSize(new Dimension(200,25));

            add(gifLabel);
            add(Box.createVerticalStrut(10));
            add(title);
            add(Box.createVerticalStrut(5));
            add(bar);
        }

        private void openDetails(String name) {
            JDialog dialog=new JDialog(FactoryManager.this,name +"Details",true);
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(null);
            dialog.add(new JLabel("Details for"+name,SwingConstants.CENTER));
            dialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FactoryManager::new);
    }
}