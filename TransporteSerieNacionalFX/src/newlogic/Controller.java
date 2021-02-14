package newlogic;

import logic.CalendarConfiguration;

import java.util.ArrayList;

public class Controller {
    private DataFiles dataFiles;
    private Distance expertDistance;
    private ArrayList<Calendar> calendarList;
    private Statistics statistics;
    private static Controller singletonController;//Singleton Pattern

    public Controller() {
    }

    public static Controller getSingletonController() {
        if (singletonController == null) {
            singletonController = new Controller();
        }
        return singletonController;
    }

    public DataFiles getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(DataFiles dataFiles) {
        this.dataFiles = dataFiles;
    }

    public Distance getExpertDistance() {
        return expertDistance;
    }

    public void setExpertDistance(Distance expertDistance) {
        this.expertDistance = expertDistance;
    }

    public ArrayList<Calendar> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(ArrayList<Calendar> calendarList) {
        this.calendarList = calendarList;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /*public int[][] symmetricCalendar(int[][] matrix, CalendarConfiguration configuration) {

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


            }
            else{

                matrix[posChampion][posSecond] = 2;
                matrix[posSecond][posChampion] = 1;
                cantLocalsAndVisitorsPerRow.get(posChampion).set(1, cantLocalsAndVisitorsPerRow.get(posChampion).get(1)+1);
                cantLocalsAndVisitorsPerRow.get(posSecond).set(0, cantLocalsAndVisitorsPerRow.get(posSecond).get(0)+1);

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
    }*/

}
