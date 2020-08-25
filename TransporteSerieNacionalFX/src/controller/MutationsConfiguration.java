package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import file_management.ReadFiles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Controller;
import logic.Date;
import logic.Duel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MutationsConfiguration implements Initializable {

    private ArrayList<ArrayList<Boolean>> booleanValues;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        booleanValues = new ArrayList<>();
        comboDate1.setVisible(false);
        comboDate2.setVisible(false);
        comboDuel1.setVisible(false);
        comboDuel2.setVisible(false);

        List<String> mutationsReaded = ReadFiles.readMutations();
        List<String> mutations = new ArrayList<>();
        for (int i = 0; i < mutationsReaded.size() ; i++) {
            String[] mutation = mutationsReaded.get(i).split("\\.");
            mutations.add(mutation[0]);

            String[] values = mutation[1].split(",");
            ArrayList<Boolean> booleans = new ArrayList<>();
            for(int j=0; j < values.length;j++){
                if(values[j].equalsIgnoreCase("V")){
                    booleans.add(true);
                }
                else{
                    booleans.add(false);
                }
            }
            booleanValues.add(booleans);
        }
        System.out.println(booleanValues);
        mutationsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mutationsListView.setItems(FXCollections.observableList(mutations));

        int dates = Controller.getSingletonController().getCalendar().size();
        for (int i = 0; i < dates; i++) {
            String date = "Fecha "+(i+1);
            comboDate1.getItems().add(date);
            comboDate2.getItems().add(date);
        }

        comboDate1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                comboDuel1.getItems().clear();
                int position = (int) newValue;
                Controller controller = Controller.getSingletonController();
                Date date = controller.getCalendar().get(position);
                for (int j = 0; j < date.getGames().size(); j++) {
                    int posLocal = date.getGames().get(j).get(0);
                    int posVisitor = date.getGames().get(j).get(1);
                    String element = ""+controller.getTeams().get(posLocal) + " - "+controller.getTeams().get(posVisitor);
                    //table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                    comboDuel1.getItems().add(element);
                }
            }
        });

        comboDate2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                comboDuel2.getItems().clear();
                int position = (int) newValue;
                Controller controller = Controller.getSingletonController();
                Date date = controller.getCalendar().get(position);
                for (int j = 0; j < date.getGames().size(); j++) {
                    int posLocal = date.getGames().get(j).get(0);
                    int posVisitor = date.getGames().get(j).get(1);
                    String element = ""+controller.getTeams().get(posLocal) + " - "+controller.getTeams().get(posVisitor);
                    //table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                    comboDuel2.getItems().add(element);
                }
            }
        });

        mutationsListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int position = (int) newValue;
                ArrayList<Boolean> values = booleanValues.get(position);
                comboDate1.setVisible(values.get(0));
                comboDate2.setVisible(values.get(1));
                comboDuel1.setVisible(values.get(2));
                comboDuel2.setVisible(values.get(3));

            }
        });


    }
}
