package Model;

public class Item extends Stockable {

    private static int nextId = 1;

    private  double price;
    private final String category;
    private  int minStockLevel;


    public Item(String name, int quantity, double price, String category, int minStockLevel) {
        super(generateId(), name, quantity);
        this.price = price;
        this.category = category;
        this.minStockLevel = minStockLevel;
    }

    public Item(int id, String name, int quantity,double price, String category, int minStockLevel) {
        super(id, name, quantity);
        syncNextId(id);
        this.price = price;
        this.category = category;
        this.minStockLevel = minStockLevel;
    }

    public double getPrice() {
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
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
