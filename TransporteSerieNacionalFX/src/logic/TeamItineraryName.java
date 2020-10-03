package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamItineraryName {
    private SimpleStringProperty team;
    

    /**
     * Class constructor
     *
     * @param team   name of the Team team
     */
    public TeamItineraryName(String team) {
        this.team = new SimpleStringProperty(team);
       
    }

    @Override
    public String toString() {

        return (team.get());
    }

    public void setTeam(String value) { teamProperty().set(value); }
    public String getTeam() { return teamProperty().get(); }
    public StringProperty teamProperty() {
        if (team == null) team = new SimpleStringProperty(this, "Equipo");
        return team;
    }

}
