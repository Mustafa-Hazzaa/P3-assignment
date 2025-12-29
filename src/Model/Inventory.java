package Model;

import io.CsvFileReader;
import io.CsvFileWriter;
import io.TxtFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private static volatile Inventory instance;

    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();

    private final CsvFileWriter writer = new CsvFileWriter();
    private final TxtFileWriter txtWriter = new TxtFileWriter();
    private final CsvFileReader reader = new CsvFileReader();

    private Inventory() {
        for (Item item : reader.loadItems("Data/Items.csv")) {
            items.put(item.getId(), item);
        }

        // Load products
        for (Product product : reader.loadProducts("Data/Products.csv", this)) {
            products.put(product.getId(), product);
        }
    }

    public static Inventory getInstance() {
        Inventory result = instance;
        if (result == null) {
            synchronized (Inventory.class) {
                result = instance;
                if (result == null) {
                    instance = result = new Inventory();
                }
            }
        }
        return result;
    }


    public void addNewItem(String name, int quantity, int price,
                           String category, int minStockLevel) {

        Item newItem = new Item(name, quantity, price, category, minStockLevel);
        items.put(newItem.getId(), newItem);
        writer.addItem(newItem, true);
    }

    public Item getItemById(int id) {
        return items.get(id);
    }

    public Item getItemByName(String name) {
        String target = name.toLowerCase();
        for (Item item : items.values()) {
            if (item.getName().toLowerCase().equals(target)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(int id) {
        if (items.remove(id) == null) return;

        rewriteItemsFile();
    }

    private void rewriteItemsFile() {
        try {
            txtWriter.writeLine(
                    "Data/Items.csv",
                    "id,name,quantity,price,category,minStockLevel",
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Item item : items.values()) {
            writer.addItem(item, true);
        }
    }


    public void addNewProduct(String name, int quantity, Map<Integer, Item> requiredItems) {
        Product newProduct = new Product(name, quantity, requiredItems);
        products.put(newProduct.getId(), newProduct);
        writer.addProduct(newProduct, true);
    }

    public Product getProductById(int id) {
        return products.get(id);
    }

    public void removeProduct(int id) {
        if (products.remove(id) == null) return;

        rewriteProductsFile();
    }

    private void rewriteProductsFile() {
        try {
            txtWriter.writeLine(
                    "Data/Products.csv",
                    "id,name,quantity,requiredItems",
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Product product : products.values()) {
            writer.addProduct(product, true);
        }
    }


    public void printInventory() {
        System.out.println("--- Current Items ---");
        for (Item item : items.values()) {
            System.out.println(item);
        }

        System.out.println("\n--- Current Products ---");
        for (Product product : products.values()) {
            System.out.println(product);
        }

        System.out.println("-------------------------------------\n");
    }
}
