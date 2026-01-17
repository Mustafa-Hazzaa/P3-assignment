package Control;

import Service.*;
import View.HRView;
import View.LoginView;
import View.ManagerView;

public class AppRouter {


    private final UserService userService;
    private final ProductLineService productLineService;
    private final TaskService taskService;
    private final ReviewNotesService reviewNotesService;
    private static AppRouter instance;


    private LoginView loginView;
    private HRView hrView;
    private ManagerView managerView;

    public AppRouter(UserService userService, ProductLineService productLineService, TaskService taskService, ReviewNotesService reviewNotesService) {
        this.userService = userService;
        this.productLineService = productLineService;
        this.taskService = taskService;
        this.reviewNotesService = reviewNotesService;
        instance = this;
    }

    public void showLoginView() {
        if (loginView == null) {
            loginView = new LoginView();
            loginView.setVisible(true);
            new LoginController(userService, loginView, productLineService, taskService, reviewNotesService, this);
        }
        loginView.usernameField.setText("");
        loginView.passwordField.setText("");
        loginView.usernameField.requestFocusInWindow();


        loginView.setVisible(true);

        if (hrView != null) hrView.setVisible(false);
        if (managerView != null) managerView.setVisible(false);
    }


    public void showHRView() {
        if (hrView == null) hrView = new HRView();
        hrView.setVisible(true);

        if (loginView != null) loginView.setVisible(false);

        new HRController(userService, hrView,this);
    }

    public void showManagerView() {
        if (managerView == null) managerView = new ManagerView(productLineService);
        managerView.setVisible(true);

        if (loginView != null) loginView.setVisible(false);

        new ManagerController(managerView, productLineService, reviewNotesService, taskService,this);
    }

    public static AppRouter getInstance() {
        return instance;
    }
}
