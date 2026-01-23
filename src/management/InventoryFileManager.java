package management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryFileManager {
    public static void saveWithChooser(JFrame parent , DefaultTableModel model)
        throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("save Inventory");

        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try(BufferedWriter w = new BufferedWriter(new FileWriter(file))){
                for (int i =0 ; i<model.getRowCount() ; i++){
                    w.write("Item name: " +model.getValueAt(i , 0));
                    w.newLine();
                    w.write("Category: " +model.getValueAt(i,1));
                    w.newLine();
                    w.write("Quantity: " +model.getValueAt(i,2));
                    w.newLine();
                    w.write("Minimum Quantity: " +model.getValueAt(i,3));
                    w.newLine();
                    w.write("Status: " +((InventoryStatus)model.getValueAt(i,4)).getText());
                    w.newLine();
                    w.write("-------------------------------------");
                    w.newLine();
                }
            }
        }
    }
}
