package Model;

public abstract class Stockable {

    protected final int id;
    protected String name;
    protected int quantity;

    protected Stockable(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getQuantity() {return quantity;}
}
