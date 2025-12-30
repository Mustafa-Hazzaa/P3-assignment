package io;

import Model.*;

import java.io.IOException;
import java.util.*;

public class CsvFileReader {

    private final TxtFileReader txtFileReader;

    public CsvFileReader() {
        this.txtFileReader = new TxtFileReader();
    }

    public List<User> loadUsers() throws IOException {
        return loadUsers("Data/Users.csv");
    }

    public List<User> loadUsers(String filePath) throws IOException {
        List<String> lines = txtFileReader.readAllLines(filePath);
        List<User> users = new ArrayList<>();

        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {
            String[] data = line.split(",");
            users.add(new User(data[0], data[1], data[2], data[3]));
        }

        return users;
    }

    public HashMap<String, ReviewNotes> loadReviewAndNotes() {
        return loadReviewAndNotes("Data/ReviewAndNotes.csv");
    }

    public HashMap<String, ReviewNotes> loadReviewAndNotes(String filePath) {
        List<String> lines;
        HashMap<String, ReviewNotes> reviewAndNotes = new HashMap<>();

        try {
            lines = txtFileReader.readAllLines(filePath);
            if (!lines.isEmpty()) {
                lines.remove(0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            String[] data = line.split(",");

            String productLine = parseCsvField(data[0]);
            String review = parseCsvField(data[1]);
            String notes = parseCsvField(data[2]);

            reviewAndNotes.put(productLine, new ReviewNotes(review, notes));
        }

        return reviewAndNotes;
    }

    public ArrayList<Item> loadItems() {
        return loadItems("Data/Items.csv");
    }

    public ArrayList<Item> loadItems(String filesPath) {
        List<String> lines;
        ArrayList<Item> items = new ArrayList<>();

        try {
            lines = txtFileReader.readAllLines(filesPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {
            String[] csv = line.split(",");
            items.add(new Item(
                    Integer.parseInt(csv[0]),
                    parseCsvField(csv[1]),
                    Integer.parseInt(csv[2]),
                    Integer.parseInt(csv[3]),
                    parseCsvField(csv[4]),
                    Integer.parseInt(csv[5])
            ));
        }

        return items;
    }

    public ArrayList<Product> loadProducts(Map<String, Item> requirements) {
        return loadProducts("Data/Products.csv", requirements);
    }

    public ArrayList<Product> loadProducts(String filesPath, Map<String, Item> requirements) {
        List<String> lines;
        ArrayList<Product> products = new ArrayList<>();

        try {
            lines = txtFileReader.readAllLines(filesPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {
            String[] csv = line.split(",");
            int id = Integer.parseInt(csv[0]);
            String name = parseCsvField(csv[1]);
            int quantity = Integer.parseInt(csv[2]);

            ArrayList<Item> items = new ArrayList<>();
            for (String itemName : csv[3].split(";"))
            {
                Item item = requirements.get(itemName);
                if (item == null) {
                    throw new RuntimeException("Item not found by name: " + itemName);
                }
                items.add(item);
            }

            products.add(new Product(id, name, quantity, items));
        }

        return products;
    }

    private String parseCsvField(String field) {
        field = field.trim();
        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1);
        }
        return field.replace("\"\"", "\"");
    }
}
