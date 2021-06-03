package Controller;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import javafx.event.ActionEvent;
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
import java.nio.file.Paths;


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
    public ImageView theImagePosted;
    public ImageView frame;
    Post post;
    public PostItemController(Post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }
    public AnchorPane init() {
       UsernameLabel.setText(post.getWriter());
       PostTitle.setText(post.getTitle());
       PostDesc.setText(post.getDescription());
       UserProfilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
       if(post.getPhoto()!=null) {
           theImagePosted.setImage(new Image(new ByteArrayInputStream(post.getPhoto())));
           frame.setVisible(false);
       }
        return RootPage;
    }

    public void ViewProfile(ActionEvent actionEvent) {
    }

    public void Retweet(MouseEvent mouseEvent) {
    }

    public void ToComment(MouseEvent mouseEvent) {
    }

    public void RemoveLike(MouseEvent mouseEvent) {
    }

    public void Like(MouseEvent mouseEvent) {
        emptyHeart.setVisible(false);
        RedHeart.setVisible(true);
    }
}
