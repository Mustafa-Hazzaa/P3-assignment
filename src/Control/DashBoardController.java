package Control;

import Model.Product;
import Model.ProductLine;
import Model.Task;
import Service.InventoryService;
import Service.ProductLineService;
import Service.TaskService;
import Util.Checker;
import Util.IsAlphabet;
import Util.PositiveIntegerException;
import java.util.ArrayList;
import java.util.Collection;
import Util.TaskStatus;
import View.DashBoard;
import io.ErrorLogger;
import raven.toast.Notifications;
import View.ProductionLineTasks;
import View.ProductTask;
import View.ProductionLinesByProduct;
import View.ProductsByProductionLine;
import View.MostRequestedProduct;

import javax.swing.*;
import java.util.List;

public class DashBoardController {
    private final TaskService taskService;
    private final ProductLineService productLineService;
    private final InventoryService inventoryService;
    private final ErrorLogger logger = new ErrorLogger();



    DashBoardController(TaskService taskService, ProductLineService productLineService, InventoryService inventoryService, DashBoard view){
        this.taskService = taskService;
        this.productLineService = productLineService;
        this.inventoryService = inventoryService;
    }


    public void handleAddTask() {
        JComboBox<Product> productCombo = new JComboBox<>();
        List<Product> allProducts = inventoryService.getAllProducts();
        if (allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no products in the inventory to create a task for.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Product product : allProducts) {
            productCombo.addItem(product);
        }

        // -- Production Line ComboBox --
        JComboBox<String> lineCombo = new JComboBox<>();
        Collection<ProductLine> productLinesCollection = productLineService.getAll();
        List<ProductLine> allProductLines = new ArrayList<>(productLinesCollection);
        if (allProductLines.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no production lines available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (ProductLine line : allProductLines) {
            lineCombo.addItem(line.getName());
        }


        JTextField qtyField = new JTextField();
        JTextField clientField = new JTextField();

        JComboBox<TaskStatus> statusCombo = new JComboBox<>(TaskStatus.values());
        statusCombo.setSelectedItem(TaskStatus.READY);

        Object[] fields = {
                "Generated ID:", new JLabel(String.valueOf(Task.nextId)),
                "Select Product:", productCombo,
                "Client:",clientField,
                "Select Production Line:", lineCombo,
                "Quantity:", qtyField,
                "Status:", statusCombo
        };

        while (true) {

            int result = JOptionPane.showConfirmDialog(null, fields, "Add New Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int quantity = Checker.parsePositiveInt(qtyField.getText());
                    String client = Checker.isAlphabet(clientField.getText());

                    Product selectedProduct = (Product) productCombo.getSelectedItem();
                    String selectedLine = (String) lineCombo.getSelectedItem();

                    if (selectedProduct == null || selectedLine == null) {
                        throw new IllegalStateException("A selection cannot be null.");
                    }
                    Task newTask = new Task(
                            selectedProduct.getName(),
                            quantity,
                            quantity,
                            client,
                            productLineService.getByName(selectedLine).getId()
                    );

                    boolean added = taskService.addTask(newTask);

                    if (added) {
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Task added successfully!");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Something wrong happened.");
                    }

                    break;

                } catch (PositiveIntegerException e) {
                    JOptionPane.showMessageDialog(null, "Task quantity must be a positive integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IsAlphabet e) {
                    JOptionPane.showMessageDialog(null, "Client name must only contain characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }catch (Exception e){
                    logger.log(e.getMessage());
                }
        }
            if (result != JOptionPane.YES_OPTION) return;
    }
    }

    public void handleTasksByProductionLine() {
        List<Task> allTasks = taskService.getAllTasks();
        Collection<ProductLine> productLinesCollection = productLineService.getAll();
        List<ProductLine> allProductLines = new ArrayList<>(productLinesCollection);

        if (allTasks == null || allTasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no tasks to display.", "No Tasks", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ProductionLineTasks taskViewPanel = new ProductionLineTasks(allProductLines,allTasks, this.productLineService);

        JFrame frame = new JFrame("Tasks by Production Line");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(taskViewPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void handleTasksByProduct() {
        // 1. Fetch all necessary data from services.
        List<Task> allTasks = taskService.getAllTasks();
        List<Product> allProducts = inventoryService.getAllProducts();

        if (allTasks == null || allTasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no tasks to display.", "No Tasks", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (allProducts == null || allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no products to filter by.", "No Products", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        ProductTask productTaskView = new ProductTask(allTasks, allProducts, productLineService);

        JFrame frame = new JFrame("Tasks by Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(productTaskView);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    public void handleProductionLinesByProduct() {
        // 1. Fetch all necessary data from services.
        List<Task> allTasks = taskService.getAllTasks();
        List<Product> allProducts = inventoryService.getAllProducts();

        if (allTasks == null || allTasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no tasks to display.", "No Tasks", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (allProducts == null || allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no products to filter by.", "No Products", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 2. Create the view panel, passing in the data it needs.
        ProductionLinesByProduct viewPanel = new ProductionLinesByProduct(allTasks, allProducts, productLineService);

        // 3. Display the panel in a new window.
        JFrame frame = new JFrame("Production Lines by Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(viewPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    public void handleProductsByProductionLine() {
        // 1. Fetch all necessary data from services.
        List<Task> allTasks = taskService.getAllTasks();
        Collection<ProductLine> productLinesCollection = productLineService.getAll();
        List<ProductLine> allProductLines = new ArrayList<>(productLinesCollection);

        if (allTasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no tasks to display.", "No Tasks", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (allProductLines.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no production lines to filter by.", "No Lines", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 2. Create the view panel, passing in the data.
        ProductsByProductionLine viewPanel = new ProductsByProductionLine(allTasks, allProductLines);

        // 3. Display the panel in a new window.
        JFrame frame = new JFrame("Products by Production Line");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(viewPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void handleMostRequestedProduct() {
        // 1. The analysis logic is now self-contained within the panel,
        //    so we just need to pass the service it depends on.
        MostRequestedProduct viewPanel = new MostRequestedProduct(taskService);

        // 2. Display the panel in a new window.
        JFrame frame = new JFrame("Most Requested Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(viewPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

