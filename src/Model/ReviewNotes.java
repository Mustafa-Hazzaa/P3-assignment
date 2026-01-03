package Model;

public class ReviewNotes {
    private final String productLineName;
    private  String review;
    private  String notes;

    public ReviewNotes(String productLineName, String review, String notes) {
        this.productLineName = productLineName;
        this.review = review;
        this.notes = notes;
    }

    public String getReview() {
        return review;
    }

    public String getNotes() {
        return notes;
    }

    public String getProductLineName() {
        return productLineName;
    }

    @Override
    public String toString() {
        return "Review: " + review + "\nNotes: " + notes;
    }
}
