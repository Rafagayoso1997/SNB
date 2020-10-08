package controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private JFXListView teamsListView;

    @FXML
    private TableView<ObservableList> itineraryTable;

    @FXML
    private JFXButton showItinerary;

    private HomeController homeController;

    private ObservableList<ObservableList> data;



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
        data = FXCollections.observableArrayList();

        itineraryTable.getColumns().removeAll(itineraryTable.getColumns());
        itineraryTable.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        ArrayList<Integer> selectedTeams = new ArrayList<>(teamsListView.getSelectionModel().getSelectedIndices());
        ArrayList<Date> calendar = controller.getCalendarsList().get(selectedCalendar);
        CalendarConfiguration configuration = controller.getConfigurations().get(selectedCalendar);

        ArrayList<ArrayList<Integer>> itinerary = controller.teamsItinerary(calendar,configuration);


        /*
        Poner el encabezado de las columnas y el valor que va dentro 
         */
        int index=0;
        for (int selectedTeam : selectedTeams) {
            final int j= index;
            TableColumn col = new TableColumn(controller.getAcronyms().get(selectedTeam));
            col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                    param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            index++;
            itineraryTable.getColumns().add(col);
        }


        for(int i=0; i < itinerary.size();i++){
            ObservableList<String> row = FXCollections.observableArrayList();
            ArrayList<Integer> current = itinerary.get(i);
            for (int selectedTeam : selectedTeams) {
                row.add(controller.getAcronyms().get(current.get(selectedTeam)));
            }
            data.add(row);
        }

        itineraryTable.setItems(data);

        /*for (int selectedTeam : selectedTeams) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                //Iterate Column
                row.add(rs.getString(i));
            }
            System.out.println("Row [1] added "+row );
            data.add(row);
           /* ArrayList<Integer> currentItinerary = itinerary.get(selectedTeam);
            for(int teamIndex: currentItinerary){
                String team = controller.getAcronyms().get(teamIndex);
                itineraryTable.getItems().add(new TeamItineraryName(team));
            }*/
        //}
        

    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


}
