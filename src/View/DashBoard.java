package View;

import javax.swing.*;
import java.awt.*;

import Control.DashBoardController;
import tasks.*;

public class DashBoard extends JPanel {
    private DashBoardController controller;
    public DashBoard() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 25, 25));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(255, 249, 230));
    }
//        mainPanel.add(createCard("Add a new task", "add a new task to be processed", new Color(76, 175, 80), "01", controller::handleAddTask));
//        mainPanel.add(createCard("Tasks by Production Line", "View tasks assigned to each Production Line", new Color(255, 183, 77), "02", controller::handleTasksByProductionLine));
//        mainPanel.add(createCard("Tasks by Product", "View tasks related to a specific Product", new Color(255, 112, 67), "03", controller::handleTasksByProduct));
//        mainPanel.add(createCard("Production Lines for a Product", "View Production Lines that worked on a product", new Color(121, 85, 72), "04", controller::handleProductionLinesByProduct));
//        mainPanel.add(createCard("Products by Production Line", "View Products produced by a specific Line", new Color(38, 166, 154), "05", controller::handleProductsByProductionLine));
//        mainPanel.add(createCard("Most requested Product", "Find the most requested Product in a period", new Color(92, 107, 192), "06", controller::handleMostRequestedProduct));
//
//        add(mainPanel, BorderLayout.CENTER);
//    }
    public void setController(DashBoardController controller) {
        this.controller = controller;
        initComponents();
    }
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(255, 249, 230));

        // Now, controller is guaranteed to be non-null here
        mainPanel.add(createCard("Add a new task", "add a new task to be processed", new Color(76, 175, 80), "01", controller::handleAddTask));
        mainPanel.add(createCard("Tasks by Production Line", "View tasks assigned to each Production Line", new Color(255, 183, 77), "02", controller::handleTasksByProductionLine));
        mainPanel.add(createCard("Tasks by Product", "View tasks related to a specific Product", new Color(255, 112, 67), "03", controller::handleTasksByProduct));
        mainPanel.add(createCard("Production Lines for\n a Product", "View Production Lines that worked on a product", new Color(121, 85, 72), "04", controller::handleProductionLinesByProduct));
        mainPanel.add(createCard("Products by Production Line", "View Products produced by a specific Line", new Color(38, 166, 154), "05", controller::handleProductsByProductionLine));
        mainPanel.add(createCard("Most requested Product", "Find the most requested Product in a period", new Color(92, 107, 192), "06", controller::handleMostRequestedProduct));

        add(mainPanel, BorderLayout.CENTER);
    }

    // The createCard method remains the same...
    private JPanel createCard (String titleText , String descText , Color accentColor , String number , Runnable onOpen) {
        JPanel card = new JPanel(new BorderLayout(15 , 15));

        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0 , 0 ,0, 10 , accentColor),BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        JPanel header = new JPanel(new BorderLayout(12,0));
        header.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI" , Font.BOLD,26));

        JLabel numLable = new JLabel(number);

        header.add(title,BorderLayout.CENTER);
        numLable.setForeground(new Color(200,200,200));
        header.add(numLable , BorderLayout.EAST);

        JLabel desc = new JLabel("<html><body style='width:150px'>" + descText + "</body></html>");
        desc.setForeground(Color.GRAY);

        JButton openBtn = new JButton("Open");
        openBtn.setFocusPainted(false);
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openBtn.putClientProperty("JButton.buttonType" , "roundRect");
        openBtn.setBackground(accentColor);
        openBtn.setForeground(Color.WHITE);
        openBtn.setFont(new Font("Segoe UI" , Font.BOLD , 16));
        openBtn.setBorder(BorderFactory.createEmptyBorder(10 , 0 ,10 , 0));

        // This makes sure the button does nothing if the controller is null (like in a test)
        if (onOpen != null) {
            openBtn.addActionListener(e -> onOpen.run());
        }

        card.add(header , BorderLayout.NORTH);
        card.add(desc , BorderLayout.CENTER);
        card.add(openBtn , BorderLayout.SOUTH);

        return card;
    }
    public static void main (String[]args){
        UIManager.put("Button.arc",15);
        UIManager.put("Component.arc",15);
        try{ com.formdev.flatlaf.FlatLightLaf.setup();}
        catch (Exception e){
            System.err.println("FlatLaf initialization failed");
        }
        SwingUtilities.invokeLater(() -> new DashBoard().setVisible(true));
    }
}