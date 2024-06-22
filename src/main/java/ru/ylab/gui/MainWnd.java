package ru.ylab.gui;

import ru.ylab.database.Repository;
import ru.ylab.model.User;
import ru.ylab.util.GraphicUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainWnd extends JFrame {

    private JPanel rootPanel;
    private JButton registerButton;
    private JButton singInButton;
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JLabel loginLabel;
    private JLabel passwordLabel;

    public MainWnd() {
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setTitle("Coworking Service: Main Window");
        GraphicUtil.setCenteredToScreen(this);

        singInButton.addActionListener(this::onSignIn);
        registerButton.addActionListener(this::onRegister);
    }

    private void onSignIn(ActionEvent event) {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        if (login.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Wrong data in login/password fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            User user = Repository.getInstance().findUser(login, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Welcome, " + user.getFirstName() + " " + user.getLastName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LKWnd(user);
            }
        }
    }

    private void onRegister(ActionEvent event) {
        new RegisterWnd(this);
    }

}
