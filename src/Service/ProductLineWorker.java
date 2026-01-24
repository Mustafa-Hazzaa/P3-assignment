package Service;

import Model.ProductLine;
import Model.Task;
import Util.LineStatus;
import Util.SimulatedClock;
import Util.TaskStatus;
import io.ErrorLogger;
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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (productLine.getStatus() != LineStatus.ACTIVE) sleep(500);
                Task task = productLine.takeTask();
                if (task.getStatus() == TaskStatus.CANCELLED) continue;
                if (!taskService.tryStartTask(task, inventoryService)) {
                    productLine.addTask(task);
                    sleep(500);
                    continue;
                }
                while (!task.isCompleted()) {

                    while (productLine.getStatus() != LineStatus.ACTIVE) {
                        sleep(200);
                    }

                    taskService.produceOneUnit(task, inventoryService);
                    sleep(1000);
                }
                productLine.completeRunningTask();
            } catch (InterruptedException e) {
                logger.log(e.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.log(e.getMessage());
            }
        }
    }


    public static void startAllWorkers(ProductLineService productLineService, TaskService taskService, InventoryService inventoryService, SimulatedClock clock) {
        for (ProductLine line : productLineService.getAll()) {
                ProductLineWorker worker = new ProductLineWorker(line, taskService, inventoryService, clock);
                Thread workerThread = new Thread(worker, "Worker - " + line.getName());
                workerThread.start();
                allWorkerThreads.add(workerThread);
        }
    }

    public static void addWorker(ProductLine productLine, TaskService taskService, InventoryService inventoryService, SimulatedClock clock){
        ProductLineWorker worker = new ProductLineWorker(productLine, taskService, inventoryService, clock);
        Thread workerThread = new Thread(worker, "Worker - " + productLine.getName());
        workerThread.start();
        allWorkerThreads.add(workerThread);
    }


    public static void stopAllWorkers() {
        for (Thread workerThread : allWorkerThreads) {
            workerThread.interrupt();
        }
    }
}
