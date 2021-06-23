package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    public Label timeOftextleft;
    @FXML
    public AnchorPane RightPhotoItem;
    @FXML
    public ImageView ImageShownleft;
    @FXML
    public ImageView ImageShownRight;
    @FXML
    public AnchorPane LeftPhotoItem;
    @FXML
    public Circle ProfileRight;
    @FXML
    public Circle ProfileLeft;
    @FXML
    private TextField EditTextFieldRight;
    @FXML
    private Button EditMessageButton;

    Message message;

    public ChatItemController(Message message) throws IOException {
        this.message = message;
        thisClient.setProfile(ClientManager.GetProfile(thisClient.getUserName()));
        ThatUser.setProfile(ClientManager.GetProfile(ThatUser.getUserName()));
        if (message.getSender().equals(thisClient.getUserName())) {
            if (message instanceof PhotoMessage) {
                new PageLoader().load("MyChatImageIcon", this);
            } else {
                new PageLoader().load("MyMessageIcon", this);
            }
        } else {
            if (message instanceof PhotoMessage) {
                new PageLoader().load("OthersChatImageIcon", this);
            } else {
                new PageLoader().load("OthersMessageIcon", this);
            }
        }
    }

    public AnchorPane init() {
        if (message instanceof TextMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                UserMessageright.setText(((TextMessage) message).getText());
                UserProfileright.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
                timeOftextright.setText(message.getTime());
                return rightItem;
            } else {
                UserMessageleft.setText(((TextMessage) message).getText());
                UserProfileleft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
                timeOftextleft.setText(message.getTime());
                return leftItem;
            }
        } else if (message instanceof PhotoMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                ProfileRight.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
                ImageShownRight.setImage(new Image(new ByteArrayInputStream(((PhotoMessage) message).getPhoto())));
                return RightPhotoItem;
            } else {
                ProfileLeft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
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

    public void TrashTextOthers(MouseEvent mouseEvent) throws IOException {
        ClientManager.TrashMessage(message);
        new PageLoader().load("DirectPersonPage");
    }

    public void EditText(MouseEvent mouseEvent) {
        EditTextFieldRight.setVisible(true);
        EditMessageButton.setVisible(true);

    }

    public void TrashMessage(MouseEvent mouseEvent) throws IOException {
        ClientManager.TrashMessage(message);
        new PageLoader().load("DirectPersonPage");
    }
    @FXML
    void EditMessage(ActionEvent event) throws IOException {
       if(EditTextFieldRight.getText().isEmpty()){
           Errors.showFillRequiredFieldsDialog();
           afterEdit();
           return;
       }
       else {
           ClientManager.EditText(message,EditTextFieldRight.getText());
           afterEdit();
           new PageLoader().load("DirectPersonPage");
       }
    }
    public void afterEdit(){
        EditTextFieldRight.clear();
        EditTextFieldRight.setVisible(false);
        EditMessageButton.setVisible(false);
    }
}
