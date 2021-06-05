package Controller;

import Client.thisClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;

public class ProfilebythisUserController {
    public Circle UserProfileImage;
    public Label BirthDate;
    public Label Location;
    public Label UserName;
    public Label FullName;
    public Label followers;
    public Label followings;

    public void initialize(){
        UserName.setText("  "+thisClient.getProfile().getUsername());
        FullName.setText("  "+thisClient.getProfile().getName()+" "+thisClient.getProfile().getLastname());
        Location.setText("  "+"Iran");
        BirthDate.setText("  "+thisClient.getProfile().getBirthDate().toString());
        UserProfileImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(thisClient.getProfile().getProfilePhoto()))));
    }

    public void UpdateAccount(ActionEvent actionEvent) {
    }

    public void DeleteAccount(ActionEvent actionEvent) {
    }

    public void ExitPage(MouseEvent mouseEvent) {
    }
}
