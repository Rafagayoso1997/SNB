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
import logic.Controller;

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
        controller.moreStatistics(controller.getCalendar());
        controller.lessStatistics(controller.getCalendar());

        float lessDistance = controller.getLessDistance();
        String teamLessDistance = controller.getTeamLessDistance();

        float moreDistance = controller.getMoreDistance();
        String teamMoreDistance = controller.getTeamMoreDistance();

        ArrayList<String> xAxisCalendarData = new ArrayList<>();
        ArrayList<String> xAxisLessTeamData = new ArrayList<>();
        ArrayList<String> xAxisMoreTeamData = new ArrayList<>();

        //ArrayList<String> xAxisData = new ArrayList<>();

        /****CAlendarios*****/
        xAxisCalendarData.add("Calendario Original");
        calendarData.addAll(xAxisCalendarData);
        XYChart.Series seriesOriginalCalendar = new XYChart.Series();
        XYChart.Series seriesCopyCalendar = new XYChart.Series();

        seriesOriginalCalendar.setName("Calendario Original");
        seriesOriginalCalendar.getData().add(new XYChart.Data(xAxisCalendarData.get(0), controller.calculateDistance(controller.getCalendar())));

        /****Menor Distancia*****/
        xAxisLessTeamData.add(teamLessDistance);
        lessTeamData.addAll(xAxisLessTeamData);
        XYChart.Series seriesOriginalLessTeam = new XYChart.Series();
        XYChart.Series seriesCopyLessTeam= new XYChart.Series();

        seriesOriginalLessTeam.setName("Equipo que menor distancia recorre del Calendario Original");
        seriesOriginalLessTeam.getData().add(new XYChart.Data(xAxisLessTeamData.get(0), lessDistance));

        /****Mayor Distancia***/


        xAxisMoreTeamData.add(teamMoreDistance);
        moreTeamData.addAll(xAxisMoreTeamData);
        XYChart.Series seriesOriginalMoreTeam = new XYChart.Series();
        XYChart.Series seriesCopyMoreTeam= new XYChart.Series();

        seriesOriginalMoreTeam.setName("Equipo que mayor distancia recorre del Calendario Original");
        seriesOriginalMoreTeam.getData().add(new XYChart.Data(xAxisMoreTeamData.get(0), moreDistance));

        //barChartCalendar.getData().addAll(seriesOriginalCalendar);
       /* xAxisData.add(teamLessDistance);
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
        series3.getData().add(new XYChart.Data(data.get(2), moreDistance));*/

        if (controller.isCopied()) {
            xAxisCalendarData.add("Nuevo Calendario");
            controller.copyMoreStatistics(controller.getCalendarCopy());
            controller.copyLessStatistics(controller.getCalendarCopy());
            float copyLessDistance = controller.getCopyLessDistance();
            float copyMoreDistance = controller.getCopyMoreDistance();
            String copyTeamLessDistance = controller.getCopyTeamLessDistance();
            String copyTeamMoreDistance = controller.getCopyTeamMoreDistance();
            xAxisLessTeamData.add(copyTeamLessDistance);
            xAxisMoreTeamData.add(copyTeamMoreDistance);

            seriesCopyCalendar.setName("Nuevo Calendario");
            seriesCopyCalendar.getData().add(new XYChart.Data(xAxisCalendarData.get(1), controller.calculateDistance(controller.getCalendarCopy())));
            seriesCopyLessTeam.setName("Equipo que menor distancia recorre del Nuevo Calendario");
            seriesCopyLessTeam.getData().add(new XYChart.Data(xAxisLessTeamData.get(1), copyLessDistance));
            seriesCopyMoreTeam.setName("Equipo que mayor distancia recorre del Nuevo Calendario");
            seriesCopyMoreTeam.getData().add(new XYChart.Data(xAxisLessTeamData.get(1), copyMoreDistance));

        }


        barChartCalendar.getData().addAll(seriesOriginalCalendar/*, series2, series3*/);
        barChartLessTeam.getData().add(seriesOriginalLessTeam);
        barChartMoreTeam.getData().add(seriesOriginalMoreTeam);

        if(seriesCopyCalendar.getData().size()>0){
            barChartCalendar.getData().addAll(seriesCopyCalendar);

            barChartLessTeam.getData().addAll(seriesCopyLessTeam);

            barChartMoreTeam.getData().addAll(seriesCopyMoreTeam);

            barChartCalendar.setCategoryGap(300);
            barChartLessTeam.setCategoryGap(300);
            barChartMoreTeam.setCategoryGap(300);
        }
        /*if(seriesCopyLessTeam.getData().size()>0){
            barChartLessTeam.getData().addAll(seriesCopyLessTeam);
            //barChartCalendar.setBarGap(3);
            barChartLessTeam.setCategoryGap(300);
        }*/


        /*barChartCalendar.setBarGap(3);
        barChartCalendar.setCategoryGap(20);*/

        setTooltipToChart(barChartCalendar);

        setTooltipToChart(barChartLessTeam);

        setTooltipToChart(barChartMoreTeam);


        AnchorPane popupPane = new AnchorPane();
        VBox vBox = new VBox();
        JFXListView<JFXButton> list = new JFXListView<JFXButton>();
        JFXButton btnCurrentCalendar = new JFXButton("Mantener el calendario original");
        btnCurrentCalendar.setCursor(Cursor.HAND);
        JFXButton btnNewCalendar = new JFXButton("Mantener el calendario nuevo");
        btnCurrentCalendar.setCursor(Cursor.HAND);


        vBox.getChildren().add(btnCurrentCalendar);
        vBox.getChildren().add(btnNewCalendar);
        popupPane.getChildren().add(vBox);
        JFXPopup popup = new JFXPopup(popupPane);

        if (controller.getCalendarCopy().size()>0) {
            backButton.setOnAction(event -> {
                popup.show(backButton, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
            });
            btnCurrentCalendar.setOnAction(event -> {
                popup.hide();
                returnButton();
            });
            btnNewCalendar.setOnAction(event -> {
                controller.setCalendar(controller.getCalendarCopy());
                returnButton();
                popup.hide();
            });


        }
        else{
            backButton.setOnAction( event -> returnButton());
        }


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
