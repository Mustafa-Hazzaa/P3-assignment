package Service;

import Model.User;
import Repository.UserRepository;
import Util.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final UserRepository userRepository;
    private final Map<String, User> usersByName;
    public UserService() {
        this.userRepository = new UserRepository();
        this.usersByName = new HashMap<>();
        this.loadUsers();
    }
    private void loadUsers() {
        List<User> userList = userRepository.loadAll();
        for (User user : userList) {
            this.usersByName.put(user.getName(), user);
        }
    }

    public User getUser(String username) {
        return usersByName.get(username);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersByName.values());
    }

    public void addUser(User user) {
        if (user != null) {
            usersByName.put(user.getName(), user);
        }
    }

    public void removeUser(String username) {
        usersByName.remove(username);
    }

    public void saveChanges() {
        userRepository.saveAll(new ArrayList<>(usersByName.values()));
    }

    public Role getRole(String username) {
        User user = usersByName.get(username);
        return user != null ? user.getRole() : null;
    }

    public String validateLoginRequest(String username, String password) {
        if (username == null || username.trim().isEmpty()) return "username cannot be empty";
        if (password == null || password.trim().isEmpty()) return "Password cannot be empty";

        User user = usersByName.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            return "Invalid username or password";
        }
        return "SUCCESS";
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{3,6}$");
    }

    public static int passwordCheck(String password) {
        if (password.length() < 6 || password.matches(".*\\s.*")) return 0;
        int score = 1;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/~|\\\\].*")) score++;
        return Math.min(score, 3);
    }

    public String validateInfo(String email,String username, String password, Role role) {
        if (username == null || username.trim().isEmpty()) {return "Username cannot be empty";}
        if (!isValidEmail(email)) {return "Invalid email format";}
        if (password == null || password.trim().isEmpty()) {return "Password cannot be empty";}
        if (passwordCheck(password) <= 1) {return "Password is too weak";}
        if (role == null) { return "Select a role"; }
        return "SUCCESS";
    }

}


