package Model;

import java.util.List;

public class Product extends Stockable {


    private final List<Item> requiredItems;

    public Product(int id, String name, int quantity, List<Item> requiredItems) {
        super(name, id, quantity);
        this.requiredItems = requiredItems;
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
