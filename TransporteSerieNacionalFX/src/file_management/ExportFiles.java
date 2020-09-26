package file_management;

import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Controller;
import logic.Duel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ExportFiles {
    private static DirectoryChooser dc;

     private static File f;
     private static XSSFWorkbook workbook;

    public ExportFiles(){

    }

    public static void exportCalendarInExcelFormat(ArrayList<TableView<Duel>> tables){
        dc = new DirectoryChooser();
        f = dc.showDialog(new Stage());

        workbook = new XSSFWorkbook();

        XSSFFont headerCellFont = workbook.createFont();
        headerCellFont.setBold(true);
        headerCellFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellFont.setFontHeightInPoints((short) 15);
        XSSFCellStyle style = workbook.createCellStyle();

        // Setting Background color
        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerCellFont);


        for (int k = 0; k < tables.size(); k++) {
            TableView<Duel> table = tables.get(k);
            Sheet spreadsheet = workbook.createSheet("Fecha " + (k + 1));

            Row row = spreadsheet.createRow(0);

            for (int i = 0; i < table.getColumns().size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(table.getColumns().get(i).getText());
            }



            style = workbook.createCellStyle();
            headerCellFont = workbook.createFont();
            headerCellFont.setBold(false);
            headerCellFont.setFontHeightInPoints((short) 12);
            for (int i = 0; i < table.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < table.getColumns().size(); j++) {
                    Cell cell = row.createCell(j);
                    if (table.getColumns().get(j).getCellData(i) != null) {
                        cell.setCellValue(table.getColumns().get(j).getCellData(i).toString());
                    } else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(style);
                }
            }

            for(int i=0; i < row.getLastCellNum();i++){
                spreadsheet.autoSizeColumn(i);
            }

        }


        FileOutputStream fileOut = null;
        try {

            fileOut = new FileOutputStream(f.getAbsolutePath() + "/ Calendario Serie Nacional.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            showSuccessfulMessage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void exportItineraryInExcelFormat(){
        dc = new DirectoryChooser();
        f = dc.showDialog(new Stage());

        workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Calendario");


        Controller controller =Controller.getSingletonController();
        ArrayList<ArrayList<Integer>> teamDate = controller.teamsItinerary(controller.getCalendar());
        Row row = spreadsheet.createRow(0);
        //Style of the cell
        XSSFFont headerCellFont = workbook.createFont();
        headerCellFont.setBold(true);
        headerCellFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellFont.setFontHeightInPoints((short) 15);
        XSSFCellStyle style = workbook.createCellStyle();

        // Setting Background color
        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerCellFont);

        //Header of the itinerary
        for (int i = 0; i < teamDate.get(0).size(); i++) {
            int posTeam = teamDate.get(0).get(i);
            String team = controller.getTeams().get(posTeam);
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(team);
        }

        //Itinerary
        style = workbook.createCellStyle();
        headerCellFont = workbook.createFont();
        headerCellFont.setBold(false);
        headerCellFont.setFontHeightInPoints((short) 12);


        for(int i=1; i < teamDate.size()-1;i++ ){
            ArrayList<Integer> date = teamDate.get(i);
            row = spreadsheet.createRow(i);
            for(int j=0; j < date.size();j++){
                int posTeam = teamDate.get(i).get(j);
                String team = controller.getAcronyms().get(posTeam);
                Cell cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(team);
            }
        }


        //autosize each column of the excel document
        for(int i=0; i < row.getLastCellNum(); i++){
            spreadsheet.autoSizeColumn(i);
        }



        FileOutputStream fileOut = null;
        try {

            fileOut = new FileOutputStream(f.getAbsolutePath() + "/ Itinerario del Calendario Serie Nacional.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            showSuccessfulMessage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void showSuccessfulMessage() {
        TrayNotification notification = new TrayNotification();
        notification.setTitle("Guardar Calendario");
        notification.setMessage("Calendario exportado con éxito");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));
    }
}
