package Model;

public class Stockable {
    private String name;
    private int id;
    private int quantity;

    public Stockable(String name, int id, int quantity) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
