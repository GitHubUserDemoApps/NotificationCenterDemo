package jayesh.shah.notificationcenterdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jayesh.shah.notificationcenterdemo.R;

/**
 * Created by Jayesh on 06/07/17.
 * <p>
 * Adapter class for Notification recycler view.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<String> mNotificationList;
    private List<String> mAppNameList;

    /**
     * @param notificationList Notification list to be shown in recycler view.
     */
    public NotificationAdapter(List<String> notificationList, List<String> appNameList) {

        mNotificationList = notificationList;
        mAppNameList = appNameList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.mNotificationTV.setText(mNotificationList.get(position));
        holder.mAppNameTV.setText(mAppNameList.get(position));
    }

    @Override
    public int getItemCount() {
        int count = -1;

        if (mNotificationList != null) {

            count = mNotificationList.size();
        }
        return count;
    }

    /**
     * Set Notification list in adapter
     * @param notificationList Notification list
     */
    public void setNotificationAppList(List<String> notificationList, List<String> appNameList) {

        mNotificationList = notificationList;
        mAppNameList = appNameList;
        notifyDataSetChanged();
    }

    /**
     * View holder class for Recycler view.
     */
    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView mNotificationTV;
        private TextView mAppNameTV;

        /**
         * @param view view for notification
         */
        public NotificationViewHolder(View view) {
            super(view);
            mNotificationTV = (TextView) view.findViewById(R.id.notification_list_item_notificationTV);
            mAppNameTV = (TextView) view.findViewById(R.id.notification_list_item_appNameTV);
        }
    }

}


