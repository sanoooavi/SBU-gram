package Controller;

import Client.ClientManager;
import Client.Profile;
import Model.Post;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.List;

public class ProfilesDirectController {
    public ListView<Profile> UserLists;

    public void initialize(){
        List<Profile> shown= ClientManager.LoadingDirectInfo();
        UserLists.setItems(FXCollections.observableArrayList(shown));
        UserLists.setCellFactory(UserLists->new UserDirectItem());
    }


}
