package Control;

import Service.*;
import Util.SimulatedClock;
import View.*;
import raven.toast.Notifications;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

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
    private SupervisorView supervisorView;

    public AppRouter(InventoryService inventoryService, UserService userService, ProductLineService productLineService, TaskService taskService, ReviewNotesService reviewNotesService) {
        this.inventoryService = inventoryService;
        this.userService = userService;
        this.productLineService = productLineService;
        this.taskService = taskService;
        this.reviewNotesService = reviewNotesService;
        instance = this;
    }

    public void showLoginView() {
        if (hrView != null) hrView.setVisible(false);
        if (managerView != null) managerView.setVisible(false);
        if (supervisorView != null) supervisorView.setVisible(false);

        new Loading(() -> {
            if (loginView == null) {
                loginView = new LoginView();
                new LoginController(userService, loginView, productLineService, taskService, reviewNotesService, this);
            }
            loginView.usernameField.setText("");
            loginView.passwordField.setText("");
            loginView.usernameField.requestFocusInWindow();
            loginView.setVisible(true);
        });
    }

    public void showHRView() {
        new Loading(() -> {
            if (hrView == null) hrView = new HRView();
            hrView.setVisible(true);

            if (loginView != null) loginView.setVisible(false);
            if (managerView != null) managerView.setVisible(false);
            if (supervisorView != null) supervisorView.setVisible(false);

            new HRController(userService, hrView, this);
        });
    }

    public void showManagerView() {
        new Loading(() -> {
            if (managerView == null) managerView = new ManagerView(productLineService);
            new ManagerController(managerView, productLineService, reviewNotesService, taskService, this);

            taskService.setOnMaterialShortage(message -> {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, message);
            });

            managerView.setOnLineAdded(line -> {
                ProductLineWorker.addWorker(line, taskService, inventoryService, clock);
            });

            taskService.checkInitialShortages(productLineService);

            managerView.setVisible(true);

            if (loginView != null) loginView.setVisible(false);
            if (hrView != null) hrView.setVisible(false);
            if (supervisorView != null) supervisorView.setVisible(false);
        });
    }

    public void showSupervisor() {
        new Loading(() -> {
            if (supervisorView == null) supervisorView = new SupervisorView();
            new SupervisorController(inventoryService, supervisorView.rightPanel);
            new ProductController(inventoryService, supervisorView.rightPanel.getProductManagementPanel());

            DashBoard dashPanel = supervisorView.rightPanel.getDashBoardPanel();
            DashBoardController dashController = new DashBoardController(taskService, productLineService, inventoryService, dashPanel);
            dashPanel.setController(dashController);

            supervisorView.setVisible(true);

            if (loginView != null) loginView.setVisible(false);
            if (hrView != null) hrView.setVisible(false);
            if (managerView != null) managerView.setVisible(false);
        });
    }


    public static AppRouter getInstance() {
        return instance;
    }
}
