package IO;

import java.io.IOException;

public class CsvFileWriter {

    public void addUser(String username, String password, String role, boolean append)
    {
        addUser("Data/Users.csv",username,password,role,append);
    }

    void addUser(String filePath,String username,String password,String role,boolean append){
        String input = username + "," + password + "," + role;
        TxtFileWriter txtFileWriter = new TxtFileWriter();
        try {
            txtFileWriter.writeLine(filePath,input,append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
