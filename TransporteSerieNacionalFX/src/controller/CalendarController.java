package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Controller;
import logic.Date;
import logic.Duel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private ArrayList<Date> calendar;
    private  Controller controller;
    private  ArrayList<TableView> tables;
    private HomeController homeController;
    public static boolean saved = false; // boolean that indicates if the file was saved;

    @FXML
    private JFXButton btnStatistics;





    @FXML
    private JFXButton saveExcel;

    @FXML
    private JFXTabPane calendarTabPane;

    @FXML
    private Label lblCalendarKM;

    @FXML
    private Label lblMoreKMTeam;

    @FXML
    private Label lblLessKMTeam;

    @FXML
    private Label lblLessKM;

    @FXML
    private Label lblMoreKM;

    @FXML
    private JFXButton configMutations;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        controller = Controller.getSingletonController();
        boolean generated = controller.isGeneratedCalendar();
        boolean copied = controller.isCopied();

        if(copied){
            calendar = controller.getCalendarCopy();
            controller.lessStatistics(calendar);
            controller.moreStatistics(calendar);
        }else{

            if(generated) {
                controller.generateCalendar();
            }else {
                controller.lessStatistics(controller.getCalendar());
                controller.moreStatistics(controller.getCalendar());
            }
            calendar = controller.getCalendar();
        }


        tables = new ArrayList<>();
        //calendar = controller.getCalendar();
        float distance = controller.calculateDistance(calendar);
        float lessDistance = controller.getLessDistance();
        String teamLessDistance = controller.getTeamLessDistance();

        float moreDistance = controller.getMoreDistance();
        String teamMoreDistance = controller.getTeamMoreDistance();
        lblCalendarKM.setText(""+distance);
        lblLessKM.setText(""+lessDistance);
        lblLessKMTeam.setText(""+teamLessDistance);
        lblMoreKM.setText(""+moreDistance);
        lblMoreKMTeam.setText(""+teamMoreDistance);
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
            }

            Tab tab = new Tab("Fecha " + (i + 1));
            tab.setContent(table);
            tables.add(table);
            calendarTabPane.getTabs().add(tab);
        }
    }

    @FXML
    void saveExcel(ActionEvent event) throws IOException {

        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(new Stage());

        Workbook workbook = new XSSFWorkbook();
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

            fileOut = new FileOutputStream(f.getAbsolutePath()+"/ Calendario Serie Nacional.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            saved = true;
            showSuccessfulMessage();
        } catch (Exception e) {
            saved = false;
            e.printStackTrace();
        }
    }


    @FXML
    void configMutations(ActionEvent event)  {
        Parent root;
        try {
            /*
            root = FXMLLoader.load(getClass().getResource("/visual/MutationsConfiguration.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Configuración de las mutaciones");
            stage.setScene(new Scene(root));
            stage.show();
            */
            homeController.createPage(new MutationsConfigurationController(), null, "/visual/MutationsConfiguration.fxml");
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showSuccessfulMessage(){
        TrayNotification notification = new TrayNotification();
        notification.setTitle("Guardar Calendario");
        notification.setMessage("Calendario exportado con éxito");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));
    }

    @FXML
    void showStatistics(ActionEvent event) throws IOException {
        homeController.createPage(new CalendarStatisticsController(), null, "/visual/CalendarStatistics.fxml");
    }



}



