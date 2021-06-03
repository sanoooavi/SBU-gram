package Controller;

import Client.Profile;
import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
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
        if ( username.isEmpty() || password.isEmpty() ) {
            InvalidLoginDialog();
            return;
        }
        Profile profile = ClientManager.login(username,password);
        if(profile == null){
            ShowInvalidLoginDialog();
            return;
        }
        thisClient.setProfile(profile);
        new PageLoader().load("timeLine");
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("SignUp");
    }

    public void Password_recovery(ActionEvent actionEvent) {
    }
    public void  InvalidLoginDialog(){
        String title = "Incomplete information";
        String contentText = "Please fill all of the required fields";
        makeAndShowInformationDialog( title, contentText );
    }
    public void ShowInvalidLoginDialog() {
        String title = "Error in login";
        String contentText = "invalid username or password\nTry again or sign up";
        this.makeAndShowInformationDialog( title, contentText );
    }
    public void makeAndShowInformationDialog(String title, String contentText ) {
        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setTitle( title );
        alert.setHeaderText( null );
        alert.setContentText( contentText );
        alert.showAndWait();
    }

    public void show_Pass(MouseEvent mouseEvent) {
        if (!password_visible.isVisible()) {
            password_visible.setVisible(true);
            Password_field.setVisible(false);
            password_visible.setText(Password_field.getText());
        }
        else {
            password_visible.setVisible(false);
            Password_field.setVisible(true);
            Password_field.setText(password_visible.getText());
        }
    }
}
