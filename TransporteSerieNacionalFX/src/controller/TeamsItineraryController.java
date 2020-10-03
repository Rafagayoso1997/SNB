package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.TableView;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class TeamsItineraryController implements Initializable {

    @FXML
    private JFXListView<String> teamListView;

    @FXML
    private TableView itineraryTable;

    @FXML
    private JFXButton showItinerary;

    @FXML
    void displayItinerary(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
