package lifemonitor.application.helper.factory;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import lifemonitor.application.R;

/**
 * Builds all notification objects that will be ready to be raised to user when he used the application
 *
 * @author Romain Philippon
 * @version 1.0
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
	private static int LIFEMONITOR_BIG_ICON = R.drawable.ic_launcher;
    /**
     * Sets the default small notification icon
     */
    private static int LIFEMONITOR_SMALL_ICON = R.drawable.ic_launcher;
    /**
     * Sets the default notification title
     */
    private static String DEFAULT_TITLE = Resources.getSystem().getString(R.string.app_name);

    /**
     * Builds a notification with the default title and icon
     * @param ctx is the current application context
     * @return
     */
	public static Notification getNotification(Context ctx) {
		Notification notification = new NotificationCompat.Builder(ctx)
			.setSmallIcon(LIFEMONITOR_SMALL_ICON)
            .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), LIFEMONITOR_BIG_ICON))
            .setContentTitle(DEFAULT_TITLE)
			.build();

		return notification;
	}

    /**
     * Builds a notification that overrides the default title
      * @param ctx is the current application context
     * @param title is the new title
     * @return a notification object ready to be displayed
     */
    public static Notification getNotification(Context ctx, String title) {
        Notification notification = new NotificationCompat.Builder(ctx)
                .setSmallIcon(LIFEMONITOR_SMALL_ICON)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), LIFEMONITOR_BIG_ICON))
                .setContentTitle(title)
                .build();

        return notification;
    }

    /**
     * Builds a notification that overrides the default title and content
     * @param ctx is the current application context
     * @param title is the new title
     * @param content is the content
     * @return a notification object ready to be displayed
     */
    public static Notification getNotification(Context ctx, String title, String content) {
        Notification notification = new NotificationCompat.Builder(ctx)
                .setSmallIcon(LIFEMONITOR_SMALL_ICON)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), LIFEMONITOR_BIG_ICON))
                .setContentTitle(title)
                .setContentText(content)
                .build();

        return notification;
    }

    /**
     * Builds a notification that overrides the default big icon
     * @param ctx is the current application context
     * @param idBigIcon is the new big icon that will be displayed
     * @return a notification object ready to be displayed
     */
    public static Notification getNotification(Context ctx, int idBigIcon) {
        Notification notification = new NotificationCompat.Builder(ctx)
                .setSmallIcon(LIFEMONITOR_SMALL_ICON)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), idBigIcon))
                .setContentTitle(DEFAULT_TITLE)
                .build();

        return notification;
    }

    /**
     * Builds a notification that overrides the default big and the small icon
     * @param ctx is the current application context
     * @param idBigIcon is the new big icon that will be displayed
     * @param idSmallIcon is the new small icon that will be displayed
     * @return a notification object ready to be displayed
     */
    public static Notification getNotification(Context ctx, int idBigIcon, int idSmallIcon) {
        Notification notification = new NotificationCompat.Builder(ctx)
                .setSmallIcon(idSmallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), idBigIcon))
                .setContentTitle(DEFAULT_TITLE)
                .build();

        return notification;
    }
}
