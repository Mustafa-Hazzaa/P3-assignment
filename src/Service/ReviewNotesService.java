package Service;

import Model.ReviewNotes;
import Repository.ReviewNotesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewNotesService {

    private final ReviewNotesRepository repository;
    private final Map<String, ReviewNotes> notesByProductLine;


    public ReviewNotesService() {
        this.repository = new ReviewNotesRepository();
        this.notesByProductLine = new HashMap<>();
        loadNotesFromFile();
    }

    private void loadNotesFromFile() {
        List<ReviewNotes> notesList = repository.loadAll();
        for (ReviewNotes note : notesList) {
            notesByProductLine.put(note.getProductLineName(), note);
        }
        System.out.println("ReviewNotesService loaded: " + notesByProductLine.size() + " notes.");
    }

    public ReviewNotes getNotesForProductLine(String productLineName) {
        return notesByProductLine.get(productLineName);
    }

    public void addUpdateNotes(ReviewNotes notes) {
        if (notes != null) {
            notesByProductLine.put(notes.getProductLineName(), notes);
        }
    }

    public List<ReviewNotes> getAllNotes() {
        return new ArrayList<>(notesByProductLine.values());
    }

    public void saveChanges() {
        System.out.println("ReviewNotesService: Saving changes...");
        repository.saveAll(new ArrayList<>(notesByProductLine.values()));
        System.out.println("...Review notes save complete.");
    }
}
