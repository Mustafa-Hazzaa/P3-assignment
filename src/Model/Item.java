package Model;

public class Item extends Stockable{
    private static int Id=0;
    private int id;
    private int price;
    private String category;
    private int minStockLevel;


    public Item(String name ,int quantity, int price, String category, int minStockLevel) {
        super(name, ++Id, quantity);
        this.price = price;
        this.category = category;
        this.minStockLevel = minStockLevel;
    }

    public Item( int id,String name, int quantity, int price, String category, int minStockLevel) {
        super(name, id, quantity);
        Id++;
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
                ", price=" + price +
                ", category='" + category + '\'' +
                ", minStockLevel=" + minStockLevel +
                '}';
    }
}
