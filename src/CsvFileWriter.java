import java.io.IOException;

public class CsvFileWriter {

    void addUser(String filePath,String username,String password,String role){
        String input = username + "," + password + "," + role;
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
