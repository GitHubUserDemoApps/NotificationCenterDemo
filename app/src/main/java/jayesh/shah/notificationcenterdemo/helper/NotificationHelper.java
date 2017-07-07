package jayesh.shah.notificationcenterdemo.helper;

import java.io.Serializable;

/**
 * Created by Jayesh on 06/07/17.
 * <p>
 * Helper class for broadcasting Notification.
 */

public class NotificationHelper implements Serializable {

    private String packageName;
    private String notificationText;

    /**
     *  Get application package name
     * @return package name
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * Set application package name
     * @param packageName set package name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     *
     * Get notification text
     * @return notification text
     */
    public String getNotificationText() {
        return notificationText;
    }

    /**
     *
     * Set notification text
     * @param notificationText set notification text
     */
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
