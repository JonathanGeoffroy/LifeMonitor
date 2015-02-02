package lifemonitor.application.controller.monitor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import lifemonitor.application.MyActivity;
import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.ShowMedicalRecordActivity;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Create a notification for a given treatment.
 * @author Célia Cacciatore, Jonathan Geoffroy, Quentin Bailleul
 */
public class TreatmentNotifier {

    /**
     * Create a notification for a given treatment.
     * @param context the activity which creates the notification
     * @param treatment the treatment recalled in notifications
     */
    public static void createNotification(Context context, Treatment treatment) {
        Medicine medicine = treatment.getMedicine();

        Intent intent = new Intent(context, MyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(context.getString(R.string.treatment))
                        .setContentText(String.format("%s %s %s %s", treatment.getQuantity(), medicine.getShape().resource(context), context.getString(R.string.of), medicine))
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(treatment.getId(), builder.build());
    }
}
