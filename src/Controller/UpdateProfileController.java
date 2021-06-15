package Controller;

import Client.ClientManager;
import Client.Gender;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateProfileController {
    public JFXTextField EmailField;
    public JFXTextField NameField;
    public JFXTextField LastNameField;
    public JFXTextField PhoneNumberField;
    public JFXTextField LocationField;
    public Ellipse ProfilePhotoField;
    public RadioButton malebutton;
    public RadioButton female_button;
    private Image image = null;
    private byte[] bytes = null;

    public void initialize() {
        ProfilePhotoField.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.profile.getProfilePhoto()))));
    }

    public void EditProfilePhoto(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if (file == null) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        bytes = fileInputStream.readAllBytes();
        image = new Image(new ByteArrayInputStream(bytes));
        ProfilePhotoField.setFill(new ImagePattern(image));
    }

    public void SaveNewProfile(ActionEvent actionEvent) {
        if (!isPhoneNumberValid(PhoneNumberField.getText())) return;
        if (malebutton.isSelected() && female_button.isSelected()) {
            ShowInvalidChooseGenderDialog();
            return;
        }
        String newName = NameField.getText();
        String newLastName = LastNameField.getText();
        String email = EmailField.getText();
        String phoneNumber = PhoneNumberField.getText();
        String location = LocationField.getText();
        if (LastNameField.getText().isEmpty()) {
            newLastName = "null";
        }
        if (NameField.getText().isEmpty()) {
            newName = "null";
        }
        if (EmailField.getText().isEmpty()) {
            email = "Not mentioned";
        }
        if (PhoneNumberField.getText().isEmpty()) {
            phoneNumber = "Not mentioned";
        }
        if (LocationField.getText().isEmpty()) {
            location = "Not mentioned";
        }
        Gender gender = selectGender();
        if (!ConfirmationAlert()) {
            return;
        }
        Profile prof = ClientManager.UpdateProfile(thisClient.getUserName(), email, newName, newLastName, phoneNumber, location, gender, bytes);
        thisClient.setProfile(prof);
    }

    private Gender selectGender() {
        Gender gender = null;
        if (malebutton.isSelected()) {
            gender = Gender.Male;
        } else if (female_button.isSelected()) {
            gender = Gender.Female;
        }
        return gender;
    }

    public boolean ConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Changing profile");
        alert.setHeaderText("Please confirm!");
        alert.setContentText("Are you sure want to Save these changes?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    public boolean isEmailValid(String email) {
        String regex = "";
        if (!Pattern.matches(regex, email)) {
            String title = "invalid email-address";
            String contentText = "Please use valid emails\n then try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "09([0-9]{9})";
        if (phoneNumber.isEmpty())
            return true;
        if (!Pattern.matches(regex, phoneNumber)) {
            String title = "invalid phoneNumber";
            String contentText = "Please enter valid number\n then try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    private void ShowInvalidChooseGenderDialog() {
        String title = "Wrong choose";
        String contentText = "You can not be both a man and a woman:|!!!!";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void BackToProfilePage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("ProfilebythisUser");
    }
}
