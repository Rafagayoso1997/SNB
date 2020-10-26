package controller;

import com.jfoenix.controls.*;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import logic.CalendarConfiguration;
import logic.Controller;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    public static ArrayList<Integer> selectedIndexes;
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
    private JFXToggleButton symmetricSecondRound;


    @FXML
    private Label lblSymmetricSecondRound;

    @FXML
    private JFXTextField calendarId;


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
            lblSymmetricSecondRound.setVisible(true);
            symmetricSecondRound.setVisible(true);
        } else {
            secondRoundButton.setText("No");
            lblSymmetricSecondRound.setVisible(false);
            symmetricSecondRound.setVisible(false);
            symmetricSecondRound.setSelected(false);
            symmetricSecondRound.setText("No");
        }
    }

    @FXML
    void setSymmetricSecondRound(ActionEvent event) {
        if (symmetricSecondRound.isSelected()) {
            symmetricSecondRound.setText("Sí");
        } else {
            symmetricSecondRound.setText("No");
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
            btnSwap.setVisible(true);


        } else {
            inauguralGame.setText("No");
        }
    }


    @FXML
    void selectTeams(ActionEvent event) throws IOException {
        validateData();
        //true indicates that show the duel selection matrix
        //false indicates that show the advance configuration
    }

    @FXML
    void advanceConfiguration(ActionEvent event) throws IOException {
        showAdvanceConfiguration(Controller.getSingletonController().getLastSavedConfiguration());
        //true indicates that show the duel selection matrix
        //false indicates that show the advance configuration
    }

    private void validateData() throws IOException {
        Controller controller = Controller.getSingletonController();

        //ArrayList<Integer> indexes = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices());
        selectedIndexes = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedIndices());
        teamsNames = new ArrayList<>();
        /*for (int index : indexes) {
            teamsNames.add(controller.getAcronyms().get(index));
        }*/
        for (int index : selectedIndexes) {
            teamsNames.add(controller.getAcronyms().get(index));
        }
        //teamsNames = new ArrayList<>(teamsSelectionListView.getSelectionModel().getSelectedItems());

        /*if (indexes.size() <= 2) {
            showNotification("Selecci?n de equipos","Debe escoger m?s de dos equipos", false);
            ok = false;
        }
        if (indexes.size() % 2 != 0) {
            showNotification("Selecci?n de equipos","Debe escoger una cantidad par de equipos.", false);
            ok = false;
        }*/
        if(calendarId.getText().equalsIgnoreCase(" ")||calendarId.getText().equalsIgnoreCase("")){
            showNotification("Debe Introducir el identificador del calendario");
            ok = false;
        }
        if (selectedIndexes.size() <= 2) {
            showNotification("Debe escoger m?s de dos equipos");
            ok = false;
        }
        if (selectedIndexes.size() % 2 != 0) {
            showNotification("Debe escoger una cantidad par de equipos.");
            ok = false;
        }

        if (inauguralGame.isSelected()) {
            if (champVsSub.isSelected()) {
                validateChampionAndSubchampion();
            } else {
                showNotification("Debe escoger al campe?n y subcampe?n.");
                ok = false;
            }
        }

        if (champVsSub.isSelected()) {
            validateChampionAndSubchampion();

        }
        if (ok) {
            HomeController.escogidos = true;
            teams = selectedIndexes.size();

            secondRound = secondRoundButton.isSelected();
            int posChampion = -1;
            int posSub =-1;
            if(champVsSub.isSelected()){
                String champion = comboChamp.getSelectionModel().getSelectedItem();
                String subchampion = comboSub.getSelectionModel().getSelectedItem();
                posChampion = controller.getTeams().indexOf(champion);
                posSub = controller.getTeams().indexOf(subchampion);
            }

            CalendarConfiguration configuration = new CalendarConfiguration(
                    calendarId.getText(),selectedIndexes,inauguralGame.isSelected(),
                    champVsSub.isSelected(),posChampion,posSub,secondRound,symmetricSecondRound.isSelected(),
                    maxHomeGamesSpinner.getValueFactory().getValue(),maxVisitorGamesSpinner.getValueFactory().getValue()
            );

            if(controller.isConfigurationAdded()){
                controller.getConfigurations().remove(controller.getConfigurations().size()-1);
                controller.setConfigurationAdded(false);
            }

            controller.getConfigurations().add(configuration);
            controller.setConfigurationAdded(true);

            controller.setLastSavedConfiguration(configuration);

            showTeamsMatrix(controller.getLastSavedConfiguration());

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
            showNotification("Debe escoger al campe?n y subcampe?n.");
            ok = false;
        } else if (champion.equalsIgnoreCase(subchampion)) {
            showNotification("El campe?n y subcampe?n deben diferentes");
            ok = false;
        } else {
            ok = true;
            posChampion = Controller.getSingletonController().getTeams().indexOf(champion);
            posSub = Controller.getSingletonController().getTeams().indexOf(subchampion);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Controller controller = Controller.getSingletonController();
        CalendarConfiguration configuration = new CalendarConfiguration();
        boolean existingConfiguration = false;

        if(controller.getLastSavedConfiguration() != null){
            existingConfiguration = true;
            configuration = controller.getLastSavedConfiguration();
        }

        //fill the TeamsListView
        List<String> teams = controller.getTeams();

        teamsSelectionListView.setItems(FXCollections.observableArrayList(teams));
        teamsSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        comboChamp.getItems().addAll(teamsSelectionListView.getItems());
        comboSub.getItems().addAll(teamsSelectionListView.getItems());

        if(!existingConfiguration){
            HomeController.escogidos = false;
            selectAll.setSelected(true);
            lblSymmetricSecondRound.setVisible(true);
            symmetricSecondRound.setVisible(true);
            secondRoundButton.setSelected(true);
            teamsSelectionListView.getSelectionModel().selectAll();

            champVsSub.setSelected(true);
            comboChamp.setVisible(true);
            comboSub.setVisible(true);
            btnSwap.setVisible(true);

            int maxGames = teamsSelectionListView.getSelectionModel().getSelectedIndices().size() / 2;
            maxHomeGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGames));
            maxVisitorGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGames - 1));
        }
        else{
            HomeController.escogidos = true;
            calendarId.setText(configuration.getCalendarId());

            if(configuration.isInauguralGame()){
                inauguralGame.setSelected(true);
                inauguralGame.setText("Sí");
            }
            else{
                inauguralGame.setSelected(false);
                inauguralGame.setText("No");
            }

            if(configuration.getTeamsIndexes().size() == controller.getTeams().size()){
                selectAll.setSelected(true);
                teamsSelectionListView.getSelectionModel().selectAll();
            }
            else{
                selectAll.setSelected(false);

                teamsSelectionListView.getSelectionModel().clearSelection();
                int[] array = new int[configuration.getTeamsIndexes().size()];
                for (int i = 0; i < configuration.getTeamsIndexes().size(); i++){
                    array[i] = configuration.getTeamsIndexes().get(i);
                }
                teamsSelectionListView.getSelectionModel().selectIndices(-1, array);
            }

            if (configuration.isSymmetricSecondRound()){
                lblSymmetricSecondRound.setVisible(true);
                symmetricSecondRound.setVisible(true);
                symmetricSecondRound.setText("Sí");
            }
            else{
                lblSymmetricSecondRound.setVisible(false);
                symmetricSecondRound.setVisible(false);
                symmetricSecondRound.setText("No");
            }

            if(configuration.isSecondRoundCalendar()){
                secondRoundButton.setSelected(true);
                secondRoundButton.setText("Sí");
            }
            else{
                secondRoundButton.setSelected(false);
                secondRoundButton.setText("No");
            }

            if(configuration.isChampionVsSecondPlace()){
                champVsSub.setSelected(true);

                champVsSub.setText("Sí");
                comboChamp.setVisible(true);
                comboSub.setVisible(true);
                btnSwap.setVisible(true);

                comboChamp.setValue(teams.get(configuration.getChampion()));
                comboSub.setValue(teams.get(configuration.getSecondPlace()));
            }
            else{
                champVsSub.setText("No");
                champVsSub.setSelected(false);
                comboChamp.setVisible(false);
                comboSub.setVisible(false);
                btnSwap.setVisible(false);
            }

            int maxGames = teamsSelectionListView.getSelectionModel().getSelectedIndices().size() / 2;
            maxHomeGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGames));
            maxVisitorGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGames - 1));
            maxHomeGamesSpinner.getValueFactory().setValue(configuration.getMaxLocalGamesInARow());
            maxVisitorGamesSpinner.getValueFactory().setValue(configuration.getMaxVisitorGamesInARow());
            ConfigurationCalendarController.teams = configuration.getTeamsIndexes().size();
        }

        notification = new TrayNotification();

        teamsSelectionListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int indices = teamsSelectionListView.getSelectionModel().getSelectedIndices().size();
            if (indices > 1) {
                int valTempLocal = maxHomeGamesSpinner.getValue();
                int valTempVisitor = maxVisitorGamesSpinner.getValue();

                int maxGamesOther = indices / 2;
                maxHomeGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGamesOther));
                maxVisitorGamesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxGamesOther));

                if(valTempLocal <= maxGamesOther){
                    maxHomeGamesSpinner.getValueFactory().setValue(valTempLocal);
                }
                else{
                    maxHomeGamesSpinner.getValueFactory().setValue(maxGamesOther);
                }

                if(valTempVisitor <= maxGamesOther){
                    maxVisitorGamesSpinner.getValueFactory().setValue(valTempVisitor);
                }
                else{
                    maxVisitorGamesSpinner.getValueFactory().setValue(maxGamesOther);
                }

            }
        });

        if(!existingConfiguration){
            maxHomeGamesSpinner.getValueFactory().setValue(2);
            maxVisitorGamesSpinner.getValueFactory().setValue(2);
            ConfigurationCalendarController.teams = 0;
        }

        calendarId.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("^[A-Za-z0-9 _]*$")) ? change : null));
    }

    private void showNotification(String message) {
        notification.setTitle("Selecci�n de equipos.");
        notification.setMessage(message);
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
        int selectedTeams = teamsSelectionListView.getSelectionModel().getSelectedIndices().size();

        if (comboChamp.isVisible() || comboSub.isVisible()) {
            comboChamp.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
            comboSub.setItems(teamsSelectionListView.getSelectionModel().getSelectedItems());
        }

        if (selectedTeams == Controller.getSingletonController().getTeams().size()) {
            selectAll.setSelected(true);
        } else {
            selectAll.setSelected(false);
        }

    }

    @FXML
    void selectTeamChamp(ActionEvent event) {
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

    void showTeamsMatrix(CalendarConfiguration configuration) throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        SelectGridController.configuration = configuration;
        homeController.createPage(new SelectGridController(), structureOver, "/visual/SelectGrid.fxml");

        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);
    }

    void showAdvanceConfiguration(CalendarConfiguration configuration) throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new AdvanceConfigurationController(configuration), structureOver, "/visual/AdvanceConfiguration.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(false);
    }
}