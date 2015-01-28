package lifemonitor.application.model.medicalRecord;

import android.support.v4.app.DialogFragment;
import android.content.Context;
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
    public String getTitle(Context context) {
        return name;
    }

    @Override
    public String getSubTitle(Context context) {
        return null;
    }

    @Override
    public int getColor() {
        return Color.argb(255, 242, 222, 222);
    }

    @Override
    public DialogFragment acceptInformation() {
        return null;
    }
}
