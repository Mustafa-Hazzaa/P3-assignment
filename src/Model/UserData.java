package Model;

import IO.CsvFileReader;
import IO.CsvFileWriter;
import IO.TxtFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {


    private Map<String, User> users = new HashMap<>();

    public UserData() {
        CsvFileReader csvFileReader = new CsvFileReader();
        try {
            List<User> allUsers = csvFileReader.loadUsers();
            for (User user:allUsers)
            {
                users.put(user.getName(),user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUser(String name) { return users.get(name); }

    public boolean authenticate(String username, String password, String role) {
        User user = getUser(username);
        return user != null && user.getPassword().equals(password) && user.getRole().equals(role);
    }

    public String validateLoginRequest(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) return "Username cannot be empty";
        if (!username.contains("@gmail")) {
            return "invalid username format (must contain '@gmail')";
        }
        if (password == null || password.trim().isEmpty()) return "Password cannot be empty";
        if (role == null) return "Select a role";

        return authenticate(username, password, role) ? "SUCCESS" : "Invalid username, password, or role";
    }

    public String validateInfo(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) return "Username cannot be empty";
        if (!username.contains("@gmail")) {
            return "invalid username format (must contain '@gmail')";
        }
        if (password == null || password.trim().isEmpty()) return "Password cannot be empty";
        if (role == null) return "Select a role";

        return "SUCCESS";
    }

    public void updateUsers(){
        CsvFileReader csvFileReader = new CsvFileReader();
        try {
            List<User> allUsers = csvFileReader.loadUsers();
            for (User user:allUsers)
            {
                users.put(user.getName(),user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUsers(String usernameToRemove) {
        // Remove user from map
        users.remove(usernameToRemove);

        CsvFileWriter csvFileWriter = new CsvFileWriter();
        TxtFileWriter txtFileWriter = new TxtFileWriter();

        try {
            // Write header first
            txtFileWriter.writeLine("Data/Users.csv", "username,password,role", false);

            for (User temp : users.values()) {
                csvFileWriter.addUser(temp.getName(), temp.getPassword(), temp.getRole(), true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
