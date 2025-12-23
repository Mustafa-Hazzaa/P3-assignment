package Model;

import IO.CsvFileReader;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> items =new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();

    public void loadItemsCsv(String filePath) {
        CsvFileReader csvReader = new CsvFileReader();
        items.addAll(csvReader.loadItems(filePath));
    }

    public void loadProductsCsv(String filePath) {
        CsvFileReader csvReader = new CsvFileReader();
        products.addAll(csvReader.loadProducts(filePath));
    }

    public Item getItemById(int id){
        for (Item item:items){
            if (item.getId() == id)
                return item;
        }
        return null;
    }
}