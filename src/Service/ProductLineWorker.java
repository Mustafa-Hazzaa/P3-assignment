package Service;

import Model.ProductLine;
import Model.Task;
import Util.LineStatus;
import Util.SimulatedClock;
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
        while (productLine.getStatus() == LineStatus.ACTIVE && !Thread.currentThread().isInterrupted()) {
            try {
                Task task = productLine.takeTask();
                if (!taskService.tryStartTask(task, inventoryService)) {
                    productLine.addTask(task);
                    sleep(500);
                    continue;
                }
                while (!task.isCompleted()) {
                    taskService.produceOneUnit(task, inventoryService);
                    sleep(1000);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
            }
        }
    }


    public static void startAllWorkers(ProductLineService productLineService, TaskService taskService, InventoryService inventoryService, SimulatedClock clock) {
        for (ProductLine line : productLineService.getAll()) {
            if (line.getStatus() == LineStatus.ACTIVE) {
                ProductLineWorker worker = new ProductLineWorker(line, taskService, inventoryService, clock);
                Thread workerThread = new Thread(worker, "Worker - " + line.getName());
                workerThread.start();
                allWorkerThreads.add(workerThread);
            }
        }
    }


    public static void stopAllWorkers() {
        for (Thread workerThread : allWorkerThreads) {
            workerThread.interrupt();
        }
    }
}
