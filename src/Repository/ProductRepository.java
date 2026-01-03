package Repository;
import Model.Item;
import Model.Product;
import io.ErrorLogger;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository extends CsvRepository<Product> {
    private Map<String, Item> items;
    private final ErrorLogger logger   = new ErrorLogger();
    public ProductRepository(Map<String, Item> items) {
        super("Data/Products.csv");
        this.items =items;
    }


    @Override
    protected String getHeader() {
        return "id,name,quantity,requiredItems";
    }

    @Override
    protected String toCsv(Product product) {
        Map<String, Integer> requiredItems = product.getRequiredItems();
        StringBuilder requirementsBuilder = new StringBuilder();

        int count = 0;
        for (Map.Entry<String, Integer> entry : requiredItems.entrySet()) {
            requirementsBuilder.append(entry.getKey());
            requirementsBuilder.append(":");
            requirementsBuilder.append(entry.getValue());
            if (++count < requiredItems.size()) {
                requirementsBuilder.append(";");
            }
        }
        return product.getId() + "," +
                product.getName() + "," +
                product.getQuantity() + ","+
                requirementsBuilder;
    }

    @Override
    protected Product fromCsv(String csvLine) {
            String[] data = csvLine.split(",");
           if (data.length != 4) {
            logger.log("Invalid data");
           }
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            int quantity = Integer.parseInt(data[2]);

            Map<String, Integer> requiredItemsMap = new HashMap<>();
            String[] requirementsPairs = data[3].split(";");

            for (String pair : requirementsPairs) {
                if (pair.isBlank()) continue;

                String[] itemAndQuantity = pair.split(":");
                String itemName = itemAndQuantity[0].toLowerCase();
                int itemQuantity = Integer.parseInt(itemAndQuantity[1]);

                requiredItemsMap.put(itemName, itemQuantity);

            }
            return new Product(id,name,quantity,requiredItemsMap);
    }
}
