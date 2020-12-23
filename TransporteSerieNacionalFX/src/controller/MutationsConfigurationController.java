package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.CalendarConfiguration;
import logic.Controller;
import logic.Date;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MutationsConfigurationController implements Initializable {


    private int selectedCalendar;
    private HomeController homeController;

    private ArrayList<ArrayList<Boolean>> booleanValues;//lista de boolean para saber que componentes activar o no

    private ArrayList<ArrayList<Integer>> configurationsList;

    private ArrayList<Integer> positionsMutationsSelected;
    private List<String> mutationsToAdd;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXListView<String> mutationsListView;

    @FXML
    private JFXComboBox<String> comboDate1;

    @FXML
    private JFXComboBox<String> comboDate2;

    @FXML
    private JFXComboBox<String> comboDuel1;

    @FXML
    private JFXComboBox<String> comboDuel2;

    @FXML
    private JFXListView<String> selectedMutationListView;

    @FXML
    private JFXButton selectMutations;

    @FXML
    private JFXButton removeMutations;

    @FXML
    private JFXButton buttonApplyMuttations;

    @FXML
    private Spinner<Integer> iterations;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.selectedCalendar = CalendarController.selectedCalendar;
        iterations.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        iterations.getValueFactory().setValue(1);
        mutationsToAdd = new ArrayList<>();
        positionsMutationsSelected = new ArrayList<>();
        booleanValues = new ArrayList<>();
        selectMutations.setVisible(false);
        removeMutations.setVisible(false);
        comboDate1.setVisible(false);
        comboDate2.setVisible(false);
        comboDuel1.setVisible(false);
        comboDuel2.setVisible(false);

        iterations.getEditor().setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,9}?")) ? change : null));

        iterations.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                iterations.getValueFactory().setValue(Integer.parseInt(iterations.getEditor().getText()));
            }
        });

        //lleno la lista de mutaciones y separo los datos de configuracion
        List<String> mutationsReaded = ReadFiles.readMutations();
        List<String> mutations = new ArrayList<>();
        for (int i = 0; i < mutationsReaded.size(); i++) {
            String[] mutation = mutationsReaded.get(i).split("\\.");
            mutations.add(mutation[0]);

            //String[] values = mutation[1].split(",");
            ArrayList<Boolean> booleans = new ArrayList<>();
            for (int j = 0; j < mutation[1].length(); j++) {
                if (mutation[1].charAt(j) == 'V') {
                    booleans.add(true);
                } else {
                    booleans.add(false);
                }
            }
            booleanValues.add(booleans);
        }
        //System.out.println(booleanValues);
        mutationsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mutationsListView.setItems(FXCollections.observableList(mutations));

        configurationsList = new ArrayList<>();


        // Ciclo para crear las listas dependiendo las mutaciones que hayan
        for (int i = 0; i < mutationsListView.getItems().size(); i++) {
            ArrayList<Integer> configuration = new ArrayList<>();
            //metodo para llenar las listas dependiedno la cantidad de componentes de configuracion que hayan
            for (int j = 0; j < pane.getChildren().size(); j++) {
                configuration.add(-1);
            }
            configurationsList.add(configuration);
        }


        ArrayList<Date> calendar = Controller.getSingletonController().getCalendarsList().get(selectedCalendar);
        CalendarConfiguration configuration = Controller.getSingletonController().getConfigurations().get(selectedCalendar);
        int dates = calendar.size();
        boolean inaugural = configuration.isInauguralGame();
        for (int i = 0; i < dates; i++) {
            if (inaugural && (i == 0)) {
                i++;
            }
            String date = "Fecha " + (i + 1);
            comboDate1.getItems().add(date);
            comboDate2.getItems().add(date);
        }
        comboBoxValidation(comboDate1, comboDuel1);
        comboBoxValidation(comboDate2, comboDuel2);

        mutationsListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue != -1) {
                    selectMutations.setVisible(true);
                    removeMutations.setVisible(true);
                }
            }
        });

        selectedMutationListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if ((int)newValue != -1){
                    int positionNew = positionsMutationsSelected.get((int) newValue);
                    ArrayList<Boolean> values = booleanValues.get(positionNew);
                    comboDate1.setVisible(values.get(0));
                    comboDate2.setVisible(values.get(1));
                    comboDuel1.setVisible(values.get(2));
                    comboDuel2.setVisible(values.get(3));

                    if ((int) oldValue != -1) {
                        int positionOld = positionsMutationsSelected.get((int) oldValue);
                        configurationsList.get(positionOld).set(0, comboDate1.getSelectionModel().getSelectedIndex());
                        configurationsList.get(positionOld).set(1, comboDate2.getSelectionModel().getSelectedIndex());
                        configurationsList.get(positionOld).set(2, comboDuel1.getSelectionModel().getSelectedIndex());
                        configurationsList.get(positionOld).set(3, comboDuel2.getSelectionModel().getSelectedIndex());
                    }
                    System.out.println("Lista de configuraciones");
                    System.out.println(configurationsList);
                    comboDate1.getSelectionModel().select(configurationsList.get(positionNew).get(0));
                    comboDate2.getSelectionModel().select(configurationsList.get(positionNew).get(1));
                    comboDuel1.getSelectionModel().select(configurationsList.get(positionNew).get(2));
                    comboDuel2.getSelectionModel().select(configurationsList.get(positionNew).get(3));
                }
            }
        });

    }

    private void comboBoxValidation(JFXComboBox<String> comboDate, JFXComboBox<String> comboDuel) {

        comboDate.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue != -1) {
                    comboDuel.getItems().clear();
                    int position = (int) newValue;
                    Controller controller = Controller.getSingletonController();
                    Date date = controller.getCalendarsList().get(selectedCalendar).get(position);
                    for (int j = 0; j < date.getGames().size(); j++) {
                        int posLocal = date.getGames().get(j).get(0);
                        int posVisitor = date.getGames().get(j).get(1);
                        String element = "" + controller.getTeams().get(posLocal) + " - " + controller.getTeams().get(posVisitor);
                        //table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                        comboDuel.getItems().add(element);
                    }
                }

            }
        });
    }


    @FXML
    void selectMutations(ActionEvent event) {
        //positionsMutationsSelected = (ArrayList<Integer>) mutationsListView.getSelectionModel().getSelectedIndices();


        for (int i = 0; i < mutationsListView.getSelectionModel().getSelectedItems().size(); i++) {
            String mutation = mutationsListView.getSelectionModel().getSelectedItems().get(i);
            int pos = mutationsListView.getSelectionModel().getSelectedIndices().get(i);
            if (!mutationsToAdd.contains(mutation))
                mutationsToAdd.add(mutation);

            if (!positionsMutationsSelected.contains(pos))
                positionsMutationsSelected.add(pos);
        }


        selectedMutationListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        selectedMutationListView.setItems(FXCollections.observableList(mutationsToAdd));


        /*System.out.println(mutationsToAdd);
        System.out.println(positionsMutationsSelected);*/
    }


    @FXML
    void removeMutations(ActionEvent event) {
        int index = selectedMutationListView.getSelectionModel().getSelectedIndex();
        if(index != -1){
            selectedMutationListView.getItems().remove(index);
            positionsMutationsSelected.remove(index);
        }
        System.out.println(positionsMutationsSelected);
    }

    @FXML
    void applyMutations(ActionEvent event) throws IOException {
        System.out.println(positionsMutationsSelected);
        System.out.println(selectedMutationListView.getSelectionModel().getSelectedIndex());


        int atLestOneMutationSelected = selectedMutationListView.getItems().size();

        ArrayList<Date> calendar = Controller.getSingletonController().getCalendarsList().get(selectedCalendar);
        CalendarConfiguration configuration = Controller.getSingletonController().getConfigurations().get(selectedCalendar);

        if (atLestOneMutationSelected != 0) {
            if(selectedMutationListView.getSelectionModel().getSelectedIndex() != -1){
                int posSelectedMutation = positionsMutationsSelected.get(selectedMutationListView.getSelectionModel().getSelectedIndex());

                configurationsList.get(posSelectedMutation).set(0, comboDate1.getSelectionModel().getSelectedIndex());
                configurationsList.get(posSelectedMutation).set(1, comboDate2.getSelectionModel().getSelectedIndex());
                configurationsList.get(posSelectedMutation).set(2, comboDuel1.getSelectionModel().getSelectedIndex());
                configurationsList.get(posSelectedMutation).set(3, comboDuel2.getSelectionModel().getSelectedIndex());

                if (configuration.isInauguralGame()){
                    for (int i = 0; i < configurationsList.size(); i++) {
                        for (int j = 0; j < configurationsList.get(i).size(); j++) {
                            if(configurationsList.get(i).get(j) != -1){
                                configurationsList.get(i).set(j, configurationsList.get(i).get(j) + 1);
                            }
                        }
                    }
                }
                System.out.println("Lista de configuraciones");
                System.out.println(configurationsList);
            }

            Controller.getSingletonController().setMutationsConfigurationsList(configurationsList);
            Controller.getSingletonController().setMutationsIndexes(positionsMutationsSelected);

            System.out.println("mutaciones seleccionadas: " + positionsMutationsSelected);

            ArrayList<Date> newCalendar = new ArrayList<>();
            Controller.getSingletonController().copyCalendar(newCalendar, calendar);

            for (int j = 0; j < iterations.getValueFactory().getValue(); j++) {
                for (int i = 0; i < positionsMutationsSelected.size(); i++) {
                    Controller.getSingletonController().selectMutation(newCalendar, positionsMutationsSelected.get(i), configuration.isInauguralGame(), configuration.isOccidenteVsOriente());
                }
            }

            Controller.getSingletonController().getCalendarsList().add(newCalendar);
            Controller.getSingletonController().getConfigurations().add(copyConfiguration(configuration));
            System.out.println("************************************************");
            System.out.println("Calendario:");
            for (Date date : newCalendar) {
                for (int h = 0; h < date.getGames().size(); h++) {
                    System.out.print(date.getGames().get(h));
                }
                System.out.println();
            }
            System.out.println("************************************************");

            Stage stage = (Stage) selectMutations.getScene().getWindow();
            stage.close();

            AnchorPane structureOver = homeController.getPrincipalPane();
            homeController.createPage(new CalendarController(), structureOver, "/visual/Calendar.fxml");
            homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);

        } else {
            TrayNotification notification = new TrayNotification();
            notification.setMessage("Debe elegir al menos una mutación");
            notification.showAndDismiss(Duration.seconds(1));
        }
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private CalendarConfiguration copyConfiguration(CalendarConfiguration configuration) {
        CalendarConfiguration conf = new CalendarConfiguration();
        conf.setCalendarId(configuration.getCalendarId() + " Mutado");
        conf.setInauguralGame(configuration.isInauguralGame());
        conf.setChampion(configuration.getChampion());
        conf.setSecondPlace(configuration.getSecondPlace());
        conf.setTeamsIndexes(configuration.getTeamsIndexes());
        conf.setChampionVsSecondPlace(configuration.isChampionVsSecondPlace());
        conf.setSecondRoundCalendar(configuration.isSecondRoundCalendar());
        conf.setSymmetricSecondRound(configuration.isSymmetricSecondRound());
        conf.setMaxLocalGamesInARow(configuration.getMaxLocalGamesInARow());
        conf.setMaxVisitorGamesInARow(configuration.getMaxVisitorGamesInARow());
        return conf;
    }
}
