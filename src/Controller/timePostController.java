package Controller;

import Client.ClientManager;
import Client.thisClient;
import Model.PageLoader;
import Model.Post;
import Whatever.ThatUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class timePostController {
    public ListView <Post>PostList;
    @FXML
    public void initialize(){
        List<Post> shown= ClientManager.LoadingPersonalInfo();
        shown.sort(TimeLineController.timeCompare);
        PostList.setItems(FXCollections.observableArrayList(shown));
        PostList.setCellFactory(PostList->new PostItem());
    }
    public void BacktoInfoPage(MouseEvent mouseEvent) throws IOException {
        if(ThatUser.getUserName().equals(thisClient.getUserName())){
            new PageLoader().load("ProfilebythisUser");
        }
        else {
            new PageLoader().load("ProfilePageOtherUsers");
        }
    }
}
