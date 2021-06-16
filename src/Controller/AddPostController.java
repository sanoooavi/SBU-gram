package Controller;


import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Whatever.Time;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;

public class AddPostController {
    public JFXTextArea description_field;
    public TextField post_title_field;
    public ImageView imageToPost;
    public ImageView defaultBackground;
    byte[] BytesOfPhotoPosted;
    Post currentPost = new Post();

    public void PublishPost(ActionEvent actionEvent) {
        if (hasEmpty()) return;
        currentPost.setTitle(post_title_field.getText());
        currentPost.setDescription(description_field.getText());
        currentPost.setWriter(thisClient.getProfile().getUsername());
        currentPost.setPublisher(thisClient.getProfile().getUsername());
        currentPost.setProfilePhoto(thisClient.getProfile().getProfilePhoto());
        currentPost.setPhoto(BytesOfPhotoPosted);
        currentPost.setTimeReleased(Time.getTime());
        currentPost.setTimerMil(Time.getMilli());
        ClientManager.PublishPost(currentPost);
        makeSuccessfulDialog();
        clearAfterPublish();
    }

    public void clearAfterPublish() {
        post_title_field.clear();
        description_field.clear();
        imageToPost.setVisible(false);
        defaultBackground.setVisible(true);
        BytesOfPhotoPosted=null;
        currentPost = new Post();
    }

    public boolean hasEmpty() {
        boolean hasEmpty = (description_field.getText().isEmpty() || post_title_field.getText().isEmpty());
        if (hasEmpty) showFillRequiredFieldsDialog();
        return hasEmpty;
    }

    public void makeSuccessfulDialog() {
        String title = "Successful";
        String contentText = "You have posted a new post";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void showFillRequiredFieldsDialog() {
        String title = "Incomplete information";
        String contentText = "Please fill all of the required fields";
        makeAndShowInformationDialog(title, contentText);
    }

    public void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void GoBackToPostList(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }

    public void ClearAll(ActionEvent actionEvent) {
        post_title_field.clear();
        description_field.clear();
        imageToPost.setVisible(false);
        defaultBackground.setVisible(true);
        BytesOfPhotoPosted=null;
    }

    public void AddImageToPost(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        if (file == null) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        BytesOfPhotoPosted = fileInputStream.readAllBytes();
        Image image = new Image(new ByteArrayInputStream(BytesOfPhotoPosted));
        imageToPost.setImage(image);
        defaultBackground.setVisible(false);
        imageToPost.setVisible(true);
    }
}
