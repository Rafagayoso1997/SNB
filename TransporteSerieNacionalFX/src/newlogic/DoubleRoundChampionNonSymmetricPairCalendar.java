package newlogic;

import newlogic.Date;
import newlogic.CalendarConfiguration;

import java.util.ArrayList;

public class DoubleRoundChampionNonSymmetricPairCalendar extends Calendar {

    DoubleRoundChampionNonSymmetricPairCalendar(CalendarConfiguration configuration){
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
    public void generateCalendar(){}

    @Override
    public ArrayList<ArrayList<Integer>> teamsItinerary() {return null;}

}