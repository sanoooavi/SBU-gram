package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Server.Server;
import Whatever.Comment;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class PostItemController {
    @FXML
    public ImageView emptyHeart;
    @FXML
    public ImageView RedHeart;
    @FXML
    public Circle UserProfilePhoto;
    @FXML
    public Label UsernameLabel;
    @FXML
    public AnchorPane RootPage;
    @FXML
    public Label PostTitle;
    @FXML
    public Label PostDesc;
    @FXML
    public ImageView theImagePosted;
    @FXML
    public ImageView frame;
    @FXML
    private JFXTextArea comments_field;
    @FXML
    private Label commentslabel;
    @FXML
    Post post;
    @FXML
    private Button AddCommentButton;
    @FXML
    private Button DismissButton;


    public PostItemController(Post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }
    public AnchorPane init() {
        UsernameLabel.setText(post.getWriter());
        PostTitle.setText(post.getTitle());
        PostDesc.setText(post.getDescription());
        UserProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
        //if(post.getComments()!=null) {
            commentslabel.setText(post.getCommentsOnField());
       // }
        if (post.getPhoto() != null) {
            theImagePosted.setImage(new Image(new ByteArrayInputStream(post.getPhoto())));
            frame.setVisible(false);
        }
        if (Server.users != null) {
            boolean isLiked = ClientManager.LikePost(thisClient.getProfile(), this.post);
            if (isLiked) {
                emptyHeart.setVisible(false);
                RedHeart.setVisible(true);
            }
        }
        return RootPage;
    }

    public void ViewProfile(ActionEvent actionEvent) {
    }

    public void Retweet(MouseEvent mouseEvent) {
    }

    public void ToComment(MouseEvent mouseEvent) {
        commentslabel.setVisible(false);
        comments_field.setVisible(true);
        AddCommentButton.setVisible(true);
        DismissButton.setVisible(true);
    }

    @FXML
    void DismissTheComment(ActionEvent event) {
        afterComment();
    }

    @FXML
    void AddTheComment(ActionEvent event) throws IOException {
        if(comments_field.getText().isEmpty()){
           ShowInvalidCommentDialog();return;
        }
        Comment comment = new Comment(thisClient.getUserName(), comments_field.getText());
        ClientManager.AddComment(comment, post);
        afterComment();
        new PageLoader().load("timeLine");
    }

    public void afterComment() {
        comments_field.clear();
        commentslabel.setVisible(true);
        comments_field.setVisible(false);
        AddCommentButton.setVisible(false);
        DismissButton.setVisible(false);
    }

    public void Like(MouseEvent mouseEvent) {
        boolean HasLiked = ClientManager.LikePost(thisClient.getProfile(), this.post);
        if (HasLiked) {
            ShowInvalidLikeDialog();
            return;
        } else {
            emptyHeart.setVisible(false);
            RedHeart.setVisible(true);
        }
    }

    public void ShowInvalidLikeDialog() {
        String title = "Error in Liking";
        String contentText = "You must have liked this post before\nOr this user has deleted account";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void ShowInvalidCommentDialog() {
        String title = "Error in adding comment";
        String contentText = "The comment part is null\nOr this user has deleted account";
        this.makeAndShowInformationDialog(title, contentText);
    }

    public void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
