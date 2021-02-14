package newlogic;


import java.util.ArrayList;

public class SimpleRoundPairCalendar extends Calendar {

    public SimpleRoundPairCalendar(CalendarConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void changeDatePosition(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

    }

    @Override
    public void changeLocalAndVisitorOnADate(ArrayList<Date> calendar) {

    }

    @Override
    public void changeBetweenLocalAndVisitorOfATeam(ArrayList<Date> calendar, int number) {

    }

    @Override
    public void swapDates(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

    }

    @Override
    public void changeTeams(ArrayList<Date> calendar) {

    }

    @Override
    public void changeTeamsInDate(ArrayList<Date> calendar, int number) {

    }

    @Override
    public void changeDateOrder(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

    }

    @Override
    public void changeDuel(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient) {

    }

    @Override
    public void generateCalendar() {

    }

    @Override
    public ArrayList<ArrayList<Integer>> teamsItinerary() {
        ArrayList<ArrayList<Integer>> teamDate = new ArrayList<>();
        ArrayList<Integer> teamsIndexes = getConfiguration().getTeamsIndexes();
        int startPosition = 0;

        ArrayList<Integer> row = new ArrayList<>(teamsIndexes);

        teamDate.add(row);

        for (int i = startPosition; i < getDates().size(); i++) {
            row = new ArrayList<>();
            for (int k = 0; k < teamsIndexes.size(); k++) {
                row.add(-1);
            }

            Date date = getDates().get(i);

            for (int m = 0; m < date.getGames().size(); m++) {
                int first = date.getGames().get(m).get(0);
                int second = date.getGames().get(m).get(1);
                row.set(teamsIndexes.indexOf(first), first);
                row.set(teamsIndexes.indexOf(second), first);
            }

            teamDate.add(row);
        }

        row = new ArrayList<>(teamsIndexes);
        teamDate.add(row);
        return teamDate;
    }




}
