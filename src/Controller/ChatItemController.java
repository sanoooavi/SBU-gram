package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.Message;
import Whatever.ThatUser;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    public Label timeOftextright;
    public Label timeOftextleft;
    Message message;

    public ChatItemController(Message message) throws IOException {
        this.message = message;
        if (message.getSender().equals(thisClient.getUserName())) {
            new PageLoader().load("MyMessageIcon", this);
        } else {
            new PageLoader().load("OthersMessageIcon", this);
        }
    }

    public AnchorPane init() {
        if (message.getSender().equals(thisClient.getUserName())) {
            UserMessageright.setText(message.getTextMessage());
            UserProfileright.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
            timeOftextright.setText(message.getTime());
            return rightItem;
        }
        UserMessageleft.setText(message.getTextMessage());
        UserProfileleft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
        timeOftextleft.setText(message.getTime());
        return leftItem;
    }

    public void TrashTextOthers(MouseEvent mouseEvent) {
    }

    public void EditText(MouseEvent mouseEvent) {
    }

    public void TrashMessage(MouseEvent mouseEvent) {
        ClientManager.TrashMessage(message,thisClient.getUserName());
    }
}
