package file_management;

import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.CalendarConfiguration;
import logic.Controller;
import logic.Date;
import logic.Duel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CrudsFiles {
    private  static FileChooser fc;
    private static DirectoryChooser dc;

     private static File f;
     private static XSSFWorkbook workbook;

    public CrudsFiles(){

    }

    public static void addModifyTeamToData(String teamName, String acronym, Double[] distances, int pos) throws IOException {

        FileInputStream fis = new FileInputStream("src/files/Data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);


        XSSFFont headerCellFont = workbook.createFont();
        headerCellFont.setBold(true);
        headerCellFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellFont.setFontHeightInPoints((short) 11);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(headerCellFont);

        //int addPos = Controller.getSingletonController().getTeams().size() + 1;
        Cell cell = xssfSheet.getRow(0).createCell(pos);
        cell.setCellStyle(style);
        cell.setCellValue(acronym);

        style = workbook.createCellStyle();
        headerCellFont = workbook.createFont();
        headerCellFont.setBold(false);
        headerCellFont.setFontHeightInPoints((short) 12);
        style.setFont(headerCellFont);


        for (int i = 1; i < distances.length+1; i++){
            cell = xssfSheet.getRow(i).createCell(pos);
            cell.setCellStyle(style);
            cell.setCellValue(distances[i-1]);
        }

        Row row = xssfSheet.createRow(pos);
        cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(teamName);

        for (int i = 1; i < distances.length+1; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(distances[i-1]);
        }

        if(pos == (Controller.getSingletonController().getTeams().size() + 1)){
            cell = row.createCell(distances.length+1);
            cell.setCellStyle(style);
            cell.setCellValue(0);
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("src/files/Data.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeTeamFromData(int pos) throws IOException {

        FileInputStream fis = new FileInputStream("src/files/Data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);


        for(int i = 0; i < Controller.getSingletonController().getTeams().size()+1; i++){
            Cell cell = xssfSheet.getRow(i).getCell(pos);
            xssfSheet.getRow(i).removeCell(cell);
        }

        if(pos < xssfSheet.getRow(0).getLastCellNum()){
            xssfSheet.shiftColumns(pos+1, xssfSheet.getRow(0).getLastCellNum(), -1);
        }

        Row row = xssfSheet.getRow(pos);
        xssfSheet.removeRow(row);

        if (pos < xssfSheet.getLastRowNum()){
            xssfSheet.shiftRows(pos+1, xssfSheet.getLastRowNum(), -1);
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("src/files/Data.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
