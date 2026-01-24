package Model;

public class ReviewNotes {
    private final int productLineID;
    private  int review;
    private  String notes;

    public ReviewNotes(int productLineID, int review, String notes) {
        this.productLineID = productLineID;
        this.review = review;
        this.notes = notes;
    }

    public int getReview() {
        return review;
    }

    public String getNotes() {
        return notes;
    }

    public int getProductLineId() {
        return productLineID;
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
