package lifemonitor.application.helper.factory;

import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;

import lifemonitor.application.R;

/**
 * Builds all notification objects that will be ready to be raised to user when he used the application
 */
public class NotificationFactory {
    /**
     * Sets the default notification visibility
     */
	private static int DEFAULT_VISIBILITY = Notification.VISIBILITY_PRIVATE;
    /**
     * Sets the default notification priority
     */
    private static int DEFAULT_PRIORITY = Notification.PRIORITY_HIGH;
    /**
     * Sets the default notification icon
     */
	private static Drawable LIFEMONITOR_ICON;
    /**
     * Sets the default notification title
     */
    private static String DEFAULT_TITLE = "LifeMonitor";

    /**
     * Builds a notification with the default title and icon
     * @param ctx
     * @return
     */
	public static Notification getNotification(Context ctx) {
		Notification notification = new NotificationCompat.Builder(ctx)
			//.setSmallIcon(LIFEMONITOR_ICON)
            //.setLargeIcon(LIFEMONITOR_ICON)
            .setContentTitle(DEFAULT_TITLE)
			.build();

		return notification;
	}

    /**
     * Builds a notification that overrides the default title
      * @param ctx
     * @param title is the new title
     * @return
     */
    public static Notification getNotification(Context ctx, String title) {
        Notification notification = new NotificationCompat.Builder(ctx)
                //.setSmallIcon(LIFEMONITOR_ICON)
                //.setLargeIcon(LIFEMONITOR_ICON)
                .setContentTitle(title)
                .build();

        return notification;
    }

    /**
     * Builds a notification that overrides the default title and content
     * @param ctx
     * @param title
     * @param content
     * @return
     */
    public static Notification getNotification(Context ctx, String title, String content) {
        Notification notification = new NotificationCompat.Builder(ctx)
                //.setSmallIcon(LIFEMONITOR_ICON)
                //.setLargeIcon(LIFEMONITOR_ICON)
                .setContentTitle(title)
                .setContentText(content)
                .build();

        return notification;
    }
}
