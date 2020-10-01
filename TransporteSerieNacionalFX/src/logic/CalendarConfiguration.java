package logic;

import java.util.ArrayList;

public class CalendarConfiguration {

    private String calendarId;
    private ArrayList<Integer> teamsIndexes;
    private boolean inauguralGame;
    private boolean championVsSecondPlace;
    private String champion;
    private String secondPlace;
    private boolean secondRoundCalendar;
    private boolean symmetricSecondRound;
    private int maxLocalGamesInARow;
    private int maxVisitorGamesInARow;

    public CalendarConfiguration(String calendarId, ArrayList<Integer> teamsIndexes, boolean inauguralGame,
                                 boolean championVsSecondPlace, String champion, String secondPlace,
                                 boolean secondRoundCalendar, boolean symmetricSecondRound, int maxLocalGamesInARow,
                                 int maxVisitorGamesInARow) {
        this.calendarId = calendarId;
        this.teamsIndexes = teamsIndexes;
        this.inauguralGame = inauguralGame;
        this.championVsSecondPlace = championVsSecondPlace;
        this.champion = champion;
        this.secondPlace = secondPlace;
        this.secondRoundCalendar = secondRoundCalendar;
        this.symmetricSecondRound = symmetricSecondRound;
        this.maxLocalGamesInARow = maxLocalGamesInARow;
        this.maxVisitorGamesInARow = maxVisitorGamesInARow;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public ArrayList<Integer> getTeamsIndexes() {
        return teamsIndexes;
    }

    public void setTeamsIndexes(ArrayList<Integer> teamsIndexes) {
        this.teamsIndexes = teamsIndexes;
    }

    public boolean isInauguralGame() {
        return inauguralGame;
    }

    public void setInauguralGame(boolean inauguralGame) {
        this.inauguralGame = inauguralGame;
    }

    public boolean isChampionVsSecondPlace() {
        return championVsSecondPlace;
    }

    public void setChampionVsSecondPlace(boolean championVsSecondPlace) {
        this.championVsSecondPlace = championVsSecondPlace;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(String secondPlace) {
        this.secondPlace = secondPlace;
    }

    public boolean isSecondRoundCalendar() {
        return secondRoundCalendar;
    }

    public void setSecondRoundCalendar(boolean secondRoundCalendar) {
        this.secondRoundCalendar = secondRoundCalendar;
    }

    public boolean isSymmetricSecondRound() {
        return symmetricSecondRound;
    }

    public void setSymmetricSecondRound(boolean symmetricSecondRound) {
        this.symmetricSecondRound = symmetricSecondRound;
    }

    public int getMaxLocalGamesInARow() {
        return maxLocalGamesInARow;
    }

    public void setMaxLocalGamesInARow(int maxLocalGamesInARow) {
        this.maxLocalGamesInARow = maxLocalGamesInARow;
    }

    public int getMaxVisitorGamesInARow() {
        return maxVisitorGamesInARow;
    }

    public void setMaxVisitorGamesInARow(int maxVisitorGamesInARow) {
        this.maxVisitorGamesInARow = maxVisitorGamesInARow;
    }
}
