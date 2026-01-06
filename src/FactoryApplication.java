import Control.ManagerController;
import Service.*;
import Util.SimulatedClock;
import Util.ShutdownManager;
import View.LoginView;
import View.ManagerView;

import javax.swing.*;

public class FactoryApplication {
//
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {

            System.out.println("Application starting... Initializing all services.");
            SimulatedClock clock = new SimulatedClock();


            UserService userService = new UserService();
            InventoryService inventoryService = new InventoryService();
            ReviewNotesService reviewNotesService = new ReviewNotesService();
            TaskService taskService = new TaskService(clock,inventoryService);
            ProductLineService productLineService = new ProductLineService();

            System.out.println("All data services initialized.");


            productLineService.assignTasksToLines(taskService);
            System.out.println("Pending tasks have been assigned to their production lines.");
            ProductLineWorker.startAllWorkers(
                    productLineService,
                    taskService,
                    inventoryService,
                    clock
            );
//
            ShutdownManager.registerShutdownHook(
                    userService,
                    inventoryService,
                    reviewNotesService,
                    taskService,
                    productLineService
            );
            System.out.println("Shutdown manager is ready.");

            ManagerView view = new ManagerView();
            ManagerController controller = new ManagerController(view, productLineService);


            System.out.println("Application initialized successfully. Login view is now visible.");
        });

    }
}
