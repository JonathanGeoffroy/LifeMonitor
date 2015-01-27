package lifemonitor.application.controller.monitor;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import lifemonitor.application.R;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class TreatmentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Treatment treatment = (Treatment) intent.getSerializableExtra("treatment");
        TreatmentNotifier.createNotification(context, treatment);
    }
}
