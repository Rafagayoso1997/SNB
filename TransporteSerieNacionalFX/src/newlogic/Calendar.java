package newlogic;

import logic.Date;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Calendar {

    /**
     * Mutation that change the Position of a Date
     *
     * @param calendar
     */
    public abstract void changeDatePosition(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    /**
     * Mutation that change the position of the local and the visitor resources.teams on a Date
     *
     * @param calendar
     */
    public abstract void changeLocalAndVisitorOnADate(ArrayList<Date> calendar);

    /**
     * Mutation that swap local and visitor of a team
     *
     * @param calendar
     */
    public abstract void changeBetweenLocalAndVisitorOfATeam(ArrayList<Date> calendar, int number);

    /**
     * Mutation that swap the positions of two dates
     *
     * @param calendar
     */
    public abstract void swapDates(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    /**
     * Mutation that swap the appearance of two resources.teams
     *
     * @param calendar
     */
    public abstract void changeTeams(ArrayList<Date> calendar);

    /**
     * Mutation that swap to resources.teams in a Date
     *
     * @param calendar
     */
    public abstract void changeTeamsInDate(ArrayList<Date> calendar, int number);

    /**
     * Mutation that change Date order in the Calendar
     *
     * @param calendar
     */
    public abstract void changeDateOrder(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);

    /**
     * Mutation that change Duel between two Dates
     *
     * @param calendar
     */
    public abstract void changeDuel(ArrayList<Date> calendar, int number, boolean inauguralGame, boolean occidentVsOrient);
}
