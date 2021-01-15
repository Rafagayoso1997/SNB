package file_management;

import logic.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadFiles {


    public ReadFiles(){

    }

    public static List<String> readMutations() {
        List<String>mutations = new ArrayList<>();
        /*File file = null;
        java.io.FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            file = new File("src/files/Mutaciones.txt");
            fr = new java.io.FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            while ((line = br.readLine()) != null)
                mutations.add(line);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }*/

        try{
            FileInputStream fis = new FileInputStream("src/files/Mutaciones.xlsx");

            //Creamos el objeto XSSF  para el archivo eexcel
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            //Nos quedamos con la primera hoja de calculo

            //XSSFSheet xssfSheet = workbook.getSheetAt(0);




                XSSFSheet xssfSheet = workbook.getSheetAt(0);

                System.out.println(xssfSheet.getSheetName());
                //Recorremos las filas
                Iterator<Row> rowIterator = xssfSheet.iterator();

                //Nos saltamos la primera fila del encabezado

                while (rowIterator.hasNext()){
                    Row row = rowIterator.next();

                    //Recorremos las celdas de la fila
                    Iterator<Cell> cellIterator = row.cellIterator();

                    StringBuilder sb = new StringBuilder();
                    while (cellIterator.hasNext()){
                        Cell cell  = cellIterator.next();
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
}
