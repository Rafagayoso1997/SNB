package interfaces;

import newlogic.CalendarConfiguration;
import newlogic.Date;

import java.util.ArrayList;

public interface IOccidentVsOrient {
    public ArrayList<Date> generateCalendarOccidentVsOrient(CalendarConfiguration configuration, int[][] matrix);
}
