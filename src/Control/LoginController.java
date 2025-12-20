package Control;

import Model.UserData;
import View.LoginView;

import View.HRView;

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
                view.SupervisorButton.isSelected() ? "Supervisor" :
                view.HRButton.isSelected() ? "HR": null;

        String status = model.validateLoginRequest(username, password, role);

        if (status.equals("SUCCESS"))
        {
            if(role.equals("HR"))
            {
                new HRView();
                view.setVisible(false);

            }
        }
            else{
            JOptionPane.showMessageDialog(view, status);
        }


    }


}
