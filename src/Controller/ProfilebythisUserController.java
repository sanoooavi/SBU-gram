package Controller;

import Client.ClientManager;
import Client.Gender;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Whatever.ThatUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ProfilebythisUserController {
    public Circle UserProfileImage;
    public Label BirthDate;
    public Label Location;
    public Label UserName;
    public Label FullName;
    public Label followers;
    public Label followings;
    public Label phone_number_field;
    public Label gender_field;
    public Label email_field;

    public void initialize() {
        Profile Mine = ClientManager.GetProfile(thisClient.getUserName());
        thisClient.setProfile(Mine);
        UserName.setText("  " + thisClient.getUserName());
        FullName.setText("  " + thisClient.getProfile().getName() + " " + thisClient.getProfile().getLastname());
        Location.setText("  " + thisClient.getLocation());
        BirthDate.setText("  " + thisClient.getProfile().getBirthDate().toString());
        UserProfileImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
        followers.setText(String.valueOf(thisClient.getFollowersNum()));
        followings.setText(String.valueOf(thisClient.getFollowingsNum()));
        email_field.setText(" " + thisClient.getEmail());
        phone_number_field.setText(" " + thisClient.getPhoneNumber());
        gender_field.setText(" " + thisClient.getGender());
    }

    public void UpdateAccount(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("UpdateProfilePage");
    }

    public void DeleteAccount(ActionEvent actionEvent) {
    }

    public void ExitPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }

    public void ShowMyPosts(ActionEvent actionEvent) throws IOException {
        ThatUser.setProfile(thisClient.getProfile());
        new PageLoader().load("timePost");
    }

    public void Refresh(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("ProfilebythisUser");
    }
}
