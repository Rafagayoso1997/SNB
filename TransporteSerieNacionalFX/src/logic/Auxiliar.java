package logic;
import java.util.ArrayList;


public class Auxiliar {

    private ArrayList<Date> calendar;
    private CalendarConfiguration configuration;

    public Auxiliar() {
        this.calendar = new ArrayList<>();
        this.configuration = new CalendarConfiguration();
    }

    public ArrayList<Date> getCalendar() {
        return calendar;
    }

    public void setCalendar(ArrayList<Date> calendar) {
        this.calendar = calendar;
    }

    public CalendarConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CalendarConfiguration configuration) {
        this.configuration = configuration;
    }
}
