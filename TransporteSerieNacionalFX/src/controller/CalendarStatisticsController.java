package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.CalendarStatistic;
import logic.Controller;
import logic.Date;
import org.apache.poi.ss.formula.functions.MatrixFunction;

import java.util.ArrayList;

public class CalendarStatisticsController {

    @FXML
    private JFXButton backButton;

    private HomeController homeController;

    @FXML
    private BarChart<String, Float> barChartCalendar;

    @FXML
    private CategoryAxis xAxisCalendar;

    @FXML
    private NumberAxis yAxisCalendar;

    @FXML
    private BarChart<String, Float> barChartLessTeam;

    @FXML
    private CategoryAxis xAxisLessTeam;

    @FXML
    private NumberAxis yAxisLessTeam;

    @FXML
    private BarChart<String, Float> barChartMoreTeam;

    @FXML
    private CategoryAxis xAxisMoreTeam;

    @FXML
    private NumberAxis yAxisMoreTeam;


   /* @FXML
    private BarChart<String, Float> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;*/


    private ObservableList<String> calendarData = FXCollections.observableArrayList();
    private ObservableList<String> lessTeamData = FXCollections.observableArrayList();
    private ObservableList<String> moreTeamData = FXCollections.observableArrayList();

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */



    @FXML
    private void initialize() {


        //barChart.setTitle("Estadísticas");
        yAxisCalendar.setLabel("Distancia en KM");
        yAxisLessTeam.setLabel("Distancia en KM");
        yAxisMoreTeam.setLabel("Distancia en KM");


        Controller controller = Controller.getSingletonController();
        ArrayList<ArrayList<Date>> calendarsList = controller.getCalendarsList();

        ArrayList<String> xAxisCalendarData = new ArrayList<>();
        ArrayList<String> xAxisLessTeamData = new ArrayList<>();
        ArrayList<String> xAxisMoreTeamData = new ArrayList<>();


        if(calendarsList.size() == 1){
            barChartCalendar.setBarGap(0);
            barChartCalendar.setCategoryGap(900);
            barChartLessTeam.setBarGap(0);
            barChartLessTeam.setCategoryGap(900);
            barChartMoreTeam.setBarGap(0);
            barChartMoreTeam.setCategoryGap(900);
        }

        CalendarStatistic statistics = null;
        for(int i =0; i < calendarsList.size();i++){
            
            //Distancias de los calendarios
            ArrayList<Date> calendar = calendarsList.get(i);
            xAxisCalendarData.add(controller.getConfigurations().get(i).getCalendarId());
            calendarData.add(xAxisCalendarData.get(i));
            XYChart.Series<String, Float>seriesCalendar = new XYChart.Series<String, Float>();
            seriesCalendar.setName(calendarData.get(i));
            seriesCalendar.getData().add(new XYChart.Data(xAxisCalendarData.get(i), controller.calculateDistance(calendar,controller.getConfigurations().get(i))));
            barChartCalendar.getData().addAll(seriesCalendar);
            
            //Estadísticas de los calendarios
            
            //estadisticas de los equipos que menos distancias recorren
            
            statistics = controller.lessStatistics(calendar);
            xAxisLessTeamData.add(statistics.getTeam());
            lessTeamData.addAll(xAxisLessTeamData);
            XYChart.Series<String, Float> seriesLessTeam = new XYChart.Series<String,Float>();

            seriesLessTeam.setName("Equipo que menor distancia recorre del "+ xAxisCalendarData.get(i));
            seriesLessTeam.getData().add(new XYChart.Data(xAxisLessTeamData.get(i), statistics.getDistance()));
            barChartLessTeam.getData().addAll(seriesLessTeam);

            statistics = controller.moreStatistics(calendar);
            xAxisMoreTeamData.add(statistics.getTeam());
            moreTeamData.addAll(xAxisMoreTeamData);
            XYChart.Series<String, Float> seriesMoreTeam = new XYChart.Series<String,Float>();

            seriesMoreTeam.setName("Equipo que mayor distancia recorre del "+ xAxisCalendarData.get(i));
            seriesMoreTeam.getData().add(new XYChart.Data(xAxisMoreTeamData.get(i), statistics.getDistance()));
            barChartMoreTeam.getData().addAll(seriesMoreTeam);
            
        }

        setTooltipToChart(barChartCalendar);

        setTooltipToChart(barChartLessTeam);

        setTooltipToChart(barChartMoreTeam);

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
            homeController.getButtonReturnSelectionTeamConfiguration().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
