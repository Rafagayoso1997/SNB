package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import file_management.ReadFiles;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
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
    private JFXListView<String> mutationListView;

    @FXML
    private JFXButton select;

    public static boolean ok = true;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<String> mutationsRead = ReadFiles.readMutations();
        List<String> mutations = new ArrayList<>();
        for (String s : mutationsRead) {
            String[] mutation = s.split("\\.");
            mutations.add(mutation[0]);
        }
        mutationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        mutationListView.setItems(FXCollections.observableList(mutations));
        mutationListView.getSelectionModel().selectAll();

    }

    @FXML
    void openDuelSelection(ActionEvent event) throws IOException {

        ArrayList<Integer> indexesMutations = new ArrayList<>(mutationListView.getSelectionModel().getSelectedIndices());
        if (indexesMutations.isEmpty()) {
            notification = getNotification();
            notification.setTitle("Selección de equipos");
            notification.setMessage("Debe escoger al menos una mutación");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
            ok = false;
        }
        if (ok) {
            Controller.getSingletonController().setMutationsIndexes(indexesMutations);
            showTeamsMatrix();
            //showAdvanceConfiguration();
            //Controller.getSingletonController().setIterations(iterationsSpinner.getValueFactory().getValue());
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