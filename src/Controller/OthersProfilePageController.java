package Controller;

import Client.ClientManager;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Whatever.ThatUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class OthersProfilePageController {
    public Button FollowButton;
    public Button MuteButton;
    public Button BlockButton;
    public Button UnfollowButton;
    public Button UnBlockButton;
    public Button UnMuteButton;
    public Label FullName;
    public Label BirthDate;
    public Label Location;
    public Label UserName;
    public Circle UserProfileImage;
    public Label followers;
    public Label followings;
    public AnchorPane RootPage;
    public Label phone_number_field;
    public Label gender_field;
    public Label email_field;
    Profile profile;

    public void initialize() {
        this.profile = ThatUser.getProfile();
        UserName.setText("  " + profile.getUsername());
        FullName.setText("  " + profile.getName() + " " + profile.getLastname());
        Location.setText("  " + profile.getLocation());
        BirthDate.setText("  " + profile.getBirthDate().toString());
        UserProfileImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profile.getProfilePhoto()))));
        followers.setText(String.valueOf(profile.getFollowers().size()));
        followings.setText(String.valueOf(profile.getFollowings().size()));
        if (thisClient.getFollowings().contains(profile)) {
            UnfollowButton.setVisible(true);
            FollowButton.setVisible(false);
        } else {
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
        }
        email_field.setText(" " + profile.getEmail());
        phone_number_field.setText(" " + profile.getPhoneNumber());
        gender_field.setText(" " + profile.getGender());
    }

    public void Follow(ActionEvent actionEvent) {
        if (profile.getBlockedByYou().contains(thisClient.getProfile())){
            ShowInvalidFollowDialog();
            return;
        }
        thisClient.getFollowings().add(profile);
        profile.getFollowers().add(thisClient.getProfile());
        ClientManager.follow(profile.getUsername(), thisClient.getUserName());
        UnfollowButton.setVisible(true);
        FollowButton.setVisible(false);
    }

    public void Mute(ActionEvent actionEvent) {
    }

    public void Block(ActionEvent actionEvent) {
        ClientManager.Block(profile.getUsername(), thisClient.getUserName());
        UnBlockButton.setVisible(true);
        BlockButton.setVisible(false);
        if (thisClient.getFollowings().contains(profile)){
            profile.getFollowers().remove(thisClient.getProfile());
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
            ClientManager.Unfollow(profile.getUsername(), thisClient.getUserName());
        }
    }

    public void Unfollow(ActionEvent actionEvent) {
        if (ConfirmationAlert()) {
            thisClient.getFollowings().remove(profile);
            profile.getFollowers().remove(thisClient.getProfile());
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
            ClientManager.Unfollow(profile.getUsername(), thisClient.getUserName());
        }
    }

    public void UnMute(ActionEvent actionEvent) {
    }

    public void UnBlock(ActionEvent actionEvent) {
    }

    public void ExitPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }

    public boolean ConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unfollow contest");
        alert.setHeaderText("Please confirm!");
        alert.setContentText("Are you sure want to unfollow this user?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
            return true;
        }
        return false;
    }


    public void ShowMyPosts(ActionEvent actionEvent) throws IOException {
        if (profile.getBlockedByYou().contains(thisClient.getProfile())) {
            ShowInvalidShowPostDialog();
            return;
        }
        ThatUser.setProfile(profile);
        new PageLoader().load("timePost");
    }
    private void ShowInvalidFollowDialog() {
        String title = "Blocked";
        String contentText = "You can not follow this user\n You are blocked!";
        this.makeAndShowInformationDialog(title, contentText);
    }
    private void ShowInvalidShowPostDialog() {
        String title = "Blocked";
        String contentText = "You can not this user's posts\n You are blocked!";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public static void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
