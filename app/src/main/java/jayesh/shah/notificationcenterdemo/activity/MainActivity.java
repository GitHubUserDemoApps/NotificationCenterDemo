package jayesh.shah.notificationcenterdemo.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import jayesh.shah.notificationcenterdemo.R;
import jayesh.shah.notificationcenterdemo.constant.AppConstants;
import jayesh.shah.notificationcenterdemo.service.NotificationService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RemoteViews mRemoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotificationCenter();

        startNotificationService();
    }

    //Initialize notification center
    private void initNotificationCenter() {
        //Initialize remote views object
        mRemoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);

        // build notification using notification compat for compatibility purpose
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setOngoing(true)
                .setAutoCancel(false)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText(getString(R.string.app_name))
                .setCustomBigContentView(mRemoteViews);

        //set individual intent for respective icon/functionality

        // Wifi icon intent
        Intent wifiIntent = new Intent(getApplicationContext(), NotificationService.class);
        wifiIntent.setAction(AppConstants.ACTION_WIFI);
        PendingIntent pendingIntentWifi = PendingIntent.getService(getApplicationContext(), 1, wifiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Bluetooth intent
        Intent bluetoothIntent = new Intent(getApplicationContext(), NotificationService.class);
        bluetoothIntent.setAction(AppConstants.ACTION_BLUETOOTH);
        PendingIntent pendingIntentBluetooth = PendingIntent.getService(getApplicationContext(), 2, bluetoothIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Rotation intent
        Intent rotationIntent = new Intent(getApplicationContext(), NotificationService.class);
        rotationIntent.setAction(AppConstants.ACTION_ROTATION);
        PendingIntent pendingIntentRotation = PendingIntent.getService(getApplicationContext(), 3, rotationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Location intent
        Intent locationIntent = new Intent(getApplicationContext(), NotificationService.class);
        locationIntent.setAction(AppConstants.ACTION_LOCATION);
        PendingIntent pendingIntentLocation = PendingIntent.getService(getApplicationContext(), 4, locationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Rotation intent
        Intent flashLightIntent = new Intent(getApplicationContext(), NotificationService.class);
        flashLightIntent.setAction(AppConstants.ACTION_FLASHLIGHT);
        PendingIntent pendingIntentFlashLight = PendingIntent.getService(getApplicationContext(), 5, flashLightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Rotation intent
        Intent airplaneModeIntent = new Intent(getApplicationContext(), NotificationService.class);
        airplaneModeIntent.setAction(AppConstants.ACTION_AIRPLANE_MODE);
        PendingIntent pendingIntentAirPlaneMode = PendingIntent.getService(getApplicationContext(), 6, airplaneModeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Show notifications intent
        Intent showNotificationsIntent = new Intent(getApplicationContext(), NotificationService.class);
        showNotificationsIntent.setAction(AppConstants.ACTION_SHOW_NOTIFICATIONS);
        PendingIntent pendingIntentShowNotifications = PendingIntent.getService(getApplicationContext(), 6, showNotificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set respective click listeners against individual controls.
        mRemoteViews.setOnClickPendingIntent(R.id.image1, pendingIntentWifi);
        mRemoteViews.setOnClickPendingIntent(R.id.image2, pendingIntentBluetooth);
        mRemoteViews.setOnClickPendingIntent(R.id.image3, pendingIntentRotation);
        mRemoteViews.setOnClickPendingIntent(R.id.image4, pendingIntentLocation);
        mRemoteViews.setOnClickPendingIntent(R.id.image5, pendingIntentFlashLight);
        mRemoteViews.setOnClickPendingIntent(R.id.image6, pendingIntentAirPlaneMode);
        mRemoteViews.setOnClickPendingIntent(R.id.btnShownotifications, pendingIntentShowNotifications);

        // Notify user/system
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(1, builder.build());
    }

    //Start notification service
    private void startNotificationService() {
        //Create and set values to notification service intent
        Intent notificationServiceIntent = new Intent(this, NotificationService.class);
        notificationServiceIntent.putExtra(AppConstants.KEY_REMOTE, mRemoteViews);
        notificationServiceIntent.setAction(AppConstants.ACTION_START_NOTIFICATION_SERVICE);

        //Start notification service
        startService(notificationServiceIntent);
    }
}
