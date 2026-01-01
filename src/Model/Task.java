package Model;

import java.time.LocalDateTime;

public class Task {
    private static int nextId = 1;
    private int id;
    private Product product;
    private int quantity;
    private String client;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskStatus status;
    private ProductLine productLine;
    private int progress;

    public Task(int id, Product product, int quantity, String client, ProductLine productLine, int secondsNeeded) {
        this.id = id;
        syncNextId(id);
        this.product = product;
        this.quantity = quantity;
        this.client = client;
        this.productLine = productLine;
        this.startTime = null;
        this.endTime = null;
        this.status = TaskStatus.PENDING;
        this.progress = 0;
    }

    public Task(Product product, int quantity, String client, ProductLine productLine) {
        this.id = generateId();
        this.product = product;
        this.quantity = quantity;
        this.client = client;
        this.productLine = productLine;

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

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
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
}
