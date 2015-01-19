package lifemonitor.application.model.medicalRecord;

import android.graphics.Color;

/**
 * @author Jonathan Geoffroy
 */
public class Illness implements  MedicalRecordItem {
    private int id;
    private String name;

    public Illness() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Illness: "+ name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public int getColor() {
        return Color.argb(255, 252, 248, 227);
    }
}
