package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private ArrayList<Date> calendar;
    private Controller controller;
    private ArrayList<TableView> tables;

    @FXML
    private JFXTabPane calendarTabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        controller = Controller.getSingletonController();

        controller.generateCalendar();
        tables = new ArrayList<>();
        calendar = controller.getCalendar();
        for (int i = 0; i < calendar.size(); i++) {
            TableView<Duel> table = new TableView<Duel>();
            TableColumn<Duel, String> col = new TableColumn<>("Local");
            TableColumn<Duel, String> col2 = new TableColumn<>("Visitante");
            col.setCellValueFactory(new PropertyValueFactory<>("local"));
            col2.setCellValueFactory(new PropertyValueFactory<>("visitor"));

            ObservableList<TableColumn<Duel,?>> columns = table.getColumns();
            columns.add(col);
            columns.add(col2);
            for (int j = 0; j < calendar.get(i).getGames().size(); j++) {
                int posLocal = calendar.get(i).getGames().get(j).get(0);
                int posVisitor = calendar.get(i).getGames().get(j).get(1);
                table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                //table.getItems().add(controller.getSingletonController().getTeams().get(posVisitante));
            }

            Tab tab = new Tab("Fecha " + (i + 1));
            tab.setContent(table);
            tables.add(table);
            calendarTabPane.getTabs().add(tab);
        }
    }

    @FXML
    void saveExcel(ActionEvent event) throws IOException {
        System.out.println("HELLO");


        Workbook workbook = new HSSFWorkbook();
        for (int k = 0; k < tables.size() ; k++) {
            TableView<Duel> table = tables.get(k);
            Sheet spreadsheet = workbook.createSheet("Fecha " + (k+1));

            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < table.getColumns().size(); j++) {
                row.createCell(j).setCellValue(table.getColumns().get(j).getText());
            }

            for (int i = 0; i < table.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < table.getColumns().size(); j++) {
                    if(table.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                    }
                    else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
        }


        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("Calendario.xls");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



