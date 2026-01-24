
import Control.AppRouter;
import Service.*;
import Util.SimulatedClock;
import Util.ShutdownManager;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class FactoryApplication {
//
    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("Toast.frameInsets", new Insets(10, 270, 35, 100));
        UIManager.put("Toast.duration", 5000);
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("ProgressBar.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        SwingUtilities.invokeLater(() -> {

            System.out.println("Application starting... Initializing all services.");
            SimulatedClock clock = SimulatedClock.getInstance();


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

            ShutdownManager.registerShutdownHook(
                    userService,
                    inventoryService,
                    reviewNotesService,
                    taskService,
                    productLineService,
                    clock
            );
            System.out.println("Shutdown manager is ready.");

            AppRouter router = new AppRouter(inventoryService,userService, productLineService, taskService, reviewNotesService);
            router.showLoginView();




            System.out.println("Application initialized successfully. Login view is now visible.");
        });

    }
}
