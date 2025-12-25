import Control.LoginController;

import IO.CsvFileReader;
import IO.CsvFileWriter;
import Model.*;
import View.LoginView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        ArrayList<Item> items = csvFileReader.loadItems();
        UserRepository repository = new UserRepository();
        repository.loadFromCsv("Data/Users.csv");
        Inventory inventory = new Inventory();
        inventory.loadItemsCsv("Data/Items.csv");

        UserService model = new UserService(repository);

        LoginView view = new LoginView();

        new LoginController(model, view);


        CsvFileWriter csvFileWriter = new CsvFileWriter();
//        ArrayList<Item> items = csvFileReader.loadItems();
        Model.Item item = new Item("potato",1,100,"food",100);
//        csvFileWriter.addProduct(product,true);
        csvFileWriter.addItem(item,true);
        System.out.println(inventory.getItemById(2));


    }




}
