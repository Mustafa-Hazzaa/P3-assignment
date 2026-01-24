package Util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class InventoryStatusRenderer extends DefaultTableCellRenderer {
    private Icon availableIcon = new ImageIcon("src/Images/Available.png");
    private Icon lowIcon = new ImageIcon("src/Images/Low.png");
    private Icon outIcon = new ImageIcon("src/Images/Out.png");
    private Color bgColor;

    public InventoryStatusRenderer(){
        setHorizontalAlignment(CENTER);
        setFont(getFont().deriveFont(Font.BOLD));
        setOpaque(false);
    }

    @Override
    public Component
    getTableCellRendererComponent(JTable table , Object value ,
                                  boolean isSelected , boolean hasFocus , int row , int column){
        super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if (value instanceof InventoryStatus){
            InventoryStatus status = (InventoryStatus) value;
            setText(status.toString());
            switch (status){
                case AVAILABLE :
                    setIcon(availableIcon);
                    bgColor = new Color(198 , 239,206);
                    break;
                case LOW:
                        setIcon(lowIcon);
                        bgColor = new Color(255 , 235 , 206);
                        break;
                case OUT_OF_STOCK:
                    setIcon(outIcon);
                    bgColor=new Color(255,199,206);
                    break;
            }
        }
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setIconTextGap(8);
        setHorizontalAlignment(CENTER);
        return this;
    }

    @Override
    protected void
    paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);

        if (bgColor!=null){
            g2.setColor(bgColor);
            g2.fillRoundRect(4,4,getWidth()-8,getHeight()-8 , 18,18);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
