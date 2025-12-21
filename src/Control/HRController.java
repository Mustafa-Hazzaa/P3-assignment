package Control;

import Model.User;
import Model.UserService;
import View.HRView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static View.HRView.underline;

public class HRController {

    private final UserService model;
    private final HRView view;

    public HRController(UserService model, HRView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.addUserBtn.addActionListener(e -> handleAddUser());
        view.rmvBtn.addActionListener(e -> handleRemoveUser());
        view.nameField.addActionListener(e ->   view.nameField.transferFocus());;
        view.emailField.addActionListener(e ->   view.emailField.transferFocus());;
        view.passwordField.addActionListener(e ->   view.passwordField.transferFocus());;
        refreshUserList();


        view.emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean valid = model.isValidEmail(view.emailField.getText());
                Color color = valid ? Color.GREEN : Color.RED;
                view.emailField.setBorder(underline(color));
            }
        });

        view.passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int strength = model.passwordCheck(new String(view.passwordField.getPassword()));
                Color color = (strength <= 1) ? Color.RED : (strength == 2) ? Color.YELLOW : Color.GREEN;
                view.passwordField.setBorder(underline(color));
            }
        });
    }

    private void handleAddUser() {
        String name = view.nameField.getText().trim();
        String email = view.emailField.getText().trim();
        String password = new String(view.passwordField.getPassword());
        String role = (String) view.roleComboBox.getSelectedItem();

        String validation = model.validateInfo(email,name, password, role);
        if (!validation.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(view, validation, "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (model.getUser(name) != null) {
            JOptionPane.showMessageDialog(view, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.addUser(name, email, password, role);

        view.userListModel.addElement(name + " - " +email + " - " + role);

        JOptionPane.showMessageDialog(view, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        clearForm();
    }

    private void handleRemoveUser() {
        int selectedIndex = view.userList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(view, "Select a user to remove", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedUser = view.userListModel.get(selectedIndex);
        String username = selectedUser.split("-")[0].trim();

        UIManager.put("OptionPane.yesButtonFocusPainted", false);
        UIManager.put("OptionPane.noButtonFocusPainted", false);

        int option = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to delete " + username + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            model.removeUser(username);
            view.userListModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(view, "User removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void refreshUserList() {
        view.userListModel.clear();
        for (User user : model.getAllUsers()) {
            view.userListModel.addElement(user.getName() + " - "+user.getEmail()+" - " + user.getRole());
        }
    }

    private void clearForm() {
        view.nameField.setText("");
        view.emailField.setText("");
        view.passwordField.setText("");
        view.roleComboBox.setSelectedIndex(0);
    }
}
