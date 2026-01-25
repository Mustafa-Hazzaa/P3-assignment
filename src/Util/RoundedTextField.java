package Util;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField{
    public RoundedTextField(int columns){
        super(columns);
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        setFont(new Font("segoe UI",Font.PLAIN , 13));
    }
    @Override
    protected void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),25 ,25);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(200,180,140));
        g2.drawRoundRect(0 ,0,getWidth()-1 , getHeight()-1 ,25 , 25);
        g2.dispose();
    }
}
