package lifemonitor.application.controller.monitor;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import lifemonitor.application.R;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class TreatmentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Treatment treatment = (Treatment) intent.getSerializableExtra("treatment");
        if (Calendar.getInstance().after(treatment.computeEndDate())) {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.cancel(alarmIntent);
        } else {
            TreatmentNotifier.createNotification(context, treatment);
        }
    }
}
