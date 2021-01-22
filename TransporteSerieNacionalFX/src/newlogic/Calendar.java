package newlogic;

import interfaces.IMutations;
import newlogic.CalendarConfiguration;

import java.util.ArrayList;

public abstract class Calendar implements IMutations{

    private ArrayList<Date> dates;
    private CalendarConfiguration configuration;

    Calendar (CalendarConfiguration configuration){
        this.configuration = configuration;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public CalendarConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CalendarConfiguration configuration) {
        this.configuration = configuration;
    }

    public abstract void generateCalendar();

    public abstract ArrayList<ArrayList<Integer>> teamsItinerary( );

    protected boolean isInDate(int row, int col, Date date) {
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

    protected ArrayList<ArrayList<Integer>> incompatibleDuels(logic.Date date, ArrayList<ArrayList<Integer>> duels, int size) {
        ArrayList<ArrayList<Integer>> incompatibleDuels = new ArrayList<>();

        for (ArrayList<Integer> duel : duels) {
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

    protected void swapTeams(int posGame, boolean compatible, logic.Date firstDate, logic.Date secondDate) {
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


}
