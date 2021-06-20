package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
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
        if(UsernameField.getText().isEmpty()||OldPasswordField.getText().isEmpty()||newPasswordField.getText().isEmpty()){
            ShowInvalidFilling();
            return;
        }
        if(!SignUpController.isValidPassword(newPasswordField.getText(),newPasswordField.getText())){
            return;
        }
        boolean CanChange=ClientManager.ChangePassword(UsernameField.getText(),OldPasswordField.getText(),newPasswordField.getText());
        if(CanChange){
            ChangedPasswordComplete();
            return;
        }
        else {
            CanNotChangePassword();
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
            ShowInvalidFilling();
            return;
        }
        String received = ClientManager.GetThePassword(UsernameField.getText(), getthepassword.getText());
        if (received == null) {
            ShowNotHasTheSecPassword();
            return;
        } else {
            password_here.setText(received);
            password_here.setVisible(true);
        }
    }
    private void ChangedPasswordComplete() {
        String title = "Success";
        String contentText = "You have changed your password";
        this.makeAndShowInformationDialog(title, contentText);
    }
    private void CanNotChangePassword() {
        String title = "Error";
        String contentText = "The old password wasn't correct\nPlease try again";
        this.makeAndShowInformationDialog(title, contentText);
    }

    private void ShowInvalidFilling() {
        String title = "Error";
        String contentText = "Please Fill in the required fields";
        this.makeAndShowInformationDialog(title, contentText);
    }

    private void ShowNotHasTheSecPassword() {
        String title = "Warning";
        String contentText = "There is no such name \nor you may have entered the wrong password";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
