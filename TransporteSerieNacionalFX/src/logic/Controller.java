package logic;

import com.opencsv.CSVReader;
import file_management.ReadFiles;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for the construction of a Calendar
 *
 * @author Rafael Gayoso and Mario Herrera
 */
public class Controller {

    private int maxVisitorGame;//Number of games that a visitor team can play in a row
    private int maxHomeGame;
    private boolean inauguralGame;
    private final int PENALIZATION = 100000;//Penalization if the calendar breaks the restrictions
    private int iterations;//Number of iterations
    private ArrayList<LocalVisitorDistance> positionsDistance;//List of LocalVisitorDistance
    private ArrayList<String> teams;//List of resources.teams



    private ArrayList<String>acronyms;
    private boolean generatedCalendar;


    private boolean isCopied;


    private ArrayList<ArrayList<Integer>> configurationsList;//list of configurations for the mutations

    private ArrayList<Date> calendar;//List of Date that belongs to the calendar
    private double[][] matrixDistance;//Matrix that represents the distance between resources.teams
    private ArrayList<Integer> teamsIndexes;

    private ArrayList<Integer> mutationsIndexes;
    private static Controller singletonController;//Singleton Pattern
    private int posChampion;//Position of the champion team
    private int posSubChampion;//Position of the subchampion team
    private boolean secondRound;

    private float calendarDistance;
    private float lessDistance;
    private float moreDistance;
    private float copyLessDistance;


    private float copyMoreDistance;
    private String teamLessDistance;

    private String teamMoreDistance;

    private String copyTeamLessDistance;

    private String copyTeamMoreDistance;

    private int[][] matrix;


    private ArrayList<Date> calendarCopy;
    private ArrayList<ArrayList<Integer>> itinerary;
    private ArrayList<ArrayList<Integer>> itineraryCopy;

    /**
     * Class Constructor
     */
    private Controller() {
        this.maxVisitorGame = 2;
        this.maxHomeGame = 2;
        this.inauguralGame = false;
        this.teams = new ArrayList<>();
        this.acronyms = new ArrayList<>();
        this.positionsDistance = new ArrayList<>();
        createTeams("src/files/Data.xlsx");
        this.calendar = new ArrayList<>();
        this.calendarCopy = new ArrayList<>();
        this.posChampion = -1;
        this.posSubChampion = -1;
        this.teamsIndexes = new ArrayList<>();
        this.mutationsIndexes = addAllMutations();
        fillMatrixDistance();
        this.secondRound = false;
        this.matrix = new int[teamsIndexes.size()][teamsIndexes.size()];
        this.calendarDistance = 0;
        this.lessDistance = 0;
        this.moreDistance = 0;
        this.teamMoreDistance = "";
        this.teamLessDistance = "";
        this.iterations = 20000;
        this.configurationsList = new ArrayList<>();
        this.generatedCalendar = true;
        this.isCopied = false;
    }

    /**
     * Singleton Pattern
     *
     * @return Controller
     */
    public static Controller getSingletonController() {
        if (singletonController == null) {
            singletonController = new Controller();
        }
        return singletonController;
    }

    public ArrayList<String> getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(ArrayList<String> acronyms) {
        this.acronyms = acronyms;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }


    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }


    public ArrayList<Integer> getTeamsIndexes() {
        return teamsIndexes;
    }

    public void setTeamsIndexes(ArrayList<Integer> teamsIndexes) {
        this.teamsIndexes = teamsIndexes;
    }

    public boolean isGeneratedCalendar() {
        return generatedCalendar;
    }

    public void setGeneratedCalendar(boolean generatedCalendar) {
        this.generatedCalendar = generatedCalendar;
    }

    public ArrayList<Integer> getMutationsIndexes() {
        return mutationsIndexes;
    }

    public void setMutationsIndexes(ArrayList<Integer> mutationsIndexes) {
        this.mutationsIndexes = mutationsIndexes;
    }

    public float getCalendarDistance() {
        return calendarDistance;
    }

    public void setCalendarDistance(float calendarDistance) {
        this.calendarDistance = calendarDistance;
    }

    public ArrayList<ArrayList<Integer>> getConfigurationsList() {
        return configurationsList;
    }

    public void setConfigurationsList(ArrayList<ArrayList<Integer>> configurationsList) {
        this.configurationsList = configurationsList;
    }

    public ArrayList<Date> getCalendarCopy() {
        return calendarCopy;
    }

    public void setCalendarCopy(ArrayList<Date> calendarCopy) {
        this.calendarCopy = calendarCopy;
    }

    public boolean isCopied() {
        return isCopied;
    }

    public void setCopied(boolean copied) {
        isCopied = copied;
    }

    public int getMaxVisitorGame() {
        return maxVisitorGame;
    }

    public void setMaxVisitorGame(int maxVisitorGame) {
        this.maxVisitorGame = maxVisitorGame;
    }

    public int getMaxHomeGame() {
        return maxHomeGame;
    }

    public void setMaxHomeGame(int maxHomeGame) {
        this.maxHomeGame = maxHomeGame;
    }

    public boolean isInauguralGame() {
        return inauguralGame;
    }

    public void setInauguralGame(boolean inauguralGame) {
        this.inauguralGame = inauguralGame;
    }


    /**
     * Return the LocalVisitorDistance list
     *
     * @return ArrayList
     */
    public ArrayList<LocalVisitorDistance> getPositionsDistance() {
        return positionsDistance;
    }

    /**
     * Set the LocalVisitorDistance list
     *
     * @param positionsDistance
     */
    public void setPositionsDistance(ArrayList<LocalVisitorDistance> positionsDistance) {
        this.positionsDistance = positionsDistance;
    }

    /**
     * Return the distance Matrix
     *
     * @return
     */
    public double[][] getMatrixDistance() {
        return matrixDistance;
    }

    /**
     * Set the distance Matrix
     *
     * @param matrixDistance
     */
    public void setMatrixDistance(double[][] matrixDistance) {
        this.matrixDistance = matrixDistance;
    }


    public float getLessDistance() {
        return lessDistance;
    }

    public void setLessDistance(float lessDistance) {
        this.lessDistance = lessDistance;
    }

    public float getMoreDistance() {
        return moreDistance;
    }

    public void setMoreDistance(float moreDistance) {
        this.moreDistance = moreDistance;
    }

    public String getTeamLessDistance() {
        return teamLessDistance;
    }

    public void setTeamLessDistance(String teamLessDistance) {
        this.teamLessDistance = teamLessDistance;
    }

    public String getTeamMoreDistance() {
        return teamMoreDistance;
    }

    public void setTeamMoreDistance(String teamMoreDistance) {
        this.teamMoreDistance = teamMoreDistance;
    }

    /**
     * Return the champion team position
     *
     * @return
     */
    public int getPosChampion() {
        return posChampion;
    }

    /**
     * Set the champion team position
     *
     * @param posChampion
     */
    public void setPosChampion(int posChampion) {
        this.posChampion = posChampion;
    }

    /**
     * Return the subchampion team position
     *
     * @return
     */
    public int getPosSubChampion() {
        return posSubChampion;
    }

    /**
     * Set the subchampion team position
     *
     * @param posSubChampion
     */
    public void setPosSubChampion(int posSubChampion) {
        this.posSubChampion = posSubChampion;
    }

    /**
     * Says that the calendar have a secondRound
     *
     * @return
     */
    public boolean isSecondRound() {
        return secondRound;
    }

    /**
     * Set if the calendar would have a secondRound
     *
     * @param secondRound
     */
    public void setSecondRound(boolean secondRound) {
        this.secondRound = secondRound;
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public ArrayList<Date> getCalendar() {
        return calendar;
    }


    public void setCalendar(ArrayList<Date> calendar) {
        this.calendar = calendar;
    }


    public float getCopyLessDistance() {
        return copyLessDistance;
    }

    public void setCopyLessDistance(float copyLessDistance) {
        this.copyLessDistance = copyLessDistance;
    }

    public float getCopyMoreDistance() {
        return copyMoreDistance;
    }

    public void setCopyMoreDistance(float copyMoreDistance) {
        this.copyMoreDistance = copyMoreDistance;
    }

    public String getCopyTeamLessDistance() {
        return copyTeamLessDistance;
    }

    public void setCopyTeamLessDistance(String copyTeamLessDistance) {
        this.copyTeamLessDistance = copyTeamLessDistance;
    }

    public String getCopyTeamMoreDistance() {
        return copyTeamMoreDistance;
    }

    public void setCopyTeamMoreDistance(String copyTeamMoreDistance) {
        this.copyTeamMoreDistance = copyTeamMoreDistance;
    }

    public ArrayList<ArrayList<Integer>> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<ArrayList<Integer>> itinerary) {
        this.itinerary = itinerary;
    }

    public ArrayList<ArrayList<Integer>> getItineraryCopy() {
        return itineraryCopy;
    }

    public void setItineraryCopy(ArrayList<ArrayList<Integer>> itineraryCopy) {
        this.itineraryCopy = itineraryCopy;
    }

    /**
     * Create the list of resources.teams from the file
     *
     * @param direction
     */
    private void createTeams(String direction) {

        try {
            FileInputStream fis = new FileInputStream(direction);

            //Creamos el objeto XSSF  para el archivo eexcel
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);

            //llenar los acrónimos

            Row row = sheet.getRow(0);
            for(int i=1;i< row.getLastCellNum();i++){
                acronyms.add(row.getCell(i).getStringCellValue());
            }
            matrixDistance  = new double[acronyms.size()][acronyms.size()];

            System.out.println(acronyms);

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
           /* CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(direction), StandardCharsets.ISO_8859_1));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String team1 = nextLine[0];
                String team2 = nextLine[1];
                double distance = Double.parseDouble(nextLine[2]);
                if (!teams.contains(team1)) {
                    teams.add(team1);
                }
                if (!teams.contains(team2)) {
                    teams.add(team2);
                }
                int indexTeam1 = teams.indexOf(team1);
                int indexTeam2 = teams.indexOf(team2);
                positionsDistance.add(new LocalVisitorDistance(indexTeam1, indexTeam2, distance));
            }
            reader.close();*/
        } catch (IOException e) {
            e.getMessage();
        }
       //System.out.println(teams);
    }

    private ArrayList<Integer> addAllMutations(){
        ArrayList<Integer> mutations  = new ArrayList<>();
        List<String> mutationsRead = ReadFiles.readMutations();
        for(int i=0; i < mutationsRead.size();i++){
            mutations.add(i);
        }
        return mutations;
    }

    /**
     * Generate the calendar
     */
    public void generateCalendar() throws IOException {

        calendar = new ArrayList<>();
        for (int f = 0; f < teamsIndexes.size() - 1; f++) {
            int j = 0;
            int lastLocal = 0;
            Date date = new Date(teamsIndexes.size() / 2);
            for (int i = 0; i < matrix.length; i++) {
                for (; j < matrix[i].length; j++) {
                    if (i < j) {
                        if (matrix[i][j] != 0) {
                            boolean isIn = isInDate(teamsIndexes.get(i), teamsIndexes.get(j), date);
                            if (!isIn) {
                                ArrayList<Integer> pair = new ArrayList<>(2);
                                if (matrix[i][j] == 1) {
                                    pair.add(teamsIndexes.get(j));
                                    pair.add(teamsIndexes.get(i));
                                } else {
                                    pair.add(teamsIndexes.get(i));
                                    pair.add(teamsIndexes.get(j));
                                }
                                date.getGames().add(pair);
                                lastLocal = matrix[i][j];
                                matrix[i][j] = 0;

                               /* System.out.println("Fecha actual: " + date.getGames());
                                System.out.println("Lastlocal" + lastLocal);

                                System.out.println("Matriz de 1 y 2:");
                                for (int g = 0; g < matrix.length; g++) {
                                    for (int h = 0; h < matrix.length; h++) {
                                        System.out.print(matrix[g][h]);
                                    }
                                    System.out.println();
                                }*/
                            }
                        }
                    }
                }
                if (i == matrix.length - 1) {
                    if (date.getGames().size() != (teamsIndexes.size() / 2)) {

                        int local = teamsIndexes.indexOf(date.getGames().get(date.getGames().size() - 1).get(0));
                        int visitor = teamsIndexes.indexOf(date.getGames().get(date.getGames().size() - 1).get(1));

                        if (local < visitor) {
                            i = local;
                            j = visitor;
                        } else {
                            i = visitor;
                            j = local;
                        }

                        matrix[i][j] = lastLocal;
                        date.getGames().remove(date.getGames().size() - 1);

                        System.out.println(date.getGames());
                        /*System.out.println("Matriz de 1 y 2:");
                        for (int g = 0; g < matrix.length; g++) {
                            for (int h = 0; h < matrix.length; h++) {
                                System.out.print(matrix[g][h]);
                            }
                            System.out.println();
                        }*/

                        i--;
                        j++;
                    }
                } else {
                    j = 0;
                }
            }
            calendar.add(date);
            System.out.println("************************************************");
            System.out.println("Calendario:");
            for (Date value : calendar) {
                for (int h = 0; h < value.getGames().size(); h++) {
                    System.out.print(value.getGames().get(h));
                }
                System.out.println();
            }
            System.out.println("************************************************");
        }
        if (this.posChampion != -1 && !inauguralGame) {
            fixChampionSubchampion(calendar);
        }

        if(inauguralGame){
            Date inauguralDate = new Date();
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(teamsIndexes.get(posSubChampion));
            pair.add(teamsIndexes.get(posChampion));
            inauguralDate.getGames().add(pair);
            this.calendar.add(0, inauguralDate);
        }

        applyMutations();

        if (secondRound) {
            ArrayList<Date> secondRoundCalendar = new ArrayList<>();
            copyCalendar(secondRoundCalendar, calendar);
            generateSecondRound(secondRoundCalendar);
            calendar.addAll(secondRoundCalendar);
        }
    }

    /**
     * Generate the second round of the calendar
     *
     * @param calendar ArrayList
     */
    private void generateSecondRound(ArrayList<Date> calendar) {
        for (Date date : calendar) {
            for (int j = 0; j < date.getGames().size(); j++) {
                ArrayList<Integer> duel = date.getGames().get(j);
                swapDuel(duel);
            }
        }
    }

    /**
     * Swap the position of two resources.teams from a duel
     *
     * @param duel ArrayList
     */
    private void swapDuel(ArrayList<Integer> duel) {
        int local = duel.get(0);
        int visitor = duel.get(1);
        duel.set(0, visitor);
        duel.set(1, local);
    }

    /**
     * Check if the duel is in a Date
     *
     * @param row  int
     * @param col  int
     * @param date Date
     * @return boolean
     */
    private boolean isInDate(int row, int col, Date date) {
        boolean isIn = false;
        int i = 0;
        while (i < date.getGames().size() && !isIn) {
            int j = 0;
            while (j < date.getGames().get(i).size() && !isIn) {
                if (date.getGames().get(i).get(j) == row || date.getGames().get(i).get(j) == col)
                    isIn = true;
                j++;
            }
            i++;
        }
        return isIn;
    }

    /*
      Generate the matrix that would be used to add the distance

      @param size int
     * @return int [][]
     */
   /* private int[][] generateMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }*/

    /**
     * Make the calendar symmetric. Balance the times that a team is local and visitor
     *
     * @param matrix int [][]
     * @return int[][]
     */
    public int[][] symmetricCalendar(int[][] matrix) {
        int posChampion = Controller.getSingletonController().getTeamsIndexes().indexOf(Controller.getSingletonController().getPosChampion());
        int posSecond = Controller.getSingletonController().getTeamsIndexes().indexOf(Controller.getSingletonController().getPosSubChampion());
        boolean champion = false;
        if (posChampion != -1) {
            champion = true;
            if (posChampion < posSecond) {
                matrix[posChampion][posSecond] = 2;
                matrix[posSecond][posChampion] = 1;
            } else {
                matrix[posChampion][posSecond] = 2;
                matrix[posSecond][posChampion] = 1;
            }
            /*
            if(isInauguralGame()){
                int temp =  matrix[posChampion][posSecond];
                matrix[posChampion][posSecond] =  matrix[posSecond][posChampion];
                matrix[posSecond][posChampion] = temp;
            }*/

           /* System.out.println("Matriz de 1 y 2:");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.print(matrix[i][j]);
                }
                System.out.println();
            }*/

        }
        int cantLocal = (int) Math.floor((matrix.length - 1) / 2) + 1;
        for (int i = 0; i < matrix.length; i++) {
            int cantLocalsRow = 0;
            if (champion && i == posSecond)
                cantLocalsRow++;
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != j) {
                    if (matrix[i][j] == 0) {
                        if (cantLocalsRow < cantLocal) {
                            if (champion) {
                                int cantVisitor = 0;
                                for (int k = 0; k < matrix[j].length; k++) {
                                    if (matrix[j][k] == 2) {
                                        cantVisitor++;
                                    }
                                }
                                if (cantVisitor == cantLocal) {
                                    int cant = 0;
                                    int pos = -1;
                                    for (int k = 0; k < matrix[i].length; k++) {
                                        if (matrix[i][k] == 2) {
                                            cant++;
                                            if (pos == -1) {
                                                pos = k;
                                            }
                                        }
                                    }
                                    if (cant < cantLocal) {
                                        matrix[i][j] = 2;
                                        matrix[j][i] = 1;
                                    } else {
                                        matrix[i][pos] = 1;
                                        matrix[pos][i] = 2;
                                        cantLocalsRow++;
                                    }
                                } else {
                                    matrix[i][j] = 1;
                                    matrix[j][i] = 2;
                                    cantLocalsRow++;
                                }
                            } else {
                                matrix[i][j] = 1;
                                matrix[j][i] = 2;
                                cantLocalsRow++;
                            }
                        } else {
                            matrix[i][j] = 2;
                            matrix[j][i] = 1;
                        }
                    }
                }
            }
        }
        /*for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println(" ");
        }*/
        return matrix;
    }

    /**
     * Fill the matrix with the distance
     */

    private void fillMatrixDistance() {
        this.matrixDistance = new double[teams.size()][teams.size()];
        for (LocalVisitorDistance aux : this.positionsDistance) {
            int indexTeam1 = aux.getPosLocal();
            int indexTeam2 = aux.getPosVisitor();
            double distance = aux.getDistance();
            matrixDistance[indexTeam1][indexTeam2] = distance;
            matrixDistance[indexTeam2][indexTeam1] = distance;
        }


    }

    /**
     * Calculate the total distance of a calendar
     *
     * @param
     * @return float
     */
    public float calculateDistance(ArrayList<ArrayList<Integer>> itinerary) {
        float totalDistance = 0;

        for (int i = 0; i < itinerary.size() - 1; i++) {
            ArrayList<Integer> row1 = itinerary.get(i);
            ArrayList<Integer> row2 = itinerary.get(i + 1);
            for (int j = 0; j < itinerary.get(i).size(); j++) {
                int first = row1.get(j);
                int second = row2.get(j);
                double dist = matrixDistance[second][first];
                totalDistance += ((first == second) ? 0.0 : dist);
            }
        }
        return totalDistance;
    }

    public ArrayList<ArrayList<Integer>> teamsItinerary(ArrayList<Date> calendar) {
        ArrayList<ArrayList<Integer>> teamDate = new ArrayList<>();
        ArrayList<Integer> row = new ArrayList<>();
        int startPosition = 0;

        for (int k = 0; k < teamsIndexes.size(); k++) {
            row.add(teamsIndexes.get(k));
        }

        teamDate.add(row);

        if(inauguralGame){
            startPosition = 1;
            row = new ArrayList<>();
            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(teamsIndexes.get(k));
            }
            int posChampeon = teamsIndexes.indexOf(calendar.get(0).getGames().get(0).get(0));
            int posSub = teamsIndexes.indexOf(calendar.get(0).getGames().get(0).get(1));
            row.set(posSub, posChampeon);
            teamDate.add(row);
        }

        for (int i = startPosition; i < calendar.size(); i++) {

            /*if (secondRound) {
                if ((i - 1) == calendar.size() / 2) {
                    row = new ArrayList<>();
                    for (int j = 0; j < teamsIndexes.size(); j++) {
                        row.add(teamsIndexes.get(j));
                    }
                    teamDate.add(row);
                }
            }*/

            row = new ArrayList<>();
            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(-1);
            }

            Date date = calendar.get(i);

            for (int m = 0; m < date.getGames().size(); m++) {
                int first = date.getGames().get(m).get(0);
                int second = date.getGames().get(m).get(1);
                row.set(teamsIndexes.indexOf(first), first);
                row.set(teamsIndexes.indexOf(second), first);
            }

            teamDate.add(row);
        }

        row = new ArrayList<>();

        for (int j = 0; j < teamsIndexes.size(); j++) {
            row.add(teamsIndexes.get(j));
        }
        teamDate.add(row);
        return teamDate;
    }

    private int checkLongTrips(ArrayList<ArrayList<Integer>> itinerary) {
        int count = 0;
        ArrayList<Double> distances = new ArrayList<>(teamsIndexes.size());
        for (int i = 0; i < teamsIndexes.size(); i++) {
            distances.add(0.0);
        }
        for (int i = 0; i < itinerary.size() - 1; i++) {
            ArrayList<Integer> row1 = itinerary.get(i);
            ArrayList<Integer> row2 = itinerary.get(i + 1);
            for (int j = 0; j < itinerary.get(i).size(); j++) {
                int first = row1.get(j);
                int second = row2.get(j);
                double dist = matrixDistance[second][first];
                distances.set(j, distances.get(j) + dist);
                if (distances.get(j) > 2000) {
                    count++;
                }
                if (teamsIndexes.get(j) == second) {
                    distances.set(j, 0.0);
                }
            }
        }
        return count;
    }


    /**
     * Mutation that change the Position of a Date
     *
     * @param calendar
     */
    private void changeDatePosition(ArrayList<Date> calendar, int number) {
        int selectedDate = -1;
        int dateToChange = -1;

        int startPosition = 0;

        if (!configurationsList.isEmpty()) {
            selectedDate = configurationsList.get(number).get(0);
            dateToChange = configurationsList.get(number).get(1);
        }
        else{
            if(inauguralGame){
                startPosition = 1;
            }
        }

        if (selectedDate == -1) {
                selectedDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
        }

        if (dateToChange == -1) {
            do {
                dateToChange = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());

            } while ((calendar.size() > 3) && ((selectedDate - dateToChange) <= 1) && ((selectedDate - dateToChange) >= (-1)));
        }

        /*System.out.println("Fecha real a cambiar: " + selectedDate);
        System.out.println("Fecha para donde va: " + dateToChange);*/

        Date date = calendar.get(selectedDate);

        if (dateToChange < calendar.size() - 1) {
            if (selectedDate < dateToChange) {
                calendar.add(dateToChange + 1, date);
            } else {
                calendar.add(dateToChange, date);
            }
        } else {
            calendar.add(dateToChange, date);
            calendar.add(calendar.size() - 2, calendar.get(calendar.size() - 1));
            calendar.remove(calendar.size() - 1);
        }


        if (dateToChange >= selectedDate) {
            calendar.remove(selectedDate);
        } else {
            calendar.remove(selectedDate + 1);
        }

    }

    /**
     * Mutation that change the position of the local and the visitor resources.teams on a Date
     *
     * @param calendar
     */
    private void changeLocalAndVisitorOnADate(ArrayList<Date> calendar) {
        int selectedDate = ThreadLocalRandom.current().nextInt(0, calendar.size());
        Date date = calendar.get(selectedDate);
        ArrayList<ArrayList<Integer>> temp = date.getGames();

        for (int i = 0; i < temp.size(); i++) {
            int local = temp.get(i).get(0);
            temp.get(i).set(0, temp.get(i).get(1));
            temp.get(i).set(1, local);
        }

        date.setGames(temp);
        calendar.set(selectedDate, date);

    }

    /**
     * Mutation that swap local and visitor of a team
     *
     * @param calendar
     */
    private void changeBetweenLocalAndVisitorOfATeam(ArrayList<Date> calendar, int number) {
        int selectedTeam = -1;

        if (!configurationsList.isEmpty()) {
            selectedTeam = configurationsList.get(number).get(2);
        }

        if (selectedTeam == -1) {
            selectedTeam = ThreadLocalRandom.current().nextInt(0, teams.size());
        }


        for (int i = 0; i < calendar.size(); i++) {
            for (int j = 0; j < calendar.get(i).getGames().size(); j++) {

                int local = calendar.get(i).getGames().get(j).get(0);
                int visitor = calendar.get(i).getGames().get(j).get(1);

                if (local == selectedTeam || visitor == selectedTeam) {

                    calendar.get(i).getGames().get(j).set(0, visitor);
                    calendar.get(i).getGames().get(j).set(1, local);
                }
            }
        }

    }

    /**
     * Mutation that swap the positions of two dates
     *
     * @param calendar
     */
    private void swapDates(ArrayList<Date> calendar, int number) {
        int firstDate = -1;
        int secondDate = -1;
        int startPosition = 0;

        if (!configurationsList.isEmpty()) {
            firstDate = configurationsList.get(number).get(0);
            secondDate = configurationsList.get(number).get(1);
        }
        else{
            if(inauguralGame){
                startPosition = 1;
            }
        }

        if (firstDate == -1) {
            do {
                firstDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
            } while (firstDate == secondDate);
        }

        if (secondDate == -1) {
            do {
                secondDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
            } while (firstDate == secondDate);
        }

        Date auxFirstDate = calendar.get(firstDate);
        Date auxSecondDate = calendar.get(secondDate);

        calendar.set(firstDate, auxSecondDate);
        calendar.set(secondDate, auxFirstDate);
    }

    /**
     * Mutation that swap the appearance of two resources.teams
     *
     * @param calendar
     */
    private void changeTeams(ArrayList<Date> calendar) {
        int firstTeam = ThreadLocalRandom.current().nextInt(0, teams.size());
        int secondTeam = firstTeam;

        while (firstTeam == secondTeam) {
            secondTeam = ThreadLocalRandom.current().nextInt(0, teams.size());
        }

        for (int i = 0; i < calendar.size(); i++) {
            for (int j = 0; j < calendar.get(i).getGames().size(); j++) {

                int local = calendar.get(i).getGames().get(j).get(0);
                int visitor = calendar.get(i).getGames().get(j).get(1);

                if (local == firstTeam) {
                    calendar.get(i).getGames().get(j).set(0, secondTeam);
                    if (visitor == secondTeam) {
                        calendar.get(i).getGames().get(j).set(1, firstTeam);
                    }
                } else if (local == secondTeam) {
                    calendar.get(i).getGames().get(j).set(0, firstTeam);
                    if (visitor == firstTeam) {
                        calendar.get(i).getGames().get(j).set(1, secondTeam);
                    }
                } else if (visitor == firstTeam) {
                    calendar.get(i).getGames().get(j).set(1, secondTeam);
                } else if (visitor == secondTeam) {
                    calendar.get(i).getGames().get(j).set(1, firstTeam);
                }
            }
        }

    }

    /**
     * Mutation that swap to resources.teams in a Date
     *
     * @param calendar
     */
    private void changeTeamsInDate(ArrayList<Date> calendar, int number) {
        int selectedDateIndex = -1;
        int selectedDuel = -1;


        if (!configurationsList.isEmpty()) {
            selectedDateIndex = configurationsList.get(number).get(0);
            selectedDuel = configurationsList.get(number).get(2);
        }

        if (selectedDateIndex == -1) {
            selectedDateIndex = ThreadLocalRandom.current().nextInt(0, calendar.size());
        }
        Date selectedDate = calendar.get(selectedDateIndex);

        if (selectedDuel == -1) {
            selectedDuel = ThreadLocalRandom.current().nextInt(0, selectedDate.getGames().size());
        }

        int temp = selectedDate.getGames().get(selectedDuel).get(0);
        selectedDate.getGames().get(selectedDuel).set(0, selectedDate.getGames().get(selectedDuel).get(1));
        selectedDate.getGames().get(selectedDuel).set(1, temp);

    }

    /**
     * Mutation that change Date order in the Calendar
     *
     * @param calendar
     */
    private void changeDateOrder(ArrayList<Date> calendar, int number) {
        int firstDate = -1;
        int lastDate = -1;
        int startPosition = 0;

        if (!configurationsList.isEmpty()) {
            firstDate = configurationsList.get(number).get(0);
            lastDate = configurationsList.get(number).get(1);

            if (firstDate > lastDate) {
                int temp = lastDate;
                lastDate = firstDate;
                firstDate = temp;
            }
        }
        else{
            if(inauguralGame){
                startPosition = 1;
            }
        }

        if (firstDate == -1) {
                firstDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size() - 1);
        }

        if (lastDate == -1) {
            while (lastDate <= firstDate) {
                    lastDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
            }
        }

        Deque<Date> stack = new ArrayDeque<>();

        for (int i = firstDate; i <= lastDate; i++) {
            Date date = calendar.get(i);

            stack.push(date);
        }
        for (int i = firstDate; i <= lastDate; i++) {
            Date date = stack.poll();
            calendar.set(i, date);
        }

    }

    /**
     * Mutation that change Duel between two Dates
     *
     * @param calendar
     */
    private void changeDuel(ArrayList<Date> calendar, int number) {

        int posFirstDate = -1;
        int posLastDate = -1;
        int posFirstDuel = -1;
        int startPosition = 0;

        if (!configurationsList.isEmpty()) {
            posFirstDate = configurationsList.get(number).get(0);
            posLastDate = configurationsList.get(number).get(1);
            posFirstDuel = configurationsList.get(number).get(2);
        }
        else{
            if(inauguralGame){
                startPosition = 1;
            }
        }

        if (posFirstDate == -1) {
            do {
                posFirstDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
            }
            while (posLastDate == posFirstDate);
        }

        if (posLastDate == -1) {
            do {
                posLastDate = ThreadLocalRandom.current().nextInt(startPosition, calendar.size());
            }
            while (posLastDate == posFirstDate);
        }

        Date firstDate = calendar.get(posFirstDate);

        Date secondDate = calendar.get(posLastDate);

        if (posFirstDuel == -1) {
            posFirstDuel = ThreadLocalRandom.current().nextInt(0, firstDate.getGames().size());
        }

        swapTeams(posFirstDuel, false, firstDate, secondDate);
    }

    private ArrayList<ArrayList<Integer>> incompatibleDuels(Date date, ArrayList<ArrayList<Integer>> duels, int size) {
        ArrayList<ArrayList<Integer>> incompatibleDuels = new ArrayList<>();

        for (int i = 0; i < duels.size(); i++) {
            ArrayList<Integer> duel = duels.get(i);

            for (int j = 0; j < size; j++) {
                ArrayList<Integer> dateDuels = date.getGames().get(j);

                if (dateDuels.contains(duel.get(0)) || dateDuels.contains(duel.get(1))) {
                    if (!incompatibleDuels.contains(dateDuels)) {
                        incompatibleDuels.add(dateDuels);
                    }
                }
            }
        }
        return incompatibleDuels;
    }

    private void fixChampionSubchampion(ArrayList<Date> calendar) {

        boolean found = false;
        int posDate = -1;
        int posGame = -1;
        int i = 0;

        while (i < calendar.size() && !found) {
            Date date = calendar.get(i);
            int j = 0;
            while (j < date.getGames().size() && !found) {
                if (date.getGames().get(j).contains(this.posChampion) && date.getGames().get(j).contains(this.posSubChampion)) {
                    found = true;
                    posDate = i;
                    posGame = j;
                } else
                    j++;
            }
            i++;
        }


        if (posDate != 0) {
            Date firstDate = calendar.get(posDate);
            Date secondDate = calendar.get(0);

            swapTeams(posGame, false, firstDate, secondDate);
        }

    }

    private void swapTeams(int posGame, boolean compatible, Date firstDate, Date secondDate) {
        ArrayList<Integer> firstDuel = firstDate.getGames().get(posGame);
        int tempSize = secondDate.getGames().size();
        secondDate.getGames().add(firstDuel);
        firstDate.getGames().remove(firstDuel);
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        results.add(firstDuel);
        while (!compatible) {
            results = incompatibleDuels(secondDate, results, tempSize);
            if (results.isEmpty()) {
                compatible = true;
            } else {
                tempSize = firstDate.getGames().size();
                firstDate.getGames().addAll(results);
                secondDate.getGames().removeAll(results);
                results = incompatibleDuels(firstDate, results, tempSize);
                if (results.isEmpty()) {
                    compatible = true;
                } else {
                    tempSize = secondDate.getGames().size();
                    secondDate.getGames().addAll(results);
                    firstDate.getGames().removeAll(results);
                }
            }
        }
    }

    /**
     * Apply the mutations to the calendar
     *
     * @return
     */
    private ArrayList<Date> applyMutations() {
        this.itinerary = teamsItinerary(calendar);
        int penalizeVisitorGames = penalizeGamesVisitor(itinerary);
        int penalizeHomeGames = penalizeGamesHome(itinerary);
        int longTrips = checkLongTrips(itinerary);
        int actualization = 0;
        float distance = calculateDistance(this.itinerary) + PENALIZATION * (penalizeVisitorGames + penalizeHomeGames);
        System.out.println("Se incumple " + longTrips);
        if (longTrips > 0) {
            distance += 100 * longTrips;
        }
        System.out.println(" Original :" + distance);

        int i = 0;
        while (i < iterations) {

            int mutation = mutationsIndexes.get(ThreadLocalRandom.current().nextInt(0, mutationsIndexes.size()));

            this.calendarCopy = new ArrayList<>();
            copyCalendar(this.calendarCopy, this.calendar);

            selectMutation(this.calendarCopy, mutation);

            if (this.posChampion != -1 && !inauguralGame) {
                fixChampionSubchampion(this.calendarCopy);
               /* if (posLocalTeam != this.posChampion && posSecondTeam != this.posSubChampion) {
                    newDistance += PENALIZATION;
                }*/
            }

            this.itineraryCopy = teamsItinerary(this.calendarCopy);
            float newDistance = calculateDistance(itineraryCopy);
            longTrips = checkLongTrips(itineraryCopy);

            if (longTrips > 0) {
                newDistance += 100 * longTrips;
            }


            penalizeVisitorGames = penalizeGamesVisitor(itineraryCopy);
            penalizeHomeGames = penalizeGamesHome(itineraryCopy);

            newDistance += PENALIZATION * (penalizeVisitorGames + penalizeHomeGames);

            if (newDistance <= distance) {
                actualization++;
                this.calendar = this.calendarCopy;
                this.itinerary = this.getItineraryCopy();
                distance = newDistance;
                System.out.println("Se incumple " + longTrips);
            }
            i++;

        }

        System.out.println("Calendario Mutado:");
        for (int z = 0; z < this.calendar.size(); z++) {
            System.out.println(this.calendar.get(z).getGames());
        }

        calendarDistance = distance;
        System.out.println("Distancia Original Calendario Mutado :" + calculateDistance(this.itinerary));
        System.out.println();
        System.out.println("Mutado " + distance);
        System.out.println();
        System.out.println("Cantidad de actualizaciones: " + actualization);

        ArrayList<ArrayList<Double>> itiner = itineraryDistances(this.itinerary);


        for (int l = 0; l < itiner.size(); l++) {
            for (int p = 0; p < itiner.get(0).size(); p++) {
                System.out.print(itiner.get(l).get(p) + " ");
            }
            System.out.println();
        }

        System.out.println("Itinerario Mutado:");
        for (int z = 0; z < this.itinerary.size(); z++) {
                System.out.println(this.itinerary.get(z));
        }


        lessStatistics(this.itinerary);
        moreStatistics(this.itinerary);

       /* System.out.println("----------");
        for (int l=0; l < itiner.get(0).size();l++){
            for (int p=0; p < itiner.size();p++){
                System.out.print(itiner.get(p).get(l)+" ");
            }
            System.out.println();
        }*/
        return this.calendar;
    }

    /**
     * Copy one calendar into another
     *
     * @param newCopy
     * @param copy
     */
    public void copyCalendar(ArrayList<Date> newCopy, ArrayList<Date> copy) {
        for (int i = 0; i < copy.size(); i++) {
            Date date = new Date(null);
            ArrayList<ArrayList<Integer>> games = new ArrayList<>();
            for (int j = 0; j < copy.get(i).getGames().size(); j++) {
                ArrayList<Integer> game = new ArrayList<>();
                game.add(copy.get(i).getGames().get(j).get(0));
                game.add(copy.get(i).getGames().get(j).get(1));
                games.add(game);
            }
            date.setGames(games);
            newCopy.add(date);
        }
    }

    /**
     * Penalize the calendar if breach the restriction
     *
     * @param
     * @return
     */
    private int penalizeGamesVisitor(ArrayList<ArrayList<Integer>> itinerary) {
        int cont = 0;
        ArrayList<Integer> counts = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            counts.add(0);
        }

        for(int i = 0; i  < itinerary.size(); i++) {
            ArrayList<Integer> row = itinerary.get(i);

            for (int j = 0; j < row.size(); j++) {
                int destiny = row.get(j);

                if(destiny != j){
                    counts.set(j, counts.get(j) + 1);
                }
                else{
                    counts.set(j, 0);
                }

                if (counts.get(j) > maxVisitorGame) {
                    cont++;
                    counts.set(j, 0);
                }
            }
        }
        return cont;
    }

    private int penalizeGamesHome(ArrayList<ArrayList<Integer>> itinerary) {
        int cont = 0;
        ArrayList<Integer> counts = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            counts.add(0);
        }

        for(int i = 0; i  < itinerary.size(); i++) {
            ArrayList<Integer> row = itinerary.get(i);

            for (int j = 0; j < row.size(); j++) {
                int pos = row.get(j);

                if(pos == j){
                    counts.set(pos, 0);
                }
                else{
                    counts.set(j, counts.get(j) + 1);
                }

                if (counts.get(j) > maxVisitorGame) {
                    cont++;
                    counts.set(j, 0);
                }
            }
        }
        return cont;
    }

    private ArrayList<ArrayList<Double>> itineraryDistances(ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distancesItinerary = new ArrayList<>();

        for (int i = 0; i < itinerary.size() - 1; i++) {

            ArrayList<Double> distances = new ArrayList<>(teamsIndexes.size());

            for (int m = 0; m < teamsIndexes.size(); m++) {
                distances.add(0.0);
            }

            ArrayList<Integer> row1 = itinerary.get(i);
            ArrayList<Integer> row2 = itinerary.get(i + 1);
            for (int j = 0; j < itinerary.get(i).size(); j++) {
                int first = row1.get(j);
                int second = row2.get(j);
                double dist = matrixDistance[second][first];
                distances.set(j, distances.get(j) + dist);
                if (teamsIndexes.get(j) == second) {
                    distances.set(j, 0.0);
                }
            }
            distancesItinerary.add(distances);
        }
        return distancesItinerary;
    }

    public void lessStatistics(ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distances = itineraryDistances(itinerary);
        double max = Double.MAX_VALUE;
        double sum = 0;
        int pos = -1;
        for (int l = 0; l < distances.get(0).size(); l++) {

            for (int p = 0; p < distances.size(); p++) {
                sum += distances.get(p).get(l);
            }

            if (sum <= max) {
                max = sum;
                pos = l;
            }
            sum = 0;
        }

        lessDistance = (float) max;
        teamLessDistance = teams.get(pos);

    }

    public void copyLessStatistics(ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distances = itineraryDistances(itinerary);
        double max = Double.MAX_VALUE;
        double sum = 0;
        int pos = -1;
        for (int l = 0; l < distances.get(0).size(); l++) {

            for (int p = 0; p < distances.size(); p++) {
                sum += distances.get(p).get(l);
            }

            if (sum <= max) {
                max = sum;
                pos = l;
            }
            sum = 0;
        }

        copyLessDistance = (float) max;
        copyTeamLessDistance = teams.get(pos);

    }

    public void moreStatistics(ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distances = itineraryDistances(itinerary);

        double max = Double.MIN_VALUE;
        double sum = 0;
        int pos = -1;
        for (int l = 0; l < distances.get(0).size(); l++) {

            for (int p = 0; p < distances.size(); p++) {
                sum += distances.get(p).get(l);
            }

            if (sum >= max) {
                max = sum;
                pos = l;
            }
            sum = 0;
        }

        moreDistance = (float) max;
        teamMoreDistance = teams.get(pos);

    }

    public void copyMoreStatistics(ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distances = itineraryDistances(itinerary);

        double max = Double.MIN_VALUE;
        double sum = 0;
        int pos = -1;
        for (int l = 0; l < distances.get(0).size(); l++) {

            for (int p = 0; p < distances.size(); p++) {
                sum += distances.get(p).get(l);
            }

            if (sum >= max) {
                max = sum;
                pos = l;
            }
            sum = 0;
        }

        copyMoreDistance = (float) max;
        copyTeamMoreDistance = teams.get(pos);


    }


    public void selectMutation(ArrayList<Date> calendar, int number) {

        switch (mutationsIndexes.get(mutationsIndexes.indexOf(number))) {

            case 0:
                changeDatePosition(calendar, number);
                break;

            case 1:
                changeDateOrder(calendar, number);//changeTeams(newCalendar);
                break;

            case 2:
                swapDates(calendar, number);//changeLocalAndVisitorOnADate(newCalendar);
                break;
            case 3:
                changeDuel(calendar, number);
                break;
                /*case 4:
                    changeTeamsInDate(calendar, number);
                    break;

                case 5:
                    changeBetweenLocalAndVisitorOfATeam(calendar, number);
                    break;

                case 6:
                    changeDateOrder(calendar, number);
                    break;

                case 7:
                    swapDates(calendar);
                    break;*/

            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }



}
