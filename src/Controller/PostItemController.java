package Controller;

import Client.ClientManager;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Server.Server;
import Whatever.Comment;
import Whatever.Errors;
import Whatever.ThatUser;
import Whatever.Time;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


public class PostItemController {
    @FXML
    public ImageView emptyHeart;
    public Circle UserProfilePhoto;
    public Label UsernameLabel;
    public AnchorPane RootPage;
    public Label PostTitle;
    public Label PostDesc;
    public ImageView theImagePosted;
    public ImageView frame;
    public Label commentslabel;
    public JFXTextArea comments_field;
    //   public ImageView RedHeart;
    public Button AddCommentButton;
    public Button DismissButton;
    public Label LikesNumber;
    public Label PostPublisher;
    public Label ReleaseTime;
    @FXML
    private Label RepostNumber;
    Post post;

    public PostItemController(Post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        UsernameLabel.setText(post.getWriter());
        PostTitle.setText(post.getTitle());
        PostDesc.setText(post.getDescription());
        UserProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(post.getProfilePhoto()))));
        commentslabel.setText(post.getCommentsOnField());
        ReleaseTime.setText(post.getTimeReleased());
        PostPublisher.setText(post.getPublisher());
        if (post.getPhoto() != null) {
            theImagePosted.setImage(new Image(new ByteArrayInputStream(post.getPhoto())));
            frame.setVisible(false);
        }
        LikesNumber.setText(String.valueOf(post.getLikes().size()));
        RepostNumber.setText(String.valueOf(post.getRepost().size()));
        boolean isLiked = post.getLikes().contains(thisClient.getProfile());
        if (isLiked) {
            emptyHeart.setImage(new Image("/pic/afterlike.png"));
        }
        return RootPage;
    }

    public void ViewProfile(ActionEvent actionEvent) throws IOException {
        Profile profile = ClientManager.getInfo(post.getWriter(), thisClient.getUserName());
        if (profile == null) {
            Errors.ShowInvalidViewProfileDialog();
            return;
        }
        if (profile.equals(thisClient.getProfile())) {
            new PageLoader().load("ProfilebythisUser");
        } else {
            if (profile.getBlocked() != null) {
                if (profile.getBlocked().contains(thisClient.getProfile())) {
                    Errors.ShowBlockedDialog();
                    return;
                }
            }
            ThatUser.setProfile(profile);
            new PageLoader().load("ProfilePageOtherUsers");
        }
    }


    public void Retweet(MouseEvent mouseEvent) {
        if (post.getPublisher().equals(thisClient.getUserName())) {
            Errors.ShowInvalidRePostDialog();
            return;
        }
        if (post.getRepost().contains(thisClient.getProfile())) {
            Errors.ShowInvalidRePostDialog();
            return;
        }
        ClientManager.rePost(post, thisClient.getUserName());
        post.getRepost().add(thisClient.getProfile());
        Errors.ShowSuccessfulRepostDialog();
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
        if (comments_field.getText().isEmpty()) {
            Errors.ShowInvalidCommentDialog();
            return;
        }
        Comment comment = new Comment(thisClient.getUserName(), comments_field.getText());
        ClientManager.AddComment(comment, post);
        post.getComments().add(comment);
        afterComment();
    }

    public void afterComment() {
        comments_field.clear();
        commentslabel.setVisible(true);
        comments_field.setVisible(false);
        AddCommentButton.setVisible(false);
        DismissButton.setVisible(false);
    }

    public void Like(MouseEvent mouseEvent) throws IOException {
        boolean HasLiked = ClientManager.LikePost(thisClient.getUserName(), this.post);
        if (HasLiked) {
            Errors.ShowInvalidLikeDialog();
            return;
        } else {
            post.getLikes().add(thisClient.getProfile());
            emptyHeart.setImage(new Image("/pic/afterlike.png"));
        }
    }
}
