package Controller;

import Client.ClientManager;
import Client.Profile;
import Client.thisClient;
import Model.PageLoader;
import Whatever.Errors;
import Whatever.ThatUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Profile> tableview;
    @FXML
    private TableColumn<Profile, String> Username;
    @FXML
    private TableColumn<Profile, String> Name;
    @FXML
    private TableColumn<Profile, Integer> age;

    //observalble list to store data
    private final ObservableList<Profile> dataList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        dataList.addAll(ClientManager.LoadingTable());

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Profile> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(profile -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (profile.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (profile.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(profile.getAge()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Profile> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableview.setItems(sortedData);
    }

    public void clear(MouseEvent mouseEvent) {
        filterField.clear();
    }

    public void GotoProfile(MouseEvent mouseEvent) throws IOException {
        Profile prof = tableview.getSelectionModel().getSelectedItem();
        if (prof.equals(thisClient.getProfile())) {
            new PageLoader().load("ProfilebythisUser");
        } else {
            if (prof.getBlocked() != null) {
                if (prof.getBlocked().contains(thisClient.getProfile())) {
                    Errors.ShowBlockedDialog();
                    return;
                }
            }
            ThatUser.setProfile(prof);
            new PageLoader().load("ProfilePageOtherUsers");
        }
    }



    public void GoBackToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("timeLine");
    }
}
