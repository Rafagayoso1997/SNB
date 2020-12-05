package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import file_management.CrudsFiles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Controller;
import logic.Table;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrudsController implements Initializable {

    private TrayNotification notification;
    private HomeController homeController;
    private ObservableList<Table> data = FXCollections.observableArrayList();

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXTextField acronymTextField;

    @FXML
    private JFXComboBox<String> locationComboBox;

    @FXML
    private Spinner<Integer> distanceSpinner;

    @FXML
    private JFXButton modifyBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton showDataBtn;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TableView<Table> table;

    @FXML
    private TableColumn<Table, String> columnName;

    @FXML
    private TableColumn<Table, String> columnAcro;

    @FXML
    private TableColumn<Table, String> columnLocation;

    @FXML
    private TableColumn<Table, JFXTextField> columnDistance;


    @FXML
    private JFXComboBox<String> teamsComboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        notification = new TrayNotification();
        columnName.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
        columnAcro.setCellValueFactory(new PropertyValueFactory<Table, String>("acronym"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<Table, String>("location"));
        columnDistance.setCellValueFactory(new PropertyValueFactory<Table, JFXTextField>("distance"));

        nameTextField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("^[A-Za-zñÑáéíóúÁÉÍÓÚ ]*$")) ? change : null));

        acronymTextField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("^[A-Za-zÑñ]{0,3}$")) ? change : null));

        ObservableList<String> comboTeams = FXCollections.observableArrayList(Controller.getSingletonController().getTeams());
        teamsComboBox.setItems(comboTeams);
        locationComboBox.setItems(FXCollections.observableArrayList("Occidental","Oriental"));

        setListenerForComboBox();
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
        fillColumns();
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
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

    private void fillColumns () {

        ArrayList<String> namesList = Controller.getSingletonController().getTeams();
        ArrayList<String> acroList = Controller.getSingletonController().getAcronyms();
        ArrayList<String> locationsList = Controller.getSingletonController().getLocations();

        for (int i = 0; i < namesList.size(); i++) {
            JFXTextField textField = new JFXTextField();

            textField.setTextFormatter(new TextFormatter<>(change ->
                    (change.getControlNewText().matches("\\d{0,9}(\\d[.]\\d{0,2})?")) ? change : null));

            textField.setText("0.0");
            data.add(new Table(namesList.get(i), acroList.get(i), locationsList.get(i), textField));
        }
        table.setItems(data);
    }

    @FXML
    void addNewTeam() throws IOException {
        String messageTitle = "Añadir Equipo";

        String teamName = nameTextField.getText();
        String acro = acronymTextField.getText();
        String  location = locationComboBox.getSelectionModel().getSelectedItem();
        acro = acro.toUpperCase();
        Double distances[] = new Double[Controller.getSingletonController().getTeams().size()];

        for (int i = 0; i < data.size(); i++){
            String temp = ((JFXTextField)(data.get(i).getDistance())).getText();
            if(!temp.isEmpty()){
                distances[i] = Double.parseDouble(temp);
            }else{
                distances[i] = 0.0;
            }
        }

        if(!(teamName.equalsIgnoreCase("") || teamName.equalsIgnoreCase(" "))){
            if(!(acro.equalsIgnoreCase("") || teamName.equalsIgnoreCase(" "))){
                if (acro.length() == 3){
                    if (!Controller.getSingletonController().getAcronyms().contains(acro)){

                        boolean exists = false;

                        int i = 0;
                        while (!exists && i < Controller.getSingletonController().getTeams().size()){

                            if (Controller.getSingletonController().getTeams().get(i).equalsIgnoreCase(teamName)){
                                exists = true;
                            }
                            i++;
                        }
                        if (!exists){

                            boolean hasZeroDistance = false;
                            int j = 0;
                            while (!hasZeroDistance && j < distances.length){
                                if(distances[j] == 0.0){
                                    hasZeroDistance = true;
                                }
                                j++;
                            }

                            if (!hasZeroDistance){
                                JFXTextField textField = new JFXTextField();
                                textField.setTextFormatter(new TextFormatter<>(change ->
                                        (change.getControlNewText().matches("\\d{0,9}(\\d[.]\\d{0,2})?")) ? change : null));

                                textField.setText("0.0");
                                Table newTableRow = new Table(teamName, acro, location, textField);
                                data.add(newTableRow);

                                //addModifyDistancesToController(distances,distances.length);

                                int pos = Controller.getSingletonController().getTeams().size() + 1;
                                CrudsFiles.addModifyTeamToData(teamName, acro, location, distances, pos);

                                Controller.getSingletonController().createTeams("src/files/Data.xlsx");
                                Controller.getSingletonController().fillMatrixDistance();

                                ObservableList<String> comboTeams = FXCollections.observableArrayList(Controller.getSingletonController().getTeams());
                                teamsComboBox.setItems(comboTeams);
                                setListenerForComboBox();

                                restore();
                                showSuccessfulMessage(messageTitle, "Equipo añadido correctamente.");

                            }else{
                                showNotification(messageTitle, "Ninguna distancia puede valer cero.");
                            }
                        }else{
                            showNotification(messageTitle, "Ya existe un equipo con ese nombre");
                        }
                    }else{
                        showNotification(messageTitle,"Ya existe un equipo con ese acrónimo");
                    }
                }else{
                    showNotification(messageTitle,"El acrónimo debe tener tres caracteres");
                }
            }else{
                showNotification(messageTitle,"Debe llenar el campo de acrónimo");
            }
        }else{
            showNotification(messageTitle,"Debe llenar el campo de nombre");
        }
    }

    @FXML
    void modifyTeam(ActionEvent event) throws IOException {

        String messageTitle = "Modificar Equipo";
        int pos = teamsComboBox.getSelectionModel().getSelectedIndex();
        String teamName = nameTextField.getText();
        String acro = acronymTextField.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        acro = acro.toUpperCase();
        Double distances[] = new Double[Controller.getSingletonController().getTeams().size()];

        for (int i = 0; i < data.size(); i++){
            String temp = ((JFXTextField)(data.get(i).getDistance())).getText();
            if(!temp.isEmpty()){
                distances[i] = Double.parseDouble(temp);
            }else{
                distances[i] = 0.0;
            }
        }

        if(!(teamName.equalsIgnoreCase("") || teamName.equalsIgnoreCase(" "))){
            if(!(acro.equalsIgnoreCase("") || teamName.equalsIgnoreCase(" "))){
                if (acro.length() == 3){

                    int posCheck = Controller.getSingletonController().getAcronyms().indexOf(acro);

                    if (posCheck == -1 || posCheck == pos){


                        boolean correct = true;
                        int i = 0;
                        while (correct && i < Controller.getSingletonController().getTeams().size()){

                            if (Controller.getSingletonController().getTeams().get(i).equalsIgnoreCase(teamName) && (i != pos)){
                                correct = false;
                            }
                            i++;
                        }
                        if (correct){

                            data.get(pos).setName(teamName);
                            data.get(pos).setAcronym(acro);
                            data.get(pos).setLocation(location);

                            //addModifyDistancesToController(distances,distances.length);


                            CrudsFiles.addModifyTeamToData(teamName, acro, location, distances, pos+1);

                            Controller.getSingletonController().createTeams("src/files/Data.xlsx");
                            Controller.getSingletonController().fillMatrixDistance();

                            teamsComboBox.getItems().set(pos, teamName);
                            showSuccessfulMessage(messageTitle, "Equipo modificado correctamente.");

                            /*
                            for(int j = 0; j < Controller.getSingletonController().getMatrixDistance().length; j++){
                                for(int k = 0; k < Controller.getSingletonController().getMatrixDistance().length; k++){
                                    System.out.print(Controller.getSingletonController().getMatrixDistance()[j][k] + " ");
                                }
                                System.out.println();
                            }
                            */
                        }else{
                            showNotification(messageTitle,"Ya existe un equipo con ese nombre");
                        }
                    }else{
                        showNotification(messageTitle,"Ya existe un equipo con ese acrónimo");
                    }
                }else{
                    showNotification(messageTitle,"El acrónimo debe tener tres caracteres");
                }
            }else{
                showNotification(messageTitle,"El campo de acrónimo no puede estar vacío");
            }
        }else{
            showNotification(messageTitle,"El campo de nombre no puede estar vacío");
        }
    }

    @FXML
    void removeTeam(ActionEvent event) throws IOException {

        int pos = teamsComboBox.getSelectionModel().getSelectedIndex();
        CrudsFiles.removeTeamFromData(pos + 1);

        data.remove(pos);
        teamsComboBox.getSelectionModel().clearSelection();
        teamsComboBox.getItems().remove(pos);

        Controller.getSingletonController().createTeams("src/files/Data.xlsx");
        Controller.getSingletonController().fillMatrixDistance();

        restore();
        showSuccessfulMessage("Eliminar Equipo", "El equipo se eliminó correctamente");
    }

    @FXML
    void restore() {
        nameTextField.clear();
        acronymTextField.clear();
        locationComboBox.getSelectionModel().clearSelection();
        teamsComboBox.getSelectionModel().clearSelection();
        data.clear();
        fillColumns();
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
        addBtn.setDisable(false);
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private void setListenerForComboBox(){
        teamsComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(newValue != null){
                    addBtn.setDisable(true);
                    deleteBtn.setDisable(false);
                    modifyBtn.setDisable(false);
                    int pos = Controller.getSingletonController().getTeams().indexOf(newValue);
                    nameTextField.setText(Controller.getSingletonController().getTeams().get(pos));
                    acronymTextField.setText(Controller.getSingletonController().getAcronyms().get(pos));
                    locationComboBox.getSelectionModel().select(Controller.getSingletonController().getLocations().get(pos));

                    double distancesRow[] = Controller.getSingletonController().getMatrixDistance()[pos];

                    for(int i = 0; i < data.size(); i++){
                        ((JFXTextField)(data.get(i).getDistance())).setText(Double.toString(distancesRow[i]));
                        if(distancesRow[i] == 0.0){
                            ((JFXTextField)(data.get(i).getDistance())).setDisable(true);
                            if(oldValue != null){
                                int posToReset = Controller.getSingletonController().getTeams().indexOf(oldValue);
                                ((JFXTextField)(data.get(posToReset).getDistance())).setDisable(false);
                            }
                        }
                    }
                }
            }
        });
    }

    private void showNotification(String title, String message) {
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.ERROR);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(1));
    }

    private static void showSuccessfulMessage(String title, String message) {
        TrayNotification notification = new TrayNotification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));
    }

    private void addModifyDistancesToController(Double[] distances, int pos){

        for(int i = 0; i < distances.length; i++){

            if(i != pos){
                Controller.getSingletonController().getMatrixDistance()[i][pos] = distances[i];
                Controller.getSingletonController().getMatrixDistance()[pos][i] = distances[i];
            }else {
                Controller.getSingletonController().getMatrixDistance()[i][pos] = 0.0;
            }

        }
    }
}
