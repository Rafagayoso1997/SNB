package newlogic;

import logic.CalendarConfiguration;
import logic.CalendarStatistic;

import java.util.ArrayList;

public class Statistics {

    private String teams;
    private double distance;

    /*public CalendarStatistic lessStatistics(CalendarConfiguration configuration, ArrayList<ArrayList<Integer>> itinerary) {

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
    }*/
}
