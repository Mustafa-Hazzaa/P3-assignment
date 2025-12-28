package Model;

import io.CsvFileReader;
import io.CsvFileWriter;
import io.TxtFileWriter;

import java.io.IOException;
import java.util.ArrayList;

public class Inventory {
    private static volatile Inventory instance;
    private  ArrayList<Item> items = new ArrayList<>();
    private  ArrayList<Product> products = new ArrayList<>();
    private final CsvFileWriter writer = new CsvFileWriter();
    private final TxtFileWriter txtWriter = new TxtFileWriter();
    private final CsvFileReader reader = new CsvFileReader();
    private final CsvFileWriter Writer = new CsvFileWriter();

    private Inventory() {
        items.addAll(reader.loadItems("Data/Items.csv")); //loading all items in csv file
        products.addAll(reader.loadProducts("Data/Products.csv" , this)); //load all products in csv file
    }

    public static Inventory getInstance(){
            Inventory result = instance;
            if (result == null) {
               synchronized (Inventory.class){
                   result = instance;
                   if (result == null){
                       instance = result = new Inventory();
                   }
               }
            }
        return result;
    }


//    public void saveItems() {
//        try {
//            txtWriter.writeLine("Data/items.csv", "id,name,quantity,price,category,minStockLevel", false); // overwrite header
//            for (Item item : items) {
//                writer.addItem(item, true); // append each line
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public void addNewItem(String name, int quantity, int price, String category, int minStockLevel) {
        Item newItem = new Item(name, quantity, price, category, minStockLevel);
        items.add(newItem);
        writer.addItem(newItem,true);

    }

    public void addNewProduct(String name, int quantity, ArrayList<Item> items) {
        Product newProduct = new Product(name, quantity, items);
        products.add(newProduct);
        writer.addProduct(newProduct, true);
    }


    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    public void removeItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                return;
            }
        }
        try {txtWriter.writeLine("Data/items.csv","id,name,quantity,price,category,minStockLevel",false);}
        catch (IOException e) {throw new RuntimeException(e);}
        for (Item item : items) {
            writer.addItem(item,true);
        }
    }

    public void removeProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return;
            }
            try {
                txtWriter.writeLine("Data/items.csv","id,name,quantity,requiredItems",false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Product product : products) {
                writer.addProduct(product,true);
            }
        }
        }


    public void printInventory() {
        System.out.println("--- Current Items ---");
        for(Item item : items) {
            System.out.println(item);
        }
        System.out.println("\n--- Current Products ---");
        for(Product product : products) {
            System.out.println(product);
        }
        System.out.println("-------------------------------------\n");
    }
}
