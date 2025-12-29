package io;

import Model.*;

import java.io.IOException;
import java.util.stream.Collectors;

public class CsvFileWriter {
    private TxtFileWriter txtFileWriter = new TxtFileWriter();

    public void addUser(String username, String email ,String password, String role, boolean append) {
        addUser("Data/Users.csv",username,email,password,role,append);
    }

    public void addUser(String filePath,String username,String email,String password,String role,boolean append){
        String input = username +","+email+ "," + password + "," + role;
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,input,append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReviewAndNotes(String productLine,String review,String Notes){
        addReviewAndNotes("Data/ReviewAndNotes.csv",productLine,review,Notes);
    }

    public void addReviewAndNotes(String filePath,String productLine,String review, String notes){
        String safeProductLine = "\"" + productLine.replace("\"", "\"\"") + "\"";
        String safeReview = "\"" + review.replace("\"", "\"\"") + "\"";
        String safeNotes = "\"" + notes.replace("\"", "\"\"") + "\"";
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,safeProductLine+","+safeReview+","+safeNotes,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(Item item, boolean append) {
        addItem("Data/Items.csv", item, append);
    }

    public void addItem(String filePath, Item item, boolean append) {
        String line = item.getId() + "," +
                parseCsvField(item.getName()) + "," +
                item.getQuantity() + "," +
                item.getPrice() + "," +
                parseCsvField(item.getCategory()) + "," +
                item.getMinStockLevel();
        try {
            txtFileWriter.writeLine(filePath, line, append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product, boolean append) {
        addProduct("Data/Products.csv", product, append);
    }

    public void addProduct(String filePath, Product product, boolean append) {
        String items = product.getRequiredItems().stream()
                .map(i -> String.valueOf(i.getId()))
                .collect(Collectors.joining(";"));

        String line = product.getId() + "," +
                parseCsvField(product.getName()) + "," +
                product.getQuantity() + "," +
                parseCsvField(items);
        try {
            txtFileWriter.writeLine(filePath, line, append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseCsvField(String field) {
        field = field.trim();
        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1);
        }
        return field.replace("\"\"", "\"");
    }
}
