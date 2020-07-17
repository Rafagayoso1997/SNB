package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static boolean conf = false;
    public static boolean escogidos = false;
    public static boolean matrix = false;
    boolean displayed = false;
    private TrayNotification notification;
    @FXML
    private JFXButton buttonCalendar;

    @FXML
    private JFXButton buttonTeam;

    @FXML
    private JFXButton buttonPrincipalMenu;

    @FXML
    private JFXButton buttonConfigurationSelecctionTeams;


    @FXML
    private JFXButton buttonCalendarConfiguration;

    @FXML
    private JFXButton buttonConfMatrix;

    @FXML
    private Pane sidePane;

    @FXML
    private AnchorPane pane;

    private AnchorPane home;

    @FXML
    private JFXButton buttonReturnSelectionTeamConfiguration;


    @FXML
    private JFXButton buttonReturnCalendarConfiguration;


    @FXML
    private Label information;

    @FXML
    private JFXButton buttonInfromation;


    @FXML
    void showCalendar(ActionEvent event) throws IOException {
        if (!conf) {
            TrayNotification notification = new TrayNotification("Generación de Calendario",
                    "Debe completar todas las configuraciones", NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#E81123"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
        } else {
            this.createPage(home, "/visual/Calendar.fxml");
            notification = getNotification();
            notification.setTitle("Generación de Calendario");
            notification.setMessage("Calendario del torneo");
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
            conf = false;
            buttonReturnCalendarConfiguration.setVisible(false);
            buttonConfigurationSelecctionTeams.setVisible(true);
        }
    }

    @FXML
    void showConfigurationSelectionTeams(ActionEvent event) throws IOException {
        this.createPage(home, "/visual/SelectionTeams.fxml");
        buttonConfigurationSelecctionTeams.setVisible(false);
        buttonCalendarConfiguration.setVisible(true);

    }

    @FXML
    void showCalendarConfiguration(ActionEvent event) throws IOException {
        if (!escogidos) {
            notification = getNotification();
            notification.setTitle("Escoger equipos");
            notification.setMessage("Debe escoger los equipos participantes");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
        } else {
            this.createPage(home, "/visual/Configuration.fxml");
            buttonReturnSelectionTeamConfiguration.setVisible(true);
            //this.createPage(home,"/visual/SelectGrid.fxml");
            buttonCalendarConfiguration.setVisible(false);
            buttonConfMatrix.setVisible(true);
        }
    }

    @FXML
    void showConfigurationMatrix(ActionEvent event) throws IOException {
        if (!ConfigurationController.ok) {
            notification = getNotification();
            notification.setTitle("Configuración del calendario");
            notification.setMessage("Debe guardar la configuración");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));
        } else {
            this.createPage(home, "/visual/SelectGrid.fxml");
            buttonConfigurationSelecctionTeams.setVisible(false);
            buttonConfMatrix.setVisible(false);
            buttonReturnSelectionTeamConfiguration.setVisible(false);
            buttonReturnCalendarConfiguration.setVisible(true);
            pane.resize(1000,1000);
            System.out.print(pane.getHeight() + " " + pane.getWidth());


        }
    }

    @FXML
    void showReturnSelectionTeamConfiguration(ActionEvent event) throws IOException {
        this.createPage(home, "/visual/SelectionTeams.fxml");
        buttonConfigurationSelecctionTeams.setVisible(false);
        buttonCalendarConfiguration.setVisible(true);
        buttonReturnSelectionTeamConfiguration.setVisible(false);
        buttonConfMatrix.setVisible(false);
    }

    @FXML
    void showReturnCalendarConfiguration(ActionEvent event) throws IOException {
        this.createPage(home, "/visual/Configuration.fxml");
        buttonConfMatrix.setVisible(true);
        buttonReturnSelectionTeamConfiguration.setVisible(true);
        buttonReturnCalendarConfiguration.setVisible(false);
    }


    @FXML
    void showPrincipalMenu(ActionEvent event) throws IOException {
        System.out.println("MENU");
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
    void showInformation(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        information.setText(" " + (year - 1960) + " Serie Nacional de Be\u00edsbol");
        buttonCalendarConfiguration.setVisible(false);
        buttonConfMatrix.setVisible(false);
        buttonReturnSelectionTeamConfiguration.setVisible(false);
        buttonReturnCalendarConfiguration.setVisible(false);
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


}
