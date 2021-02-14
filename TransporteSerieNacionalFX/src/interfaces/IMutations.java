package interfaces;

import newlogic.Date;

import java.util.ArrayList;

public interface IMutations {

    void changeDatePosition(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    void changeLocalAndVisitorOnADate(ArrayList<Date> calendar);

    void changeBetweenLocalAndVisitorOfATeam(ArrayList<Date> calendar, int number);

    void swapDates(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    void changeTeams(ArrayList<Date> calendar);

    void changeTeamsInDate(ArrayList<Date> calendar, int number);

    void changeDateOrder(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    void changeDuel(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

}
