package newlogic;


import interfaces.IOccidentVsOrient;
import newlogic.CalendarConfiguration;
import newlogic.Date;

import java.util.ArrayList;

public class SimpleRoundChampionOccidentOrientPairCalendar extends Calendar implements IOccidentVsOrient {

    SimpleRoundChampionOccidentOrientPairCalendar(CalendarConfiguration configuration) {
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
        return null;
    }

    @Override
    public ArrayList<Date> generateCalendarOccidentVsOrient(CalendarConfiguration configuration, int[][] matrix) {
        return null;
    }

}