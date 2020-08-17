package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
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

public class ConfigurationController implements Initializable {

    public static int posChampion = -1, posSub = -2;
    public static boolean secondRound = false;
    public static boolean ok = true;
    @FXML
    private AnchorPane config;

    @FXML
    private JFXToggleButton champVsSub;

    @FXML
    private JFXToggleButton secondRoundButton;

    @FXML
    private JFXComboBox<String> comboChamp;

    @FXML
    private JFXComboBox<String> comboSub;

    @FXML
    private JFXButton save;


    @FXML
    void setChampVsSub(ActionEvent event) {
        if (champVsSub.isSelected()) {
            comboChamp.setVisible(true);
            comboSub.setVisible(true);
            champVsSub.setText("Sí");
        } else {
            comboChamp.setVisible(false);
            comboSub.setVisible(false);
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
    void saveConfiguration(ActionEvent event) {

        if (champVsSub.isSelected()) {
            posChampion = comboChamp.getSelectionModel().getSelectedIndex();
            posSub = comboSub.getSelectionModel().getSelectedIndex();
            if (posSub == posChampion) {
                ok = false;
                TrayNotification notification = new TrayNotification();
                notification.setTitle("Escoger equipos");
                notification.setMessage("El campe?n y subcampe?n deben diferentes");
                notification.setNotificationType(NotificationType.ERROR);
                notification.setRectangleFill(Paint.valueOf("#2F2484"));
                notification.setAnimationType(AnimationType.FADE);
                notification.showAndDismiss(Duration.seconds(2));
            } else {
                String            champion    = comboChamp.getSelectionModel().getSelectedItem();
                String            subchampion = comboSub.getSelectionModel().getSelectedItem();
                ArrayList<String> teams       = SelectionTeamsController.teamsNames;
                if (!teams.contains(champion) || !teams.contains(subchampion)) {
                    ok = false;
                    TrayNotification notification = new TrayNotification();
                    notification.setTitle("Escoger equipos");
                    notification.setMessage("El campe?n y subcampe?n deben haber sido seleccionados previamente");
                    notification.setNotificationType(NotificationType.ERROR);
                    notification.setRectangleFill(Paint.valueOf("#2F2484"));
                    notification.setAnimationType(AnimationType.FADE);
                    notification.showAndDismiss(Duration.seconds(2));
                } else {
                    ok = true;
                }
            }

        } else {
            ok = true;
        }

        if (ok) {
            secondRound = secondRoundButton.isSelected();
            Controller.getSingletonController().setPosChampion(posChampion);
            Controller.getSingletonController().setPosSubChampion(posSub);
            Controller.getSingletonController().setSecondRound(secondRound);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        HomeController.conf = false;
        ok = false;
        Controller.getSingletonController().setPosChampion(-1);
        Controller.getSingletonController().setPosSubChampion(-1);
        Controller.getSingletonController().setSecondRound(false);
        secondRoundButton.setSelected(false);
        List<String> teams = Controller.getSingletonController().getTeams();
        comboChamp.setItems(FXCollections.observableArrayList(teams));
        comboSub.setItems(FXCollections.observableArrayList(teams));
        comboChamp.setVisible(false);
        comboSub.setVisible(false);

    }


}
