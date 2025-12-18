import javax.swing.*;

public class LoginController {

    private UserData model;
    private LoginView view;

    public LoginController(UserData model, LoginView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.submitButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());
        String role = view.adminRadio.isSelected() ? "admin" :
                view.userRadio.isSelected() ? "user" : null;

        String status = model.validateLoginRequest(username, password, role);
        JOptionPane.showMessageDialog(view, status.equals("SUCCESS") ? "Login successful!" : status);
    }
}
