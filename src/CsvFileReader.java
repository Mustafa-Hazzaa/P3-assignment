import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            String password = data[1];
            String role = data[2];
            users.add(new User(username,password,role));
        }
        return users;
    }
}
