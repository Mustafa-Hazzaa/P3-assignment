package IO;

import Model.ReviewNotes;
import Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFileReader {
    private TxtFileReader txtFileReader;

    public CsvFileReader() {
        this.txtFileReader = new TxtFileReader();
    }

    public List<User> loadUsers() throws IOException {
        return loadUsers("Data/Users.csv");
    }

    public List<User> loadUsers(String filePath) throws IOException {
        List<String> lines = txtFileReader.readLines(filePath);
        List<User> users = new ArrayList<>();

        lines.remove(0);
        for (String line:lines){

            String[] data = line.split(",");


            String username = data[0];
            String email = data[1];
            String password = data[2];
            String role = data[3];
            users.add(new User(username,email,password,role));
        }
        return users;
    }

    public HashMap<String, ReviewNotes> loadReviewAndNotes(String filePath)  {
        List<String> lines;
        ReviewNotes reviewNotes;
        HashMap<String,ReviewNotes> reviewAndNotes = new HashMap<>();

        try {
            lines = txtFileReader.readLines(filePath);
            lines.removeFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line:lines){

            String[] data = line.split(",");

            String productLine = parseCsvField(data[0]);
            String review      = parseCsvField(data[1]);
            String notes       = parseCsvField(data[2]);


            reviewNotes = new ReviewNotes(review,notes);
            reviewAndNotes.put(productLine,reviewNotes);
        }
        return  reviewAndNotes;
    }

    private String parseCsvField(String field) {
        field = field.trim();

        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1);
        }

        return field.replace("\"\"", "\"");
    }

}
