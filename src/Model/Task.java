package Model;

import Util.*;

import java.time.LocalDateTime;

public class Task {
    private static int nextId = 1;
    private int id;
    private String productName;
    private int quantity;
    private String client;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskStatus status;
    private int productLineId;
    private int progress;
    private int step;


    public Task(int id, String productName, int quantity, String client, TaskStatus taskStatus, int productLineId, int progress) {
        this.id = id;
        syncNextId(id);
        this.productName = productName;
        this.quantity = quantity;
        this.client = client;
        this.productLineId = productLineId;
        this.startTime = null;
        this.endTime = null;
        this.status = taskStatus;
        this.progress = progress;
        int step = getStep();
    }

    public Task(String product, int quantity, String client, int productLineId) {
        this.id = generateId();
        this.productName = product;
        this.quantity = quantity;
        this.client = client;
        this.productLineId = productLineId;
        this.startTime = null;
        this.endTime = null;
        this.status = TaskStatus.PENDING;
        this.progress = 0;
        int step = getStep();
    }


    private static synchronized int generateId() {
        return nextId++;
    }

    private static synchronized void syncNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getProductLineId() {
        return productLineId;
    }

    public void setProductLine(int productLineId) {
        this.productLineId = productLineId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void start(SimulatedClock clock) {
        this.startTime = clock.now();
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void complete(SimulatedClock clock) {
        this.status = TaskStatus.COMPLETED;
        this.progress = 100;
        this.endTime = clock.now();
    }

    public boolean isCompleted() {
        return getQuantity() <= 0;
    }

    public int remainingUnits() {
        return quantity;
    }

    public boolean produceOneUnit() {
        if (status != TaskStatus.IN_PROGRESS)
            throw new IllegalStateException("Task not in progress");
        quantity--;
        progress = Math.min(100, progress + step);
        return quantity == 0;
    }

    public int getStep() {
        if (quantity <= 0) {
            return 0;
        }

        int remainingProgress = 100 - progress;
        return Math.max(1, remainingProgress / quantity);
    }

}
