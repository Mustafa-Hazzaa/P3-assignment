package Control;

import Model.User;
import Service.UserService;
import Util.Role;
import View.HRView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static View.underline.underline;

public class HRController {

    private final UserService model;
    private final HRView view;

    public HRController(UserService model, HRView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.rightPanel.addUserBtn.addActionListener(e -> handleAddUser());
        view.rightPanel.rmvBtn.addActionListener(e -> handleRemoveUser());
        //when you type press go to the next filed
        view.rightPanel.nameField.addActionListener(e ->   view.rightPanel.nameField.transferFocus());
        view.rightPanel.emailField.addActionListener(e ->   view.rightPanel.emailField.transferFocus());
        view.rightPanel.passwordField.addActionListener(e -> handleAddUser());
        refreshUserList();

        //check every time a key is typed
        view.rightPanel.emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean valid = model.isValidEmail(view.rightPanel.emailField.getText());
                Color color = valid ? Color.GREEN : Color.RED;
                view.rightPanel.emailField.setBorder(underline(color));
            }
        });

        view.rightPanel.passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int strength = model.passwordCheck(new String(view.rightPanel.passwordField.getPassword()));
                Color color = (strength <= 1) ? Color.RED : (strength == 2) ? Color.YELLOW : Color.GREEN;
                view.rightPanel.passwordField.setBorder(underline(color));
            }
        });
    }

    private void handleAddUser() {
        String name = view.rightPanel.nameField.getText().trim();
        String email = view.rightPanel.emailField.getText().trim();
        String password = new String(view.rightPanel.passwordField.getPassword());
        Role role = (Role) view.rightPanel.roleComboBox.getSelectedItem();

        String validation = model.validateInfo(email,name, password, role);
        if (!validation.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(view, validation, "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (model.getUser(name) != null) {
            JOptionPane.showMessageDialog(view, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.addUser(new User(name, email, password, role));

        view.rightPanel.userListModel.addElement(name + " - " +email + " - " + role);

        JOptionPane.showMessageDialog(view, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        clearForm();
    }

    private void handleRemoveUser() {
        int selectedIndex = view.rightPanel.userList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(view, "Select a user to remove", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedUser = view.rightPanel.userListModel.get(selectedIndex);
        String username = selectedUser.split("-")[0].trim();

        int option = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to delete " + username + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            model.removeUser(username);
            view.rightPanel.userListModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(view, "User removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //make sure the table is upToDate
    private void refreshUserList() {
        view.rightPanel.userListModel.clear();
        for (User user : model.getAllUsers()) {
            view.rightPanel.userListModel.addElement(user.getName() + " - "+user.getEmail()+" - " + user.getRole());
        }
    }

    private void clearForm() {
        view.rightPanel.nameField.setText("");
        view.rightPanel.emailField.setText("");
        view.rightPanel.passwordField.setText("");
        view.rightPanel.roleComboBox.setSelectedIndex(0);
    }


}
