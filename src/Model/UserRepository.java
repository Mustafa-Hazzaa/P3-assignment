package Model;

import IO.CsvFileReader;
import IO.CsvFileWriter;
import IO.TxtFileWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public void loadFromCsv(String filePath) {
        CsvFileReader csvReader = new CsvFileReader();
        try {
            for (User user : csvReader.loadUsers(filePath)) {
                users.put(user.getName(), user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load users", e);
        }
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User username) {
        users.put(username.getName(), username);
        saveAll("Data/Users.csv");
    }

    public void removeUser(String username) {
        users.remove(username);
        saveAll("Data/Users.csv");
    }

    private void saveAll(String filePath) {
        CsvFileWriter csvWriter = new CsvFileWriter();
        TxtFileWriter txtWriter = new TxtFileWriter();
        try {
            txtWriter.writeLine(filePath, "username,email,password,role", false);
            for (User user : users.values()) {
                csvWriter.addUser(user.getName(),user.getEmail(), user.getPassword(), user.getRole(), true);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users", e);
        }
    }
}
