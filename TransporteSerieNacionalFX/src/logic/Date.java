package logic;

import java.util.ArrayList;

/**
 * Class responsible for representations of a Date that conforms the calendar
 *
 * @author Rafael Gayoso and Mario Herrera
 */
public class Date {

    private ArrayList<ArrayList<Integer>> games;//Games that belongs to a Date

    /**
     * Class constructor
     *
     * @param n number of teams
     */

    public Date(){
        this.games = new ArrayList<>();
    }
    public Date(int n) {
        this.games = new ArrayList<>(n / 2);
    }

    /**
     * Class constructor
     *
     * @param games
     */
    public Date(ArrayList<ArrayList<Integer>> games) {
        this.games = games;
    }

    /**
     * Return the games of a Date
     *
     * @return games
     */
    public ArrayList<ArrayList<Integer>> getGames() {
        return games;
    }

    /**
     * Set the games of a Date
     *
     * @param games
     */
    public void setGames(ArrayList<ArrayList<Integer>> games) {
        this.games = games;
    }
}
