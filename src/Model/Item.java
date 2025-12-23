package Model;

public class Item extends Stockable{
    private static int id;
    private int price;
    private String category;
    private int minStockLevel;


    public Item(String name, int quantity, int price, String category, int minStockLevel) {
        super(name, ++id, quantity);
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
}
