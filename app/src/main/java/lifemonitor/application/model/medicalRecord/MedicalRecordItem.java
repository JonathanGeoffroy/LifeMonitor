package lifemonitor.application.model.medicalRecord;

import android.support.v4.app.DialogFragment;
import android.content.Context;

import java.io.Serializable;

/**
 * Medical Record Item displayed by Medical Record Activity
 * @author Jonathan Geoffroy
 */
public interface MedicalRecordItem extends Serializable {
    public String getTitle(Context context);
    public String getSubTitle(Context context);
    public int getColor();
    public DialogFragment acceptInformation();

}
