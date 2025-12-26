package Model;

public class Stockable {
    private final String name;
    private final int id;
    private int quantity;

    public Stockable(String name, int id, int quantity) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
    }

    // getters
    public String getName() { return name; }
    public int getId() { return id; }
    public int getQuantity() { return quantity; }

    public boolean setQuantity(int quantity) {
        if (quantity < 0) return false;
        this.quantity = quantity;
        return true;
    }
}
