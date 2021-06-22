package Controller;

import Client.Profile;
import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.Errors;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController {
    public PasswordField Password_field;
    public TextField Username_field;
    public TextField password_visible;

    public void login(ActionEvent actionEvent) throws IOException {
        String username = Username_field.getText();
        String password;
        if (Password_field.isVisible())
            password = Password_field.getText();
        else
            password = password_visible.getText();
        if (username.isEmpty() || password.isEmpty()) {
            Errors.InvalidLoginDialog();
            return;
        }
        Profile profile = ClientManager.login(username, password);
        if (profile == null) {
            Errors.ShowInvalidLoginDialog();
            return;
        }
        thisClient.setProfile(profile);
        new PageLoader().load("timeLine");
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("SignUp");
    }

    public void Password_recovery(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("FindPassWord");
    }

    public void show_Pass(MouseEvent mouseEvent) {
        if (!password_visible.isVisible()) {
            password_visible.setVisible(true);
            Password_field.setVisible(false);
            password_visible.setText(Password_field.getText());
        } else {
            password_visible.setVisible(false);
            Password_field.setVisible(true);
            Password_field.setText(password_visible.getText());
        }
    }
}
