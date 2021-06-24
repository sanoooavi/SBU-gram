package Controller;

import Client.ClientManager;
import Client.Profile;
import Model.PageLoader;
import Whatever.Errors;
import Whatever.Message;
import Whatever.ThatUser;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class ProfilesDirectController {
    public ListView<Profile> UserLists;
    public TextField SearchField;

    public void initialize() {
        List<Profile> shown = ClientManager.LoadingDirectInfo();
        UserLists.setItems(FXCollections.observableArrayList(shown));
        UserLists.setCellFactory(UserLists -> new UserDirectItem());
    }

    public void BackToMenu(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("MainMenu");
    }

    public void SearchForUser(MouseEvent mouseEvent) throws IOException {
        if (SearchField.getText().isEmpty()) {
            Errors.ShowInvalidFilling();
            return;
        } else {
            Boolean Exist = ClientManager.SearchForChat(SearchField.getText());
            if (Exist == null) {
                Errors.ShowInvalidUserDialog();
                return;
            } else {
                ThatUser.setProfile(ClientManager.GetProfile(SearchField.getText()));
                new PageLoader().load("DirectPersonPage");
            }
        }
    }
}
