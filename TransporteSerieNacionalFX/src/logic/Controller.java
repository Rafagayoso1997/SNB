package logic;

import file_management.ReadFiles;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.INTERNAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for the construction of a Calendar
 *
 * @author Rafael Gayoso and Mario Herrera
 */
public class Controller {


    private final int PENALIZATION = 100000;//Penalization if the calendar breaks the restrictions
    private int iterations;//Number of iterations
    private ArrayList<LocalVisitorDistance> positionsDistance;//List of LocalVisitorDistance
    private ArrayList<String> teams;//List of resources.teams

    private ArrayList<CalendarConfiguration>configurations;
    private ArrayList<ArrayList<Date>> calendarsList;
    private ArrayList<String> acronyms;
    private ArrayList<String> locations;


    private ArrayList<ArrayList<Integer>> mutationsConfigurationsList;//list of configurations for the mutations

    private double[][] matrixDistance;//Matrix that represents the distance between resources.teams
    private ArrayList<Integer> teamsIndexes;
    private ArrayList<Integer> mutationsIndexes;
    private static Controller singletonController;//Singleton Pattern

    private float calendarDistance;
    private int[][] matrix;
    private CalendarConfiguration lastSavedConfiguration;
    private boolean configurationAdded;





    /**
     * Class Constructor
     */
    private Controller() {
        this.teams = new ArrayList<>();
        this.acronyms = new ArrayList<>();
        this.positionsDistance = new ArrayList<>();
        createTeams("src/files/Data.xlsx");
        this.teamsIndexes = new ArrayList<>();
        this.mutationsIndexes = addAllMutations();
        fillMatrixDistance();
        this.matrix = new int[teamsIndexes.size()][teamsIndexes.size()];
        this.iterations = 20000;
        this.calendarDistance = 0;
        this.mutationsConfigurationsList = new ArrayList<>();
        this.configurations = new ArrayList<>();
        this.calendarsList = new ArrayList<>();
        this.configurationAdded = false;

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

    public ArrayList<String> getLocations() { return locations; }

    public void setLocations(ArrayList<String> locations) { this.locations = locations; }

    public boolean isConfigurationAdded() {
        return configurationAdded;
    }

    public void setConfigurationAdded(boolean configurationAdded) {
        this.configurationAdded = configurationAdded;
    }

    public CalendarConfiguration getLastSavedConfiguration() {
        return lastSavedConfiguration;
    }

    public void setLastSavedConfiguration(CalendarConfiguration lastSavedConfiguration) {
        this.lastSavedConfiguration = lastSavedConfiguration;
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



    public ArrayList<Integer> getMutationsIndexes() {
        return mutationsIndexes;
    }

    public void setMutationsIndexes(ArrayList<Integer> mutationsIndexes) {
        this.mutationsIndexes = mutationsIndexes;
    }


    public ArrayList<ArrayList<Integer>> getmutationsConfigurationsList() {
        return mutationsConfigurationsList;
    }

    public void setMutationsConfigurationsList(ArrayList<ArrayList<Integer>> mutationsConfigurationsList) {
        this.mutationsConfigurationsList = mutationsConfigurationsList;
    }

    public float getCalendarDistance() {
        return calendarDistance;
    }

    public void setCalendarDistance(float calendarDistance) {
        this.calendarDistance = calendarDistance;
    }



    public ArrayList<CalendarConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(ArrayList<CalendarConfiguration> configurations) {
        this.configurations = configurations;
    }

    public ArrayList<ArrayList<Date>> getCalendarsList() {
        return calendarsList;
    }

    public void setCalendarsList(ArrayList<ArrayList<Date>> calendarsList) {
        this.calendarsList = calendarsList;
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

    public ArrayList<String> getTeams() {
        return teams;
    }

    /**
     * Create the list of resources.teams from the file
     *
     * @param direction
     */
    public void createTeams(String direction) {

        try {
            FileInputStream fis = new FileInputStream(direction);

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
    public ArrayList<Date> generateCalendar(CalendarConfiguration configuration, int[][] matrix, int numberOfDates, Date dateToStart) throws IOException {

        boolean impar = false;
        int newMatrix[][] = matrix.clone();
        ArrayList<Integer> teamsIndexes = configuration.getTeamsIndexes();
        ArrayList<Date> calendar = new ArrayList<>();
        int numberToAdd = -1;

        if(teamsIndexes.size() %2 != 0){
            impar = true;
        }

        if(impar) {
            newMatrix = new int[matrix.length + 1][matrix.length + 1];
            for (int i = 0; i < matrix.length + 1; i++) {
                for (int k = 0; k < matrix.length + 1; k++) {
                    if (k == matrix.length || i == matrix.length) {
                        newMatrix[i][k] = 1;
                    } else {
                        newMatrix[i][k] = matrix[i][k];
                    }
                }
            }

            boolean added = false;
            numberToAdd = 0;
            while (!added) {
                if (teamsIndexes.contains(numberToAdd)) {
                    numberToAdd++;
                } else {
                    teamsIndexes.add(numberToAdd);
                    added = true;
                }
            }
        }

        for (int f = 0; f < numberOfDates; f++) {
            int j = 0;
            int lastLocal = 0;

            Date date;
            if(!impar){
                date = new Date(teamsIndexes.size() / 2);
            }
            else {
                date = new Date(((int)Math.floor(teamsIndexes.size() / 2)) + 1);
            }

            for (int i = 0; i < newMatrix.length; i++) {
                for (; j < newMatrix[i].length; j++) {
                    if (i < j) {
                        if (newMatrix[i][j] != 0) {
                            boolean isIn = isInDate(teamsIndexes.get(i), teamsIndexes.get(j), date);
                            if (!isIn) {
                                ArrayList<Integer> pair = new ArrayList<>(2);
                                if (newMatrix[i][j] == 1) {
                                    pair.add(teamsIndexes.get(j));
                                    pair.add(teamsIndexes.get(i));
                                } else {
                                    pair.add(teamsIndexes.get(i));
                                    pair.add(teamsIndexes.get(j));
                                }
                                date.getGames().add(pair);
                                lastLocal = newMatrix[i][j];
                                newMatrix[i][j] = 0;
                            }
                        }
                    }
                }
                if (i == newMatrix.length - 1) {
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

                        newMatrix[i][j] = lastLocal;
                        date.getGames().remove(date.getGames().size() - 1);

                        i--;
                        j++;
                    }
                } else {
                    j = 0;
                }
            }
            calendar.add(date);
        }

        if(impar){
            teamsIndexes.remove(teamsIndexes.size()-1);
            for (int i = 0; i < calendar.size(); i++) {
                for (int k = 0; k < calendar.get(i).getGames().size(); k++) {

                    int pos = calendar.get(i).getGames().get(k).indexOf(numberToAdd);
                    if (pos != -1){
                        if (pos == 0){
                            calendar.get(i).getGames().get(k).set(pos, calendar.get(i).getGames().get(k).get(1));
                        }
                        else{
                            calendar.get(i).getGames().get(k).set(pos, calendar.get(i).getGames().get(k).get(0));
                        }
                    }
                }
            }
        }

        if (configuration.getChampion() != -1 && !configuration.isInauguralGame()) {
            fixChampionSubchampion(calendar, configuration.getChampion(),configuration.getSecondPlace());
        }

        if(configuration.isInauguralGame() && !configuration.isOccidenteVsOriente()){
            Date inauguralDate = new Date();
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(configuration.getChampion());
            pair.add(configuration.getSecondPlace());
            inauguralDate.getGames().add(pair);
            calendar.add(0, inauguralDate);
        }

        if (configuration.isSecondRoundCalendar() && !configuration.isSymmetricSecondRound()) {
            ArrayList<Date> secondRoundCalendar = new ArrayList<>();
            copyCalendar(secondRoundCalendar, calendar);
            generateSecondRound(secondRoundCalendar, configuration);
            calendar.addAll(secondRoundCalendar);
        }

        calendar = applyMutations(calendar,configuration, dateToStart);

        if (configuration.isSecondRoundCalendar() && configuration.isSymmetricSecondRound()) {
            ArrayList<Date> secondRoundCalendar = new ArrayList<>();
            copyCalendar(secondRoundCalendar, calendar);
            generateSecondRound(secondRoundCalendar,configuration);
            calendar.addAll(secondRoundCalendar);
        }
       return calendar;
    }

    public ArrayList<Date> generateCalendarOccidentVsOrient(CalendarConfiguration configuration, int[][] matrix) throws IOException {

        ArrayList<Integer> teamsOnlyOccident = new ArrayList<>();
        ArrayList<Integer> teamsOnlyOrient = new ArrayList<>();
        int newMatrix[][] = matrix.clone();

        for (Integer index: configuration.getTeamsIndexes()) {
            if (locations.get(index).equalsIgnoreCase("Occidental")){
                teamsOnlyOccident.add(index);
            }
            else{
                teamsOnlyOrient.add(index);
            }
        }

        int[][] matrixOnlyOccident = new int[teamsOnlyOccident.size()][teamsOnlyOccident.size()];
        int[][] matrixOnlyOrient = new int[teamsOnlyOrient.size()][teamsOnlyOrient.size()];
        ArrayList<ArrayList<Integer>> matrixOccidentVsOrient = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++){
            int posIOccident = teamsOnlyOccident.indexOf(configuration.getTeamsIndexes().get(i));
            int posIOrient = teamsOnlyOrient.indexOf(configuration.getTeamsIndexes().get(i));

            for (int j = 0; j < matrix[i].length; j++){
                if (i < j){
                    int posJOccident = teamsOnlyOccident.indexOf(configuration.getTeamsIndexes().get(j));
                    int posJOrient = teamsOnlyOrient.indexOf(configuration.getTeamsIndexes().get(j));
                    if(posIOccident != -1 && posJOccident != -1){
                        matrixOnlyOccident[posIOccident][posJOccident] = matrix[i][j];
                        matrixOnlyOccident[posJOccident][posIOccident] = matrix[j][i];
                        newMatrix[i][j] = 0;
                        newMatrix[j][i] = 0;
                    }
                    else if(posIOrient != -1 && posJOrient != -1){
                        matrixOnlyOrient[posIOrient][posJOrient] = matrix[i][j];
                        matrixOnlyOrient[posJOrient][posIOrient] = matrix[j][i];
                        newMatrix[i][j] = 0;
                        newMatrix[j][i] = 0;
                    }
                    else{
                        ArrayList<Integer> pair = new ArrayList<>(2);
                        if (matrix[i][j] == 1) {
                            pair.add(configuration.getTeamsIndexes().get(j));
                            pair.add(configuration.getTeamsIndexes().get(i));
                        } else {
                            pair.add(configuration.getTeamsIndexes().get(i));
                            pair.add(configuration.getTeamsIndexes().get(j));
                        }
                        matrixOccidentVsOrient.add(pair);
                    }
                }
            }
        }


        CalendarConfiguration configurationOnlyOccident = new CalendarConfiguration(configuration.getCalendarId(),
                teamsOnlyOccident, configuration.isInauguralGame(),configuration.isChampionVsSecondPlace(),
                configuration.getChampion(), configuration.getSecondPlace(), configuration.isSecondRoundCalendar(),
                configuration.isSymmetricSecondRound(), configuration.isOccidenteVsOriente(), configuration.getMaxLocalGamesInARow(),
                configuration.getMaxVisitorGamesInARow());

        CalendarConfiguration configurationOnlyOrient = new CalendarConfiguration(configuration.getCalendarId(),
                teamsOnlyOrient, configuration.isInauguralGame(),configuration.isChampionVsSecondPlace(),
                configuration.getChampion(), configuration.getSecondPlace(), configuration.isSecondRoundCalendar(),
                configuration.isSymmetricSecondRound(), configuration.isOccidenteVsOriente(), configuration.getMaxLocalGamesInARow(),
                configuration.getMaxVisitorGamesInARow());

        CalendarConfiguration configurationOccidentVsOrient = new CalendarConfiguration(configuration.getCalendarId(),
                configuration.getTeamsIndexes(), configuration.isInauguralGame(),configuration.isChampionVsSecondPlace(),
                configuration.getChampion(), configuration.getSecondPlace(), configuration.isSecondRoundCalendar(),
                configuration.isSymmetricSecondRound(), configuration.isOccidenteVsOriente(), configuration.getMaxLocalGamesInARow(),
                configuration.getMaxVisitorGamesInARow());


        if (configuration.getChampion() != -1){
            int posChamp = teamsOnlyOccident.indexOf(configuration.getChampion());
            int posSub = teamsOnlyOccident.indexOf(configuration.getSecondPlace());

            if(posChamp != -1 && posSub != -1){
                configurationOnlyOrient.setChampionVsSecondPlace(false);
                configurationOnlyOrient.setInauguralGame(false);
                configurationOccidentVsOrient.setChampionVsSecondPlace(false);
            }
            else if(posChamp == -1 && posSub == -1){
                configurationOnlyOccident.setChampionVsSecondPlace(false);
                configurationOnlyOccident.setInauguralGame(false);
                configurationOccidentVsOrient.setChampionVsSecondPlace(false);
            }
            else if(posChamp != -1 && posSub == -1 && configuration.isInauguralGame()){
                configurationOnlyOccident.setChampionVsSecondPlace(false);
                configurationOnlyOccident.setInauguralGame(false);
            }
            else if(posChamp == -1 && posSub != -1 && configuration.isInauguralGame()){
                configurationOnlyOrient.setChampionVsSecondPlace(false);
                configurationOnlyOrient.setInauguralGame(false);
            }
            else{
                configurationOnlyOrient.setChampionVsSecondPlace(false);
                configurationOnlyOccident.setChampionVsSecondPlace(false);
            }
            if (configuration.isInauguralGame()){
                configurationOccidentVsOrient.setInauguralGame(false);
            }
        }

        ArrayList<Date> onlyOccident = generateCalendar(configurationOnlyOccident, matrixOnlyOccident, teamsOnlyOccident.size() -1, null);
        ArrayList<Date> onlyOrient = generateCalendar(configurationOnlyOrient, matrixOnlyOrient, teamsOnlyOrient.size() - 1, null);
        ArrayList<Date> allTogether = new ArrayList<>();

        for (int i = 0; i < onlyOccident.size(); i++) {
            Date dateToAdd = new Date();
            dateToAdd.getGames().addAll(onlyOccident.get(i).getGames());
            dateToAdd.getGames().addAll(onlyOrient.get(i).getGames());
            allTogether.add(dateToAdd);
        }

        int numberOfDate = 0;
        if (!configuration.isSecondRoundCalendar()){
            numberOfDate = (configuration.getTeamsIndexes().size()-1) - onlyOccident.size();
        }
        else{
            numberOfDate = (configuration.getTeamsIndexes().size()-1) - (onlyOccident.size()/2);
        }

        Date dateToStart = allTogether.get(allTogether.size()-1);
        ArrayList<Date> OccidentVsOrient = generateCalendar(configurationOccidentVsOrient, newMatrix, numberOfDate, dateToStart);

        for (Date temp: OccidentVsOrient) {
            allTogether.add(temp);
        }

        if (configuration.isInauguralGame()){
            Date inauguralDate = new Date();
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(configuration.getChampion());
            pair.add(configuration.getSecondPlace());
            inauguralDate.getGames().add(pair);
            allTogether.add(0, inauguralDate);
        }


        System.out.println("Calendario final: ");
        for (Date temp: allTogether) {
            System.out.println(temp.getGames());
        }


        /*ArrayList<Date> occidentVsOrient = new ArrayList<>();
        //int cantDates = (configuration.getTeamsIndexes().size() - 1) - onlyOccident.size();
        int cantDuelsPerDate = onlyOccident.get(0).getGames().size() * 2;

        Date temp = new Date();

        for (int i = 0; i < matrixOccidentVsOrient.size(); i++){
            if(temp.getGames().size() < cantDuelsPerDate){
                temp.getGames().add(matrixOccidentVsOrient.get(i));
                if (i == (matrixOccidentVsOrient.size() - 1)){
                    occidentVsOrient.add(temp);
                }
            }
            else{
                occidentVsOrient.add(temp);
                temp = new Date();
                temp.getGames().add(matrixOccidentVsOrient.get(i));
            }
        }


        ArrayList<Date> allTogether = new ArrayList<>();
        ArrayList<ArrayList<Integer>> duelsAdded = new ArrayList<>();
        boolean finishedCalendar = false;
        boolean finishedDate = false;
        int i = 0;
        int j = 0;
        Date tempDate = new Date();

        while (!finishedCalendar && i < occidentVsOrient.size()){
            if(finishedDate){
                tempDate = new Date();
                finishedDate = false;
            }

            while (!finishedDate && j < occidentVsOrient.get(i).getGames().size()){

                if(!duelsAdded.contains(occidentVsOrient.get(i).getGames().get(j))){

                    boolean isIn = isInDate(occidentVsOrient.get(i).getGames().get(j).get(0), occidentVsOrient.get(i).getGames().get(j).get(1), tempDate);
                    if (!isIn){
                        tempDate.getGames().add(occidentVsOrient.get(i).getGames().get(j));
                        duelsAdded.add(occidentVsOrient.get(i).getGames().get(j));
                        if (tempDate.getGames().size() == occidentVsOrient.get(i).getGames().size()){
                            finishedDate = true;
                            allTogether.add(tempDate);
                            i = -1;
                            j = 0;
                        }
                    }
                }
                j++;
            }

            if (allTogether.size() == occidentVsOrient.size()){
                finishedCalendar = true;
            }
            i++;
            j = 0;
        }
*/


        return allTogether;
    }

    /**
     * Generate the second round of the calendar
     *
     * @param calendar ArrayList
     */
    private void generateSecondRound(ArrayList<Date> calendar, CalendarConfiguration configuration) {


        if(configuration.isInauguralGame() && !configuration.isOccidenteVsOriente()){
            calendar.remove(0);
        }

        for (int i = 0; i < calendar.size(); i++) {
            for (int j = 0; j < calendar.get(i).getGames().size(); j++) {
                ArrayList<Integer> duel = calendar.get(i).getGames().get(j);
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
    public int[][] symmetricCalendar(int[][] matrix, CalendarConfiguration configuration) {

        ArrayList<ArrayList<Integer>> cantLocalsAndVisitorsPerRow = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++){
            ArrayList<Integer> row = new ArrayList<>();
            row.add(0);
            row.add(0);
            cantLocalsAndVisitorsPerRow.add(row);
        }

        int posChampion = configuration.getTeamsIndexes().indexOf(configuration.getChampion());
        int posSecond = configuration.getTeamsIndexes().indexOf(configuration.getSecondPlace());
        boolean champion = false;
        if (posChampion != -1) {
            champion = true;
            if(lastSavedConfiguration.isInauguralGame()) {

               // if(posChampion < posSecond){
                    matrix[posChampion][posSecond] = 1;
                    matrix[posSecond][posChampion] = 2;
                    cantLocalsAndVisitorsPerRow.get(posChampion).set(0, cantLocalsAndVisitorsPerRow.get(posChampion).get(1)+1);
                    cantLocalsAndVisitorsPerRow.get(posSecond).set(1, cantLocalsAndVisitorsPerRow.get(posSecond).get(1)+1);
                /*}
                else{
                    matrix[posChampion][posSecond] = 2;
                    matrix[posSecond][posChampion] = 1;
                    cantLocalsAndVisitorsPerRow.get(posChampion).set(1, cantLocalsAndVisitorsPerRow.get(posChampion).get(1)+1);
                    cantLocalsAndVisitorsPerRow.get(posSecond).set(0, cantLocalsAndVisitorsPerRow.get(posSecond).get(0)+1);
                }*/

            }
            else{
                //if(posChampion < posSecond){
                    matrix[posChampion][posSecond] = 2;
                    matrix[posSecond][posChampion] = 1;
                    cantLocalsAndVisitorsPerRow.get(posChampion).set(1, cantLocalsAndVisitorsPerRow.get(posChampion).get(1)+1);
                    cantLocalsAndVisitorsPerRow.get(posSecond).set(0, cantLocalsAndVisitorsPerRow.get(posSecond).get(0)+1);
                /*}
                else{
                    matrix[posChampion][posSecond] = 1;
                    matrix[posSecond][posChampion] = 2;
                    cantLocalsAndVisitorsPerRow.get(posChampion).set(0, cantLocalsAndVisitorsPerRow.get(posChampion).get(1)+1);
                    cantLocalsAndVisitorsPerRow.get(posSecond).set(1, cantLocalsAndVisitorsPerRow.get(posSecond).get(1)+1);
                }*/
            }
        }

        int cantMaxLocalOrVisitor = matrix.length / 2;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != j) {
                    if (matrix[i][j] == 0) {
                        if (cantLocalsAndVisitorsPerRow.get(i).get(0) < cantMaxLocalOrVisitor) {
                            if (champion) {
                                if (cantLocalsAndVisitorsPerRow.get(j).get(1) < cantMaxLocalOrVisitor) {
                                    matrix[i][j] = 1;
                                    matrix[j][i] = 2;
                                    cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)+1);
                                    cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)+1);
                                }
                                else {
                                    if (cantLocalsAndVisitorsPerRow.get(i).get(1) < cantMaxLocalOrVisitor && cantLocalsAndVisitorsPerRow.get(j).get(0) < cantMaxLocalOrVisitor){
                                        matrix[i][j] = 2;
                                        matrix[j][i] = 1;
                                        cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)+1);
                                        cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)+1);
                                    }
                                    else{
                                        boolean goBackPossibility = false;
                                        int lastRowModified = -1;
                                        while (!goBackPossibility && i >= 0){
                                            while (!goBackPossibility & j > 0){
                                                j--;
                                                if (matrix[i][j] == 1){
                                                    if (cantLocalsAndVisitorsPerRow.get(i).get(1) < cantMaxLocalOrVisitor && cantLocalsAndVisitorsPerRow.get(j).get(0) < cantMaxLocalOrVisitor){
                                                        matrix[i][j] = 2;
                                                        matrix[j][i] = 1;

                                                        cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)+1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)+1);

                                                        cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)-1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)-1);

                                                        goBackPossibility = true;
                                                        i = lastRowModified;
                                                        j = -1;
                                                    }
                                                }
                                                else if(matrix[i][j] == 2){
                                                    if (cantLocalsAndVisitorsPerRow.get(i).get(0) < cantMaxLocalOrVisitor && cantLocalsAndVisitorsPerRow.get(j).get(1) < cantMaxLocalOrVisitor){
                                                        matrix[i][j] = 1;
                                                        matrix[j][i] = 2;

                                                        cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)+1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)+1);

                                                        cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)-1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)-1);

                                                        goBackPossibility = true;
                                                        i = lastRowModified;
                                                        j = -1;
                                                    }
                                                }
                                                if(!goBackPossibility && matrix[i][j] != 0){
                                                    if (matrix[i][j] == 1){
                                                        cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)-1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)-1);
                                                    }
                                                    else {
                                                        cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)-1);
                                                        cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)-1);
                                                    }
                                                    matrix[i][j] = 0;
                                                    matrix[j][i] = 0;
                                                    if (i < j){
                                                        if(i < lastRowModified){
                                                            lastRowModified = i;
                                                        }
                                                    }
                                                    else{
                                                        if(j < lastRowModified){
                                                            lastRowModified = j;
                                                        }
                                                    }
                                                }
                                            }
                                            if (!goBackPossibility){
                                                i--;
                                                j = matrix.length;
                                            }
                                        }
                                        if(i < 0){
                                            i = 0;
                                        }
                                    }
                                }
                            }
                            else {
                                matrix[i][j] = 1;
                                matrix[j][i] = 2;
                                cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)+1);
                                cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)+1);
                            }
                        }
                        else {
                            if(cantLocalsAndVisitorsPerRow.get(j).get(0) < cantMaxLocalOrVisitor){
                                matrix[i][j] = 2;
                                matrix[j][i] = 1;
                                cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)+1);
                                cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)+1);
                            }
                            else{
                                boolean goBackPossibility = false;
                                int lastRowModified = -1;
                                while (!goBackPossibility && i >= 0){
                                    while (!goBackPossibility & j > 0){
                                        j--;
                                        if (matrix[i][j] == 1){
                                            if (cantLocalsAndVisitorsPerRow.get(i).get(1) < cantMaxLocalOrVisitor && cantLocalsAndVisitorsPerRow.get(j).get(0) < cantMaxLocalOrVisitor){
                                                matrix[i][j] = 2;
                                                matrix[j][i] = 1;

                                                cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)+1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)+1);

                                                cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)-1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)-1);

                                                goBackPossibility = true;
                                                i = lastRowModified;
                                                j = -1;
                                            }
                                        }
                                        else if(matrix[i][j] == 2){
                                            if (cantLocalsAndVisitorsPerRow.get(i).get(0) < cantMaxLocalOrVisitor && cantLocalsAndVisitorsPerRow.get(j).get(1) < cantMaxLocalOrVisitor){
                                                matrix[i][j] = 1;
                                                matrix[j][i] = 2;

                                                cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)+1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)+1);

                                                cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)-1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)-1);

                                                goBackPossibility = true;
                                                i = lastRowModified;
                                                j = -1;
                                            }
                                        }
                                        if(!goBackPossibility && matrix[i][j] != 0){
                                            if (matrix[i][j] == 1){
                                                cantLocalsAndVisitorsPerRow.get(i).set(0, cantLocalsAndVisitorsPerRow.get(i).get(0)-1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(1, cantLocalsAndVisitorsPerRow.get(j).get(1)-1);
                                            }
                                            else {
                                                cantLocalsAndVisitorsPerRow.get(i).set(1, cantLocalsAndVisitorsPerRow.get(i).get(1)-1);
                                                cantLocalsAndVisitorsPerRow.get(j).set(0, cantLocalsAndVisitorsPerRow.get(j).get(0)-1);
                                            }
                                            matrix[i][j] = 0;
                                            matrix[j][i] = 0;
                                            if (i < j){
                                                if(i < lastRowModified){
                                                    lastRowModified = i;
                                                }
                                            }
                                            else{
                                                if(j < lastRowModified){
                                                    lastRowModified = j;
                                                }
                                            }
                                        }
                                    }
                                    if (!goBackPossibility){
                                        i--;
                                        j = matrix.length;
                                    }
                                }
                                if(i < 0){
                                    i = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * Fill the matrix with the distance
     */

    public void fillMatrixDistance() {
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
    public float calculateDistance(ArrayList<ArrayList<Integer>> itinerary)  {
        float totalDistance = 0;

        for (int i = 0; i < itinerary.size() - 1; i++) {
            ArrayList<Integer> row1 = itinerary.get(i);
            ArrayList<Integer> row2 = itinerary.get(i + 1);
            for (int j = 0; j < itinerary.get(i).size(); j++) {
                int first = row1.get(j);
                int second = row2.get(j);
                double dist = matrixDistance[second][first];
                totalDistance += dist;
            }
        }
        return totalDistance;
    }

    public ArrayList<ArrayList<Integer>> teamsItinerary(ArrayList<Date> calendar, CalendarConfiguration configuration, Date dateToStart) {
        ArrayList<ArrayList<Integer>> teamDate = new ArrayList<>();
        ArrayList<Integer> teamsIndexes = configuration.getTeamsIndexes();
        ArrayList<Integer> row = new ArrayList<>();
        int startPosition = 0;

        if(dateToStart == null){
            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(teamsIndexes.get(k));
            }
        }
        else{

            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(-1);
            }

            for (int k = 0; k < dateToStart.getGames().size(); k++) {

                int local = dateToStart.getGames().get(k).get(0);
                int visitor = dateToStart.getGames().get(k).get(1);

                int posLocal = teamsIndexes.indexOf(local);
                int posVisitor = teamsIndexes.indexOf(visitor);

                row.set(posLocal, local);
                row.set(posVisitor, local);

            }
        }
        teamDate.add(row);

        if(configuration.isInauguralGame() && dateToStart == null){
            if (calendar.get(0).getGames().size() == 1){
                startPosition = 1;
            }

            row = new ArrayList<>();
            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(teamsIndexes.get(k));
            }
            int posChampeon = teamsIndexes.indexOf(configuration.getChampion());
            int posSub = teamsIndexes.indexOf(configuration.getSecondPlace());

            if(posChampeon != -1 && posSub != -1){
                row.set(posSub, configuration.getChampion());
            }
            else if(posChampeon == -1 && posSub != -1){
                row.set(posSub, configuration.getChampion());
            }
            teamDate.add(row);
        }

        int cantRealDatesAdded = 0;


        for (int i = startPosition; i < calendar.size(); i++) {

            if (configuration.isSecondRoundCalendar()) {

                //if ((i - 1 + startPosition) == (calendar.size() / 2) + 1) {
                if(cantRealDatesAdded == teamsIndexes.size() - 1){
                    row = new ArrayList<>();
                    for (int j = 0; j < teamsIndexes.size(); j++) {
                        row.add(teamsIndexes.get(j));
                    }
                    teamDate.add(row);
                }
            }

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
            cantRealDatesAdded++;
        }

        row = new ArrayList<>();

        for (int j = 0; j < teamsIndexes.size(); j++) {
            row.add(teamsIndexes.get(j));
        }
        teamDate.add(row);
        return teamDate;
    }

    public int checkLongTrips(ArrayList<ArrayList<Integer>> itinerary, ArrayList<Integer> teamsIndexes) {

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
    private void changeDatePosition(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {
        int selectedDate = -1;
        int dateToChange = -1;

        int startPosition = 0;

        if (!mutationsConfigurationsList.isEmpty()) {
            selectedDate = mutationsConfigurationsList.get(number).get(0);
            dateToChange = mutationsConfigurationsList.get(number).get(1);
            if(inauguralGame){
                startPosition = 1;
            }
        }
        else{
            if(inauguralGame && !occidentVsOrient){
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

        System.out.println("Fecha real a cambiar: " + selectedDate);
        System.out.println("Fecha para donde va: " + dateToChange);
        System.out.println("*********************************************************");

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

        if (!mutationsConfigurationsList.isEmpty()) {
            selectedTeam = mutationsConfigurationsList.get(number).get(2);
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
    private void swapDates(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {
        int firstDate = -1;
        int secondDate = -1;
        int startPosition = 0;

        if (!mutationsConfigurationsList.isEmpty()) {
            firstDate = mutationsConfigurationsList.get(number).get(0);
            secondDate = mutationsConfigurationsList.get(number).get(1);
            if (inauguralGame){
                startPosition = 1;
            }
        }
        else{
            if(inauguralGame && !occidentVsOrient){
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

        System.out.println("Primera Fecha: " + firstDate);
        System.out.println("Ultima Fecha: " + secondDate);
        System.out.println("Posicion para inicar: " + startPosition);
        System.out.println("*********************************************************");

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


        if (!mutationsConfigurationsList.isEmpty()) {
            selectedDateIndex = mutationsConfigurationsList.get(number).get(0);
            selectedDuel = mutationsConfigurationsList.get(number).get(2);
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
    private void changeDateOrder(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {
        int firstDate = -1;
        int lastDate = -1;
        int startPosition = 0;

        if (!mutationsConfigurationsList.isEmpty()) {
            firstDate = mutationsConfigurationsList.get(number).get(0);
            lastDate = mutationsConfigurationsList.get(number).get(1);

            if (firstDate > lastDate) {
                int temp = lastDate;
                lastDate = firstDate;
                firstDate = temp;
            }
            if (inauguralGame){
                startPosition = 1;
            }
        }
        else{
            if(inauguralGame && !occidentVsOrient){
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

        System.out.println("Primera Fecha: " + firstDate);
        System.out.println("Ultima Fecha: " + lastDate);
        System.out.println("Posicion para inicar: " + startPosition);
        System.out.println("*********************************************************");

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
    private void changeDuel(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

        int posFirstDate = -1;
        int posLastDate = -1;
        int posFirstDuel = -1;
        int startPosition = 0;

        if (!mutationsConfigurationsList.isEmpty()) {
            posFirstDate = mutationsConfigurationsList.get(number).get(0);
            posLastDate = mutationsConfigurationsList.get(number).get(1);
            posFirstDuel = mutationsConfigurationsList.get(number).get(2);
            if (inauguralGame){
                startPosition = 1;
            }
        }
        else{
            if(inauguralGame && !occidentVsOrient){
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
            do{
                posFirstDuel = ThreadLocalRandom.current().nextInt(0, firstDate.getGames().size());
            }
            while (secondDate.getGames().contains(firstDate.getGames().get(posFirstDuel)));
        }
        System.out.println("Primera Fecha: " + posFirstDate);
        System.out.println("Ultima Fecha: " + posLastDate);
        System.out.println("Duelo a cambiar" + posFirstDuel);
        System.out.println("Posicion para inicar: " + startPosition);
        System.out.println("*********************************************************");

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

    private void fixChampionSubchampion(ArrayList<Date> calendar, int posChampion, int posSubChampion) {

        boolean found = false;
        int posDate = -1;
        int posGame = -1;
        int i = 0;

        while (i < calendar.size() && !found) {
            Date date = calendar.get(i);
            int j = 0;
            while (j < date.getGames().size() && !found) {
                if (date.getGames().get(j).contains(posChampion) && date.getGames().get(j).contains(posSubChampion)) {
                    found = true;
                    posDate = i;
                    posGame = j;
                } else
                    j++;
            }
            i++;
        }

        if ((posDate != -1) && (posDate != 0) ) {
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

                for (ArrayList<Integer> duel: results) {
                    secondDate.getGames().remove(secondDate.getGames().indexOf(duel));
                }

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
    private ArrayList<Date> applyMutations(ArrayList<Date> calendar, CalendarConfiguration configuration, Date dateToStart) {
        ArrayList<ArrayList<Integer>>itinerary = teamsItinerary(calendar, configuration, dateToStart);
        int penalizeVisitorGames = penalizeGamesVisitor(itinerary, configuration.getMaxVisitorGamesInARow(), configuration.getTeamsIndexes());
        int penalizeHomeGames = penalizeGamesHome(itinerary, configuration.getMaxLocalGamesInARow(), configuration.getTeamsIndexes());
        int penalizeWrongInaugural = penalizeWrongInaugural(configuration, calendar);
        int longTrips = checkLongTrips(itinerary, configuration.getTeamsIndexes());
        int actualization = 0;
        float distance = calculateDistance(itinerary) + (PENALIZATION * (penalizeVisitorGames + penalizeHomeGames + penalizeWrongInaugural));

        //System.out.println("Se incumple " + longTrips);
        if (longTrips > 0) {
            distance += 100 * longTrips;
        }
        System.out.println(" Original :" + distance);

        int i = 0;
        while (i < iterations) {

            int mutation = mutationsIndexes.get(ThreadLocalRandom.current().nextInt(0, mutationsIndexes.size()));

            System.out.println("Mutacion elegida: " + mutation);

            ArrayList<Date> calendarCopy = new ArrayList<>();
            copyCalendar(calendarCopy, calendar);

            selectMutation(calendarCopy, mutation,configuration.isInauguralGame(), configuration.isOccidenteVsOriente());

            if (configuration.getChampion() != -1 && !configuration.isInauguralGame()) {
                fixChampionSubchampion(calendarCopy,configuration.getChampion(),configuration.getSecondPlace());
            }

            ArrayList<ArrayList<Integer>> itineraryCopy = teamsItinerary(calendarCopy, configuration, dateToStart);
            try {
                float newDistance = calculateDistance(itineraryCopy);

                 longTrips = checkLongTrips(itineraryCopy, configuration.getTeamsIndexes());

                if (longTrips > 0) {
                    newDistance += 100 * longTrips;
                }

                penalizeVisitorGames = penalizeGamesVisitor(itineraryCopy, configuration.getMaxVisitorGamesInARow(), configuration.getTeamsIndexes());
                System.out.println(penalizeVisitorGames + "Penalizar");
                penalizeHomeGames = penalizeGamesHome(itineraryCopy, configuration.getMaxLocalGamesInARow(), configuration.getTeamsIndexes());
                penalizeWrongInaugural = penalizeWrongInaugural(configuration, calendarCopy);

                newDistance += (PENALIZATION * (penalizeVisitorGames + penalizeHomeGames + penalizeWrongInaugural));

                if (newDistance <= distance) {
                    actualization++;
                    calendar = calendarCopy;
                    itinerary = itineraryCopy;
                    distance = newDistance;
                    //System.out.println("Se incumple " + longTrips);
                }
            }
            catch (Exception e){
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");

                System.out.println("Iteracion: " + i);

                System.out.println("Calendario ERROR: ");
                for (int z = 0; z < calendarCopy.size(); z++) {
                    System.out.println(calendarCopy.get(z).getGames());
                }

                System.out.println("Itinerario ERROR:");
                for (int z = 0; z < itineraryCopy.size(); z++) {
                    for (int y = 0; y < itineraryCopy.get(z).size(); y++)
                    System.out.println(itineraryCopy.get(z));
                }
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");
                System.out.println("**********************************************************************************************");

            }
            i++;

        }

        System.out.println("Calendario Mutado:");
        for (int z = 0; z < calendar.size(); z++) {
            System.out.println(calendar.get(z).getGames());
        }

        calendarDistance = distance;
        ArrayList<ArrayList<Integer>> finalItinerary = teamsItinerary(calendar, configuration, null);
        System.out.println("Distancia Original Calendario Mutado :" + calculateDistance(finalItinerary));
        System.out.println();
        System.out.println("Mutado " + distance);
        System.out.println();
        System.out.println("Cantidad de actualizaciones: " + actualization);

        ArrayList<ArrayList<Double>> itiner = itineraryDistances(configuration, finalItinerary);


        for (int l = 0; l < itiner.size(); l++) {
            for (int p = 0; p < itiner.get(0).size(); p++) {
                System.out.print(itiner.get(l).get(p) + " ");
            }
            System.out.println();
        }

        System.out.println("Itinerario Mutado:");
        for (int z = 0; z < itinerary.size(); z++) {
            System.out.println(itinerary.get(z));
        }
        return  calendar;
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
    public int penalizeGamesVisitor(ArrayList<ArrayList<Integer>> itinerary, int maxVisitorGame, ArrayList<Integer> teamsIndexes) {
        int cont = 0;
        ArrayList<Integer> counts = new ArrayList<>();

        for (int i = 0; i < teamsIndexes.size(); i++) {
            counts.add(0);
        }

        for(int i = 1; i  < itinerary.size() - 1; i++) {
            ArrayList<Integer> row = itinerary.get(i);

            for (int j = 0; j < row.size(); j++) {
                int destiny = row.get(j);
                if(destiny != teamsIndexes.get(j)){
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

    public int penalizeGamesHome(ArrayList<ArrayList<Integer>> itinerary, int maxHomeGame, ArrayList<Integer> teamsIndexes) {
        int cont = 0;
        ArrayList<Integer> counts = new ArrayList<>();

        for (int i = 0; i < teamsIndexes.size(); i++) {
            counts.add(0);
        }

        for(int i = 1; i  < itinerary.size() - 1; i++) {
            ArrayList<Integer> row = itinerary.get(i);

            for (int j = 0; j < row.size(); j++) {
                int destiny = row.get(j);

                if(destiny == teamsIndexes.get(j)){
                    counts.set(j, counts.get(j) + 1);
                }
                else{
                    counts.set(j, 0);
                }

                if (counts.get(j) > maxHomeGame) {
                    cont++;
                    counts.set(j, 0);
                }
            }
        }
        return cont;
    }

    private int penalizeWrongInaugural(CalendarConfiguration configuration, ArrayList<Date> calendar){

        boolean inauguralGame = configuration.isInauguralGame();
        int champion = configuration.getChampion();
        int subChampion = configuration.getSecondPlace();
        int wrong = 0;
        int i = 0;

        if(inauguralGame){

            if (calendar.get(0).getGames().size() == 1){
                while (wrong == 0 && i < calendar.get(1).getGames().size()) {
                    if(calendar.get(1).getGames().get(i).get(0) == champion && calendar.get(1).getGames().get(i).get(1) == subChampion){
                        wrong = 1;
                    }
                    else if (calendar.get(1).getGames().get(i).get(0) == subChampion && calendar.get(1).getGames().get(i).get(1) == champion){
                        wrong = 1;
                    }
                    i++;
                }
            }
            else{
                while (wrong == 0 && i < calendar.get(0).getGames().size()) {
                    if(calendar.get(1).getGames().get(i).get(0) == champion && calendar.get(1).getGames().get(i).get(1) == subChampion){
                        wrong = 1;
                    }
                    else if (calendar.get(1).getGames().get(i).get(0) == subChampion && calendar.get(1).getGames().get(i).get(1) == champion){
                        wrong = 1;
                    }
                    i++;
                }
            }
        }
        return wrong;
    }

    private ArrayList<ArrayList<Double>> itineraryDistances(CalendarConfiguration configuration, ArrayList<ArrayList<Integer>> itinerary) {
        ArrayList<ArrayList<Double>> distancesItinerary = new ArrayList<>();

        for (int i = 0; i < itinerary.size() - 1; i++) {

            ArrayList<Double> distances = new ArrayList<>(configuration.getTeamsIndexes().size());

            for (int m = 0; m < configuration.getTeamsIndexes().size(); m++) {
                distances.add(0.0);
            }

            ArrayList<Integer> row1 = itinerary.get(i);
            ArrayList<Integer> row2 = itinerary.get(i + 1);
            for (int j = 0; j < itinerary.get(i).size(); j++) {
                int first = row1.get(j);
                int second = row2.get(j);
                double dist = matrixDistance[second][first];
                distances.set(j, distances.get(j) + dist);
                if (configuration.getTeamsIndexes().get(j) == second) {
                    distances.set(j, 0.0);
                }
            }
            distancesItinerary.add(distances);
        }
        return distancesItinerary;
    }

    public CalendarStatistic lessStatistics(CalendarConfiguration configuration, ArrayList<ArrayList<Integer>> itinerary) {

        ArrayList<ArrayList<Double>> distances = itineraryDistances(configuration, itinerary);
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

        return new CalendarStatistic(teams.get(pos),(float) max);

    }



    public CalendarStatistic moreStatistics(CalendarConfiguration configuration, ArrayList<ArrayList<Integer>> itinerary) {

        ArrayList<ArrayList<Double>> distances = itineraryDistances(configuration, itinerary);

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

        return new CalendarStatistic(teams.get(pos),(float) max);
    }

    public void selectMutation(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

        switch (mutationsIndexes.get(mutationsIndexes.indexOf(number))) {

            case 0:
                changeDatePosition(calendar, number, inauguralGame, occidentVsOrient);
                break;

            case 1:
                changeDateOrder(calendar, number,inauguralGame, occidentVsOrient);//changeTeams(newCalendar);
                break;

            case 2:
                swapDates(calendar, number, inauguralGame, occidentVsOrient);//changeLocalAndVisitorOnADate(newCalendar);
                break;
            case 3:
                changeDuel(calendar, number,inauguralGame, occidentVsOrient);
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

    public boolean existCalendarName(String name){
    boolean exist =false;

    int i =0;

    while(i < configurations.size() && !exist){
        if(configurations.get(i).getCalendarId().equalsIgnoreCase(name)){
            exist = true;
            break;
        }
        else{
            i++;
        }
    }

    return exist;
    }



}
