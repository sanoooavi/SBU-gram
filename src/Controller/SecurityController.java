package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SecurityController {

    public TextField savethepassword;
    public TextField getthepassword;
    public Label password_here;

    public void AddSecondPassword(ActionEvent actionEvent) {
        if(!savethepassword.getText().isEmpty()){
            if(thisClient.getSecondPassword()==null){
                ClientManager.SaveTheSecondPassword(thisClient.getUserName(),savethepassword.getText());
                ShowHasSuccessfullymade();
            }
            else {
                ShowHasTheSecPassword();
                return;
            }
        }
        else {
            ShowInvalidFilling();return;
        }
    }

    private void ShowHasSuccessfullymade() {
        String title = "Success";
        String contentText = "Password saved successfully!";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void GetThePassword(ActionEvent actionEvent) {
        if(!getthepassword.getText().isEmpty()) {
            String received = ClientManager.GetThePassword(thisClient.getUserName(),getthepassword.getText());
            if (received == null) {
                ShowNotHasTheSecPassword();
                return;
            } else {
                password_here.setText(received);
            }
        }
        else {
            ShowInvalidFilling();return;
        }
    }

    private void ShowInvalidFilling() {
        String title = "Error";
        String contentText = "Please Fill in the required fields";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void GotoTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }
    private void ShowHasTheSecPassword() {
        String title = "Warning";
        String contentText = "You already have completed this field";
        this.makeAndShowInformationDialog(title, contentText);
    }
    private void ShowNotHasTheSecPassword() {
        String title = "Warning";
        String contentText = "You didn't have the second password or you may have written the wrong password";
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
