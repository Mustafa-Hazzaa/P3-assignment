package Control;

import Service.*;
import Util.SimulatedClock;
import View.HRView;
import View.LoginView;
import View.ManagerView;
import raven.toast.Notifications;
import com.formdev.flatlaf.FlatLightLaf;

public class AppRouter {

    private final InventoryService inventoryService;
    private final UserService userService;
    private final ProductLineService productLineService;
    private final TaskService taskService;
    private final ReviewNotesService reviewNotesService;
    private static AppRouter instance;
    SimulatedClock clock = SimulatedClock.getInstance();


    private LoginView loginView;
    private HRView hrView;
    private ManagerView managerView;

    public AppRouter(InventoryService inventoryService, UserService userService, ProductLineService productLineService, TaskService taskService, ReviewNotesService reviewNotesService) {
        this.inventoryService = inventoryService;
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
        new ManagerController(managerView, productLineService, reviewNotesService, taskService,this);
        taskService.setOnMaterialShortage(message -> {
            Notifications.getInstance().show(Notifications.Type.WARNING,Notifications.Location.BOTTOM_LEFT, message);
        });
        managerView.setOnLineAdded(line -> {
            ProductLineWorker.addWorker(line, taskService, inventoryService, clock);
        });
        taskService.checkInitialShortages(productLineService);

        managerView.setVisible(true);
        if (loginView != null) loginView.setVisible(false);
    }

    public static AppRouter getInstance() {
        return instance;
    }
}
