package Controller;

import Model.Post;
import Whatever.Message;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ChatItem extends ListCell<Message> {
    @Override
    protected void updateItem(Message Message, boolean empty) {
        super.updateItem(Message, empty);
        if (Message != null) {
            try {
                setGraphic(new ChatItemController(Message).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            setGraphic(null);
        }
    }
}
