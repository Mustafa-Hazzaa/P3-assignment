package Model;

import java.util.List;

public class Product extends Stockable {
    private List<Item> requiredItems;

    public Product(String name, int id, int quantity, List<Item> requiredItems) {
        super(name, id, quantity);
        this.requiredItems = requiredItems;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }
}
