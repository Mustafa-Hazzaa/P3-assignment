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
        view.signInButton.addActionListener(e -> handleSignIn());
        view.signUpButton.addActionListener(e -> handleSignUp());
    }

    private void handleSignIn() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());
        String role = view.MangerButton.isSelected() ? "Manager" :
                view.SupervisorButton.isSelected() ? "Supervisor" : null;

        String status = model.validateLoginRequest(username, password, role);
        JOptionPane.showMessageDialog(view, status.equals("SUCCESS") ?
                "Login successful!" : status);
    }

    private void handleSignUp() {


    }
}
