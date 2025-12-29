package Model;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private static int nextId = 1;

    private final int id;
    private final String name;
    private int quantity;

    // Each item + required amount
    private final Map<Integer, Item> requiredItems;

    public Product(String name, int quantity, Map<Integer, Item> requiredItems) {
        this.id = nextId++;
        this.name = name;
        this.quantity = quantity;
        this.requiredItems = new HashMap<>(requiredItems);
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Item> getRequiredItems() {
        return requiredItems;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", requiredItems=" + requiredItems.values() +
                '}';
    }
}
