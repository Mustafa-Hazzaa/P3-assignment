package Repository;
import Model.*;


public class ItemRepository extends CsvRepository<Item>{

    public ItemRepository() {
        super("Data/Items.csv");
    }

    @Override
    protected String getHeader() {
        return "id,name,quantity,price,category,minStockLevel";
    }

    @Override
    protected String toCsv(Item item) {
        return  item.getId() + "," +
                item.getName() + "," +
                item.getQuantity() + "," +
                item.getPrice() + "," +
                item.getCategory() + "," +
                item.getMinStockLevel();
    }

    @Override
    protected Item fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 6) {
            throw new IllegalArgumentException("Invalid data");
        }
        return (new Item(
                Integer.parseInt(data[0]),
                data[1],
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                data[4],
                Integer.parseInt(data[5])));
    }
}
