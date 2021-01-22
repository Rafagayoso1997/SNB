package newlogic;

import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Auxiliar;
import newlogic.CalendarConfiguration;
import newlogic.Controller;
import newlogic.Date;
import newlogic.LocalVisitorDistance;
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
import java.util.List;

public class DataFiles {


    private static DataFiles singletonFiles;//Singleton Pattern
    private ArrayList<String> teams;//List of resources.teams
    private ArrayList<String> acronyms;
    private ArrayList<String> locations;
    private ArrayList<LocalVisitorDistance> positionsDistance;//List of LocalVisitorDistance

    public DataFiles() {
        this.teams = new ArrayList<>();
        this.acronyms = new ArrayList<>();
        this.positionsDistance = new ArrayList<>();
        createTeams();
    }

    public static DataFiles getSingletonDataFiles() {
        if (singletonFiles == null) {
            singletonFiles = new DataFiles();
        }
        return singletonFiles;
    }

    public void addModifyTeamToData(String teamName, String acronym, String location, Double[] distances, int pos) throws IOException {

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

        if(pos == (getTeams().size() + 1)){
            cell = row.createCell(distances.length+1);
            cell.setCellStyle(style);
            cell.setCellValue(0);
        }

        XSSFSheet locationSheet = workbook.getSheetAt(1);
        cell = locationSheet.getRow(0).createCell(pos - 1);
        cell.setCellValue(location);

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("src/files/Data.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeTeamFromData(int pos) throws IOException {

        FileInputStream fis = new FileInputStream("src/files/Data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);


        for(int i = 0; i < getTeams().size()+1; i++){
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

        XSSFSheet locationsSheet = workbook.getSheetAt(1);
        Cell cell = locationsSheet.getRow(0).getCell(pos - 1);
        locationsSheet.getRow(0).removeCell(cell);

        if(pos - 1 < locationsSheet.getRow(0).getLastCellNum() - 1){
            locationsSheet.shiftColumns(pos, locationsSheet.getRow(0).getLastCellNum() - 1, -1);
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

    public  List<String> readMutations() {
        List<String>mutations = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream("src/files/Mutaciones.xlsx");

            //Creamos el objeto XSSF  para el archivo eexcel
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            //Nos quedamos con la primera hoja de calculo

            //XSSFSheet xssfSheet = workbook.getSheetAt(0);

            XSSFSheet xssfSheet = workbook.getSheetAt(0);

            System.out.println(xssfSheet.getSheetName());
            //Recorremos las filas

            //Nos saltamos la primera fila del encabezado

            for (Row row : xssfSheet) {
                //Recorremos las celdas de la fila
                Iterator<Cell> cellIterator = row.cellIterator();

                StringBuilder sb = new StringBuilder();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    sb.append(cell.toString());
                }
                mutations.add(new String(sb));
            }

            workbook.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return mutations;
    }

    //Arreglar para ver el tipo de caeldnario que se importa
    /*public Calendar readExcelItineraryToCalendar(String route) throws IOException {
        Auxiliar aux = new Auxiliar();
        try {
            CalendarConfiguration configuration  = new CalendarConfiguration();
            Controller controller = Controller.getSingletonController();
            ArrayList<Date> calendar = new ArrayList<>();
            //ArrayList<Integer>teamsIndexes = new ArrayList<>();

            FileInputStream fis = new FileInputStream(route);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            XSSFSheet xssfSheetData = workbook.getSheetAt(1);
            Iterator<Row> rowIteratorData = xssfSheetData.iterator();

            configuration.setCalendarId(rowIteratorData.next().getCell(0).getStringCellValue());

            Row rowTeamIndexes = rowIteratorData.next();

            for (Cell cellData : rowTeamIndexes) {
               configuration.getTeamsIndexes().add((int) cellData.getNumericCellValue());
            }

            configuration.setInauguralGame(rowIteratorData.next().getCell(0).getBooleanCellValue());
            configuration.setChampionVsSecondPlace(rowIteratorData.next().getCell(0).getBooleanCellValue());
            configuration.setChampion((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            configuration.setSecondPlace((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            configuration.setSecondRoundCalendar(rowIteratorData.next().getCell(0).getBooleanCellValue());
            configuration.setSymmetricSecondRound(rowIteratorData.next().getCell(0).getBooleanCellValue());
            configuration.setOccidenteVsOriente(rowIteratorData.next().getCell(0).getBooleanCellValue());
            configuration.setMaxLocalGamesInARow((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            configuration.setMaxVisitorGamesInARow((int) rowIteratorData.next().getCell(0).getNumericCellValue());


            XSSFSheet xssfSheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = xssfSheet.iterator();
            rowIterator.next();

            if (configuration.isInauguralGame()) {
                rowIterator.next();
                Date date = new Date();
                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(configuration.getChampion());
                pair.add(configuration.getSecondPlace());
                date.getGames().add(pair);
                calendar.add(date);
            }


            boolean secondRound = configuration.isSecondRoundCalendar();
            int cantRealRowAdded = 0;
            boolean imparTeams = false;
            int restMoment = configuration.getTeamsIndexes().size() - 1;
            if (configuration.getTeamsIndexes().size() % 2 != 0) {
                imparTeams = true;
                //restMoment += 1;
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (!secondRound || cantRealRowAdded != restMoment) {

                    Date date = new Date();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    int i = 0;
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        int local = getAcronyms().indexOf(cell.toString());
                        int visitor = configuration.getTeamsIndexes().get(i);

                        ArrayList<Integer> pair = new ArrayList<>();
                        pair.add(local);
                        pair.add(visitor);

                        if (imparTeams) {
                            boolean added = false;
                            int j = 0;
                            while (j < date.getGames().size() && !added) {

                                if (date.getGames().get(j).contains(local) || date.getGames().get(j).contains(visitor)) {
                                    if (local != visitor) {
                                        date.getGames().remove(j);
                                        date.getGames().add(pair);
                                    }
                                    added = true;
                                }
                                j++;
                            }
                            if (!added) {
                                date.getGames().add(pair);
                            }
                        } else {
                            if (local != visitor) {
                                date.getGames().add(pair);
                            }
                        }
                        i++;
                    }
                    calendar.add(date);
                }
                cantRealRowAdded++;
            }

            aux.setCalendar(calendar);


            workbook.close();
            fis.close();
        }catch (Exception e) {

            TrayNotification notification = new TrayNotification();
            notification.setTitle("Importación de Calendario");
            notification.setMessage("Archivo con formato incorrecto.");
            notification.setNotificationType(NotificationType.ERROR);
            notification.setRectangleFill(Paint.valueOf("#2F2484"));
            notification.setAnimationType(AnimationType.FADE);
            notification.showAndDismiss(Duration.seconds(2));

        }

        return aux;
    }*/

    public  void exportItineraryInExcelFormat(int calendarToExport){
        FileChooser fc = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fc.getExtensionFilters().add(extFilter);


        //dc = new DirectoryChooser();
        File f = fc.showSaveDialog(new Stage());

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Calendario");


        Controller controller = Controller.getSingletonController();
        Calendar calendar = controller.getCalendarList().get(calendarToExport);
        CalendarConfiguration configuration = calendar.getConfiguration();
        ArrayList<ArrayList<Integer>> teamDate = calendar.teamsItinerary();
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
            String team = getTeams().get(posTeam);
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
                String team = getAcronyms().get(posTeam);
                Cell cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(team);
            }
        }

        Sheet spreadsheetData = workbook.createSheet("Data");
        Row rowData = spreadsheetData.createRow(0);
        Cell cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.getCalendarId());

        rowData = spreadsheetData.createRow(1);

        for (int i = 0; i < configuration.getTeamsIndexes().size(); i++){
            cellData = rowData.createCell(i);
            cellData.setCellStyle(style);
            cellData.setCellValue(configuration.getTeamsIndexes().get(i));
        }

        rowData = spreadsheetData.createRow(2);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.isInauguralGame());

        rowData = spreadsheetData.createRow(3);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.isChampionVsSecondPlace());

        rowData = spreadsheetData.createRow(4);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.getChampion());

        rowData = spreadsheetData.createRow(5);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.getSecondPlace());

        rowData = spreadsheetData.createRow(6);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.isSecondRoundCalendar());

        rowData = spreadsheetData.createRow(7);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.isSymmetricSecondRound());

        rowData = spreadsheetData.createRow(8);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.isOccidenteVsOriente());

        rowData = spreadsheetData.createRow(9);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.getMaxLocalGamesInARow());

        rowData = spreadsheetData.createRow(10);
        cellData =  rowData.createCell(0);
        cellData.setCellStyle(style);
        cellData.setCellValue(configuration.getMaxVisitorGamesInARow());

        workbook.setSheetHidden(1, true);

        //autosize each column of the excel document
        for(int i=0; i < row.getLastCellNum(); i++){
            spreadsheet.autoSizeColumn(i);
        }

        for(int i=0; i < rowData.getLastCellNum(); i++){
            spreadsheetData.autoSizeColumn(i);
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(f.getAbsolutePath());
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
        notification.setMessage("Calendario exportado con �xito");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setRectangleFill(Paint.valueOf("#2F2484"));
        notification.setAnimationType(AnimationType.FADE);
        notification.showAndDismiss(Duration.seconds(2));
    }

    public void createTeams() {

        try {
            FileInputStream fis = new FileInputStream("src/files/Data.xlsx");
            //Creamos el objeto XSSF  para el archivo excel
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);

            acronyms = new ArrayList<>();
            teams = new ArrayList<>();
            positionsDistance = new ArrayList<>();

            //llenar los acrónimos
            Row row = sheet.getRow(0);
            Iterator<Cell> cellAcro = row.cellIterator();
            while (cellAcro.hasNext()){
                acronyms.add(cellAcro.next().getStringCellValue());
            }

            Iterator<Row> rowIterator = sheet.iterator();

            //Nos saltamos la primera fila del encabezado
            rowIterator.next();

            int posLocal =-1;
            while (rowIterator.hasNext()){
                row = rowIterator.next();

                //Recorremos las celdas de la fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                teams.add(cell.getStringCellValue());
                posLocal++;
                int posVisitor = -1;
                while (cellIterator.hasNext()){
                    posVisitor++;
                    cell  = cellIterator.next();
                    //System.out.print(cell.toString() + ";");
                    positionsDistance.add(new LocalVisitorDistance(posLocal,posVisitor,cell.getNumericCellValue()));
                }
            }


            Sheet sheetLocations = workbook.getSheetAt(1);
            locations = new ArrayList<>();
            Row rowLocations = sheetLocations.getRow(0);
            Iterator<Cell> cellLoc = rowLocations.cellIterator();

            while(cellLoc.hasNext()){
                locations.add(cellLoc.next().getStringCellValue());
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public ArrayList<String> getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(ArrayList<String> acronyms) {
        this.acronyms = acronyms;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }

    public ArrayList<LocalVisitorDistance> getPositionsDistance() {
        return positionsDistance;
    }

    public void setPositionsDistance(ArrayList<LocalVisitorDistance> positionsDistance) {
        this.positionsDistance = positionsDistance;
    }
}
