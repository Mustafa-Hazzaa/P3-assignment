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

        UserService model = new UserService(repository);

        LoginView view = new LoginView();

        new LoginController(model, view);


        CsvFileWriter csvFileWriter = new CsvFileWriter();
//        ArrayList<Item> items = csvFileReader.loadItems();
//        Product product = new Product("hotChips",1,100,items);
//        csvFileWriter.addProduct(product,true);
        System.out.println(inventory.getItemById(4));


    }




}
