package logic;

import file_management.ReadExcel;
import org.apache.commons.math3.util.MathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Date> calendar = ReadExcel.readExcelItineraryToCalendar("src/files/Itinerario del Calendario Serie Nacional.xlsx");

        for (Date value : calendar) {
            for (int h = 0; h < value.getGames().size(); h++) {
                System.out.print(value.getGames().get(h));
            }
            System.out.println();
        }
        System.out.println("************************************************");
    }


}
