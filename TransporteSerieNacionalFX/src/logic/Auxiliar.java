package logic;

public class Auxiliar {
    private int team;
    private int from;
    private int to;
    private float travelDistance;
    private float totalDistance;

    public Auxiliar(int team, int from, int to, float travelDistance) {
        this.team = team;
        this.from = from;
        this.to = to;
        this.travelDistance = travelDistance;
        this.totalDistance = 0;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public float getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(float travelDistance) {
        this.travelDistance = travelDistance;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
}
