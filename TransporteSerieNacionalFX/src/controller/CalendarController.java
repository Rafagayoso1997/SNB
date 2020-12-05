package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import logic.CalendarConfiguration;
import logic.Controller;
import logic.Date;
import logic.Duel;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private ArrayList<Date> calendar;
    private Controller controller;
    private ArrayList<TableView<Duel>> tables;
    private HomeController homeController;
    public static int selectedCalendar;
    private TrayNotification notification;

    @FXML
    private JFXTabPane calendarsTabPane;


    @FXML
    private JFXButton statisticsBtn;

    @FXML
    private JFXButton configurationBtn;

    @FXML
    private JFXButton exportBtn;

    @FXML
    private JFXTabPane calendarTabPane;

    @FXML
    private JFXButton itineraryBtn;

    @FXML
    private JFXButton stadiumItineraryBtn;

    @FXML
    private JFXButton restrictionsBtn;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notification = new TrayNotification();
        selectedCalendar = 0;

        controller = Controller.getSingletonController();

        tables = new ArrayList<>();
        ArrayList<ArrayList<Date>> calendarsList = controller.getCalendarsList();

        for(int i=0; i < calendarsList.size();i++){
            ArrayList<Date> calendar = calendarsList.get(i);
            JFXTabPane currentCalendarTabPane = new JFXTabPane();
            String id = controller.getConfigurations().get(i).getCalendarId();
            //currentCalendarTabPane.setPrefHeight(calendarsTabPane.getHeight());
            for(int j=0; j < calendar.size();j++){
                TableView<Duel> table = new TableView<Duel>();
                TableColumn<Duel, String> col = new TableColumn<>("Local");
                TableColumn<Duel, String> col2 = new TableColumn<>("Visitante");

                col.setCellValueFactory(new PropertyValueFactory<>("local"));
                col2.setCellValueFactory(new PropertyValueFactory<>("visitor"));

                ObservableList<TableColumn<Duel, ?>> columns = table.getColumns();
                columns.add(col);
                columns.add(col2);

                for (int k = 0; k < calendar.get(j).getGames().size(); k++) {
                    int posLocal = calendar.get(j).getGames().get(k).get(0);
                    int posVisitor = calendar.get(j).getGames().get(k).get(1);
                    table.getItems().add(new Duel(controller.getTeams().get(posLocal), controller.getTeams().get(posVisitor)));
                }

                Tab tab = new Tab("Fecha " + (j + 1));
                tab.setContent(table);
                tables.add(table);
                currentCalendarTabPane.getTabs().add(tab);
            }
            Tab tab =  new Tab(id);
            tab.setContent(currentCalendarTabPane);
            calendarsTabPane.getTabs().add(tab);
        }

        calendarsTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        selectedCalendar = calendarsTabPane.getTabs().indexOf(t1);
                    }
                }
        );

        //System.out.println("Mutaciones a oplicar " +controller.getMutationsIndexes().size());
        //boolean generated = controller.isGeneratedCalendar();
        //boolean copied = controller.isCopied();

        //if (copied) {
          //  calendar = controller.getCalendarCopy();
            //controller.lessStatistics(calendar);
            //controller.moreStatistics(calendar);
       // } else {

           /*if (generated) {
               if(controller.getCalendar().size() ==0){
                   try {
                       controller.generateCalendar();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

            }
            calendar = controller.getCalendar();*/
            //controller.setItinerary(controller.teamsItinerary(calendar));


        /*tables = new ArrayList<>();

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
        }*/

    }

    /*@FXML
    void exportCalendar(ActionEvent event) {
        AnchorPane popupExportPane = new AnchorPane();
        VBox vBoxExport = new VBox();
        JFXButton btnExportCalendar = new JFXButton("Exportar Calendario");
        btnExportCalendar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/resources/exportar.png"))));
        btnExportCalendar.setCursor(Cursor.HAND);

        JFXButton btnExportItinerary = new JFXButton("Exportar Itinerario");
        btnExportItinerary.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/resources/exportar.png"))));
        btnExportItinerary.setCursor(Cursor.HAND);

        vBoxExport.getChildren().add(btnExportCalendar);
        vBoxExport.getChildren().add(btnExportItinerary);

        popupExportPane.getChildren().add(vBoxExport);
        JFXPopup popupExport = new JFXPopup(popupExportPane);


        btnExportCalendar.setOnAction(event1 -> {
            ExportFiles.exportCalendarInExcelFormat(this.tables);
            popupExport.hide();
        });
        btnExportItinerary.setOnAction(event1 -> {
            ExportFiles.exportItineraryInExcelFormat();
            popupExport.hide();
        });

        popupExport.show(exportBtn, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT);
    }*/

    @FXML
    void showConfiguration(ActionEvent event) {
        if(!calendarsTabPane.getTabs().isEmpty()) {
            try {
                homeController.createPage(new MutationsConfigurationController(), null, "/visual/MutationsConfiguration.fxml");
                // Hide this current window (if this is what you want)
                // ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            showNotification("No existe ningún calendario", "Debe crear o importar al menos un calendario.");
        }
    }

    @FXML
    void showStatistics(ActionEvent event) {
        if(!calendarsTabPane.getTabs().isEmpty()) {
            try {
                homeController.createPage(new CalendarStatisticsController(), null, "/visual/CalendarStatistics.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            showNotification("No existe ningún calendario", "Debe crear o importar al menos un calendario.");
        }
    }

    @FXML
    void showItinerary(ActionEvent event) {
        if(!calendarsTabPane.getTabs().isEmpty()) {
            try {
                homeController.createPage(new TeamsItineraryController(), null, "/visual/TeamsItinerary.fxml");
                // Hide this current window (if this is what you want)
                // ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            showNotification("No existe ningún calendario", "Debe crear o importar al menos un calendario.");
        }
    }

    @FXML
    void showRestrictions(ActionEvent event) {
        if(!calendarsTabPane.getTabs().isEmpty()){
            try {
                homeController.createPage(new RestrictionsController(), null, "/visual/Restrictions.fxml");
                // Hide this current window (if this is what you want)
                // ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            showNotification("No existe ningún calendario", "Debe crear o importar al menos un calendario.");
        }
    }

    @FXML
    void closeSelectedTab(ActionEvent event) {
        /*
        for (ArrayList<Date> lis: Controller.getSingletonController().getCalendarsList()) {
            System.out.println("Calendario: ");
            System.out.println(lis.get(0).getGames());
            System.out.println();
        }

        for (CalendarConfiguration lis: Controller.getSingletonController().getConfigurations()) {
            System.out.println("Identificadores: ");
            System.out.println(lis.getCalendarId());
            System.out.println();
        }

        System.out.println("Calendario seleccionado: " + selectedCalendar);


        System.out.println("Calendario seleccionado" + Controller.getSingletonController().getCalendarsList().get(selectedCalendar).get(0).getGames());
        System.out.println("Configuracion seleccionada" + Controller.getSingletonController().getConfigurations().get(selectedCalendar).getCalendarId());
*/
        Controller.getSingletonController().getCalendarsList().remove(selectedCalendar);
        Controller.getSingletonController().getConfigurations().remove(selectedCalendar);
        calendarsTabPane.getTabs().remove(selectedCalendar);

/*
        System.out.println("Despues de eliminar: ");
        for (ArrayList<Date> lis: Controller.getSingletonController().getCalendarsList()) {
            System.out.println("Calendario: ");
            System.out.println(lis.get(0).getGames());
            System.out.println();
        }

        for (CalendarConfiguration lis: Controller.getSingletonController().getConfigurations()) {
            System.out.println("Identificadores: ");
            System.out.println(lis.getCalendarId());
            System.out.println();
        }*/
    }

    private void showNotification(String title, String message) {
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.ERROR);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(1));
    }
}



