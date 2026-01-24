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
        header.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(230 , 230 , 230)) , BorderFactory.createEmptyBorder(25 , 30 , 15 , 30)));

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
    public void customizeTable(JTable table){
        table.getTableHeader().setBackground(new Color(180 , 170 ,145));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getTableHeader().setFont(new Font("Segoe UI" , Font.BOLD , 14));
        table.getTableHeader().setPreferredSize(new Dimension(0 , 40));

        table.setBackground(new Color(252 , 251 , 249));
        table.setRowHeight(40);
        table.setSelectionBackground(Color.BLACK);

        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230 , 230 , 230));

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v , boolean isSel , boolean hasF , int r , int c){
                Component comp = super.getTableCellRendererComponent(t , v , isSel , hasF , r , c);
                if (!isSel){
                    comp.setBackground(r%2 == 0 ? Color.WHITE : new Color(248 , 246 , 240));
                }
                return comp;
            }
        });
    }
}
