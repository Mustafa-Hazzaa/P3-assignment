package Service;

import Model.Item;
import Model.Product;
import Model.Task;
import Repository.ItemRepository;
import Repository.ProductRepository;
import io.ErrorLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final Map<String, Item> itemsByName;
    private final Map<String, Product> productsByName;
     final Map<String, Integer> reservedItems;

    private final ErrorLogger logger = new ErrorLogger();

    public InventoryService() {
        this.itemRepository = new ItemRepository();
        this.itemsByName = new HashMap<>();
        loadItemsFromFile();
        this.productRepository = new ProductRepository(itemsByName);
        this.productsByName = new HashMap<>();
        loadProductsFromFile();
        this.reservedItems = new HashMap<>();

    }

    private void loadData(){
        loadItemsFromFile();
        loadProductsFromFile();
    }

    private void loadItemsFromFile() {
        List<Item> itemList = itemRepository.loadAll();
        for (Item item : itemList) {
            itemsByName.put(item.getName().toLowerCase(), item);
        }
    }
    private void loadProductsFromFile() {
        List<Product> productList = productRepository.loadAll();
        for (Product product : productList) {
            productsByName.put(product.getName().toLowerCase(), product);
        }
    }

    public Item getItemByName(String name) {
        return itemsByName.get(name.toLowerCase());
    }

    public Item getItemById(int id) {
        for (Item item:itemsByName.values()){
            if(item.getId() == id)
                return item;
        }
        return null;
    }

    public void addItem(String name, int quantity, int price, String category, int minStock) {
        String lowerCaseName = name.toLowerCase();

        if (itemsByName.containsKey(lowerCaseName)) {
                updateItemQuantity(name,quantity);
        } else {
            Item newItem = new Item(name, quantity, price, category, minStock);
            itemsByName.put(lowerCaseName, newItem);
        }
    }

    public void removeItem(String name) {
        Item removedItem = itemsByName.remove(name.toLowerCase());
    }

    public Product getProductByName(String name) {
        return productsByName.get(name.toLowerCase());
    }

    public Product getProductById(int id) {
        for (Product product:productsByName.values()){
            if(product.getId() == id)
                return product;
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productsByName.values());
    }

    public void addProduct(String name, int quantity, Map<String, Integer> requiredItems) {
        String lowerCaseName = name.toLowerCase();
        if (productsByName.containsKey(lowerCaseName)) {
            // If product exists, add to the quantity
            updateProductQuantity(name , quantity);
        } else {
            // Otherwise, create a new one
            Product newProduct = new Product(name, quantity, requiredItems);
            productsByName.put(lowerCaseName, newProduct);
        }
    }

    public void removeProduct(String name) {
        Product removedProduct = productsByName.remove(name.toLowerCase());
        }


    public synchronized  boolean checkStock(Task task) {
        Map<String, Integer> requirements = getProductByName(task.getProductName()).getRequiredItems();
        for (Map.Entry<String, Integer> requirement : requirements.entrySet()) {
            String itemName = requirement.getKey();
            int requiredQuantity = requirement.getValue() * task.getQuantity();
            int reservedQuantity = reservedItems.getOrDefault(itemName, 0);
            Item item = getItemByName(itemName);
            if (item == null) {
                logger.log("Item not found: " + itemName);
                return false;
            }
            int available = getItemByName(itemName).getQuantity() - reservedQuantity;

            if (available < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean addReservedItem(Task task) {
        if (checkStock(task)) {
            Map<String, Integer> requirements = getProductByName(task.getProductName()).getRequiredItems();
            for (Map.Entry<String, Integer> requirement : requirements.entrySet()) {
                String itemName = requirement.getKey();
                int requiredForThisTask = requirement.getValue() * task.getQuantity();
                int reservedQuantity = reservedItems.getOrDefault(itemName, 0);
                reservedItems.put(itemName, reservedQuantity + requiredForThisTask);
            }
            return true;
        }
        return false;
    }



    public void updateProductQuantity(String name, int quantity) {
        Product product = getProductByName(name);
        if(product != null) {
            product.setQuantity(product.getQuantity() + quantity);
        }
    }

    public void updateItemQuantity(String name, int quantity) {
        Item item = getItemByName(name);
        if(item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    public synchronized  void consumeReserved(String itemName, int amount) {
        reservedItems.put(itemName, reservedItems.get(itemName) - amount);
        getItemByName(itemName).setQuantity(getItemByName(itemName).getQuantity() - amount);
    }

    public void saveChanges() {
        System.out.println("InventoryService: Saving changes...");
        itemRepository.saveAll(new ArrayList<>(itemsByName.values()));
        productRepository.saveAll(new ArrayList<>(productsByName.values()));
    }
}
