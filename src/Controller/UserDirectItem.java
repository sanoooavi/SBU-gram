package Controller;

import Client.Profile;
import Model.Post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserDirectItem extends ListCell<Profile> {
    @Override
    protected void updateItem(Profile profile, boolean empty) {
        super.updateItem(profile, empty);
        if (profile!= null) {
            try {
                setGraphic(new UserDirectItemController(profile).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            setGraphic(null);
        }
    }
}
