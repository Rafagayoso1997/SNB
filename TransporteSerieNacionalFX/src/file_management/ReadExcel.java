package file_management;

import logic.Auxiliar;
import logic.Controller;
import logic.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadExcel {

    //read the calendar format in excel file
    public static ArrayList<Date> readExcel(String route) throws IOException {
        Controller controller = Controller.getSingletonController();
        ArrayList<Date> calendar = new ArrayList<>();

        FileInputStream fis = new FileInputStream(route);

        //Creamos el objeto XSSF  para el archivo eexcel
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        //Nos quedamos con la primera hoja de calculo

        //XSSFSheet xssfSheet = workbook.getSheetAt(0);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

            Date date = new Date();
            XSSFSheet xssfSheet = workbook.getSheetAt(i);

            System.out.println(xssfSheet.getSheetName());
            //Recorremos las filas
            Iterator<Row> rowIterator = xssfSheet.iterator();

            //Nos saltamos la primera fila del encabezado
            rowIterator.next();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();

                //Recorremos las celdas de la fila
                Iterator<Cell> cellIterator = row.cellIterator();

                ArrayList<Integer> pair = new ArrayList<>();
                while (cellIterator.hasNext()){
                    Cell cell  = cellIterator.next();
                    pair.add(controller.getTeams().indexOf(cell.toString()));
                }
                date.getGames().add(pair);
            }
            calendar.add(date);
        }
        workbook.close();
        fis.close();

        return calendar;
    }

    public static Auxiliar readExcelItineraryToCalendar(String route) throws IOException {

        Auxiliar aux = new Auxiliar();

        Controller controller = Controller.getSingletonController();
        ArrayList<Date> calendar = new ArrayList<>();
        //ArrayList<Integer>teamsIndexes = new ArrayList<>();

        FileInputStream fis = new FileInputStream(route);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        XSSFSheet xssfSheetData = workbook.getSheetAt(1);
        Iterator<Row> rowIteratorData = xssfSheetData.iterator();

        aux.getConfiguration().setCalendarId(rowIteratorData.next().getCell(0).getStringCellValue());

        Row rowTeamIndexes = rowIteratorData.next();
        Iterator<Cell> cellIteratorData = rowTeamIndexes.iterator();

        while(cellIteratorData.hasNext()){
            Cell cellData  = cellIteratorData.next();
            aux.getConfiguration().getTeamsIndexes().add((int)cellData.getNumericCellValue());
        }

        aux.getConfiguration().setInauguralGame(rowIteratorData.next().getCell(0).getBooleanCellValue());
        aux.getConfiguration().setChampionVsSecondPlace(rowIteratorData.next().getCell(0).getBooleanCellValue());
        aux.getConfiguration().setChampion((int)rowIteratorData.next().getCell(0).getNumericCellValue());
        aux.getConfiguration().setSecondPlace((int)rowIteratorData.next().getCell(0).getNumericCellValue());
        aux.getConfiguration().setSecondRoundCalendar(rowIteratorData.next().getCell(0).getBooleanCellValue());
        aux.getConfiguration().setSymmetricSecondRound(rowIteratorData.next().getCell(0).getBooleanCellValue());
        aux.getConfiguration().setMaxLocalGamesInARow((int)rowIteratorData.next().getCell(0).getNumericCellValue());
        aux.getConfiguration().setMaxVisitorGamesInARow((int)rowIteratorData.next().getCell(0).getNumericCellValue());


        XSSFSheet xssfSheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = xssfSheet.iterator();
        rowIterator.next();
/*
        Iterator<Cell> cellIteratorColumns = columnNames.cellIterator();

        while(cellIteratorColumns.hasNext()){
            Cell cellNames = cellIteratorColumns.next();
            teamsIndexes.add(controller.getTeams().indexOf(cellNames.toString()));
        }

       */
        if(aux.getConfiguration().isInauguralGame()){
            rowIterator.next();
            Date date = new Date();
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(aux.getConfiguration().getChampion());
            pair.add(aux.getConfiguration().getSecondPlace());
            date.getGames().add(pair);
            calendar.add(date);
        }


        boolean secondRound = aux.getConfiguration().isSecondRoundCalendar();
        int countRow = 1;
        boolean imparTeams = false;
        int restMoment = aux.getConfiguration().getTeamsIndexes().size();
        if (aux.getConfiguration().getTeamsIndexes().size() %2 != 0){
            imparTeams = true;
            restMoment += 1;
        }

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            if (!secondRound || (secondRound && countRow != restMoment)){

                Date date = new Date();
                Iterator<Cell> cellIterator = row.cellIterator();

                int i = 0;
                while (cellIterator.hasNext()){

                    Cell cell  = cellIterator.next();
                    int local = controller.getAcronyms().indexOf(cell.toString());
                    int visitor = aux.getConfiguration().getTeamsIndexes().get(i);

                    ArrayList<Integer> pair = new ArrayList<>();
                    pair.add(local);
                    pair.add(visitor);

                    if(imparTeams){
                        boolean added = false;
                        int j = 0;
                        while (j < date.getGames().size() && !added){

                            if(date.getGames().get(j).contains(local) || date.getGames().get(j).contains(visitor)){
                                if (local != visitor){
                                    date.getGames().remove(j);
                                    date.getGames().add(pair);
                                }
                                added = true;
                            }
                            j++;
                        }
                        if (added == false){
                            date.getGames().add(pair);
                        }
                    }
                    else {
                        if (local != visitor){
                            date.getGames().add(pair);
                        }
                    }
                    i++;
                }
                calendar.add(date);
            }
            countRow++;
        }

        aux.setCalendar(calendar);


        workbook.close();
        fis.close();
        return aux;
    }
}
