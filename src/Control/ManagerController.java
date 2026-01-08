package Control;

import Model.ProductLine;
import Model.ReviewNotes;
import Service.ProductLineService;
import Service.ReviewNotesService;
import Util.LineStatus;
import View.ManagerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerController {

    private final ManagerView view;
    private final ProductLineService service;
    private final ReviewNotesService rn;

    public ManagerController(ManagerView view, ProductLineService service, ReviewNotesService rn) {
        this.view = view;
        this.service = service;
        this.rn = rn;

        view.createPanelsForLines(service.getAll(),this);
    }


    public void openLineDetails(JFrame parent, ProductLine productLine) {
        ReviewNotes existingNotes = rn.getNotesForProductLine(productLine.getName());

        JDialog dialog = new JDialog(parent, productLine.getName() + " Review", true);
        dialog.setSize(450, 420);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout(10, 10));
        //###############################################   TITLE&INIT   ############################################
        JLabel header = new JLabel("NAME: "+productLine.getName() + "   |   ID: " + productLine.getId(), SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(header, BorderLayout.NORTH);
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        //############################################# Status  #################################
        JLabel statusTitle = new JLabel("Line Status:");
        statusTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusTitle.setOpaque(false);
        statusTitle.setAlignmentX(Component.CENTER_ALIGNMENT);


        JRadioButton active = new JRadioButton();
        JRadioButton stopped = new JRadioButton();
        JRadioButton broken = new JRadioButton();

        active.setIcon(ManagerView.activeIcon);
        stopped.setIcon(ManagerView.stoppedIcon);
        broken.setIcon(ManagerView.maintenanceIcon);

        ButtonGroup group = new ButtonGroup();
        group.add(active);
        group.add(stopped);
        group.add(broken);


        active.addActionListener(e -> updateStatusIcons(active, stopped, broken));
        stopped.addActionListener(e -> updateStatusIcons(active, stopped, broken));
        broken.addActionListener(e -> updateStatusIcons(active, stopped, broken));

        switch (productLine.getStatus()) {
            case ACTIVE -> active.setSelected(true);
            case STOPPED -> stopped.setSelected(true);
            case MAINTENANCE -> broken.setSelected(true);
        }
        updateStatusIcons(active, stopped, broken);


        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statusPanel.add(active);
        statusPanel.add(stopped);
        statusPanel.add(broken);
        statusPanel.setOpaque(false);


        //###############################   Stars    ################################################
        JLabel rateLabel = new JLabel("Rate this line:");
        rateLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        rateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel starsPanel = new JPanel();
        starsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        starsPanel.setOpaque(false);
        final int[] currentRating = {existingNotes != null ? existingNotes.getReview() : 0};

        JLabel[] stars = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JLabel("☆");
            stars[i].setFont(new Font("SansSerif", Font.PLAIN, 28));
            stars[i].setForeground(Color.BLACK);

            if (i < currentRating[0]) {
                stars[i].setText("★");
                stars[i].setForeground(new Color(235, 201, 52)); // yellow
            }

            int idx = i; // for inner class
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentRating[0] = idx + 1;
                    // update all stars visually
                    for (int j = 0; j < 5; j++) {
                        if (j <= idx) {
                            stars[j].setText("★");
                            stars[j].setForeground(new Color(235, 201, 52));
                        } else {
                            stars[j].setText("☆");
                            stars[j].setForeground(Color.BLACK);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    stars[idx].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });

            starsPanel.add(stars[i]);
        }
        //###############################   NOTES   ##################################################
        JTextArea notes = new JTextArea(5, 20);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(notes);
        notes.setText(existingNotes.getNotes() == null ? "" : existingNotes.getNotes());
        scroll.setBorder(BorderFactory.createTitledBorder("Notes"));


        //##############################    SaveButton  ##############################################
        JButton save = new JButton("Save Review");
        //dont allow enter in notes
        notes.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    e.consume();
                    save.doClick();
                }
            }
        });

        save.addActionListener(e -> {
            rn.addUpdateNotes(new ReviewNotes(productLine.getName(),currentRating[0],notes.getText()));
            LineStatus newStatus;
            if (active.isSelected()) newStatus = LineStatus.ACTIVE;
            else if (stopped.isSelected()) newStatus = LineStatus.STOPPED;
            else newStatus = LineStatus.MAINTENANCE;
            productLine.setStatus(newStatus);
            dialog.dispose();
        });
        JPanel bottom = new JPanel();
        bottom.add(save);
        //##############################    addEveryThing ############################################
        card.add(statusTitle);
        card.add(statusPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(rateLabel);
        card.add(starsPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(scroll);
        dialog.add(card, BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.getRootPane().setDefaultButton(save);

        dialog.setVisible(true);




    }

    private void updateStatusIcons(JRadioButton active, JRadioButton stopped, JRadioButton broken) {
        active.setIcon(active.isSelected() ? ManagerView.clickedActiveIcon : ManagerView.activeIcon);
        stopped.setIcon(stopped.isSelected() ? ManagerView.clickedStoppedIcon : ManagerView.stoppedIcon);
        broken.setIcon(broken.isSelected() ? ManagerView.clickedMaintenanceIcon : ManagerView.maintenanceIcon);
    }


}
