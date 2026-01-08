package View;

import Model.ProductLine;
import Model.ReviewNotes;
import Util.LineStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReviewDialog extends JDialog {

    private LineStatus selectedStatus;
    private int selectedRating;
    private String selectedNotes;

    public ReviewDialog(JFrame parent, ProductLine line, ReviewNotes notes) {
        super(parent, line.getName() + " Review", true);
        setSize(450, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== Header =====
        JLabel header = new JLabel("NAME: " + line.getName() + " | ID: " + line.getId(), SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.setBackground(Color.WHITE);

        // ===== Status =====
        JLabel statusLabel = new JLabel("Line Status:");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);

        JRadioButton active = new JRadioButton();
        JRadioButton stopped = new JRadioButton();
        JRadioButton broken = new JRadioButton();
        ButtonGroup group = new ButtonGroup();
        group.add(active);
        group.add(stopped);
        group.add(broken);

        active.setIcon(ManagerView.activeIcon);
        stopped.setIcon(ManagerView.stoppedIcon);
        broken.setIcon(ManagerView.maintenanceIcon);

        LineStatus current = line.getStatus();
        if (current == LineStatus.ACTIVE) active.setSelected(true);
        if (current == LineStatus.STOPPED) stopped.setSelected(true);
        if (current == LineStatus.MAINTENANCE) broken.setSelected(true);

        Runnable updateIcons = () -> {
            active.setIcon(active.isSelected() ? ManagerView.clickedActiveIcon : ManagerView.activeIcon);
            stopped.setIcon(stopped.isSelected() ? ManagerView.clickedStoppedIcon : ManagerView.stoppedIcon);
            broken.setIcon(broken.isSelected() ? ManagerView.clickedMaintenanceIcon : ManagerView.maintenanceIcon);
        };

        active.addActionListener(e -> updateIcons.run());
        stopped.addActionListener(e -> updateIcons.run());
        broken.addActionListener(e -> updateIcons.run());
        updateIcons.run();

        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.setOpaque(false);
        statusPanel.add(active);
        statusPanel.add(stopped);
        statusPanel.add(broken);

        // ===== Stars =====
        JLabel rateLabel = new JLabel("Rate this line:");
        rateLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        rateLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel starsPanel = new JPanel();
        starsPanel.setOpaque(false);

        int existingRating = (notes != null) ? notes.getReview() : 0;
        JLabel[] stars = new JLabel[5];
        final int[] ratingRef = { existingRating };

        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel(i < existingRating ? "★" : "☆");
            star.setFont(new Font("SansSerif", Font.PLAIN, 28));

            star.setForeground(i < existingRating ? new Color(235, 201, 52) : Color.GRAY);

            int index = i;
            star.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    ratingRef[0] = index + 1;
                    for (int j = 0; j < 5; j++) {
                        if (j <= index) {
                            stars[j].setText("★");
                            stars[j].setForeground(new Color(235, 201, 52));
                        } else {
                            stars[j].setText("☆");
                            stars[j].setForeground(Color.GRAY);
                        }
                    }
                }
            });

            stars[i] = star;
            starsPanel.add(star);
        }

        // ===== Notes =====
        JTextArea notesArea = new JTextArea(5, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setText(notes == null ? "" : notes.getNotes());

        JScrollPane scroll = new JScrollPane(notesArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Notes"));

        // ===== Save Button =====
        JButton save = new JButton("Save Review");
        notesArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    e.consume();
                    save.doClick();
                }
            }
        });

        save.addActionListener(e -> {
            selectedStatus = active.isSelected() ? LineStatus.ACTIVE :
                    stopped.isSelected() ? LineStatus.STOPPED :
                            LineStatus.MAINTENANCE;

            selectedRating = ratingRef[0];
            selectedNotes = notesArea.getText();
            dispose();
        });

        panel.add(statusLabel);
        panel.add(statusPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(rateLabel);
        panel.add(starsPanel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scroll);

        add(panel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(save);
        add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(save);
    }

    public LineStatus getStatus() { return selectedStatus; }
    public int getRating() { return selectedRating; }
    public String getNotes() { return selectedNotes; }
}
