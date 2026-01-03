package Service;

import Model.Task;
import Repository.TaskRepository;
import Util.SimulatedClock;
import Util.TaskStatus;
import io.ErrorLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {

    private final TaskRepository taskRepository;
    private final InventoryService inventoryService;
    private final Map<Integer, Task> tasksById;
    private final SimulatedClock clock;
    private final ErrorLogger logger = new ErrorLogger();

    public TaskService(SimulatedClock clock,InventoryService inventoryService ) {
        this.taskRepository = new TaskRepository();
        this.tasksById = new HashMap<>();
        this.clock = clock;
        this.inventoryService = inventoryService;
        loadTasks();
    }

    private void loadTasks() {
        List<Task> taskList = taskRepository.loadAll();

        for (Task task : taskList) {
            tasksById.put(task.getId(), task);

            if (task.getStatus() == TaskStatus.IN_PROGRESS || task.getStatus() == TaskStatus.WAITING_FOR_MATERIAL){
                inventoryService.addReservedItem(task);
            }
        }
    }

    public Task getTask(int id) {
        return tasksById.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksById.values());
    }

    public List<Task> getTasksByProductLine(int productLineId) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasksById.values()) {
            if (task.getProductLineId() == productLineId) {
                result.add(task);
            }
        }
        return result;
    }

    public void addTask(Task task) {
        if (task != null) {
            tasksById.put(task.getId(), task);
        }
    }

    public void removeTask(int taskId) {
        tasksById.remove(taskId);
    }

    public boolean tryStartTask(Task task, InventoryService inventoryService) {
        if (task.getStatus() == TaskStatus.IN_PROGRESS)
            return true;

        if (!inventoryService.addReservedItem(task)) {
            task.setStatus(TaskStatus.WAITING_FOR_MATERIAL);
            return false;
            }
            else {
                task.setStatus(TaskStatus.IN_PROGRESS);
                task.start(clock);
            }

        return true;
    }

    public boolean produceOneUnit(Task task, InventoryService inventoryService) {
        if (task.getStatus() != TaskStatus.IN_PROGRESS)
            return false;

        Map<String, Integer> requirements = inventoryService.getProductByName(task.getProductName()).getRequiredItems();
        for (Map.Entry<String, Integer> req : requirements.entrySet()) {
            inventoryService.consumeReserved(req.getKey(), req.getValue());
        }
        inventoryService.updateProductQuantity(task.getProductName(), 1);
        boolean completed = task.produceOneUnit();

        if (completed) {
            task.complete(clock);
        }
        return completed;

    }

    public void saveChanges() {
        taskRepository.saveAll(new ArrayList<>(tasksById.values()));
    }
}