package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
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

    public static int posChampion = -1, posSub = -2;
    public static boolean secondRound = false;
    public static boolean ok = true;

    public static int teams;
    public static ArrayList<String> teamsNames;
    @FXML
    private JFXListView<String> teamsSelectionListView;

    @FXML
    private JFXToggleButton selectAll;

    @FXML
    private JFXButton select;

    @FXML
    private JFXToggleButton champVsSub;

    @FXML
    private JFXToggleButton secondRoundButton;

    @FXML
    private JFXComboBox<String> comboChamp;

    @FXML
    private JFXComboBox<String> comboSub;

    @FXML
    private JFXListView<String> mutationListView;


    @FXML
    void selectAllTeams(ActionEvent event) {
        if (selectAll.isSelected()) {
            teamsSelectionListView.getSelectionModel().selectAll();

        } else {
            teamsSelectionListView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void setChampVsSub(ActionEvent event) {
        if (champVsSub.isSelected()) {
            comboChamp.setVisible(true);
            comboSub.setVisible(true);
            champVsSub.setText("S�");
        } else {
            comboChamp.setVisible(false);
            comboSub.setVisible(false);
            champVsSub.setText("No");
        }
    }

    @FXML
    void setSecondRound(ActionEvent event) {
        if (secondRoundButton.isSelected()) {
            secondRoundButton.setText("S�");

        } else {
            secondRoundButton.setText("No");
        }
    }


    @FXML
    void selectTeams(ActionEvent event) {
        ArrayList<Integer> indexes = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices());
        ArrayList<Integer> indexesMutations = new ArrayList<>(mutationListView.getSelectionModel().getSelectedIndices());
        System.out.println(indexesMutations);
        teamsNames = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedItems());
        System.out.println(HomeController.escogidos);
        /*for (int i = 0; i < teamsSelectionListView.getSelectionModel().getSelectedIndices().size(); i++) {
            indexes.add(teamsSelectionListView.getSelectionModel().getSelectedIndices().get(i));
        }
        for (int i = 0; i < teamsSelectionListView.getSelectionModel().getSelectedItems().size(); i++) {
            String nombre = teamsSelectionListView.getSelectionModel().getSelectedItems().get(i);
            teamsNames.add(nombre);
        }*/
        if (indexes.size() <= 2) {
            notification = getNotification();
            notification.setTitle("Selecci�n de equipos");
            notification.setMessage("Debe escoger m�s de dos equipos");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        } else if (indexes.size() % 2 != 0) {
            notification = getNotification();
            notification.setTitle("Selecci�n de equipos.");
            notification.setMessage("Debe escoger una cantidad par de equipos.");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        }

        if (indexesMutations.size() ==0 ) {
            notification = getNotification();
            notification.setTitle("Selecci�n de equipos");
            notification.setMessage("Debe escoger al menos una mutaci�n");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        }

        if (champVsSub.isSelected()) {
            posChampion = comboChamp.getSelectionModel().getSelectedIndex();
            posSub = comboSub.getSelectionModel().getSelectedIndex();
            if (posSub == posChampion) {
                //ok = false;
                TrayNotification notification = new TrayNotification();
                notification.setTitle("Selecci�n equipos");
                if(posChampion == -1 && posSub == -1){
                    notification.setMessage("Debe escoger al campe�n y subcampe�n.");
                }
                else {
                    notification.setMessage("El campe�n y subcampe�n deben diferentes");
                }
                notification.setNotificationType(NotificationType.ERROR);
                notification.setRectangleFill(Paint.valueOf("#2F2484"));
                notification.setAnimationType(AnimationType.FADE);
                notification.showAndDismiss(Duration.seconds(1));
            } else {
                String  champion    = comboChamp.getSelectionModel().getSelectedItem();
                String  subchampion = comboSub.getSelectionModel().getSelectedItem();
                ArrayList<String> teams = teamsNames;
                if (!teams.contains(champion) || !teams.contains(subchampion)) {
                    //ok = false;
                    TrayNotification notification = new TrayNotification();
                    notification.setTitle("Escoger equipos");
                    notification.setMessage("El campe�n y subcampe�n deben haber sido seleccionados previamente");
                    notification.setNotificationType(NotificationType.ERROR);
                    notification.setRectangleFill(Paint.valueOf("#2F2484"));
                    notification.setAnimationType(AnimationType.FADE);
                    notification.showAndDismiss(Duration.seconds(1));
                } else {
                    ok = true;
                }
            }

        }if (ok == true){
            HomeController.escogidos = true;
            teams = indexes.size();
            System.out.println(indexes);
            Controller.getSingletonController().setTeamsIndexes(indexes);
            Controller.getSingletonController().setMutationsIndexes(indexesMutations);
            secondRound = secondRoundButton.isSelected();
            Controller.getSingletonController().setPosChampion(posChampion);
            Controller.getSingletonController().setPosSubChampion(posSub);
            Controller.getSingletonController().setSecondRound(secondRound);
        }
            //ok = true;
        /*if (ok) {
            secondRound = secondRoundButton.isSelected();
            Controller.getSingletonController().setPosChampion(posChampion);
            Controller.getSingletonController().setPosSubChampion(posSub);
            Controller.getSingletonController().setSecondRound(secondRound);
        }*/


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomeController.escogidos = false;

        Controller.getSingletonController().setPosChampion(-1);
        Controller.getSingletonController().setPosSubChampion(-1);
        Controller.getSingletonController().setSecondRound(false);
        secondRoundButton.setSelected(false);
        List<String> teamsName = Controller.getSingletonController().getTeams();
        comboChamp.setItems(FXCollections.observableArrayList(teamsName));
        comboSub.setItems(FXCollections.observableArrayList(teamsName));
        comboChamp.setVisible(false);
        comboSub.setVisible(false);
        teams = 0;
        Controller.getSingletonController().setTeamsIndexes(new ArrayList<>());
        List<String> teams = Controller.getSingletonController().getTeams();
        teamsSelectionListView.setItems(FXCollections.observableArrayList(teams));
        teamsSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamsSelectionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                /*if(teamsSelectionListView.getSelectionModel().getSelectedItems().size()>0){
                    selectAll.setSelected(false);
                }*/
            }
        });

        List<String> mutations = ReadFiles.readMutations();
        mutationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mutationListView.setItems(FXCollections.observableList(mutations));




    }

    private TrayNotification getNotification() {
        return new TrayNotification();
    }


}