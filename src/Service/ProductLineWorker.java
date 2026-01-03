package Service;

import Model.ProductLine;
import Model.Task;
import Util.LineStatus;
import Util.SimulatedClock;
import io.ErrorLogger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
public class ProductLineWorker implements Runnable {


    private final ProductLine productLine;
    private final TaskService taskService;
    private final InventoryService inventoryService;
    private final SimulatedClock clock;
    private final ErrorLogger logger = new ErrorLogger();

    private static final List<Thread> allWorkerThreads = new ArrayList<>();

    public ProductLineWorker(ProductLine productLine, TaskService taskService, InventoryService inventoryService, SimulatedClock clock) {
        this.productLine = productLine;
        this.taskService = taskService;
        this.inventoryService = inventoryService;
        this.clock = clock;
    }


    @Override
    public void run() {
        System.out.println("✅ Worker for Product Line '" + productLine.getName() + "' is starting.");
        while (productLine.getStatus() == LineStatus.ACTIVE && !Thread.currentThread().isInterrupted()) {
            try {
                Task task = productLine.takeTask();
                System.out.println("INFO: Line '" + productLine.getName() + "' took task " + task.getId());

                if (!taskService.tryStartTask(task, inventoryService)) {
                    logger.log("WARN: Task " + task.getId() + " is waiting for materials. Re-queuing...");
                    productLine.addTask(task); // Put it back at the end of the queue
                    sleep(500); // Wait for 5 simulated seconds before retrying
                    continue;
                }

                System.out.println("INFO: Line '" + productLine.getName() + "' started producing task " + task.getId());
                while (!task.isCompleted()) {
                    taskService.produceOneUnit(task, inventoryService);
                    sleep(1000); // Simulate 1 second of production time per unit
                }

            } catch (InterruptedException e) {
                logger.log("INFO: Worker for '" + productLine.getName() + "' was interrupted and is shutting down.");
                Thread.currentThread().interrupt(); // Preserve the interrupted status
            } catch (Exception e) {
                logger.log("ERROR: Unexpected error in worker for '" + productLine.getName() + "': " + e.getMessage());
            }
        }
        System.out.println("🛑 Worker for Product Line '" + productLine.getName() + "' has stopped.");
    }


    public static void startAllWorkers(ProductLineService productLineService, TaskService taskService, InventoryService inventoryService, SimulatedClock clock) {
        System.out.println("INFO: Starting all workers from static method in ProductLineWorker...");
        for (ProductLine line : productLineService.getAll()) {
            if (line.getStatus() == LineStatus.ACTIVE) {
                ProductLineWorker worker = new ProductLineWorker(line, taskService, inventoryService, clock);
                Thread workerThread = new Thread(worker, "Worker-" + line.getName());
                workerThread.start();
                allWorkerThreads.add(workerThread); // Add to the static list of all threads
                System.out.println("✅ Started worker for Product Line: " + line.getName());
            }
        }
    }


    public static void stopAllWorkers() {
        System.out.println("INFO: Stopping all workers from static method in ProductLineWorker...");
        for (Thread workerThread : allWorkerThreads) {
            workerThread.interrupt();
        }
        System.out.println("INFO: Shutdown signal sent to all workers.");
    }
}
