package Model;

import java.util.List;

public class Product extends Stockable {
    private static int nextId=1;

    private final List<Item> requiredItems;

    public Product(String name, int quantity, List<Item> requiredItems) {
        super(generateId(), name, quantity);
        this.requiredItems = requiredItems;
    }


    public Product(int id, String name, int quantity, List<Item> requiredItems) {
        super(id, name, quantity);
        syncNextId(id);
        this.requiredItems = requiredItems;
    }

    private static synchronized int generateId() {
        return nextId++;
    }

    private static synchronized void syncNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", requiredItemsCount=" + (requiredItems != null ? requiredItems.size() : 0) +
                '}';
    }
}
