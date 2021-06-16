package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.Post;
import Whatever.Message;
import Whatever.ThatUser;
import Whatever.Time;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.util.List;

public class DirectPersonPageController {
    public Circle ProfilePhoto;
    public Label UsernameLabel;
    public ListView<Message> ListViewChats;
    public TextField MessageField;


    public void initialize() {
        UsernameLabel.setText(ThatUser.getUserName());
        ProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream((ThatUser.getProfile().getProfilePhoto())))));
        List<Message> shown = ClientManager.LoadingChatInfo();
        ListViewChats.setItems(FXCollections.observableArrayList(shown));
        ListViewChats.setCellFactory(ListViewChats -> new ChatItem());
    }

    public void SendMessage(MouseEvent mouseEvent) {
        Message message = new Message(MessageField.getText(), Time.getTime());
        message.setSender(thisClient.getUserName());
        message.setReceiver(ThatUser.getUserName());
        ClientManager.SendMessage(message, thisClient.getUserName(), ThatUser.getUserName());
    }

    public void ExitPage(MouseEvent mouseEvent) {
    }
}
