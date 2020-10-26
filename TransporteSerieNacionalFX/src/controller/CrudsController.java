package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import logic.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrudsController implements Initializable {

    private HomeController homeController;

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXTextField acronymTextField;

    @FXML
    private Spinner<Integer> distanceSpinner;

    @FXML
    private JFXButton addModifyButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton showDataBtn;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXComboBox<String> teamsComboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> comboTeams = FXCollections.observableArrayList(Controller.getSingletonController().getTeams());
        distanceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        distanceSpinner.getValueFactory().setValue(1);
       teamsComboBox.setItems(comboTeams);
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) distanceSpinner.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showData(ActionEvent event) throws IOException {
        File file = new File("src/files/Data.xlsx");

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();

        //let's try to open PDF file
        if(file.exists()) desktop.open(file);
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

}
