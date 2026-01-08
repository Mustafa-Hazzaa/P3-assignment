package Model;

public class ReviewNotes {
    private final String productLineName;
    private  int review;
    private  String notes;

    public ReviewNotes(String productLineName, int review, String notes) {
        this.productLineName = productLineName;
        this.review = review;
        this.notes = notes;
    }

    public int getReview() {
        return review;
    }

    public String getNotes() {
        return notes;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Review: " + review + "\nNotes: " + notes;
    }
}
