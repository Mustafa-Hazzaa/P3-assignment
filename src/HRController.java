import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class HRController {
    private UserData model;
    private HRView view;

    public HRController(UserData model, HRView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.addUserButton.addActionListener(e -> handleAdd());
        view.removeUserButton.addActionListener(e -> handleRemove());

        CsvFileReader csvFileReader = new CsvFileReader();
        try {
            List<User> users = csvFileReader.loadUsers();
            for (User user : users)
                view.userListModel.addElement(user.getName() + " - " + user.getRole());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleAdd() {
        CsvFileWriter csvFileWriter = new CsvFileWriter();
        String username = view.nameField.getText();
        String password = new String(view.passwordField.getPassword());
        String role = (String) view.roleComboBox.getSelectedItem();

        String status = model.validateLoginRequest(username, password, role);

        if (status.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(view, "User already exist");
        } else {
            if (model.validateInfo(username, password, role).equals("SUCCESS")) {
                csvFileWriter.addUser(username, password, role, true);
                String user = view.nameField.getText() + " - " + view.roleComboBox.getSelectedItem();
                view.userListModel.addElement(user);
                model.updateUsers();
            } else {
                JOptionPane.showMessageDialog(view, status);
            }
        }
    }

        void handleRemove () {
            int selectedIndex = view.userList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedUser = view.userListModel.get(selectedIndex);
                view.userListModel.remove(selectedIndex);

                selectedUser = selectedUser.split("-")[0].trim();
                model.updateUsers(selectedUser);
            }
        }


    }


