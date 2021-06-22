package Controller;

import Client.*;
import Model.PageLoader;
import Whatever.Errors;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class SignUpController {
    public TextField name_field;
    public TextField lastname_field;
    public TextField username_field;
    public PasswordField pass_field;
    public Circle circle;
    public DatePicker Birthdate;
    public ImageView defaultProfile;
    public TextField password_again;
    public RadioButton MaleButton;
    public RadioButton FemaleButton;
    private Image image = null;
    private byte[] bytes;

    public void SignUp(ActionEvent actionEvent) {
        if (hasEmptyField()) return;
        if (!Errors.isValidPassword(pass_field.getText(), password_again.getText())) return;
        if (!Errors.isValidUsername(username_field.getText())) return;
        if (!Errors.isValidBirth(Birthdate.getValue().getYear())) return;
        if (MaleButton.isSelected() && FemaleButton.isSelected()) {
            Errors.ShowInvalidChooseGenderDialog();
            return;
        }
        //profile seems valid
        Profile justCreatedProfile = this.makeProfileFromPageContent();
        thisClient.setProfile(justCreatedProfile);
        ClientManager.signUp(justCreatedProfile);
        Errors.showProfileCreatedDialog();
        try {
            new PageLoader().load("SecurityQuestion");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Profile makeProfileFromPageContent() {
        Profile newProfile = new Profile(username_field.getText());
        newProfile.setName(name_field.getText());
        newProfile.setPassword(pass_field.getText());
        if (lastname_field.getText() != null) {
            newProfile.setLastname(lastname_field.getText());
        }
        newProfile.setBirthDate((Birthdate.getValue()));
        if (MaleButton.isSelected()) {
            newProfile.setGender(Gender.Male);
        } else if (FemaleButton.isSelected()) {
            newProfile.setGender(Gender.Female);
        } else {
            newProfile.setGender(Gender.Not_Say);
        }
        if (image == null) {
            File file = new File("src/pic/prof-removebg-preview.png");
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            newProfile.setProfilePhoto(bytes);
        } else {
            newProfile.setProfilePhoto(bytes);
        }
        return newProfile;
    }


    public void addProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if (file == null) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        bytes = fileInputStream.readAllBytes();
        image = new Image(new ByteArrayInputStream(bytes));
        circle.setFill(new ImagePattern(image));
        circle.setVisible(true);
    }

    public void HadAccount(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }

    public boolean hasEmptyField() {
        if (username_field.getText().isEmpty() || pass_field.getText().isEmpty() || name_field.getText().isEmpty() || Birthdate.getValue() == null || password_again.getText().isEmpty()) {
            Errors.ErrorSignUpEmptyFields();
            return true;
        }
        return false;
    }


}
