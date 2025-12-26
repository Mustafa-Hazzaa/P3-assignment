package Model;

import java.util.List;
import java.util.Stack;

public class Product extends Stockable {
    static int ID=0;
    private List<Item> requiredItems;

    public Product( int id,String name, int quantity, List<Item> requiredItems) {
        id++;
        super(name, id, quantity);
        this.requiredItems = requiredItems;
    }

    public Product(String name,int quantity, List<Item> requiredItems) {
        ID++;
        int id =ID;
        super(name, id, quantity);
        this.requiredItems = requiredItems;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }
}
