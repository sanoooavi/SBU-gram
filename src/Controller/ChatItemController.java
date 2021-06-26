package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Whatever.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ChatItemController {

    public AnchorPane leftItem;
    @FXML
    public AnchorPane rightItem;
    @FXML
    public Circle UserProfileleft;
    @FXML
    public Circle UserProfileright;
    @FXML
    public Label UserMessageleft;
    @FXML
    public Label UserMessageright;
    @FXML
    public Label timeOftextright;
    @FXML
    public Label timeOftextleft;
    @FXML
    public AnchorPane RightPhotoItem;
    @FXML
    public AnchorPane LeftPhotoItem;
    @FXML
    public ImageView ImageShownleft;
    @FXML
    public ImageView ImageShownRight;
    @FXML
    public Circle ProfileRight;
    @FXML
    public Circle ProfileLeft;
    @FXML
    private Label timeImageMessage;
    @FXML
    private Label timeImageMessageLeft;
    @FXML
    private TextField EditTextFieldRight;
    @FXML
    private Button EditMessageButton;
    @FXML
    private AnchorPane MyMusicPage;
    @FXML
    private Label MusicNameLabelRight;
    Message message;
    @FXML
    Media media;
    @FXML
    MediaPlayer mediaPlayer;
    @FXML
    private Circle UserProfilerightAudio;

    @FXML
    private AnchorPane OthersMusicPage;
    @FXML
    private Circle UserProfilerightAudioLeft;
    @FXML
    private Label timeVoiceOthers;
    @FXML
    private Label timeVoiceMine;
    @FXML
    private Slider volumeSliderRight;
    @FXML
    private ProgressBar songMineProgressbar;
    @FXML
    private Label MusicNameLabelleft;
    @FXML
    private Slider volumeSliderleft;

    public ChatItemController(Message message) throws IOException {
        this.message = message;
        thisClient.setProfile(ClientManager.GetProfile(thisClient.getUserName()));
        ThatUser.setProfile(ClientManager.GetProfile(ThatUser.getUserName()));
        if (message.getSender().equals(thisClient.getUserName())) {
            if (message instanceof PhotoMessage) {
                new PageLoader().load("MyChatImageIcon", this);
            } else if (message instanceof TextMessage) {
                new PageLoader().load("MyMessageIcon", this);
            } else {
                new PageLoader().load("MySongMessage", this);
            }
        } else {
            if (message instanceof PhotoMessage) {
                new PageLoader().load("OthersChatImageIcon", this);
            } else if (message instanceof TextMessage) {
                new PageLoader().load("OthersMessageIcon", this);
            } else {
                new PageLoader().load("OthersMusicMessage", this);
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
                timeImageMessage.setText(message.getTime());
                return RightPhotoItem;
            } else {
                ProfileLeft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
                ImageShownleft.setImage(new Image(new ByteArrayInputStream(((PhotoMessage) message).getPhoto())));
                timeImageMessageLeft.setText(message.getTime());
                return LeftPhotoItem;
            }
        } else if (message instanceof VoiceMessage) {
            if (message.getSender().equals(thisClient.getUserName())) {
                UserProfilerightAudio.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
                media = new Media(((VoiceMessage) message).getVoice().toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MusicNameLabelRight.setText(((VoiceMessage) message).getVoice().getName());
                volumeSliderRight.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        mediaPlayer.setVolume(volumeSliderRight.getValue() * 0.01);
                    }
                });
                timeVoiceMine.setText(message.getTime());
                return MyMusicPage;
            } else {
                UserProfilerightAudioLeft.setFill(new ImagePattern(new Image(new ByteArrayInputStream(ThatUser.getProfile().getProfilePhoto()))));
                media = new Media(((VoiceMessage) message).getVoice().toURI().toString());
                MusicNameLabelleft.setText(((VoiceMessage) message).getVoice().getName());
                volumeSliderleft.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        mediaPlayer.setVolume(volumeSliderleft.getValue() * 0.01);
                    }
                });
                mediaPlayer = new MediaPlayer(media);
                timeVoiceOthers.setText(message.getTime());
                return OthersMusicPage;
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
        if (EditTextFieldRight.getText().isEmpty()) {
            Errors.showFillRequiredFieldsDialog();
            afterEdit();
            return;
        } else {
            ClientManager.EditText(message, EditTextFieldRight.getText());
            afterEdit();
            new PageLoader().load("DirectPersonPage");
        }
    }

    public void afterEdit() {
        EditTextFieldRight.clear();
        EditTextFieldRight.setVisible(false);
        EditMessageButton.setVisible(false);
    }

    @FXML
    void PauseMusic(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    void PlayMusic(ActionEvent event) {
        mediaPlayer.play();
    }

    @FXML
    void ResetMusic(ActionEvent event) {
        songMineProgressbar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0.0));
    }

    @FXML
    void TrashImageMessage(MouseEvent event) throws IOException {
        ClientManager.TrashMessage(message);
        new PageLoader().load("DirectPersonPage");
    }
}
