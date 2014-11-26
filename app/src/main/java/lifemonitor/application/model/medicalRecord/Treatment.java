package lifemonitor.application.model.medicalRecord;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by cacciatore on 08/10/14.
 */
@JsonIgnoreProperties({"medicine", "prescription"})
public class Treatment {

    private int id;

    private Date date;

    private String frequency;

    private int units;

    private Medicine medicine;

    private String prescription;

    public Treatment(Date date, String frequency, int units, Medicine medicine) {
        this.date = date;
        this.frequency = frequency;
        this.medicine = medicine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String toString() {
        return "Treatment = " + id;
    }
}
