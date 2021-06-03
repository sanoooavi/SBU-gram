package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void LogOut(ActionEvent actionEvent) {
    }

    public void GoToProfilePage(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ProfilebythisUser");
    }

    public void SecretChat(ActionEvent actionEvent) {
    }

    public void AddPost(ActionEvent actionEvent) {
    }

    public void backToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }

    public void AboutUs(ActionEvent actionEvent) {
    }
}
