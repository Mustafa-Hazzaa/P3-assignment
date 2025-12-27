package Model;

import io.CsvFileReader;
import io.CsvFileWriter;
import io.TxtFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Inventory {
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    private final CsvFileWriter writer = new CsvFileWriter();
    private final TxtFileWriter txtWriter = new TxtFileWriter();


    private int nextItemId = 1;
    private int nextProductId = 1;

    public void loadItems(String filePath) {
        CsvFileReader csvReader = new CsvFileReader(this);
        items.addAll(csvReader.loadItems(filePath));
        int maxId = 0;
        for (Item item : items) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        this.nextItemId = maxId + 1;
    }


    public void loadProducts(String filePath) {
        CsvFileReader csvReader = new CsvFileReader(this);
        products.addAll(csvReader.loadProducts(filePath));
        int maxId = 0;
        for (Product product : products) {
            if (product.getId() > maxId) {
                maxId = product.getId();
            }
        }

        this.nextProductId = maxId + 1;
    }

    public void saveItems() {
        try {
            txtWriter.writeLine("Data/items.csv", "id,name,quantity,price,category,minStockLevel", false); // overwrite header
            for (Item item : items) {
                writer.addItem(item, true); // append each line
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addNewItem(String name, int quantity, int price, String category, int minStockLevel) {
        Item newItem = new Item(nextItemId++, name, quantity, price, category, minStockLevel);
        items.add(newItem);
        saveItems();
    }



    public void addNewProduct(String name, int quantity, ArrayList<Item> items) {
        Product newProduct = new Product(nextProductId++, name, quantity, items);
        products.add(newProduct);
        writer.addProduct(newProduct, true);
    }


    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    public void removeItem(int id) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == id) {
                    items.remove(i);
                    return;
                }
            }
        try {
            txtWriter.writeLine("Data/items.csv","id,name,quantity,price,category,minStockLevel",false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Item item : items) {
            writer.addItem(item,true);
        }
    }

    public void removeProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return;
            }
            try {
                txtWriter.writeLine("Data/items.csv","id,name,quantity,requiredItems",false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Product product : products) {
                writer.addProduct(product,true);
            }
        }
        }


    public void printInventory() {
        System.out.println("--- Current Items ---");
        for(Item item : items) {
            System.out.println(item);
        }
        System.out.println("\n--- Current Products ---");
        for(Product product : products) {
            System.out.println(product);
        }
        System.out.println("\nNext Item ID will be: " + nextItemId);
        System.out.println("Next Product ID will be: " + nextProductId);
        System.out.println("-------------------------------------\n");
    }
}
