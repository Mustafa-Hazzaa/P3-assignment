import java.util.HashMap;
import java.util.Map;

public class UserData {

    //to store admins info
    public class User {
        private String name;
        private String password;
        private String role;

        public User(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }

    private Map<String, User> users = new HashMap<>();

    public UserData() {
        users.put("Mustafa", new User("Mustafa", "1234", "admin"));
        users.put("Masa", new User("Masa", "abcd", "user"));
        users.put("Luna", new User("Luna", "abcd", "user"));
    }

    public User getUser(String name) {
        return users.get(name);
    }

    //check if the entered user value exist
    public boolean authenticate(String username, String password, String role) {
        User user = getUser(username);
        if (user != null) {
            return user.getPassword().equals(password) && user.getRole().equals(role);
        } else {
            return false;
        }

    }


    // Makes sure that the data and ready
    public String validateLoginRequest(String username, String password, String role) {

        //  Empty checks
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty";
        }

        if (role == null) {
            return "Select a role";
        }

        //check if data exists
        boolean isValid = authenticate(username, password, role);
        if (!isValid) {
            return "Invalid username, password, or role";
        }
        return "SUCCESS";
    }
}

