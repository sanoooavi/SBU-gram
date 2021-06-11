package Controller;

import Client.ClientManager;
import Client.thisClient;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.shape.Ellipse;

public class UpdateProfileController {
    public JFXTextField EmailField;
    public JFXTextField NameField;
    public JFXTextField LastNameField;
    public JFXTextField PhoneNumberField;
    public JFXTextField LocationField;
    public Ellipse ProfilePhotoField;

    public void EditProfilePhoto(ActionEvent actionEvent) {
        String email = EmailField.getText();
        String newName = NameField.getText();
        String newLastName = LastNameField.getText();
        String phoneNumber = PhoneNumberField.getText();
        String location = LocationField.getText();
        ClientManager.UpdateProfile(thisClient.getUserName(),email,newName,newLastName,phoneNumber,location);
    }

    public void SaveNewProfile(ActionEvent actionEvent) {
    }
}
