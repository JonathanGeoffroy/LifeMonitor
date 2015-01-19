package lifemonitor.application.model.medicalRecord;

import android.content.Context;

/**
 * Medical Record Item displayed by Medical Record Activity
 * @author Jonathan Geoffroy
 */
public interface MedicalRecordItem {
    public String getTitle(Context context);
    public String getSubTitle(Context context);
    public int getColor();
}
