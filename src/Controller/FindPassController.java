package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.Errors;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FindPassController {
    public Label password_here;
    public TextField newPasswordField;
    public TextField OldPasswordField;
    public TextField getthepassword;
    public TextField UsernameField;

    public void SaveChangedPassword(ActionEvent actionEvent) {
        if (UsernameField.getText().isEmpty() || OldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty()) {
            Errors.ShowInvalidFilling();
            return;
        }
        if (!Errors.isValidPassword(newPasswordField.getText(), newPasswordField.getText())) {
            return;
        }
        boolean CanChange = ClientManager.ChangePassword(UsernameField.getText(), OldPasswordField.getText(), newPasswordField.getText());
        if (CanChange) {
            Errors.ChangedPasswordComplete();
            return;
        } else {
            Errors.CanNotChangePassword();
            return;
        }
    }

    public void BackToLogin(MouseEvent mouseEvent) throws IOException {
        password_here.setVisible(false);
        password_here.setText("");
        new PageLoader().load("Login");
    }

    public void GetThePassword(ActionEvent actionEvent) {
        if (UsernameField.getText().isEmpty() || getthepassword.getText().isEmpty()) {
            Errors. ShowInvalidFilling();
            return;
        }
        String received = ClientManager.GetThePassword(UsernameField.getText(), getthepassword.getText());
        if (received == null) {
            Errors. ShowNotHasTheSecPassword();
            return;
        } else {
            password_here.setText(received);
            password_here.setVisible(true);
        }
    }

}
