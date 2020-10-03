package controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import logic.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class TeamsItineraryController implements Initializable {

    private  Controller controller;
    public static int selectedCalendar;
    @FXML
    private JFXListView<String> teamsListView;

    @FXML
    private TableView<String> itineraryTable;

    @FXML
    private JFXButton showItinerary;

    private HomeController homeController;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedCalendar = CalendarController.selectedCalendar;

        controller = Controller.getSingletonController();
        ArrayList<Integer> teamsIndexes = controller.getConfigurations().get(selectedCalendar).getTeamsIndexes();
        System.out.println(teamsIndexes);
        List<String> teams = new ArrayList<>() ;
        for (int teamsIndex : teamsIndexes) {
            teams.add(controller.getTeams().get(teamsIndex));
        }

        teamsListView.setItems(FXCollections.observableArrayList(teams));
        teamsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void displayItinerary(ActionEvent event) {

        itineraryTable.getColumns().removeAll(itineraryTable.getColumns());
        ArrayList<Integer> selectedTeams = new ArrayList<>(teamsListView.getSelectionModel().getSelectedIndices());
        ArrayList<Date> calendar = controller.getCalendarsList().get(selectedCalendar);
        CalendarConfiguration configuration = controller.getConfigurations().get(selectedCalendar);

        ArrayList<ArrayList<Integer>> itinerary = controller.teamsItinerary(calendar,configuration);


        for (int selectedTeam : selectedTeams) {
            TableColumn col = new TableColumn<>(controller.getAcronyms().get(selectedTeam));
            col.setCellValueFactory(param -> new SimpleStringProperty(controller.getAcronyms().get(selectedTeam)));
            itineraryTable.getColumns().add(col);
        }
        

    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


}
