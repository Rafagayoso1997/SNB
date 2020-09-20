package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Controller;
import logic.Date;
import logic.ReadExcel;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static boolean conf = false;
    public static boolean escogidos = false;
    public static boolean matrix = false;
    private TrayNotification notification;
    private File file;


    @FXML
    private JFXButton buttonPrincipalMenu;


    @FXML
    private JFXButton buttonCalendarConfiguration;


    @FXML
    private JFXButton buttonImportCalendar;


    @FXML
    private AnchorPane pane;

    private AnchorPane home;


    @FXML
    private JFXButton buttonReturnSelectionTeamConfiguration;



    @FXML
    private Label information;

    @FXML
    private JFXButton buttonInfromation;

    public JFXButton getButtonReturnSelectionTeamConfiguration() {
        return buttonReturnSelectionTeamConfiguration;
    }

    public void setButtonReturnSelectionTeamConfiguration(JFXButton buttonReturnSelectionTeamConfiguration) {
        this.buttonReturnSelectionTeamConfiguration = buttonReturnSelectionTeamConfiguration;
    }

    @FXML
    void showCalendar(ActionEvent event) throws IOException {

        this.createPage(new ConfigurationCalendarController(), home, "/visual/ConfigurationCalendar.fxml");
        Controller.getSingletonController().setGeneratedCalendar(true);
        Controller.getSingletonController().setCopied(false);

    }


    @FXML
    void showReturnSelectionTeamConfiguration(ActionEvent event) throws IOException {
        this.createPage(new ConfigurationCalendarController(), home, "/visual/ConfigurationCalendar.fxml");
        buttonReturnSelectionTeamConfiguration.setVisible(false);

    }

    @FXML
    void showTeams(ActionEvent event) throws IOException {
        this.createPage(home, "/visual/Teams.fxml");
        notification = getNotification();
        notification.setTitle("Listado de Equipos");
        notification.setMessage("Equipos participantes");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));

    }

    @FXML
    void importCalendar(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documento Excel", "*xlsx"));
        file = fc.showOpenDialog(stage);

        try {

            if (file != null) {
                ArrayList<Date> importedCalendar = ReadExcel.readExcel(file.toString());
                ArrayList<Integer> indexes = new ArrayList<>();
                for(int i=0; i < importedCalendar.get(0).getGames().size();i++){
                    ArrayList<Integer> game = importedCalendar.get(0).getGames().get(i);
                    indexes.addAll(game);
                }
                Collections.sort(indexes);
                System.out.println(indexes);


                Controller.getSingletonController().setTeamsIndexes(indexes);
                Controller.getSingletonController().setCalendar(importedCalendar);

            /*ArrayList<Date> calendar = Controller.getSingletonController().getCalendar();
            System.out.println("Calendario Final:");
            for (int i= 0; i < calendar.size(); i++) {
                for(int j=0; j < calendar.get(i).getGames().size();j++){
                    int posLocal = calendar.get(i).getGames().get(j).get(0);
                    int posVisitor = calendar.get(i).getGames().get(j).get(1);
                    System.out.print("["+ Controller.getSingletonController().getTeams().get(posLocal)+","+ Controller.getSingletonController().getTeams().get(posVisitor)+"]");

                }
                System.out.println(" ");
            }*/

                notification = getNotification();
                notification.setTitle("Importación de Calendario");
                notification.setMessage("Calendario importado con éxito");
                notification.setNotificationType(NotificationType.SUCCESS);
                notification.setRectangleFill(Paint.valueOf("#2F2484"));
                notification.setAnimationType(AnimationType.FADE);
                notification.showAndDismiss(Duration.seconds(2));
                buttonReturnSelectionTeamConfiguration.setVisible(false);
                Controller.getSingletonController().setGeneratedCalendar(false);
                Controller.getSingletonController().setCopied(false);
                this.createPage(new CalendarController(),home, "/visual/Calendar.fxml");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showInformation(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        information.setText(" " + (year - 1960) + " Serie Nacional de Be\u00edsbol");
        buttonReturnSelectionTeamConfiguration.setVisible(false);
    }


    public void setNode(Node node) {
        pane.getChildren().clear();
        pane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(2000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    public void createPage(AnchorPane home, String loc) throws IOException {
        home = FXMLLoader.load(getClass().getResource(loc));
        setNode(home);
    }

    private TrayNotification getNotification() {
        return new TrayNotification();
    }

    //********************DAVID CHaNGE
    public AnchorPane getPrincipalPane() {
        return this.pane;
    }

    public void createPage(Object object, AnchorPane anchorPane, String loc) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HomeController.class.getResource(loc));
        anchorPane = loader.load();

        if (object instanceof MutationsConfigurationController) {

            Parent root = loader.load(getClass().getResource("/visual/MutationsConfiguration.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Configuración de las mutaciones");
            stage.setScene(new Scene(anchorPane));


            object = loader.getController();
            ((MutationsConfigurationController) object).setHomeController(this);

            stage.show();
        } else if (object instanceof CalendarController) {
            object = loader.getController();
            ((CalendarController) object).setHomeController(this);
            setNode(anchorPane);
        } else if (object instanceof SelectGridController) {
            object = loader.getController();
            ((SelectGridController) object).setHomeController(this);
            setNode(anchorPane);
        } else if (object instanceof ConfigurationCalendarController) {
            object = loader.getController();
            ((ConfigurationCalendarController) object).setHomeController(this);
            setNode(anchorPane);
        }
        else if (object instanceof CalendarStatisticsController) {
            object = loader.getController();

            ((CalendarStatisticsController) object).setHomeController(this);
            setNode(anchorPane);
        }
    }
}
