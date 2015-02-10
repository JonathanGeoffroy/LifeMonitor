package lifemonitor.application.model.medicalRecord;

import java.util.Date;

/**
 * Created by geoffroy on 09/02/15.
 */
public class Intake {
    private int id;
    private Date time;

    public Intake() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isBetween(Date first, Date second) {
        return time.after(first) && time.before(second);
    }
}
