package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.CalendarConfiguration;
import logic.Controller;
import logic.Date;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RestrictionsController implements Initializable {

    @FXML
    private JFXButton closeButton;

    @FXML
    private Label lblLocalText;

    @FXML
    private Label lblVisitorText;

    @FXML
    private Label lblLocalNumber;

    @FXML
    private Label lblVisitorNumber;

    @FXML
    private Label lblLongTripNumber;



    private HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */



    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        Controller controller = Controller.getSingletonController();
        ArrayList<Date> calendar = controller.getCalendarsList().get(CalendarController.selectedCalendar);
        CalendarConfiguration configuration = controller.getConfigurations().get(CalendarController.selectedCalendar);
        ArrayList<ArrayList<Integer>> itinerary = controller.teamsItinerary(calendar,configuration, null);
        System.out.println(configuration);

        int maxVisitorGamesBrokeRule = controller.penalizeGamesVisitor(itinerary,configuration.getMaxVisitorGamesInARow(), configuration.getTeamsIndexes());
        int maxHomeGamesBrokeRule = controller.penalizeGamesHome(itinerary,configuration.getMaxLocalGamesInARow(), configuration.getTeamsIndexes());
        int longTripBrokeRule = controller.checkLongTrips(itinerary,configuration.getTeamsIndexes());

        lblLocalText.setText(lblLocalText.getText().replace("#",Integer.toString(configuration.getMaxLocalGamesInARow())));
        lblVisitorText.setText(lblVisitorText.getText().replace("#",Integer.toString(configuration.getMaxVisitorGamesInARow())));

        lblLocalNumber.setText(Integer.toString(maxHomeGamesBrokeRule));
        lblVisitorNumber.setText(Integer.toString(maxVisitorGamesBrokeRule));
        lblLongTripNumber.setText(Integer.toString(longTripBrokeRule));



    }

    @FXML
    void closeButtonAction(ActionEvent event) {
    // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }




}
