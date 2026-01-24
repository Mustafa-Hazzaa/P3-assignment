package View;

import javax.swing.*;
import java.awt.*;
import tasks.*;

public class ProductionLineDashBoard extends JPanel {
    public ProductionLineDashBoard() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 25, 25));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(255, 249, 230));

        mainPanel.add(createCard("Tasks by Production Line", "View tasks assigned to each Production Line",new Color(255 , 183 , 77), "01",() -> new ProductionLineTasks().setVisible(true)));
        mainPanel.add(createCard("Tasks by Product", "View tasks related to a specific Product", new Color(255 , 112 ,67 ), "02",() -> new ProductTask().setVisible(true)));
        mainPanel.add(createCard("Production Lines for a Product", "View Production Lines that worked on a product", new Color(121,85,72),"03",() -> new ProductionLinesByProduct().setVisible(true)));
        mainPanel.add(createCard("Products by Production Line", "View Products produced by a specific Line", new Color(76,175,80) ,"04", () ->new ProductsByProductionLine().setVisible(true)));
        mainPanel.add(createCard("All produced Products", "View all Products produced by all Lines", new Color(38 , 166 , 154),"05",() -> new AllProducedProducts().setVisible(true)));
        mainPanel.add(createCard("Most requested Product", "Find the most requested Product in a period", new Color(92 , 107 , 192),"06",() -> new MostRequestedProduct().setVisible(true)));

        add(mainPanel, BorderLayout.CENTER);
    }
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

        openBtn.addActionListener(e -> onOpen.run());

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
        SwingUtilities.invokeLater(() -> new ProductionLineDashBoard().setVisible(true));
    }
}