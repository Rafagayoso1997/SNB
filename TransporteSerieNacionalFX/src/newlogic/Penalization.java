package newlogic;


import logic.CalendarConfiguration;
import logic.Date;

import java.util.ArrayList;

public class Penalization {
    private static Penalization singletonPenalization;//Singleton Pattern
    private final int PENALIZATION = 100000;

    public Penalization(){
        System.out.println("Se han creado las penalizaciones");
    }

    public static Penalization getSingletonPenalization() {
        if (singletonPenalization == null) {
            singletonPenalization = new Penalization();
        }
        return singletonPenalization;
    }

    public int penalizeLongTrips(ArrayList<ArrayList<Integer>> itinerary, ArrayList<Integer> teamsIndexes, double [][] matrixDistance){
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
    };


    public int penalizeVisitorGames(ArrayList<ArrayList<Integer>> itinerary, int maxVisitorGame,
                                    ArrayList<Integer> teamsIndexes){
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
    };

    public int penalizeLocalGames(ArrayList<ArrayList<Integer>> itinerary, int maxHomeGame,
                                 ArrayList<Integer> teamsIndexes){
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
    };

    public int penalizeInauguralGame(CalendarConfiguration configuration, ArrayList<Date> calendar){

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
}
