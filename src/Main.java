public class Main {
    public static void main(String[] args) {
        UserData model = new UserData();
        LoginView view = new LoginView();
        new LoginController(model, view);
    }
}
