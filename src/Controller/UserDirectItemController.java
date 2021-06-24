package Controller;

import Client.ClientManager;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Whatever.Message;
import Whatever.ThatUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private Label NotReadMessages;
    public AnchorPane rootPage;
    Profile profile;

    public UserDirectItemController(Profile profile) throws IOException {
        new PageLoader().load("UserIconChat", this);
        this.profile = profile;
    }

    public AnchorPane init() {
        thisClient.setProfile(ClientManager.GetProfile(thisClient.getUserName()));
        UserProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profile.getProfilePhoto()))));
        UsernameLabel.setText(profile.getUsername());
        NotReadMessages.setText(String.valueOf(profile.getNotSeen().get(thisClient.getUserName())));
        return rootPage;
    }

    public void StartChat(ActionEvent actionEvent) throws IOException {
        ThatUser.setProfile(ClientManager.GetProfile(profile.getUsername()));
        // ThatUser.setProfile(profile);
        new PageLoader().load("DirectPersonPage");
    }
}
