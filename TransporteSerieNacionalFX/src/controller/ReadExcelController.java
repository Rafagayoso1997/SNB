package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import logic.Controller;
import logic.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ReadExcelController implements Initializable {

    @FXML
    private JFXButton readExcel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @FXML
    void read(ActionEvent event) throws IOException {
        Controller controller = Controller.getSingletonController();
        ArrayList<Date> calendar = new ArrayList<>();
        File excel =new File("Calendario.xlsx");
        FileInputStream fis = new FileInputStream(excel);

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
                    //System.out.print(cell.toString() + ";");
                    pair.add(controller.getTeams().indexOf(cell.toString()));
                }
                date.getGames().add(pair);
                //System.out.println();
            }
            calendar.add(date);

            //System.out.println();
        }




        workbook.close();
        fis.close();

        System.out.println("************************************************");
        System.out.println("Calendario:");
        for (int g = 0; g < calendar.size(); g++) {
            for (int h = 0; h < calendar.get(g).getGames().size(); h++) {
                System.out.print(calendar.get(g).getGames().get(h));
            }
            System.out.println();
        }
        System.out.println("************************************************");

    }
}
