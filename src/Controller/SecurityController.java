package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.Errors;
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
        if (savethepassword.getText().isEmpty()) {
            Errors.ShowInvalidFilling();
            return;
        }
        ClientManager.SaveTheSecondPassword(thisClient.getUserName(), savethepassword.getText());
        Errors.ShowHasSuccessfullymade();
        new PageLoader().load("timeLine");
    }


}
