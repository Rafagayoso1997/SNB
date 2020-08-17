package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import logic.Controller;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectionTeamsController implements Initializable {

    private TrayNotification notification;

    public static int teams;
    public static ArrayList<String> teamsNames;
    @FXML
    private JFXListView<String> teamsSelectionListView;

    @FXML
    private Pane imageSelectionPane;

    @FXML
    private JFXButton select;


    @FXML
    void selectTeams(ActionEvent event) {
        ArrayList<Integer> indexes = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices().size());

        teamsNames = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices().size());
        System.out.println(HomeController.escogidos);
        for (int i = 0; i < teamsSelectionListView.getSelectionModel().getSelectedIndices().size(); i++) {
            indexes.add(teamsSelectionListView.getSelectionModel().getSelectedIndices().get(i));
        }
        for (int i = 0; i < teamsSelectionListView.getSelectionModel().getSelectedItems().size(); i++) {
            String nombre = teamsSelectionListView.getSelectionModel().getSelectedItems().get(i);
            teamsNames.add(nombre);
        }
        if (indexes.size() <= 2) {
            notification = getNotification();
            notification.setTitle("Selecci�n de equipos");
            notification.setMessage("Debe escoger m�s de dos equipos");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
        } else if (indexes.size() % 2 != 0) {
            notification = getNotification();
            notification.setTitle("Selecci�n de equipos");
            notification.setMessage("Debe escoger una cantidad par de equipos");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
        } else {
            HomeController.escogidos = true;
            System.out.println(HomeController.escogidos);
            teams = indexes.size();
            System.out.println(indexes);
            Controller.getSingletonController().setIndexes(indexes);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomeController.escogidos = false;
        teams = 0;
        Controller.getSingletonController().setIndexes(new ArrayList<>());
        List<String> teams = Controller.getSingletonController().getTeams();
        teamsSelectionListView.setItems(FXCollections.observableArrayList(teams));
        teamsSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamsSelectionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Image     image     = new Image(getClass().getResourceAsStream("/resources/teams/" + newValue + ".png"));
                ImageView teamImage = new ImageView(image);
                teamImage.setFitHeight(imageSelectionPane.getHeight());
                teamImage.setFitWidth(imageSelectionPane.getWidth());
                teamImage.setPreserveRatio(true);
                teamImage.setSmooth(true);
                imageSelectionPane.getChildren().clear();
                imageSelectionPane.getChildren().add(teamImage);
            }
        });
    }

    private TrayNotification getNotification() {
        return new TrayNotification();
    }
}