package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void LogOut(ActionEvent actionEvent) {
          PageLoader.logout();
    }

    public void GoToProfilePage(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ProfilebythisUser");
    }

    public void SecretChat(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ChatPage");
    }

    public void AddPost(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("AddPost");
    }

    public void backToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }

    public void AboutUs(ActionEvent actionEvent) {
    }
}
