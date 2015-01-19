package lifemonitor.application.model.medicalRecord;

import android.graphics.Color;

/**
 * @author Jonathan Geoffroy
 */
public class Allergy implements MedicalRecordItem {
    private int id;
    private String name;

    public Allergy() {
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
        return "Allergy: "+ name;
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
        return Color.argb(255, 242, 222, 222);
    }
}
