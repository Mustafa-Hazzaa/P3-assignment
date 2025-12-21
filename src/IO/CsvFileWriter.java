package IO;

import java.io.IOException;

public class CsvFileWriter {

    public void addUser(String username, String email ,String password, String role, boolean append)
    {
        addUser("Data/Users.csv",username,email,password,role,append);
    }

    public void addUser(String filePath,String username,String email,String password,String role,boolean append){
        String input = username +","+email+ "," + password + "," + role;
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,input,append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReviewAndNotes(String review,String Notes){
        addReviewAndNotes("Data/ReviewAndNotes.csv",review,Notes);
    }
    public void addReviewAndNotes(String filePath,String review, String notes){
        String safeReview = "\"" + review.replace("\"", "\"\"") + "\"";
        String safeNotes = "\"" + notes.replace("\"", "\"\"") + "\"";
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,safeReview+","+safeNotes,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
