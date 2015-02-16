package lifemonitor.application.model.medicalRecord;

import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.content.Context;

import lifemonitor.application.R;

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
        return R.color.allergy_color;
    }

    @Override
    public DialogFragment acceptInformation() {
        return null;
    }
}
