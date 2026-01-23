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
    private final int totalQuantity;



    public Task(int id, String productName, int quantity, String client, TaskStatus taskStatus, int productLineId, int progress) {
        this.id = id;
        syncNextId(id);
        this.productName = productName;
        this.quantity = quantity;
        totalQuantity= quantity;
        this.client = client;
        this.productLineId = productLineId;
        this.startTime = null;
        this.endTime = null;
        this.status = taskStatus;
        this.progress = progress;

    }

    public Task(String product, int quantity, String client, int productLineId) {
        this.id = generateId();
        this.productName = product;
        this.quantity = quantity;
        totalQuantity= quantity;
        this.client = client;
        this.productLineId = productLineId;
        this.startTime = null;
        this.endTime = null;
        this.status = TaskStatus.PENDING;
        this.progress = 0;

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
        return quantity <= 0;
    }

    public int remainingUnits() {
        return quantity;
    }

    public synchronized boolean produceOneUnit() {
        if (status != TaskStatus.IN_PROGRESS)
            throw new IllegalStateException("Task not in progress");

        quantity--; // remaining
        progress = (int) ((1.0 * (totalQuantity - quantity) / totalQuantity) * 100);

        return quantity == 0;
    }


    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
