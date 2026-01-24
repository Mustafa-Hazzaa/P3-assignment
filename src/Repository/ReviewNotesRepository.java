package Repository;

import Model.ReviewNotes;

public class ReviewNotesRepository extends CsvRepository<ReviewNotes> {
    public ReviewNotesRepository() {
        super("Data/ReviewAndNotes.csv");
    }

    @Override
    protected String getHeader() {
        return "ProductLine,Review,Notes";
    }

    @Override
    protected String toCsv(ReviewNotes object) {
        return object.getProductLineId()+ "," +
                object.getReview()+ "," +
                object.getNotes();
    }

    @Override
    protected ReviewNotes fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 3) {
            throw new IllegalArgumentException("Invalid Data");
        }

        return (new ReviewNotes(
                Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                data[2]));
    }
}
