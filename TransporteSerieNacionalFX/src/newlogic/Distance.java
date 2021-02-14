package newlogic;

import newlogic.CalendarConfiguration;

import newlogic.LocalVisitorDistance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Distance {

    private Penalization penalization;
    private double[][] matrixDistance;//Matrix that represents the distance between resources.teams


    public float calculateCalendarDistance(ArrayList<ArrayList<Integer>> itinerary)  {
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

    public void fillMatrixDistance() {
        int matrixSize = Controller.getSingletonController().getDataFiles().getTeams().size();
        ArrayList<LocalVisitorDistance> positionsDistance = Controller.getSingletonController().getDataFiles().getPositionsDistance();
        this.matrixDistance = new double[matrixSize][matrixSize];
        for (LocalVisitorDistance aux : positionsDistance) {
            int indexTeam1 = aux.getPosLocal();
            int indexTeam2 = aux.getPosVisitor();
            double distance = aux.getDistance();
            matrixDistance[indexTeam1][indexTeam2] = distance;
            matrixDistance[indexTeam2][indexTeam1] = distance;
        }


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


}
