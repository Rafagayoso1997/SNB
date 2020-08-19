package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
import java.util.stream.Collectors;

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
    private JFXButton btnSwap;


    @FXML
    void selectAllTeams(ActionEvent event) {
        if (selectAll.isSelected()) {

            //DAVID change => update the Champion ComboBox and Sub-Champion ComboBox
            teamsSelectionListView.getSelectionModel().selectAll();
            comboChamp.setItems(teamsSelectionListView.getItems());
            comboSub.setItems(teamsSelectionListView.getItems());

        } else {
            teamsSelectionListView.getSelectionModel().clearSelection();

            //DAVID change => update the Champion ComboBox and Sub-Champion ComboBox
            comboChamp.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
            comboSub.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
        }
    }

    @FXML
    void setChampVsSub(ActionEvent event) {
        if (champVsSub.isSelected()) {
            comboChamp.setVisible(true);
            comboSub.setVisible(true);
            btnSwap.setVisible(true);
            champVsSub.setText("Sí");

            //DAVID change => update the Champion ComboBox and Sub-Champion ComboBox
            comboChamp.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
            comboSub.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
        } else {
            comboChamp.setVisible(false);
            comboSub.setVisible(false);
            btnSwap.setVisible(false);
            champVsSub.setText("No");
        }
    }

    @FXML
    void setSecondRound(ActionEvent event) {
        if (secondRoundButton.isSelected()) {
            secondRoundButton.setText("Sí");

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
        } //
        for (int i = 0; i < teamsSelectionListView.getSelectionModel().getSelectedItems().size(); i++) {
            String nombre = teamsSelectionListView.getSelectionModel().getSelectedItems().get(i);
            teamsNames.add(nombre);
        }*/
        if (indexes.size() <= 2) {
            notification = getNotification();
            notification.setTitle("Selección de equipos");
            notification.setMessage("Debe escoger más de dos equipos");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        } else if (indexes.size() % 2 != 0) {
            notification = getNotification();
            notification.setTitle("Selección de equipos.");
            notification.setMessage("Debe escoger una cantidad par de equipos.");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(1));
        }

        if (indexesMutations.size() ==0 ) {
            notification = getNotification();
            notification.setTitle("Selección de equipos");
            notification.setMessage("Debe escoger al menos una mutación");
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
                notification.setTitle("Selección equipos");
                if(posChampion == -1 && posSub == -1){
                    notification.setMessage("Debe escoger al campeón y subcampeón.");
                }
                else {
                    notification.setMessage("El campeón y subcampeón deben diferentes");
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
                    notification.setMessage("El campeón y subcampeón deben haber sido seleccionados previamente");
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

        //fill the TeamsListView
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

        //Fill the Champion and Sub-Champions ComboBox
        comboChamp.setVisible(false);
        comboSub.setVisible(false);
        btnSwap.setVisible(false);

        this.teams = 0;


        List<String> mutations = ReadFiles.readMutations();
        mutationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mutationListView.setItems(FXCollections.observableList(mutations));
    }

    private TrayNotification getNotification() {
        return new TrayNotification();
    }

    //************DAVID's New Methods**************\\

    @FXML
    void scrollListView(ScrollEvent event) {
        System.out.println("Scroll");
    }

    @FXML
    void mouseClickedListView(MouseEvent event) {
        System.out.println("Clicked");
        System.out.println(teamsSelectionListView.getSelectionModel().getSelectedIndices());

        if (comboChamp.isVisible() || comboSub.isVisible()) {
            comboChamp.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
            comboSub.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
        }
    }

    @FXML
    void selectTeamChamp(ActionEvent event) {
        System.out.println("Champion Team Selected => " + comboChamp.getSelectionModel().getSelectedItem());
        //update Sub-Champion ComboBox without Champion Team

        /*
        if (comboChamp.getSelectionModel().getSelectedItem().equalsIgnoreCase(comboSub.getSelectionModel().getSelectedItem())) {
            comboSub.getSelectionModel().clearSelection();
            System.out.println("Same selection. Swap Team to Sub-Champion");
        } else if (!comboSub.getSelectionModel().isEmpty() && comboChamp.getSelectionModel().getSelectedItem().equalsIgnoreCase(comboSub.getSelectionModel().getSelectedItem())) {
            String teamSwap = comboSub.getSelectionModel().getSelectedItem();
            comboSub.getSelectionModel().select(comboChamp.getSelectionModel().getSelectedItem());
            comboChamp.getSelectionModel().select(teamSwap);
        }
        */

        List<String> list = comboChamp.getItems().stream().collect(Collectors.toList());
        list.remove(comboChamp.getSelectionModel().getSelectedItem());
        comboSub.setItems(FXCollections.observableList(list));

    }

    @FXML
    void selectTeamSubChamp(ActionEvent event) {
        System.out.println("Sub-Champion Team Selected => " + comboSub.getSelectionModel().getSelectedItem());

        //comentario



        /*
        if (comboSub.getSelectionModel().getSelectedItem().equalsIgnoreCase(comboChamp.getSelectionModel().getSelectedItem())) {
            comboChamp.getSelectionModel().clearSelection();
        }

         */
    }

    @FXML
    void swapTeams(ActionEvent event) {
        String teamSwap = comboSub.getSelectionModel().getSelectedItem();
        comboSub.getSelectionModel().select(comboChamp.getSelectionModel().getSelectedItem());
        comboChamp.getSelectionModel().select(teamSwap);
    }
}