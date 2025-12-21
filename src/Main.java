import Control.LoginController;
import Model.UserRepository;
import Model.UserService;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        repository.loadFromCsv("Data/Users.csv");

        UserService model = new UserService(repository);

        LoginView view = new LoginView();

        new LoginController(model, view);
    }
}
