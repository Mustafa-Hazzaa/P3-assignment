package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product extends Stockable {

    private static int nextId = 1;
    private final Map<String , Item> requiredItems;

    public Product(String name, int quantity, ArrayList<Item> requiredItems) {
        super(generateId(), name, quantity);
        this.requiredItems = new HashMap<>();
        for (Item item : requiredItems) {
            this.requiredItems.put(item.getName(), item);
        }
    }

    public Product(int id, String name, int quantity, ArrayList<Item> requiredItems) {
        super(id, name, quantity);
        syncNextId(id);
        this.requiredItems = new HashMap<>();
        for (Item item : requiredItems) {
            this.requiredItems.put(item.getName(), item);
        }
    }


    public int getId() {
        return id;
    }

    public Map<String,Item> getRequiredItems() {
        return requiredItems;
    }

    @Override
    public String toString() {
        return "Product{" +
                "requiredItems=" + requiredItems +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    private static synchronized int generateId() {
        return nextId++;
    }

    private static synchronized void syncNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
}
