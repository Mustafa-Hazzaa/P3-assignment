package Model;

public class ReviewNotes {
    private final String review;
    private final String notes;

    public ReviewNotes(String review, String notes) {
        this.review = review;
        this.notes = notes;
    }

    public String getReview() {
        return review;
    }

    public String getNotes() {
        return notes;
    }



    @Override
    public String toString() {
        return "Review: " + review + "\nNotes: " + notes;
    }
}
