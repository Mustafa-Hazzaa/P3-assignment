package Model;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String validateLoginRequest(String username, String password) {
        if (username == null || username.trim().isEmpty()) return "Email cannot be empty";
        if (password == null || password.trim().isEmpty()) return "Password cannot be empty";

        User user = repository.getUser(username);
        if (user == null || !user.getPassword().equals(password)) {
            return "Invalid username or password";
        }
        return "SUCCESS";
    }


    public void addUser(String name, String email, String password, String role) {
        User user = new User(name, email, password, role);
        repository.addUser(user);
    }

    public void removeUser(String username) {
        repository.removeUser(username);
    }

    public String getRole(String username) {
        User user = repository.getUser(username);
        return user != null ? user.getRole() : null;
    }

    public User getUser(String username) {
        return repository.getUser(username);
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

    public String validateInfo(String email,String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {return "Username cannot be empty";}
        if (!isValidEmail(email)) {return "Invalid email format";}
        if (password == null || password.trim().isEmpty()) {return "Password cannot be empty";}
        if (passwordCheck(password) <= 1) {return "Password is too weak";}
        if (role == null || role.trim().isEmpty()) {return "Select a role";}
        return "SUCCESS";
    }

    public java.util.Collection<User> getAllUsers() {
        return repository.getAllUsers();
    }
}
