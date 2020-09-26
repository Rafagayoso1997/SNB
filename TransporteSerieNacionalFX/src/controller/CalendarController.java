package controller;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Controller;
import logic.Date;
import logic.Duel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private ArrayList<Date> calendar;
    private Controller controller;
    private ArrayList<TableView> tables;
    private HomeController homeController;
    public static boolean saved = false; // boolean that indicates if the file was saved;

    @FXML
    private JFXButton btnStatistics;

    @FXML
    private JFXButton popupBtn;


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

        System.out.println("Mutaciones a oplicar " +controller.getMutationsIndexes().size());
        boolean generated = controller.isGeneratedCalendar();
        boolean copied = controller.isCopied();

        //if (copied) {
          //  calendar = controller.getCalendarCopy();
            //controller.lessStatistics(calendar);
            //controller.moreStatistics(calendar);
       // } else {

           if (generated) {
               if(controller.getCalendar().size() ==0){
                   controller.generateCalendar();
               }

            }
            calendar = controller.getCalendar();


        tables = new ArrayList<>();

        for (int i = 0; i < calendar.size(); i++) {
            TableView<Duel> table = new TableView<Duel>();
            TableColumn<Duel, String> col = new TableColumn<>("Local");
            TableColumn<Duel, String> col2 = new TableColumn<>("Visitante");
            col.setCellValueFactory(new PropertyValueFactory<>("local"));
            col2.setCellValueFactory(new PropertyValueFactory<>("visitor"));

            ObservableList<TableColumn<Duel, ?>> columns = table.getColumns();
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


        AnchorPane popupPane = new AnchorPane();
        VBox vBox = new VBox();
        JFXButton btnStat = new JFXButton("Mostrar estadísticas");
        btnStat.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/resources/bar_chart.png"))));
        btnStat.setCursor(Cursor.HAND);
        JFXButton btnExcel = new JFXButton("Exportar calendario ");
        btnExcel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/resources/exportar.png"))));
        btnExcel.setCursor(Cursor.HAND);
        JFXButton btnMutations = new JFXButton("Configurar cambios ");
        btnMutations.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/resources/switch.png"))));
        btnMutations.setCursor(Cursor.HAND);

        vBox.getChildren().add(btnStat);
        vBox.getChildren().add(btnMutations);
        vBox.getChildren().add(btnExcel);
        popupPane.getChildren().add(vBox);
        JFXPopup popup = new JFXPopup(popupPane);

        popupBtn.setOnAction(event -> {
            popup.show(popupBtn, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT);
        });
        btnStat.setOnAction(event -> {
            showStatistics();
            popup.hide();
        });
        btnExcel.setOnAction(event -> {
            saveExcel();
            popup.hide();

        });
        btnMutations.setOnAction(event -> {
            configMutations();
            popup.hide();
        });
    }

    // @FXML
    void saveExcel(/*ActionEvent event*/) {
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(new Stage());

        Workbook workbook = new XSSFWorkbook();
        for (int k = 0; k < tables.size(); k++) {
            TableView<Duel> table = tables.get(k);
            Sheet spreadsheet = workbook.createSheet("Fecha " + (k + 1));

            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < table.getColumns().size(); j++) {
                row.createCell(j).setCellValue(table.getColumns().get(j).getText());
            }

            for (int i = 0; i < table.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < table.getColumns().size(); j++) {
                    if (table.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
        }


        FileOutputStream fileOut = null;
        try {

            fileOut = new FileOutputStream(f.getAbsolutePath() + "/ Calendario Serie Nacional.xlsx");
            if (fileOut != null) {
                workbook.write(fileOut);
                fileOut.close();
                saved = true;
                showSuccessfulMessage();
            }

        } catch (Exception e) {
            saved = false;
            e.printStackTrace();
        }

    }


    //@FXML
    void configMutations(/*ActionEvent event*/) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showSuccessfulMessage() {
        TrayNotification notification = new TrayNotification();
        notification.setTitle("Guardar Calendario");
        notification.setMessage("Calendario exportado con éxito");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));
    }

    //@FXML
    void showStatistics(/*ActionEvent event*/) {
        try {
            homeController.createPage(new CalendarStatisticsController(), null, "/visual/CalendarStatistics.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



