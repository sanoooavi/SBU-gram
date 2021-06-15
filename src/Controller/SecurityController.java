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

    public void AddSecondPassword(ActionEvent actionEvent) throws IOException {
        if ( savethepassword.getText().isEmpty()) {
            ShowInvalidFilling();
            return;
        }
            ClientManager.SaveTheSecondPassword(thisClient.getUserName(), savethepassword.getText());
            ShowHasSuccessfullymade();
            new PageLoader().load("timeLine");
    }


    private void ShowHasSuccessfullymade() {
        String title = "Success";
        String contentText = "Password saved successfully!";
        this.makeAndShowInformationDialog(title, contentText);
    }

    private void ShowInvalidFilling() {
        String title = "Error";
        String contentText = "Please Fill in the required fields";
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
