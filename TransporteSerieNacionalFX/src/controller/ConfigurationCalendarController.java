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
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
import java.util.stream.Collectors;

public class ConfigurationCalendarController implements Initializable {

    private TrayNotification notification;

    private HomeController homeController;

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
    private Spinner<Integer> maxHomeGamesSpinner;

    @FXML
    private Spinner<Integer> maxVisitorGamesSpinner;

    @FXML
    private JFXButton btnSwap;

    @FXML
    private JFXToggleButton inauguralGame;



    @FXML
    private JFXButton advanceConfigurationBtn;






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
    void setInauguralGame(ActionEvent event) {
        if (inauguralGame.isSelected()) {
            inauguralGame.setText("Sí");
            champVsSub.setSelected(true);
            champVsSub.setText("Sí");
            comboChamp.setVisible(true);
            comboSub.setVisible(true);
        } else {
            inauguralGame.setText("No");
            champVsSub.setSelected(false);
            champVsSub.setText("No");
            comboChamp.setVisible(false);
            comboSub.setVisible(false);
        }
    }


    @FXML
    void selectTeams(ActionEvent event) throws IOException {
        validateData(true);
        //true indicates that show the duel selection matrix
        //false indicates that show the advance configuration
    }

    @FXML
    void advanceConfiguration(ActionEvent event) throws  IOException {
        validateData(false);
        //true indicates that show the duel selection matrix
        //false indicates that show the advance configuration
    }

    private void validateData(boolean showMatrix) throws IOException {
        ArrayList<Integer> indexes = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices());
        teamsNames = new ArrayList<>();
        for (int index : indexes) {
            teamsNames.add(Controller.getSingletonController().getAcronyms().get(index));
        }
        //teamsNames = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedItems());
        System.out.println(teamsNames);
        if (indexes.size() <= 2) {
            showNotification("Selección de equipos","Debe escoger más de dos equipos", false);
            ok = false;
        }
        if (indexes.size() % 2 != 0) {
            showNotification("Selección de equipos","Debe escoger una cantidad par de equipos.", false);
            ok = false;
        }

        if(inauguralGame.isSelected()){
            if(champVsSub.isSelected()){
                validateChampionAndSubchampion();
            }
            else{
                showNotification("Selección de equipos", "Debe escoger al campeón y subcampeón.", false);
                ok = false;
            }
        }

        if (champVsSub.isSelected()) {
            validateChampionAndSubchampion();

        }
        if (ok) {
            HomeController.escogidos = true;
            teams = indexes.size();
            System.out.println(indexes);
            Controller.getSingletonController().setTeamsIndexes(indexes);
            secondRound = secondRoundButton.isSelected();
            Controller.getSingletonController().setPosChampion(posChampion);
            Controller.getSingletonController().setPosSubChampion(posSub);
            Controller.getSingletonController().setSecondRound(secondRound);
            Controller.getSingletonController().setMaxHomeGame(maxHomeGamesSpinner.getValueFactory().getValue());
            Controller.getSingletonController().setMaxVisitorGame(maxVisitorGamesSpinner.getValueFactory().getValue());
            if(showMatrix)
                showTeamsMatrix();
            else
                showAdvanceConfiguration();
        }
        ok = true;
    }

    private void validateChampionAndSubchampion() {
        String champion = comboChamp.getSelectionModel().getSelectedItem();
        String subchampion = comboSub.getSelectionModel().getSelectedItem();
        //posChampion = comboChamp.getSelectionModel().getSelectedIndex();
        //posSub = comboSub.getSelectionModel().getSelectedIndex();
        if (champion == null || subchampion == null) {
            //ok = false;
            showNotification("Selección de equipos","Debe escoger al campeón y subcampeón.", false);
           ok = false;
        } else if(champion.equalsIgnoreCase(subchampion)) {
            showNotification("Selección de equipos","El campeón y subcampeón deben diferentes", false);
            ok = false;
        }else {
                ok = true;
                posChampion = Controller.getSingletonController().getTeams().indexOf(champion);
                posSub = Controller.getSingletonController().getTeams().indexOf(subchampion);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomeController.escogidos = false;
        selectAll.setSelected(true);
        notification = new TrayNotification();


        Controller.getSingletonController().setPosChampion(-1);
        Controller.getSingletonController().setPosSubChampion(-1);
        Controller.getSingletonController().setSecondRound(false);
        secondRoundButton.setSelected(false);



        //fill the TeamsListView
        Controller.getSingletonController().setTeamsIndexes(new ArrayList<>());
        List<String> teams = Controller.getSingletonController().getTeams();

        teamsSelectionListView.setItems(FXCollections.observableArrayList(teams));
        teamsSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamsSelectionListView.getSelectionModel().selectAll();
        teamsSelectionListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int indices = teamsSelectionListView.getSelectionModel().getSelectedIndices().size();
            if(indices > 1){
                int maxGames = indices/2;
                maxHomeGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxGames));
                maxVisitorGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxGames));
            }

        });

        int maxGames = teamsSelectionListView.getSelectionModel().getSelectedIndices().size()/2;
        maxHomeGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxGames));
        maxHomeGamesSpinner.getValueFactory().setValue(2);

        maxVisitorGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxGames));
        maxVisitorGamesSpinner.getValueFactory().setValue(2);

        //Fill the Champion and Sub-Champions ComboBox
        comboChamp.setVisible(false);
        comboSub.setVisible(false);
        btnSwap.setVisible(false);

        ConfigurationCalendarController.teams = 0;

    }

    private void showNotification(String title,String message, boolean success) {
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        if(!success)
            notification.setNotificationType(NotificationType.ERROR);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(1));
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

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    void showTeamsMatrix() throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new SelectGridController(), structureOver, "/visual/SelectGrid.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);
    }

    void showAdvanceConfiguration() throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new AdvanceConfigurationController(), structureOver, "/visual/AdvanceConfiguration.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);
    }
}