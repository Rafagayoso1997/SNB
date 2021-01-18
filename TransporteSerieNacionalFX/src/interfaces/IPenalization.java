package interfaces;

import java.util.ArrayList;

public interface IPenalization {
    int checkLongTrips(ArrayList<ArrayList<Integer>> itinerary, ArrayList<Integer> teamsIndexes);
    int penalizeGamesVisitor(ArrayList<ArrayList<Integer>> itinerary, int maxVisitorGame, ArrayList<Integer> teamsIndexes);
    int penalizeGamesHome(ArrayList<ArrayList<Integer>> itinerary, int maxVisitorGame, ArrayList<Integer> teamsIndexes);
}

