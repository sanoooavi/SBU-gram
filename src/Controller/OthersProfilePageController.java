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
        ClientManager.UpdateData();
        this.profile = ThatUser.getProfile();
        UserName.setText("  " + profile.getUsername());
        FullName.setText("  " + profile.getName() + " " + profile.getLastname());
        Location.setText("  " + profile.getLocation());
        BirthDate.setText("  " + profile.getBirthDate().toString());
        UserProfileImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profile.getProfilePhoto()))));
        followers.setText(String.valueOf(profile.getFollowers().size()));
        followings.setText(String.valueOf(profile.getFollowings().size()));
        if (profile.getFollowers().contains(thisClient.getProfile())) {
            UnfollowButton.setVisible(true);
            FollowButton.setVisible(false);
        } else {
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
        }
        if (thisClient.getProfile().getMute().contains(profile)) {
            MuteButton.setVisible(false);
            UnMuteButton.setVisible(true);
        } else {
            MuteButton.setVisible(true);
            UnMuteButton.setVisible(false);
        }
        if (thisClient.getProfile().getBlocked().contains(profile)) {
            UnBlockButton.setVisible(true);
            BlockButton.setVisible(false);
        } else {
            UnBlockButton.setVisible(false);
            BlockButton.setVisible(true);
        }
        email_field.setText(" " + profile.getEmail());
        phone_number_field.setText(" " + profile.getPhoneNumber());
        gender_field.setText(" " + profile.getGender());
    }

    public void Follow(ActionEvent actionEvent) throws IOException {
        if (profile.getBlocked().contains(thisClient.getProfile())) {
            ShowInvalidFollowDialog();
            return;
        }
        thisClient.getFollowings().add(profile);
        profile.getFollowers().add(thisClient.getProfile());
        ClientManager.follow(profile.getUsername(), thisClient.getUserName());
        new PageLoader().load("ProfilePageOtherUsers");
    }

    public void Unfollow(ActionEvent actionEvent) throws IOException {
        if (ConfirmationAlert()) {
            thisClient.getFollowings().remove(profile);
            profile.getFollowers().remove(thisClient.getProfile());
            ClientManager.Unfollow(profile.getUsername(), thisClient.getUserName());
            new PageLoader().load("ProfilePageOtherUsers");
        }
    }


    public void Block(ActionEvent actionEvent) throws IOException {
        //if this user has followed you before
        ClientManager.Block(profile.getUsername(), thisClient.getUserName());
        thisClient.getProfile().getBlocked().add(profile);
        if (thisClient.getFollowers().contains(profile)) {
            ClientManager.Unfollow(thisClient.getUserName(), profile.getUsername());
            profile.getFollowings().remove(thisClient.getProfile());
            thisClient.getFollowers().remove(profile);
        }
        new PageLoader().load("ProfilePageOtherUsers");
    }

    public void UnBlock(ActionEvent actionEvent) throws IOException {
        ClientManager.UnBlock(profile.getUsername(), thisClient.getUserName());
        thisClient.getProfile().getBlocked().remove(profile);
        new PageLoader().load("ProfilePageOtherUsers");
    }

    public void Mute(ActionEvent actionEvent) throws IOException {
        ClientManager.Mute(profile.getUsername(), thisClient.getUserName());
        thisClient.getProfile().getMute().add(profile);
        new PageLoader().load("ProfilePageOtherUsers");
    }

    public void UnMute(ActionEvent actionEvent) throws IOException {
        ClientManager.UnMute(profile.getUsername(), thisClient.getUserName());
        thisClient.getProfile().getMute().remove(profile);
        new PageLoader().load("ProfilePageOtherUsers");
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
        ThatUser.setProfile(profile);
        new PageLoader().load("timePost");
    }

    private void ShowInvalidFollowDialog() {
        String title = "Blocked";
        String contentText = "You can not follow this user\n You are blocked!";
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
