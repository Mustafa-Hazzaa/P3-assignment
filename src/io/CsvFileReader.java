package io;

import Model.*;
import java.io.IOException;
import java.util.*;

public class CsvFileReader {
    private final TxtFileReader txtFileReader;
    private final Inventory inventory;

    public CsvFileReader(Inventory inventory) {
        this.txtFileReader = new TxtFileReader();
        this.inventory= inventory;
    }

    public CsvFileReader() {
        this.txtFileReader = new TxtFileReader();
        inventory=null;
    }

    public List<User> loadUsers() throws IOException {
        return loadUsers("Data/Users.csv");
    }

    public List<User> loadUsers(String filePath) throws IOException {
        List<String> lines = txtFileReader.readAllLines(filePath);
        List<User> users = new ArrayList<>();

        if (!lines.isEmpty()) {
            lines.removeFirst();
        }
        for (String line:lines){

            String[] data = line.split(",");


            String username = data[0];
            String email = data[1];
            String password = data[2];
            String role = data[3];
            users.add(new User(username,email,password,role));
        }
        return users;
    }


    public HashMap<String, ReviewNotes> loadReviewAndNotes() {
        return loadReviewAndNotes("Data/ReviewAndNotes.csv");
    }

    public HashMap<String, ReviewNotes> loadReviewAndNotes(String filePath) {
        List<String> lines;
        ReviewNotes reviewNotes;
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

            reviewNotes = new ReviewNotes(review, notes);
            reviewAndNotes.put(productLine, reviewNotes);
        }
        return reviewAndNotes;
    }

    public ArrayList<Item> loadItems() {
        return loadItems("Data/Items.csv");
    }

    public ArrayList<Product> loadProducts(Inventory inventory) {
        return loadProducts("Data/Products.csv", inventory);
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
            int id = Integer.parseInt(csv[0]);
            String name = csv[1];
            int quantity = Integer.parseInt(csv[2]);
            int price = Integer.parseInt(csv[3]);
            String category = csv[4];
            int minStockLevel = Integer.parseInt(csv[5]);
            Item item = new Item(id, name, quantity, price, category, minStockLevel);
            items.add(item);
        }
        return items;
    }

    public ArrayList<Product> loadProducts(String filePath, Inventory inventory) {

        List<String> lines;
        ArrayList<Product> products = new ArrayList<>();

        try {
            lines = txtFileReader.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Remove header
        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {

            String[] csv = line.split(",");

            int id = Integer.parseInt(csv[0]);
            String name = csv[1];
            int quantity = Integer.parseInt(csv[2]);

            // Map instead of ArrayList
            Map<Integer, Item> requiredItems = new HashMap<>();

            String[] itemIds = csv[3].split(";");
            for (String itemIdStr : itemIds) {

                int itemId = Integer.parseInt(itemIdStr);
                Item item = inventory.getItemById(itemId);

                if (item == null) {
                    throw new RuntimeException("Item ID not found: " + itemId);
                }

                requiredItems.put(itemId, item);
            }

            Product product = new Product(id, name, quantity, requiredItems);
            products.add(product);
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
