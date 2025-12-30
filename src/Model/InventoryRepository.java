package Model;

import io.CsvFileReader;
import io.CsvFileWriter;
import io.TxtFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



    public class InventoryRepository {

        private final Map<String, Item> itemsByName = new HashMap<>();
        private final Map<String, Product> productsByName = new HashMap<>();

        private final CsvFileWriter writer = new CsvFileWriter();
        private final TxtFileWriter txtWriter = new TxtFileWriter();
        private final CsvFileReader reader = new CsvFileReader();

        public InventoryRepository() {

            for (Item item : reader.loadItems()) {
                itemsByName.put(item.getName(), item);
            }

            for (Product product : reader.loadProducts(itemsByName)) {
                productsByName.put(product.getName().toLowerCase(), product);
            }
        }

        public Map<String, Item> getItems() {
            return itemsByName;
        }

        public Map<String, Product> getProducts() {
            return productsByName;
        }


    public void saveItem(Item item) {
        writer.addItem(item, true);
    }

    public void saveProduct(Product product) {
        writer.addProduct(product, true);
    }

    public void rewriteItems() {
        try {
            txtWriter.writeLine(
                    "Data/Items.csv",
                    "id,name,quantity,price,category,minStockLevel",
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Item item : itemsByName.values()) {
            writer.addItem(item, true);
        }
    }

    public void rewriteProducts() {
        try {
            txtWriter.writeLine(
                    "Data/Products.csv",
                    "id,name,quantity,requiredItems",
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Product product : productsByName.values()) {
            writer.addProduct(product, true);
        }
    }
}
