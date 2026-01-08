package Control;

import Model.ProductLine;
import Model.ReviewNotes;
import Service.ProductLineService;
import Service.ReviewNotesService;
import Util.LineStatus;
import View.ManagerView;
import View.ReviewDialog;

import javax.swing.*;

public class ManagerController {

    private final ManagerView view;
    private final ProductLineService productService;
    private final ReviewNotesService notesService;

    public ManagerController(ManagerView view,
                             ProductLineService productService,
                             ReviewNotesService notesService) {

        this.view = view;
        this.productService = productService;
        this.notesService = notesService;

        view.displayLines(productService.getAll());
        view.setOnLineClicked(this::handleLineClicked);
    }

    private void handleLineClicked(ProductLine line) {
        ReviewNotes notes = notesService.getNotesForProductLine(line.getName());

        ReviewDialog dlg = new ReviewDialog(view, line, notes);
        dlg.setVisible(true);

        LineStatus status = dlg.getStatus();
        int rating = dlg.getRating();
        String text = dlg.getNotes();

        notesService.addUpdateNotes(new ReviewNotes(line.getName(), rating, text));
        line.setStatus(status);
        view.refreshLine(line);


    }
}
