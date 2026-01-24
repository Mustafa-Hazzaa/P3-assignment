package tasks;

import javax.swing.*;
import java.awt.*;

public class BaseDetails extends JFrame{
    protected JPanel contentPanel;
    protected JPanel toolbarPanel;

    public BaseDetails(String title , String subTitle){
        setTitle(title);
        setSize(1000 , 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(25 , 30 , 15 , 30));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI" , Font.BOLD , 22));

        JLabel lblSub = new JLabel(subTitle);
        lblSub.setFont(new Font("Segoe UI" , Font.PLAIN , 14));
        lblSub.setForeground(Color.GRAY);

        JPanel titles = new JPanel(new GridLayout(2,1));
        titles.setOpaque(false);
        titles.add(lblTitle);
        titles.add(lblSub);

        header.add(titles,BorderLayout.WEST);

        toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT , 15 , 10));
        toolbarPanel.setBackground(new Color(250 , 250 , 250));

        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10 , 30 , 10 , 30));

        contentPanel = new JPanel(new BorderLayout());

        contentPanel.setBorder(BorderFactory.createEmptyBorder(15 , 30 ,30 ,30));
        contentPanel.setBackground(new Color(245 , 245 , 245));

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.add(header , BorderLayout.NORTH);
        topWrapper.add(toolbarPanel , BorderLayout.SOUTH);

        add(topWrapper , BorderLayout.NORTH);
        add(contentPanel , BorderLayout.CENTER);
    }
}
