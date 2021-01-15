package logic;

public class CalendarStatistic {

    private String team;
    private float distance;


    public CalendarStatistic(String team, float distance) {
        this.team = team;
        this.distance = distance;

    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }


}
