package lifemonitor.application.model.medicalRecord;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jonathan Geoffroy
 */
public class Intake {
    /**
     * Intake is considered as expired if current time is
     * today at <code>time</code> + NB_MINUTES_BEFORE_EXPIRATION minutes
     */
    public static final int NB_MS_BEFORE_EXPIRATION = 1 /* minutes */ *  60 * 1000;
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

    /**
     *
     * @return true if this Intake is considered as expired.
     */
    public boolean isExpired() {
        Date currentDate = Calendar.getInstance().getTime();
        Date expiredDate = new Date(time.getTime() + NB_MS_BEFORE_EXPIRATION);
        return currentDate.after(expiredDate);
    }
}
