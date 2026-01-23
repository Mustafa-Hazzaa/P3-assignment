package tasks;

import javax.swing.*;
import java.awt.*;

public class MostRequestedProduct extends JFrame {
    private JLabel resultLable;

    public MostRequestedProduct(){
        setTitle("Most Requested Product");
        setSize(700 , 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT , 15 , 10));
        JTextField fromDateField = new JTextField(10);
        JTextField toDateField = new JTextField(10);

        JButton analyzeBtn = new JButton("Analyze");

        topPanel.add(new JLabel("From Date:"));
        topPanel.add(fromDateField);

        topPanel.add(new JLabel("To Date:"));
        topPanel.add(toDateField);

        topPanel.add(analyzeBtn);

        JPanel resultPanel = new JPanel() ;
        resultPanel.setLayout(new BorderLayout());

        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        resultLable= new JLabel("No analysis performed yet." , SwingConstants.CENTER);
        resultLable.setFont(new Font("Segoe UI" , Font.BOLD , 16));
        resultPanel.add(resultLable , BorderLayout.CENTER);

        analyzeBtn.addActionListener(e -> analyzeMostRequestedProduct(fromDateField.getText() , toDateField.getText()));

        add(topPanel , BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    private  void analyzeMostRequestedProduct (String from , String to){
        if (from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this , "Please enter both dates" , "Missing Date" , JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
}
