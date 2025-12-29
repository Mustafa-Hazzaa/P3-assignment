import Model.Inventory;
import Model.Item;
import Model.Product;
import io.ErrorLogger;
import io.TxtFileReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SimulatedClock clock = new SimulatedClock();
        System.out.println(clock.nowFormatted());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clock.nowFormatted());
        clock.shutdown();
//        try {
//            System.out.println(txtFileReader.readLine("Data/Error.txt",2));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Inventory inventory = Inventory.getInstance();
//
//        // ----------------------------
//        // Add new items
//        // ----------------------------
//        inventory.addNewItem("Apple", 50, 10, "Food", 5);
//        inventory.addNewItem("Banana", 30, 8, "Food", 5);
//        inventory.addNewItem("Carrot", 20, 5, "Food", 2);
//
//        System.out.println("After adding items:");
//        inventory.printInventory();
//
//        // ----------------------------
//        // Add a product using some items
//        // ----------------------------
//        ArrayList<Item> productItems = new ArrayList<>();
//        productItems.add(inventory.getItemById(1)); // Apple
//        productItems.add(inventory.getItemById(2)); // Banana
//
//        inventory.addNewProduct("Fruit Salad", 10, productItems);
//
//        System.out.println("After adding product:");
//        inventory.printInventory();
//
//        // ----------------------------
//        // Remove an item
//        // ----------------------------
//        System.out.println("Removing item with ID 2 (Banana)...");
//        inventory.removeItem(2);
//
//        inventory.printInventory();
//
//        // ----------------------------
//        // Remove a product
//        // ----------------------------
//        System.out.println("Removing product with ID 1 (Fruit Salad)...");
//        inventory.removeProduct(1);
//
//        inventory.printInventory();
//
//        // ----------------------------
//        // Add another item after deletion
//        // ----------------------------
//        inventory.addNewItem("Orange", 40, 12, "Food", 3);
//
//        System.out.println("After adding Orange:");
//        inventory.printInventory();
    }
}
