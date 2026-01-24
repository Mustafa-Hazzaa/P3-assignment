package Service;

import Model.ReviewNotes;
import Repository.ReviewNotesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewNotesService {

    private final ReviewNotesRepository repository;
    private final Map<Integer, ReviewNotes> notesByProductLineId;

    public ReviewNotesService() {
        this.repository = new ReviewNotesRepository();
        this.notesByProductLineId = new HashMap<>();
        loadNotesFromFile();
    }

    private void loadNotesFromFile() {
        List<ReviewNotes> notesList = repository.loadAll();
        for (ReviewNotes note : notesList) {
            notesByProductLineId.put(note.getProductLineId(), note);
        }
    }

    public ReviewNotes getNotesForProductLine(int productLineId) {
        return notesByProductLineId.get(productLineId);
    }

    public void addUpdateNotes(ReviewNotes notes) {
        if (notes != null) {
            notesByProductLineId.put(notes.getProductLineId(), notes);
        }
    }

    public List<ReviewNotes> getAllNotes() {
        return new ArrayList<>(notesByProductLineId.values());
    }

    public void saveChanges() {
        repository.saveAll(new ArrayList<>(notesByProductLineId.values()));
    }
}