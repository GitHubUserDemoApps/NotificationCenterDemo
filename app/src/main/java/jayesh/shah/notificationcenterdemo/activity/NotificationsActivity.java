package jayesh.shah.notificationcenterdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import jayesh.shah.notificationcenterdemo.R;
import jayesh.shah.notificationcenterdemo.adapter.NotificationAdapter;
import jayesh.shah.notificationcenterdemo.constant.AppConstants;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Created by Jayesh on 06/07/17.
 * <p>
 * Activity to show Notifications.
 */

public class NotificationsActivity extends AppCompatActivity {

    private static final String TAG = NotificationsActivity.class.getSimpleName();
    private ArrayList<String> mAppNameList;
    private ArrayList<String> mNotificationList;
    private ArrayList<String> mFilteredNotificationList;
    private ArrayList<String> mFilteredAppNameList;
    private NotificationAdapter mAdapter;
    private EditText mSearchBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);

        mNotificationList = (ArrayList) getIntent().getSerializableExtra(AppConstants.KEY_NOTIFICATION);
        mAppNameList = (ArrayList) getIntent().getSerializableExtra(AppConstants.KEY_APP_NAME);
        mFilteredNotificationList = new ArrayList<>();
        mFilteredAppNameList = new ArrayList<>();

        updateNotificationList();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mAppNameList.clear();;
        mNotificationList.clear();
        mFilteredNotificationList.clear();
        mFilteredNotificationList.clear();

        mNotificationList = (ArrayList) intent.getSerializableExtra(AppConstants.KEY_NOTIFICATION);
        mAppNameList = (ArrayList) intent.getSerializableExtra(AppConstants.KEY_APP_NAME);

        updateNotificationList();
    }

    /**
     * Update notification list as per received list of notification
     *
     */
    private void updateNotificationList() {

        if (mNotificationList == null || mNotificationList.size() == 0) {

            (findViewById(R.id.notificationsLayout)).setVisibility(View.GONE);
            (findViewById(R.id.noNotificationsTV)).setVisibility(View.VISIBLE);

        } else {

            mSearchBox = (EditText) findViewById(R.id.search_box);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notificationsRV);

            mAdapter = new NotificationAdapter(mNotificationList, mAppNameList);
            recyclerView.setAdapter(mAdapter);

            // search suggestions using the edittext widget
            mSearchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    mFilteredNotificationList.clear();
                    mFilteredAppNameList.clear();

                    if (s.length() > 0) {
                        int ratio;
                        for (int i = 0; i < mNotificationList.size(); i++) {

                            //To filter based on app name
                            String appName = mAppNameList.get(i);

                            //To filter based on notification text.
                           /* String notification = mNotificationList.get(i);
                            ratio = FuzzySearch.partialRatio(s.toString(), notification.toLowerCase()); */

                            ratio = FuzzySearch.partialRatio(s.toString(), appName.toLowerCase());
                            if (ratio > 50) {
                                mFilteredNotificationList.add(mNotificationList.get(i));
                                mFilteredAppNameList.add(mAppNameList.get(i));
                            }
                        }

                        mAdapter.setNotificationAppList(mFilteredNotificationList, mFilteredAppNameList);

                    } else {

                        mAdapter.setNotificationAppList(mNotificationList, mAppNameList);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

}

