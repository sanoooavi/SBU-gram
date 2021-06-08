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
    Profile profile;

    public void initialize() {
        this.profile = ThatUser.getProfile();
        UserName.setText("  " + profile.getUsername());
        FullName.setText("  " + profile.getName() + " " + profile.getLastname());
        Location.setText("  " + "Iran");
        BirthDate.setText("  " + profile.getBirthDate().toString());
        UserProfileImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profile.getProfilePhoto()))));
        if (thisClient.getFollowings().contains(profile)) {
           // UnfollowButton.setVisible(true);
            FollowButton.setVisible(false);
        }
    }

    public void Follow(ActionEvent actionEvent) {
        if (profile == thisClient.getProfile()) {
            ShowInvalidFollowDialog();
            return;
        }
        ClientManager.follow(profile, thisClient.getProfile());
        //UnfollowButton.setVisible(true);
        FollowButton.setVisible(false);
    }

    public void Mute(ActionEvent actionEvent) {
    }

    public void Block(ActionEvent actionEvent) {
    }

    public void Unfollow(ActionEvent actionEvent) {
        ConfirmationAlert();
    }

    public void UnMute(ActionEvent actionEvent) {
    }

    public void UnBlock(ActionEvent actionEvent) {
    }

    public void ExitPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("SearchPage");
    }

    public void ShowInvalidFollowDialog() {
        String title = "Error in Following";
        String contentText = "You can not follow yourself!";
        this.makeAndShowInformationDialog(title, contentText);
    }
    public void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public void ConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unfollow contest");
        alert.setHeaderText("Please confirm!");
        alert.setContentText("Are you sure want to unfollow this user?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            UnfollowButton.setVisible(false);
            FollowButton.setVisible(true);
          ClientManager.Unfollow(profile, thisClient.getProfile());
        } else{return;
        }
    }


}
