package Model;

import java.util.Map;

public class InventoryService {

    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    public void addItem(String name, int quantity, int price, String category, int minStockLevel) {
        name = name.toLowerCase();
        Map<String, Item> items = repo.getItems();

        if (items.containsKey(name)) {
            Item existing = items.get(name);
            existing.setQuantity(existing.getQuantity() + quantity);
            repo.rewriteItems();
            return;
        }

        Item item = new Item(name, quantity, price, category, minStockLevel);
        items.put(name, item);
        repo.saveItem(item);
    }

    public Item getItemByName(String name) {
        return repo.getItems().get(name.toLowerCase());

    }

    public Item getItemById(int id) {
        for (Item item : repo.getItems().values()) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    public void removeItem(String name) {
        if (repo.getItems().remove(name) == null) return;
        repo.rewriteItems();
    }

    public void addProduct(String name, int quantity, Map<Item, Integer> requiredItems) {
        name = name.toLowerCase();
        Map<String, Product> products = repo.getProducts();

        if (products.containsKey(name)) {
            Product existingProduct = products.get(name);
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            repo.rewriteProducts();
            return;
        }

        Product product = new Product(name, quantity, requiredItems);
        products.put(name, product);
        repo.saveProduct(product);
    }




    public Product getProductByName(String name) {
        return repo.getProducts().get(name);
    }

    public Product getProductById(int id) {
        for (Product p : repo.getProducts().values()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void removeProduct(String name) {
        if (repo.getProducts().remove(name) == null) return;
        repo.rewriteProducts();
    }
}
