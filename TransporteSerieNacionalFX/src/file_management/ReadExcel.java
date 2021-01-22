package file_management;

import javafx.scene.paint.Paint;
import javafx.util.Duration;
import logic.Auxiliar;
import logic.Controller;
import logic.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadExcel {



    public static Auxiliar readExcelItineraryToCalendar(String route) throws IOException {
        Auxiliar aux = new Auxiliar();
        try {
            Controller controller = Controller.getSingletonController();
            ArrayList<Date> calendar = new ArrayList<>();
            //ArrayList<Integer>teamsIndexes = new ArrayList<>();

            FileInputStream fis = new FileInputStream(route);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            XSSFSheet xssfSheetData = workbook.getSheetAt(1);
            Iterator<Row> rowIteratorData = xssfSheetData.iterator();

            aux.getConfiguration().setCalendarId(rowIteratorData.next().getCell(0).getStringCellValue());

            Row rowTeamIndexes = rowIteratorData.next();

            for (Cell cellData : rowTeamIndexes) {
                aux.getConfiguration().getTeamsIndexes().add((int) cellData.getNumericCellValue());
            }

            aux.getConfiguration().setInauguralGame(rowIteratorData.next().getCell(0).getBooleanCellValue());
            aux.getConfiguration().setChampionVsSecondPlace(rowIteratorData.next().getCell(0).getBooleanCellValue());
            aux.getConfiguration().setChampion((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            aux.getConfiguration().setSecondPlace((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            aux.getConfiguration().setSecondRoundCalendar(rowIteratorData.next().getCell(0).getBooleanCellValue());
            aux.getConfiguration().setSymmetricSecondRound(rowIteratorData.next().getCell(0).getBooleanCellValue());
            aux.getConfiguration().setOccidenteVsOriente(rowIteratorData.next().getCell(0).getBooleanCellValue());
            aux.getConfiguration().setMaxLocalGamesInARow((int) rowIteratorData.next().getCell(0).getNumericCellValue());
            aux.getConfiguration().setMaxVisitorGamesInARow((int) rowIteratorData.next().getCell(0).getNumericCellValue());


            XSSFSheet xssfSheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = xssfSheet.iterator();
            rowIterator.next();

            if (aux.getConfiguration().isInauguralGame()) {
                rowIterator.next();
                Date date = new Date();
                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(aux.getConfiguration().getChampion());
                pair.add(aux.getConfiguration().getSecondPlace());
                date.getGames().add(pair);
                calendar.add(date);
            }


            boolean secondRound = aux.getConfiguration().isSecondRoundCalendar();
            int cantRealRowAdded = 0;
            boolean imparTeams = false;
            int restMoment = aux.getConfiguration().getTeamsIndexes().size() - 1;
            if (aux.getConfiguration().getTeamsIndexes().size() % 2 != 0) {
                imparTeams = true;
                //restMoment += 1;
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (!secondRound || (secondRound && cantRealRowAdded != restMoment)) {

                    Date date = new Date();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    int i = 0;
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        int local = controller.getAcronyms().indexOf(cell.toString());
                        int visitor = aux.getConfiguration().getTeamsIndexes().get(i);

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
    }
}
