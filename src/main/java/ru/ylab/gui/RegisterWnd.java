package ru.ylab.gui;

import ru.ylab.database.Repository;
import ru.ylab.model.User;
import ru.ylab.util.GraphicUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterWnd extends JFrame {

    private JPanel rootPanel;
    private JTextField loginTextField;
    private JLabel loginLabel;
    private JLabel titleLabel;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JButton registerButton;
    private JTextField firstNameTextField;
    private JLabel firstNameLabel;
    private JTextField lastNameTextField;
    private JLabel lastNameLabel;

    public RegisterWnd(JFrame parent) {
        setContentPane(rootPanel);
        setVisible(true);
        setSize(640, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Coworking Service: Registration");
        GraphicUtil.setCenteredToScreen(parent, this);

        registerButton.addActionListener(this::onRegister);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
    }

    private void onRegister(ActionEvent event) {
        User newUser = User.builder()
            .firstName(firstNameTextField.getText())
            .lastName(lastNameTextField.getText())
            .password(passwordTextField.getText())
            .login(loginTextField.getText())
            .build();
        if (!Repository.getInstance().create(newUser)) {
            JOptionPane.showMessageDialog(this, "User with login name " + newUser.getLogin() + " already exists!", "Failure", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Successfully registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }

}
