package morozov.ru.model.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ControlPeriod {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private Calendar start = new GregorianCalendar();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private Calendar end = new GregorianCalendar();

    public ControlPeriod() {
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
}
