package Service;

import Model.ProductLine;
import Model.Task; // <-- Import Task
import Repository.ProductLineRepository;
import Util.LineStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductLineService {

    private final ProductLineRepository repository;
    private final Map<Integer, ProductLine> productLinesById;

    public ProductLineService() {
        this.repository = new ProductLineRepository();
        this.productLinesById = new HashMap<>();
        loadProductLines(); // Load data when the service is created
    }


    private void loadProductLines() {
        List<ProductLine> productLines = repository.loadAll();
        for (ProductLine line : productLines) {
            this.productLinesById.put(line.getId(), line);
        }
    }

    public void assignTasksToLines(TaskService taskService) {
        List<Task> allTasks = taskService.getAllTasks();
        for (Task task : allTasks) {
            ProductLine line = getById(task.getProductLineId());
            if (line != null) {
                try {
                    if (task.getStatus() != Util.TaskStatus.COMPLETED) {
                        line.addTask(task);
                    }
                } catch (InterruptedException e) {
                    System.err.println("ERROR: Failed to assign task " + task.getId() + " to line " + line.getName());
                    Thread.currentThread().interrupt(); // Preserve interrupted status
                }
            }
        }
    }

    public ProductLine getById(int id) {
        return productLinesById.get(id);
    }

    public ProductLine getByName(String name) {
        for (ProductLine productLine : productLinesById.values()) {
            if (productLine.getName().equalsIgnoreCase(name)) {
                return productLine;
            }
        }
        return null;
    }

    public Collection<ProductLine> getAll() {
        return productLinesById.values();
    }

    public void add(ProductLine productLine) {
        if (productLine != null) {
            productLinesById.put(productLine.getId(), productLine);
        }
    }

    public void remove(int id) {
        productLinesById.remove(id);
    }

    public void changeStatus(ProductLine line, LineStatus status) {
        line.setStatus(status);
    }

    public void saveChanges() {
        repository.saveAll(new ArrayList<>(productLinesById.values()));
    }
}
