import Control.LoginController;
import IO.CsvFileReader;
import IO.CsvFileWriter;
import Model.Item;
import Model.ReviewNotes;
import Model.UserRepository;
import Model.UserService;
import View.LoginView;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        UserRepository repository = new UserRepository();
//        repository.loadFromCsv("Data/Users.csv");
//
//        UserService model = new UserService(repository);
//
//        LoginView view = new LoginView();
//
//        new LoginController(model, view);
//    }
        CsvFileWriter csvFileWriter = new CsvFileWriter();
        Item item = new Item("potato",100,100,"food",50);
        csvFileWriter.addItem(item,true);

//        CsvFileReader csvFileReader = new CsvFileReader();
//        HashMap<String, ReviewNotes> hashMap;
//
//            hashMap = csvFileReader.loadReviewAndNotes("Data/ReviewAndNotes.csv");
//
//        ReviewNotes result = hashMap.get("hot");
//        System.out.println("Review: " + result.getReview());
//        System.out.println("Notes: " + result.getNotes());
    }
}
