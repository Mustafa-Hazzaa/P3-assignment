package Repository;

import Model.ProductLine;
import Util.*;
import java.util.HashMap;

import java.util.Map;

public class ProductLineRepository extends CsvRepository<ProductLine> {

    public ProductLineRepository() {
        super("Data/ProductLines.csv");
    }
    @Override
    protected String getHeader() {
        return "id,name,status";
    }

    @Override
    protected String toCsv(ProductLine productLine) {
        return productLine.getId() + "," +
                productLine.getName()+ ","+
                productLine.getStatus();
    }


    @Override
    protected ProductLine fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 3 )
            throw new IllegalArgumentException("Invalid data");
        return new ProductLine(Integer.parseInt(data[0]), data[1], LineStatus.valueOf(data[2]));
    }
}

