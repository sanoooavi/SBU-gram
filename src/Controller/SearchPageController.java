package Controller;

import Client.ClientManager;
import Client.Profile;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    public JFXTextField search_field;
    @FXML
    private TableView<Profile> table;
    @FXML
    private TableColumn<Profile, String> username;

    @FXML
    private TableColumn<Profile, String> name;
    @FXML
    private TableColumn<Profile, Integer> age;
    private ObservableList<Profile> masterData = FXCollections.observableArrayList();
    private ObservableList<Profile> filteredData = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setCellValueFactory(new PropertyValueFactory<Profile, String>("username"));
        name.setCellValueFactory(new PropertyValueFactory<Profile, String>("name"));
        age.setCellValueFactory(new PropertyValueFactory<Profile, Integer>("age"));
        masterData.addAll(ClientManager.LoadingTable());
        filteredData.addAll(masterData);
        masterData.addListener(new ListChangeListener<Profile>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Profile> change) {
                updateFilteredData();
            }
        });
        table.setItems(FXCollections.observableArrayList(filteredData));
        search_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                updateFilteredData();
            }
        });
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Profile p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }


    private boolean matchesFilter(Profile person) {
        String filterString = search_field.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (person.getName().toLowerCase().contains(lowerCaseFilterString)) {
            return true;
        } else if (person.getUsername().toLowerCase().contains(lowerCaseFilterString)) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Profile, ?>> sortOrder = new ArrayList<>(table.getSortOrder());
        table.getSortOrder().clear();
        table.getSortOrder().addAll(sortOrder);
    }


    public void GoFind(MouseEvent mouseEvent) {
    }

    public void clearSearch(MouseEvent mouseEvent) {
    }
}
