package Repository;

import Model.User;
import Util.Role;

public class UserRepository extends CsvRepository<User> {
    public UserRepository() {
        super("Data/Users.csv");
    }

    @Override
    protected String getHeader() {
        return "username,email,password,role";
    }


    @Override
    protected String toCsv(User user) {
        return user.getName() + "," +
                user.getEmail() + "," +
                user.getPassword() + "," +
                user.getRole().name();
    }

    @Override
    protected User fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length != 4) {
            throw new IllegalArgumentException("Invalid data");
        }
        return new User(data[0], data[1], data[2], Role.valueOf(data[3].toUpperCase()));
    }
}
