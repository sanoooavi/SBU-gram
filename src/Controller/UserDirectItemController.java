package Controller;

import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Whatever.ThatUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class UserDirectItemController {

    public Circle UserProfilePhoto;
    public Label messageLabel;
    public Label UsernameLabel;
    public AnchorPane rootPage;
    Profile profile;

    public UserDirectItemController(Profile profile) throws IOException {
        new PageLoader().load("UserIconChat", this);
        this.profile = profile;
    }

    public AnchorPane init() {
        UserProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profile.getProfilePhoto()))));
        UsernameLabel.setText(profile.getUsername());
        return rootPage;
    }

    public void StartChat(ActionEvent actionEvent) throws IOException {
        ThatUser.setProfile(profile);
        new PageLoader().load("DirectPersonPage");
    }
}
