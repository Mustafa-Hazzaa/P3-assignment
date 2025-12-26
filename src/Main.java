import Control.LoginController;

import io.CsvFileReader;
import io.CsvFileWriter;
import Model.*;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        CsvFileReader csvFileReader = new CsvFileReader();
        UserRepository repository = new UserRepository();
        repository.loadFromCsv("Data/Users.csv");
        Inventory inventory = new Inventory();
        inventory.loadItemsCsv("Data/Items.csv");
        inventory.loadProductsCsv("Data/Products.csv");
//
        UserService model = new UserService(repository);

        LoginView view = new LoginView();

        new LoginController(model, view);


        CsvFileWriter csvFileWriter = new CsvFileWriter();
        Item item = new Item("potato",1,100,"food",100);
        Item item2 = new Item("potato",2,100,"food",100);
        csvFileWriter.addItem(item,true);
        csvFileWriter.addItem(item2,true);

        Product product = new Product("HotChips",15,items);
        csvFileWriter.addProduct(product,true);

//        csvFileWriter.addItem(item3,true);
//        csvFileWriter.addItem(item4,true);
//
//        System.out.println(inventory.getItemById(2));


    }




}
