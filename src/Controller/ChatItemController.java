package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ChatItemController {
    @FXML
    public Circle UserProfileleft;
    @FXML
    public Label UserMessageleft;
    @FXML
    public Circle UserProfileright;
    @FXML
    public Label UserMessageright;
    @FXML
    public AnchorPane leftItem;
    @FXML
    public AnchorPane rightItem;
    @FXML
    public Label timeOftextright;
    public Label timeOftextleft;
    public AnchorPane RightPhotoItem;
    public ImageView ImageShownleft;
    public ImageView ImageShownRight;
    public AnchorPane LeftPhotoItem;
    public Circle UserProfilerightImageIcon;
    public Circle UserProfileleftImageIcon;
    Message message;

    public ChatItemController(Message message) throws IOException {
        this.message = message;
        if(message instanceof TextMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                new PageLoader().load("MyMessageIcon", this);
            } else {
                new PageLoader().load("OthersMessageIcon", this);
            }
        }
        if(message instanceof PhotoMessage){
            if (message.getSender().equals(thisClient.getUserName())) {
                new PageLoader().load("MyImageIcon", this);
            } else {
                new PageLoader().load("OthersImageIcon", this);
            }
        }
    }

    public AnchorPane init() {
        if (message instanceof TextMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                UserMessageright.setText(((TextMessage) message).getText());
                UserProfileright.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
                timeOftextright.setText(String.valueOf(message.getTimeMilli()));
                return rightItem;
            } else {
                UserMessageleft.setText(((TextMessage) message).getText());
                UserProfileleft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
                timeOftextleft.setText(String.valueOf(message.getTimeMilli()));
                return leftItem;
            }
        } else if (message instanceof PhotoMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                UserProfilerightImageIcon.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
                ImageShownRight.setImage(new Image(new ByteArrayInputStream(((PhotoMessage) message).getPhoto())));
                return RightPhotoItem;
            } else {
                UserProfileleftImageIcon.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
                ImageShownleft.setImage(new Image(new ByteArrayInputStream(((PhotoMessage) message).getPhoto())));
                return LeftPhotoItem;
            }
        } else if (message instanceof VoiceMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
            } else {
            }
        }
        return null;
    }

    public void TrashTextOthers(MouseEvent mouseEvent) {
    }

    public void EditText(MouseEvent mouseEvent) {
    }

    public void TrashMessage(MouseEvent mouseEvent) {
        ClientManager.TrashMessage(message, thisClient.getUserName());
    }
}
