package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class DirectPersonPageController {
    public Circle ProfilePhoto;
    public Label UsernameLabel;
    public ListView<Message> ListViewChats;
    public TextField MessageField;
    public Pane AttachPage;
    byte[] ToSendPhoto;
    byte[] ToSendVoice;
    public static Comparator<Message> timeCompare = (a, b) -> -1 * Long.compare(a.getTimeMilli(), b.getTimeMilli());

    public void initialize() {
        thisClient.setProfile(ClientManager.GetProfile(thisClient.getUserName()));
        ThatUser.setProfile(ClientManager.GetProfile(ThatUser.getUserName()));
        UsernameLabel.setText(ThatUser.getUserName());
        ProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream((ThatUser.getProfile().getProfilePhoto())))));
        List<Message> shown = ClientManager.LoadingChatInfo();
        shown.sort(timeCompare);
        ListViewChats.setItems(FXCollections.observableArrayList(shown));
        ListViewChats.setCellFactory(ListViewChats -> new ChatItem());
    }

    public void SendMessage(MouseEvent mouseEvent) throws IOException {
        Message message;
        if (!MessageField.getText().isEmpty()) {
            message = new TextMessage(MessageField.getText(), Time.getMilli(), Time.getTime());
        } else if (ToSendVoice != null) {
            message = new VoiceMessage(ToSendVoice, Time.getMilli(), Time.getTime());
        } else if (ToSendPhoto != null) {
            message = new PhotoMessage(ToSendPhoto, Time.getMilli(), Time.getTime());
        } else {
            return;
        }
        message.setSender(thisClient.getUserName());
        message.setReceiver(ThatUser.getUserName());
        ClientManager.SendMessage(message);
        MessageField.clear();
        ToSendVoice = null;
        ToSendPhoto = null;
        AttachPage.setVisible(false);
        new PageLoader().load("DirectPersonPage");
    }

    public void ExitPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("ChatPage");
    }

    public void SendPhotoMessage(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if (file == null) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ToSendPhoto = fileInputStream.readAllBytes();
    }

    public void SendVoiceMessage(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if (file == null) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ToSendVoice = fileInputStream.readAllBytes();
    }

    public void Attach(MouseEvent mouseEvent) {
        AttachPage.setVisible(true);
    }

    public void Refresh(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("DirectPersonPage");
    }
}
