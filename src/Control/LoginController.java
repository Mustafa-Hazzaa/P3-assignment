package Control;

import Model.UserService;
import View.LoginView;

import View.HRView;

import javax.swing.*;

public class LoginController {

    private UserService model;
    private LoginView view;

    public LoginController(UserService model, LoginView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.signInButton.addActionListener(e -> handleSignIn());
        view.usernameField.addActionListener(e ->   view.usernameField.transferFocus());;
    }

    private void handleSignIn() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        String status = model.validateLoginRequest(username, password);
        if (status.equals("SUCCESS")) {
           String role = model.getRole(username);
            switch (role) {
                case "HR" -> {
                    view.setVisible(false);
                    HRView hrView = new HRView();
                    UserService hrModel = model;
                    new HRController(hrModel, hrView);
                }
                case "Manager" -> System.out.println("He is a Manager");
                case "Supervisor"-> System.out.println("He is a Supervisor ");
            }
            view.setVisible(false);
        }else {
            JOptionPane.showMessageDialog(view,status,"Invaild", JOptionPane.ERROR_MESSAGE);
        }


    }


}
