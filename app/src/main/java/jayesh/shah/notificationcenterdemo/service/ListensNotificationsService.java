package jayesh.shah.notificationcenterdemo.service;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import jayesh.shah.notificationcenterdemo.constant.AppConstants;
import jayesh.shah.notificationcenterdemo.helper.NotificationHelper;

/**
 * Created by Jayesh on 06/07/17.
 * <p>
 * Notification listener service to get all notifications
 */

public class ListensNotificationsService extends NotificationListenerService {

    private static final String TAG = ListensNotificationsService.class.getSimpleName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.e(TAG, "POSTED");

        Bundle extras = sbn.getNotification().extras;

        String notificationText;
        String packageName = sbn.getPackageName();
        String title = extras.getString("android.title");

        CharSequence ticker = sbn.getNotification().tickerText;
        CharSequence text = extras.getCharSequence("android.text");

        if (text != null) {

            notificationText = text.toString();

        } else if (title != null) {

            notificationText = title;

        } else if (ticker != null) {

            notificationText = ticker.toString();

        } else {

            notificationText = packageName;
        }

        NotificationHelper notificationHelper = new NotificationHelper();
        notificationHelper.setPackageName(packageName);
        notificationHelper.setNotificationText(notificationText);

        Intent broadcastNotificationIntent = new Intent(AppConstants.ACTION_BROADCAST_NOTIFICATION);
        broadcastNotificationIntent.putExtra(AppConstants.KEY_NOTIFICATION, notificationHelper);
        sendBroadcast(broadcastNotificationIntent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
}
