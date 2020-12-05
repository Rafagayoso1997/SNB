package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import logic.CalendarConfiguration;
import logic.Controller;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdvanceConfigurationController implements Initializable {

    private HomeController homeController;

    private TrayNotification notification;

    @FXML
    private Spinner<Integer> iterationsSpinner;

    @FXML
    private JFXListView<String> mutationListView;

    @FXML
    private JFXButton select;

    public static boolean ok = true;

    private CalendarConfiguration configuration;


    public AdvanceConfigurationController() {
    }

    public AdvanceConfigurationController(CalendarConfiguration configuration) {
        this.configuration = configuration;
    }

    public CalendarConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CalendarConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iterationsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE, Controller.getSingletonController().getIterations()));
        //iterationsSpinner.getValueFactory().setValue(Controller.getSingletonController().getIterations());

        List<String> mutationsRead = ReadFiles.readMutations();
        List<String> mutations = new ArrayList<>();
        for (String s : mutationsRead) {
            String[] mutation = s.split("\\.");
            mutations.add(mutation[0]);
        }
        mutationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        mutationListView.setItems(FXCollections.observableList(mutations));

        if(Controller.getSingletonController().getMutationsIndexes().isEmpty()){
            mutationListView.getSelectionModel().selectAll();
        }
        else{
            int[] array = new int[Controller.getSingletonController().getMutationsIndexes().size()];
            for (int i = 0; i < Controller.getSingletonController().getMutationsIndexes().size(); i++){
                array[i] = Controller.getSingletonController().getMutationsIndexes().get(i);
            }
            mutationListView.getSelectionModel().selectIndices(-1, array);
        }

       iterationsSpinner.getEditor().setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,9}?")) ? change : null));

        iterationsSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                iterationsSpinner.getValueFactory().setValue(Integer.parseInt(iterationsSpinner.getEditor().getText()));
            }
        });
    }
/*
    @FXML
    void openDuelSelection(ActionEvent event) throws IOException {

        ArrayList<Integer> indexesMutations = new ArrayList<>(mutationListView.getSelectionModel().getSelectedIndices());
        if (indexesMutations.isEmpty()) {
            notification = getNotification();
            notification.setTitle("Selección de cambios");
            notification.setMessage("Debe escoger al menos una mutación");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
            ok = false;
        }
        if (ok) {
            Controller.getSingletonController().setMutationsIndexes(indexesMutations);
            Controller.getSingletonController().setIterations(iterationsSpinner.getValueFactory().getValue());
            showTeamsMatrix();
        }
    }*/

    @FXML
    void saveNewAdvancesConfigurations(ActionEvent event) throws IOException {
        System.out.println(iterationsSpinner.getValueFactory().getValue());
        ArrayList<Integer> indexesMutations = new ArrayList<>(mutationListView.getSelectionModel().getSelectedIndices());
        if (indexesMutations.isEmpty()) {
            notification = getNotification();
            notification.setTitle("Selección de cambios");
            notification.setMessage("Debe escoger al menos una mutación");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        }
        else{
            Controller.getSingletonController().setMutationsIndexes(indexesMutations);
            Controller.getSingletonController().setIterations(iterationsSpinner.getValueFactory().getValue());

            AnchorPane structureOver = homeController.getPrincipalPane();
            homeController.createPage(new ConfigurationCalendarController(), structureOver, "/visual/ConfigurationCalendar.fxml");
            homeController.getButtonReturnSelectionTeamConfiguration().setVisible(false);
        }
    }

    private TrayNotification getNotification() {
        return new TrayNotification();
    }

    void showTeamsMatrix() throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new SelectGridController(), structureOver, "/visual/SelectGrid.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
