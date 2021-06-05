package Controller;

import Client.*;
import Model.PageLoader;
import Model.Post;
import Server.Server;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeLineController {
    public ListView<Post> PostList;
    @FXML
    public void initialize(){
        List<Post>shown=ClientManager.LoadingInfo();
        PostList.setItems(FXCollections.observableArrayList(shown));
        PostList.setCellFactory(PostList->new PostItem());
    }
    public void GotoMenu(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("MainMenu");
    }
    public void AddPost(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("AddPost");
    }

    public void RefreshTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("SearchPage");
    }
}
