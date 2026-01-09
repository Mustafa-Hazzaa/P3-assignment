package Control;

import Service.*;
import View.LoginView;

import View.HRView;
import View.ManagerView;

import javax.swing.*;

public class LoginController {

    private UserService model;
    private LoginView view;
    private final ProductLineService productLineService;
    private final TaskService taskService;
    private final ReviewNotesService reviewNotesService;


    public LoginController(UserService model, LoginView view, ProductLineService productLineService, TaskService taskService, ReviewNotesService reviewNotesService) {
        this.model = model;
        this.view = view;
        this.productLineService = productLineService;
        this.taskService = taskService;
        this.reviewNotesService = reviewNotesService;
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
            switch (model.getRole(username)) {
                case HR -> {
                    view.setVisible(false);
                    new HRController(model, new HRView());
                }
                case MANAGER -> {
                    ManagerView view = new ManagerView(productLineService);
                    view.setVisible(true);

                    ManagerController controller = new ManagerController(view, productLineService,reviewNotesService,taskService);
                }
                case SUPERVISOR-> System.out.println("He is a Supervisor ");
            }
            view.setVisible(false);
        }else {
            JOptionPane.showMessageDialog(view,status,"Invaild", JOptionPane.ERROR_MESSAGE);
        }


    }


}
