package Util;

import Service.*;

public class ShutdownManager {


    public static void registerShutdownHook(
            UserService userService,
            InventoryService inventoryService,
            ReviewNotesService reviewNotesService,
            TaskService taskService,
            ProductLineService productLineService,
            SimulatedClock clock
    ) {

        Thread shutdownThread = new Thread(() -> {
            System.out.println("-------------------------------------------");
            System.out.println("Shutdown hook triggered. Closing application...");

            ProductLineWorker.stopAllWorkers();

            System.out.println("Saving all data...");
            userService.saveChanges();
            inventoryService.saveChanges();
            reviewNotesService.saveChanges();
            taskService.saveChanges();
            productLineService.saveChanges();
            clock.shutdown();

            System.out.println("...All data saved. Goodbye.");
            System.out.println("-------------------------------------------");
        });

        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }
}
