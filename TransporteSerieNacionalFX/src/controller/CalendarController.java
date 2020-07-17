package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import logic.Controller;
import logic.Date;
import logic.Duel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private ArrayList<Date> calendar;
    private Controller controller;

    @FXML
    private JFXTabPane calendarTabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        controller = Controller.getSingletonController();

        controller.generateCalendar();

        calendar = controller.getCalendar();
        for (int i = 0; i < calendar.size(); i++) {
            TableView                 table = new TableView();
            TableColumn<String, Duel> col   = new TableColumn<>("Local");
            TableColumn<String, Duel> col2  = new TableColumn<>("Visitante");
            col.setCellValueFactory(new PropertyValueFactory<>("local"));
            col2.setCellValueFactory(new PropertyValueFactory<>("visitor"));
            table.getColumns().add(col);
            table.getColumns().add(col2);
            for (int j = 0; j < calendar.get(i).getGames().size(); j++) {
                int posLocal   = calendar.get(i).getGames().get(j).get(0);
                int posVisitor = calendar.get(i).getGames().get(j).get(1);
                table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                //table.getItems().add(controller.getSingletonController().getTeams().get(posVisitante));
            }

            Tab tab = new Tab("Fecha " + (i + 1));
            tab.setContent(table);
            calendarTabPane.getTabs().add(tab);
        }
    }
}
