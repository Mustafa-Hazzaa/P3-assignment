import java.util.HashMap;
import java.util.Map;

public class UserData {

    //to store admins info
    public static class User {
        String name;
        String password;
        String role;

        public User(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(String role) {
            this.role = role;
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
    public boolean validateLogin(User entered_User){
        User user = getUser(entered_User.name);
        if (user!=null){
            return user.password.equals(entered_User.password) && user.role.equals(entered_User.role);
        }
        else {
            return false;
        }

    }


}
