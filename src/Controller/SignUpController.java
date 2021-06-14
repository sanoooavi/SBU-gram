package Controller;

import Client.*;
import Model.PageLoader;
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
    private byte[]bytes;

    public void SignUp(ActionEvent actionEvent) {
        if (hasEmptyField()) return;
        if (!isValidPassword(pass_field.getText(), password_again.getText())) return;
        if (!isValidUsername(username_field.getText())) return;
        if(!isValidBirth(Birthdate.getValue().getYear()))return;
        if(MaleButton.isSelected() && FemaleButton.isSelected()){
            ShowInvalidChooseGenderDialog();
            return;
        }
        //profile seems valid
        Profile justCreatedProfile = this.makeProfileFromPageContent();
        thisClient.setProfile(justCreatedProfile);
        ClientManager.signUp(justCreatedProfile);
        showProfileCreatedDialog();
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
        if(MaleButton.isSelected()){
            newProfile.setGender(Gender.Male);
        }
        else if(FemaleButton.isSelected()){
            newProfile.setGender(Gender.Female);
        }
        else {
            newProfile.setGender(Gender.Not_Say);
        }
        if(image==null){
            File file = new File("src/pic/prof-removebg-preview.png");
            try {
                bytes= Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            newProfile.setProfilePhoto(bytes);
        }
        else {
            newProfile.setProfilePhoto(bytes);
        }
        return newProfile;
    }



    public void addProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if(file==null){
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
            ErrorEmptyFields();
            return true;
        }
        return false;
    }

    public void ErrorEmptyFields() {
        String title = "Error in SignUp";
        String contentText = "Please Fill in the required fields";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void showProfileCreatedDialog() {
        String title = "Success";
        String contentText = "profile created successfully!";
        this.makeAndShowInformationDialog(title, contentText);
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

    public boolean isValidUsername(String username) {
        boolean exists = ClientManager.isUserNameExists(username_field.getText());
        if (exists) {
            String title = "Failed to create profile";
            String contentText = "Username already exists, choose another one!";
            this.makeAndShowInformationDialog(title, contentText);
        }
        return !exists;
    }
    private boolean isValidPassword(String text, String text2) {
        if (!text.equals(text2)) {
            String title = "invalid password";
            String contentText = "The passwords are not the same \nPlease try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        String regex = "^[a-zA-Z0-9]{8,}";
        if (!Pattern.matches(regex, text)) {
            String title = "invalid password";
            String contentText = "The password is at least 8 letters long and includes numbers and letters Please try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }
    public boolean isValidBirth(int year){
        if (  (2021 - (year)) < 13){
            String title = "Age warning";
            String contentText = "you should be at least 13\nplease wait few years :D";
            this.makeAndShowInformationDialog( title, contentText );
            return false;
        }
        return true;
    }

}
