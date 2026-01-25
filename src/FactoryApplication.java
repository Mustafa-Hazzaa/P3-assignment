
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

            SimulatedClock clock = SimulatedClock.getInstance();


            UserService userService = new UserService();
            InventoryService inventoryService = new InventoryService();
            ReviewNotesService reviewNotesService = new ReviewNotesService();
            TaskService taskService = new TaskService(clock,inventoryService);
            ProductLineService productLineService = new ProductLineService();



            productLineService.assignTasksToLines(taskService);

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

            AppRouter router = new AppRouter(inventoryService,userService, productLineService, taskService, reviewNotesService);
            router.showLoginView();

            System.out.println("Application initialized successfully.");
        });

    }
}
