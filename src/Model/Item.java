package Model;

public class Item extends Stockable {

    private final int price;
    private final String category;
    private final int minStockLevel;


    public Item(int id, String name, int quantity, int price, String category, int minStockLevel) {
        super(name, id, quantity);
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
