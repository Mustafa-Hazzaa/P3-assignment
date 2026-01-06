package View;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class ManagerView extends JFrame {

    public ManagerView() {
        setTitle("Factory Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        SideNavPanel sideNav= new SideNavPanel("Manager Panel","Factory Control","factory_logo.png",List.of("⚙ Edit Production Line", "📊 Reports", "👥 Workers"),this::onMenuClick,()->System.exit(0)
        );
        add(sideNav,BorderLayout.WEST);

        JPanel rightPanel= new JPanel(new GridLayout(1,3,25,0));
        rightPanel.setBackground(new Color(245,220,230));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        ImageIcon gif= new ImageIcon(getClass().getResource("/Images/factory.gif"));

        rightPanel.add(new ProductionLinePanel("Line 1","01",gif));
        rightPanel.add(new ProductionLinePanel("Line 2","02",gif));
        rightPanel.add(new ProductionLinePanel("Line 3","03",gif));

        add(rightPanel,BorderLayout.CENTER);
    }
    private void onMenuClick(String item) {
        JOptionPane.showMessageDialog(this,item +"Clicked");
    }
    class ProductionLinePanel extends JPanel {

        private int rating= 0;
        private String status ="RUNNING";
        private JLabel statusLabel;
        private String lineId;

        public ProductionLinePanel(String name, String id, ImageIcon gif) {
            this.lineId = id;

            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setOpaque(false);

            JLabel gifLabel= new JLabel(gif);
            gifLabel.setAlignmentX(CENTER_ALIGNMENT);
            gifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            gifLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    openDetails(name);
                }
            });

            JLabel title=new JLabel(name);
            title.setFont(new Font("SansSerif",Font.BOLD,15));
            title.setAlignmentX(CENTER_ALIGNMENT);

            JProgressBar bar=new JProgressBar(0,100);
            bar.setValue(0);
            bar.setStringPainted(true);
            bar.setMaximumSize(new Dimension(200,22));

            statusLabel=new JLabel(status);
            statusLabel.setForeground(new Color(0,150,0));
            statusLabel.setAlignmentX(CENTER_ALIGNMENT);

            add(gifLabel);
            add(Box.createVerticalStrut(8));
            add(title);
            add(Box.createVerticalStrut(5));
            add(bar);
            add(Box.createVerticalStrut(8));
            add(statusLabel);
        }

        private void openDetails(String name) {
            JDialog dialog=new JDialog(ManagerView.this,name +"Review",true);
            dialog.setSize(450,420);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new BorderLayout(10,10));
            dialog.getContentPane().setBackground(new Color(250,245,248));

            JLabel header=new JLabel(name+"ID:"+lineId,SwingConstants.CENTER);
            header.setFont(new Font("SansSerif",Font.BOLD,16));
            header.setBorder(new EmptyBorder(10,10,10,10));
            dialog.add(header, BorderLayout.NORTH);

            JPanel card=new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY,1,true), new EmptyBorder(15,15,15,15)));
            card.setBackground(Color.WHITE);

            JLabel statusTitle=new JLabel("Line Status:");
            statusTitle.setAlignmentX(CENTER_ALIGNMENT);

            JRadioButton run=new JRadioButton("RUN");
            JRadioButton pause=new JRadioButton("PAUSE");
            JRadioButton stop=new JRadioButton("STOP");

            ButtonGroup group=new ButtonGroup();
            group.add(run);
            group.add(pause);
            group.add(stop);

            if (status.equals("RUNNING"))run.setSelected(true);
            else if (status.equals("PAUSED"))pause.setSelected(true);
            else stop.setSelected(true);

            run.addActionListener(e -> updateStatus("RUNNING",new Color(0,150,0)));
            pause.addActionListener(e -> updateStatus("PAUSED",new Color(180,140,0)));
            stop.addActionListener(e -> updateStatus("STOPPED",Color.RED));

            JPanel statusPanel = new JPanel();
            statusPanel.add(run);
            statusPanel.add(pause);
            statusPanel.add(stop);


            JPanel starsPanel=new JPanel();
            starsPanel.setBackground(Color.WHITE);
            JLabel[] stars=new JLabel[5];

            for (int i = 0; i < 5; i++) {
                stars[i] = new JLabel("☆");
                stars[i].setFont(new Font("SansSerif", Font.PLAIN, 28));
                final int idx = i;
                stars[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                stars[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        rating = idx + 1;
                        for (int j = 0; j < 5; j++) {
                            stars[j].setText(j <= idx ? "★" : "☆");
                        }
                    }
                });
                starsPanel.add(stars[i]);
            }

            JLabel rateLbl = new JLabel("Rate this line:");
            rateLbl.setAlignmentX(CENTER_ALIGNMENT);
            JTextArea notes = new JTextArea(5, 20);
            notes.setLineWrap(true);
            notes.setWrapStyleWord(true);
            JScrollPane scroll = new JScrollPane(notes);
            scroll.setBorder(new TitledBorder("Notes"));

            card.add(statusTitle);
            card.add(statusPanel);
            card.add(Box.createVerticalStrut(10));
            card.add(rateLbl);
            card.add(starsPanel);
            card.add(Box.createVerticalStrut(10));
            card.add(scroll);

            dialog.add(card, BorderLayout.CENTER);

            JButton save=new JButton("Save Review");
            save.setFocusPainted(false);
            save.addActionListener(e -> {
                JOptionPane.showMessageDialog(dialog,
                        "Saved Successfully ⭐\nRating:"+rating+"\nStatus:"+status);
                dialog.dispose();
            });

            JPanel bottom=new JPanel();
            bottom.setBackground(new Color(250,245,248));
            bottom.add(save);

            dialog.add(bottom,BorderLayout.SOUTH);
            dialog.setVisible(true);
        }

        private void updateStatus(String newStatus, Color color) {
            status=newStatus;
            statusLabel.setText(newStatus);
            statusLabel.setForeground(color);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagerView().setVisible(true));
    }
}
