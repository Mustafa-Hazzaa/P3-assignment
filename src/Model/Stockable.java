package Model;

public abstract class Stockable {

    protected final int id;
    protected final String name;
    protected int quantity;

    protected Stockable(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
