package controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.Controller;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The controller for the birthday statistics view.
 * 
 * @author Marco Jakob
 */
public class CalendarStatisticsController {


    @FXML
    private JFXButton backButton;

    private HomeController homeController;

    @FXML
    private BarChart<String, Float> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    private ObservableList<String> data = FXCollections.observableArrayList();

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {


        barChart.setTitle("Estadísticas");
        yAxis.setLabel("Distancia en KM");



        Controller controller = Controller.getSingletonController();
        controller.moreStatistics(controller.getCalendar());
        controller.lessStatistics(controller.getCalendar());

        float lessDistance = controller.getLessDistance();
        String teamLessDistance = controller.getTeamLessDistance();

        float moreDistance = controller.getMoreDistance();
        String teamMoreDistance = controller.getTeamMoreDistance();
        ArrayList<String> xAxisData = new ArrayList<>();

        xAxisData.add("Calendario Original");
        xAxisData.add(teamLessDistance);
        xAxisData.add(teamMoreDistance);


        data.addAll(xAxisData);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Calendarios");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Equipos que menor distancia recorren");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Equipos que más distancia recorren");
        series1.getData().add(new XYChart.Data(xAxisData.get(0), controller.calculateDistance(controller.getCalendar())));
        series2.getData().add(new XYChart.Data(data.get(1), lessDistance));
        series3.getData().add(new XYChart.Data(data.get(2), moreDistance));

        if(controller.isCopied()){
            xAxisData.add("Nuevo Calendario");
            controller.copyMoreStatistics(controller.getCalendarCopy());
            controller.copyLessStatistics(controller.getCalendarCopy());
            float copyLessDistance = controller.getCopyLessDistance();
            float copyMoreDistance = controller.getCopyMoreDistance();
            String copyTeamLessDistance = controller.getCopyTeamLessDistance();
            String copyTeamMoreDistance = controller.getCopyTeamMoreDistance();
            xAxisData.add(copyTeamLessDistance);
            xAxisData.add(copyTeamMoreDistance);
            data.add(xAxisData.get(3));
            data.add(xAxisData.get(4));
            data.add(xAxisData.get(5));
            series1.getData().add(new XYChart.Data(xAxisData.get(3), controller.calculateDistance(controller.getCalendarCopy())));
            series2.getData().add(new XYChart.Data(data.get(4), copyLessDistance));
            series3.getData().add(new XYChart.Data(data.get(5), copyMoreDistance));
        }




        barChart.getData().addAll(series1, series2, series3);

        for (XYChart.Series<String, Float> series : barChart.getData()) {
            for (XYChart.Data<String, Float> item : series.getData()) {
                /*item.getNode().setOnMousePressed((MouseEvent event) -> {
                    System.out.println("you clicked " + item.toString() + series.toString());
                    Tooltip.install(item.getNode(), new Tooltip(item.getXValue() + ":\n" + item.getYValue()));
                });*/
                item.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip.install(item.getNode(), new Tooltip(item.getXValue() + ":\n" + item.getYValue()));
                    }
                });

            }
        }
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);

    }

    @FXML
    void returnButton(ActionEvent event) throws IOException {
        AnchorPane structureOver = homeController.getPrincipalPane();
        homeController.createPage(new CalendarController(), structureOver, "/visual/Calendar.fxml");
        homeController.getButtonReturnSelectionTeamConfiguration().setVisible(false);
    }

    /**
     * Sets the persons to show the statistics for.
     * 
     * @param persons
     */
    /*public void setPersonData(List<Person> persons) {
        // Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }*/


}