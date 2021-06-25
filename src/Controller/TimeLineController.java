package Controller;

import Client.*;
import Model.PageLoader;
import Model.Post;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.*;

public class TimeLineController {
    public static Comparator<Post> timeCompare = (a, b) -> -1 * Long.compare(a.getTimerMil(), b.getTimerMil());
    public ListView<Post> PostList;

    /**
     *
     *
     *
     */

    @FXML
    public void initialize() {
        List<Post> shown = ClientManager.LoadingInfo();
        shown.sort(timeCompare);
        PostList.setItems(FXCollections.observableArrayList(shown));
        PostList.setCellFactory(PostList -> new PostItem());
    }

    public void GotoMenu(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("MainMenu");
    }

    public void AddPost(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("AddPost");
    }

    public void RefreshTimeLine(MouseEvent mouseEvent) throws IOException {
            new PageLoader().load("timeLine");
    }

    public void SearchForPeople(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("SearchPage");
    }
}
