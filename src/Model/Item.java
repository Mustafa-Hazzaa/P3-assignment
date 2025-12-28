package Model;

public class Item extends Stockable {

    private static int nextId = 1;

    private final int price;
    private final String category;
    private final int minStockLevel;


    public Item(String name, int quantity, int price, String category, int minStockLevel) {
        super(generateId(), name, quantity);
        this.price = price;
        this.category = category;
        this.minStockLevel = minStockLevel;
    }

    public Item(int id, String name, int quantity,int price, String category, int minStockLevel) {
        super(id, name, quantity);
        syncNextId(id);
        this.price = price;
        this.category = category;
        this.minStockLevel = minStockLevel;
    }

    public int getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }
    public int getMinStockLevel() {
        return minStockLevel;
    }



    private static synchronized int generateId() {
        return nextId++;
    }

    private static synchronized void syncNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", minStockLevel=" + minStockLevel +
                '}';
    }
}
