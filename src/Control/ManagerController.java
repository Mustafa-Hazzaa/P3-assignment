package Control;

import Model.ProductLine;
import Model.ReviewNotes;
import Service.ProductLineService;
import Service.ReviewNotesService;
import Service.TaskService;
import Util.DialogResult;
import Util.LineStatus;
import View.ManagerView;
import View.ReviewDialog;

import javax.swing.*;

public class ManagerController {

    private final ManagerView view;
    private final ProductLineService productService;
    private final ReviewNotesService notesService;
    private final TaskService taskService;

    public ManagerController(ManagerView view,
                             ProductLineService productService,
                             ReviewNotesService notesService, TaskService taskService) {

        this.view = view;
        this.productService = productService;
        this.notesService = notesService;
        this.taskService = taskService;

        view.displayLines(productService.getAll());
        view.setOnLineClicked(this::handleLineClicked);
    }

    private void handleLineClicked(ProductLine line) {
        ReviewNotes notes = notesService.getNotesForProductLine(line.getName());

        ReviewDialog dlg = new ReviewDialog(view, line, notes);
        dlg.setVisible(true);
        DialogResult result = dlg.getDialogResult();

        switch (result) {
            case SAVED:
                LineStatus status = dlg.getStatus();
                int rating = dlg.getRating();
                String text = dlg.getNotes();

                notesService.addUpdateNotes(new ReviewNotes(line.getName(), rating, text));
                line.setStatus(status);
                view.refreshLine(line);
                break;

            case DELETED:
                productService.removeProductLine(line.getId(),taskService);
                view.displayLines(productService.getAll());
                break;

            case CANCELLED:
                break;
        }
    }
}


