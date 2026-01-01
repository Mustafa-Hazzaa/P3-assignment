import Model.InventoryRepository;
import Model.InventoryService;
import Model.Item;
import Model.Product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            setupInitialFiles();
        } catch (IOException e) {
            System.err.println("Failed to set up initial files. Aborting test.");
            e.printStackTrace();
            return;
        }

        System.out.println("=================================================");
        System.out.println("      INVENTORY MANAGEMENT SYSTEM TEST");
        System.out.println("=================================================\n");

        System.out.println("--- PHASE 1: Populating initial data ---");
        InventoryRepository repo1 = new InventoryRepository();
        InventoryService service1 = new InventoryService(repo1);

        System.out.println("\n[STEP 1.1] Adding new items...");
        service1.addItem("wood", 50, 10, "material", 20);
        service1.addItem("screw", 200, 1, "hardware", 100);
        service1.addItem("paint", 10, 15, "finishing", 5);
        System.out.println("-> Successfully added 3 items.");

        System.out.println("\n[STEP 1.2] Testing item quantity update...");
        System.out.println("-> 'wood' quantity before: " + service1.getItemByName("wood").getQuantity());
        service1.addItem("wood", 15,100,"wood",30);
        System.out.println("-> 'wood' quantity after adding 15: " + service1.getItemByName("wood").getQuantity());

        System.out.println("\n[STEP 1.3] Creating a product with required items and quantities...");
        Map<Item, Integer> chairRequirements = new HashMap<>();
        chairRequirements.put(service1.getItemByName("wood"), 4);
        chairRequirements.put(service1.getItemByName("screw"), 20);
        service1.addProduct("chair", 5, chairRequirements);
        System.out.println("-> Successfully added product 'chair'.");

        System.out.println("\n--- PHASE 1 COMPLETE: Data has been saved to CSV files. ---\n");

        System.out.println("=================================================");
        System.out.println("--- PHASE 2: Testing Persistence (Loading from files) ---");
        InventoryRepository repo2 = new InventoryRepository();
        InventoryService service2 = new InventoryService(repo2);

        System.out.println("\n[STEP 2.1] Verifying re-loaded items...");
        Item reloadedWood = service2.getItemByName("wood");
        if (reloadedWood.getQuantity() != 65) {
            System.err.println("!!! TEST FAILED: Wood quantity is incorrect on reload.");
        } else {
            System.out.println("-> OK: Wood quantity is correct (65).");
        }

        System.out.println("\n[STEP 2.2] Verifying re-loaded product and its recipe...");
        Product reloadedChair = service2.getProductByName("chair");
        if (reloadedChair == null) {
            System.err.println("!!! TEST FAILED: Product 'chair' was not loaded.");
            return;
        }
        System.out.println("-> Re-loaded 'chair': " + reloadedChair.getName() + ", Quantity: " + reloadedChair.getQuantity());

        Map<Item, Integer> reloadedRequirements = reloadedChair.getRequiredItems();
        System.out.println("-> Re-loaded Required Items Map: " + reloadedRequirements);

        int woodRequired = reloadedRequirements.getOrDefault(reloadedWood, -1);
        if (woodRequired == 4) {
            System.out.println("-> OK: Product recipe for wood loaded correctly (4).");
        } else {
            System.err.println("!!! TEST FAILED: Incorrect required quantity for wood (" + woodRequired + ").");
        }

        System.out.println("\n=================================================");
        System.out.println("                  TEST COMPLETE");
        System.out.println("=================================================");
    }

    private static void setupInitialFiles() throws IOException {
        File dataDir = new File("Data");
        if (!dataDir.exists()) dataDir.mkdirs();

        File itemsFile = new File("Data/Items.csv");
        if(itemsFile.exists()) itemsFile.delete();
        itemsFile.createNewFile();
        try (PrintWriter writer = new PrintWriter(itemsFile)) {
            writer.println("id,name,quantity,price,category,minStockLevel");
        }

        File productsFile = new File("Data/Products.csv");
        if(productsFile.exists()) productsFile.delete();
        productsFile.createNewFile();
        try (PrintWriter writer = new PrintWriter(productsFile)) {
            writer.println("id,name,quantity,requiredItems");
        }
        System.out.println("Clean 'Data' directory and CSV files created.");
    }
}
