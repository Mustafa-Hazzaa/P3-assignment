package io;

import Model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvFileWriter {

    private final TxtFileWriter txtFileWriter = new TxtFileWriter();

    public void addUser(String username, String email, String password, String role, boolean append) {
        addUser("Data/Users.csv", username, email, password, role, append);
    }

    public void addUser(String filePath, String username, String email, String password, String role, boolean append) {
        String line = username + "," + email + "," + password + "," + role;
        try {
            txtFileWriter.writeLine(filePath, line, append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReviewAndNotes(String productLine, String review, String notes) {
        addReviewAndNotes("Data/ReviewAndNotes.csv", productLine, review, notes);
    }

    public void addReviewAndNotes(String filePath, String productLine, String review, String notes) {
        String safeProductLine = escape(productLine);
        String safeReview = escape(review);
        String safeNotes = escape(notes);

        String line = safeProductLine + "," + safeReview + "," + safeNotes;
        try {
            txtFileWriter.writeLine(filePath, line, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(Item item, boolean append) {
        addItem("Data/Items.csv", item, append);
    }

    public void addItem(String filePath, Item item, boolean append) {
        String line =
                item.getId() + "," +
                        escape(item.getName()) + "," +
                        item.getQuantity() + "," +
                        item.getPrice() + "," +
                        escape(item.getCategory()) + "," +
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

        Map<String, Item> requiredItems = product.getRequiredItems();
        StringBuilder names = new StringBuilder();

        int count = 0;
        for (Item item : requiredItems.values()) {
            names.append(item.getName());
            if (++count < requiredItems.size()) {
                names.append(";");
            }
        }

        String line =
                product.getId() + "," +
                        escape(product.getName()) + "," +
                        product.getQuantity() + "," +
                        names.toString();

        try {
            txtFileWriter.writeLine(filePath, line, append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String escape(String field) {
        field = field.replace("\"", "\"\"");
        return "\"" + field + "\"";
    }
}
