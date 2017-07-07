package jayesh.shah.notificationcenterdemo.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import jayesh.shah.notificationcenterdemo.R;
import jayesh.shah.notificationcenterdemo.activity.NotificationsActivity;
import jayesh.shah.notificationcenterdemo.constant.AppConstants;
import jayesh.shah.notificationcenterdemo.helper.NotificationHelper;

/**
 * Created by Jayesh on 05/07/17.
 * <p>
 * Service that would receive all actions from notification center and update notification center accordingly.
 */

public class NotificationService extends Service {

    private static final String TAG = NotificationService.class.getSimpleName();

    private RemoteViews mRemoteViews;
    private SharedPreferences mSharedPreferences;
    private ArrayList<String> mAppNameList;
    private ArrayList<String> mNotificationList;
    private NotificationReceiver mNotificationReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        // initNotificationCenter();

        //Service may get killed by system, for such scenario shared preferences is used to preserve user preferences.
        mSharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        mAppNameList = new ArrayList<>();
        mNotificationList = new ArrayList<>();

        mNotificationReceiver = new NotificationReceiver();

        IntentFilter notificationIntentFilter = new IntentFilter(AppConstants.ACTION_BROADCAST_NOTIFICATION);

        registerReceiver(mNotificationReceiver, notificationIntentFilter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent != null && intent.getAction() != null) {
            Log.e(TAG, intent.getAction());

            String action = intent.getAction();

            SharedPreferences.Editor editor = mSharedPreferences.edit();

            switch (action) {

                case AppConstants.ACTION_START_NOTIFICATION_SERVICE: {

                    mRemoteViews = intent.getParcelableExtra(AppConstants.KEY_REMOTE);
                    break;
                }

                case AppConstants.ACTION_WIFI: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.wifi), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image1, R.drawable.wifi_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image1, R.drawable.wifi_enabled);
                        }

                        editor.putBoolean(getString(R.string.wifi), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_BLUETOOTH: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.bluetooth), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image2, R.drawable.bluetooth_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image2, R.drawable.bluetooth_enabled);
                        }

                        editor.putBoolean(getString(R.string.bluetooth), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_ROTATION: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.rotation), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image3, R.drawable.screen_rotation_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image3, R.drawable.screen_rotation_enabled);
                        }

                        editor.putBoolean(getString(R.string.rotation), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_LOCATION: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.location), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image4, R.drawable.location_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image4, R.drawable.location_enabled);
                        }

                        editor.putBoolean(getString(R.string.location), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_FLASHLIGHT: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.flashlight), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image5, R.drawable.flashlight_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image5, R.drawable.flashlight_enabled);
                        }

                        editor.putBoolean(getString(R.string.flashlight), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_AIRPLANE_MODE: {

                    if (mRemoteViews != null) {

                        boolean state = mSharedPreferences.getBoolean(getString(R.string.airplane_mode), true);

                        if (state) {

                            mRemoteViews.setImageViewResource(R.id.image6, R.drawable.airplane_mode_disabled);

                        } else {

                            mRemoteViews.setImageViewResource(R.id.image6, R.drawable.airplane_mode_enabled);
                        }

                        editor.putBoolean(getString(R.string.airplane_mode), !state);
                        editor.commit();
                    }

                    break;
                }

                case AppConstants.ACTION_SHOW_NOTIFICATIONS: {
                    //Below collapses notification center and will launch new activity
                    Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                    sendBroadcast(closeIntent);

                    Log.e(TAG, "STARTING ACTIVITY");
                    Intent notificationIntent = new Intent(this, NotificationsActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    notificationIntent.putExtra(AppConstants.KEY_NOTIFICATION, mNotificationList);
                    notificationIntent.putExtra(AppConstants.KEY_APP_NAME, mAppNameList);
                    startActivity(notificationIntent);

                    break;
                }

                default: {

                    Log.i(TAG, "Different action is received: " + action);
                    break;
                }
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true).setOngoing(true).setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setCustomBigContentView(mRemoteViews).setAutoCancel(false);

            NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            notificationmanager.notify(1, builder.build());
        }

        // Sticky service to re-Start itself when gets killed by system.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mNotificationReceiver);
    }

    /**
     * BroadcastReceiver for receiving notification broadcast
     */
    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            NotificationHelper notificationHelper = (NotificationHelper) intent.
                    getSerializableExtra(AppConstants.KEY_NOTIFICATION);

            String packageName = notificationHelper.getPackageName();

            if ((getPackageName().equals(packageName) && !mAppNameList.contains(packageName)) || (!getPackageName().equals(packageName))) {

                Log.e(TAG, "ADDED TO LIST FROM RECEIVER");
                mAppNameList.add(packageName);
                mNotificationList.add(notificationHelper.getNotificationText());
            }
        }
    }
}
