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
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Controller;
import logic.Date;
import logic.Duel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MutationsConfigurationController implements Initializable {



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
        iterations.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        iterations.getValueFactory().setValue(20000);
        mutationsToAdd = new ArrayList<>();
        positionsMutationsSelected = new ArrayList<>();
        booleanValues = new ArrayList<>();
        selectMutations.setVisible(false);
        removeMutations.setVisible(false);
        comboDate1.setVisible(false);
        comboDate2.setVisible(false);
        comboDuel1.setVisible(false);
        comboDuel2.setVisible(false);

        //lleno la lista de mutaciones y separo los datos de configuracion
        List<String> mutationsReaded = ReadFiles.readMutations();
        List<String> mutations = new ArrayList<>();
        for (int i = 0; i < mutationsReaded.size(); i++) {
            String[] mutation = mutationsReaded.get(i).split("\\.");
            mutations.add(mutation[0]);

            String[] values = mutation[1].split(",");
            ArrayList<Boolean> booleans = new ArrayList<>();
            for (int j = 0; j < values.length; j++) {
                if (values[j].equalsIgnoreCase("V")) {
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


        int dates = Controller.getSingletonController().getCalendar().size();
        boolean inaugural = Controller.getSingletonController().isInauguralGame();
        for (int i = 0; i < dates; i++) {
            if(inaugural && (i == 0)){
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
                /*int position = (int) newValue;
                ArrayList<Boolean> values = booleanValues.get(position);
                comboDate1.setVisible(values.get(0));
                comboDate2.setVisible(values.get(1));
                comboDuel1.setVisible(values.get(2));
                comboDuel2.setVisible(values.get(3));
*/
            }
        });


        selectedMutationListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

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
                comboDate1.getSelectionModel().select(configurationsList.get(positionsMutationsSelected.get((int) newValue)).get(0));
                comboDate2.getSelectionModel().select(configurationsList.get(positionsMutationsSelected.get((int) newValue)).get(1));
                comboDuel1.getSelectionModel().select(configurationsList.get(positionsMutationsSelected.get((int) newValue)).get(2));
                comboDuel2.getSelectionModel().select(configurationsList.get(positionsMutationsSelected.get((int) newValue)).get(3));
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
                    Date date = controller.getCalendar().get(position);
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
        selectedMutationListView.getItems().remove(index);
        positionsMutationsSelected.remove(index);

        System.out.println(positionsMutationsSelected);
    }

    @FXML
    void applyMutations(ActionEvent event) throws IOException {
        System.out.println(positionsMutationsSelected);
        System.out.println(selectedMutationListView.getSelectionModel().getSelectedIndex());



        int lastPosSelected = selectedMutationListView.getSelectionModel().getSelectedIndex();
        //si no seleccionas ninguna configuracion para las mutaciones
        if(lastPosSelected != -1){
            int realPos = positionsMutationsSelected.get(lastPosSelected);

            if(Controller.getSingletonController().isInauguralGame()){
                configurationsList.get(realPos).set(0, comboDate1.getSelectionModel().getSelectedIndex() + 1);
                configurationsList.get(realPos).set(1, comboDate2.getSelectionModel().getSelectedIndex() + 1);
                configurationsList.get(realPos).set(2, comboDuel1.getSelectionModel().getSelectedIndex() + 1);
                configurationsList.get(realPos).set(3, comboDuel2.getSelectionModel().getSelectedIndex() + 1);
            }
            else{
                configurationsList.get(realPos).set(0, comboDate1.getSelectionModel().getSelectedIndex());
                configurationsList.get(realPos).set(1, comboDate2.getSelectionModel().getSelectedIndex());
                configurationsList.get(realPos).set(2, comboDuel1.getSelectionModel().getSelectedIndex());
                configurationsList.get(realPos).set(3, comboDuel2.getSelectionModel().getSelectedIndex());
            }
        }
        else{
            lastPosSelected = 0;
        }


        Controller.getSingletonController().setConfigurationsList(configurationsList);
        //if(Controller.getSingletonController().getMutationsIndexes().isEmpty())
            Controller.getSingletonController().setMutationsIndexes(positionsMutationsSelected);

        System.out.println("mutaciones seleccionadas: " + positionsMutationsSelected);

        ArrayList<Date> newCalendar = new ArrayList<>();
        Controller.getSingletonController().copyCalendar(newCalendar, Controller.getSingletonController().getCalendar());

        //for (int j = 0; j < iterations.getValueFactory().getValue(); j++) {
            for (int i = 0; i < positionsMutationsSelected.size(); i++) {
                Controller.getSingletonController().selectMutation(newCalendar, positionsMutationsSelected.get(i));
            }
        //}

        Controller.getSingletonController().setCalendarCopy(newCalendar);
        Controller.getSingletonController().setGeneratedCalendar(false);
        Controller.getSingletonController().setCopied(true);

        /*AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new CalendarController(), structureOver, "/visual/Calendar.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(false);*/


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
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
