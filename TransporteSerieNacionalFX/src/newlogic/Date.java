package newlogic;

import java.util.ArrayList;

public class Date {

    private ArrayList<ArrayList<Integer>> games;//Games that belongs to a Date


    /**
     * Class constructor
     */
    public Date(){
        this.games = new ArrayList<>();
    }

    /**
     * Class constructor
     *
     * @param n number of resources.teams
     */
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
