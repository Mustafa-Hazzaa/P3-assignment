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
    }

    private void handleSignIn() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());
        String role = view.ManagerButton.isSelected() ? "Manager" :
                view.SupervisorButton.isSelected() ? "Supervisor" : null;

        String status = model.validateLoginRequest(username, password, role);

        if (status.equals("SUCCESS"))
        {
            JOptionPane.showMessageDialog(view, status);
        }
            else{
            JOptionPane.showMessageDialog(view, status);
        }


    }


}
