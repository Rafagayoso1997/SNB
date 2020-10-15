package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.CalendarStatistic;
import logic.Controller;
import logic.Date;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RestrictionsController implements Initializable {
    @FXML
    private JFXButton backButton;

    private HomeController homeController;

    @FXML
    private BarChart<String, Float> barChartCalendar;

    @FXML
    private CategoryAxis xAxisCalendar;

    @FXML
    private NumberAxis yAxisCalendar;


    private ObservableList<String> calendarData = FXCollections.observableArrayList();

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */



    @FXML
    public void initialize(URL location, ResourceBundle resources) {


        //barChart.setTitle("Estadísticas");
        yAxisCalendar.setLabel("Distancia en KM");

        Controller controller = Controller.getSingletonController();
        ArrayList<ArrayList<Date>> calendarsList = controller.getCalendarsList();

        ArrayList<String> xAxisCalendarData = new ArrayList<>();
        ArrayList<String> xAxisLessTeamData = new ArrayList<>();
        ArrayList<String> xAxisMoreTeamData = new ArrayList<>();

        setTooltipToChart(barChartCalendar);

    }



    static void setTooltipToChart(BarChart<String, Float> barChart) {
        for (XYChart.Series<String, Float> series : barChart.getData()) {
            for (XYChart.Data<String, Float> item : series.getData()) {
                item.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip.install(item.getNode(), new Tooltip(item.getXValue() + ":\n" + item.getYValue()));
                    }
                });

            }
        }
    }


    //@FXML
    @FXML
    private void returnButton(/*ActionEvent event*/) {
        try {
            AnchorPane structureOver = homeController.getPrincipalPane();
            homeController.createPage(new CalendarController(), structureOver, "/visual/Calendar.fxml");
            homeController.getButtonReturnSelectionTeamConfiguration().setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
