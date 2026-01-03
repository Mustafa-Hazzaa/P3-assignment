package Util;

import Service.*; // Import all services

public class ShutdownManager {

    /**
     * Registers a shutdown hook to perform all necessary cleanup tasks.
     * Note: This version does not take a ProductionService.
     */
    public static void registerShutdownHook(
            UserService userService,
            InventoryService inventoryService,
            ReviewNotesService reviewNotesService,
            TaskService taskService,
            ProductLineService productLineService
    ) {

        Thread shutdownThread = new Thread(() -> {
            System.out.println("-------------------------------------------");
            System.out.println("Shutdown hook triggered. Closing application...");

            ProductLineWorker.stopAllWorkers();

            // STEP 2: Save the state from all data services.
            System.out.println("Saving all data...");
            userService.saveChanges();
            inventoryService.saveChanges();
            reviewNotesService.saveChanges();
            taskService.saveChanges();
            productLineService.saveChanges();

            System.out.println("...All data saved. Goodbye.");
            System.out.println("-------------------------------------------");
        });

        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }
}
