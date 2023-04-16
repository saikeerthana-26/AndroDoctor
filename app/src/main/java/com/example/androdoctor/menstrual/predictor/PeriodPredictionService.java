package com.example.androdoctor.menstrual.predictor;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.androdoctor.R;
import com.example.androdoctor.menstrual.period_days.PeriodDaysManager;
import com.example.androdoctor.menstrual.util.AppPreferences;

import org.joda.time.LocalDate;

import java.util.Set;

public class PeriodPredictionService extends IntentService {
    private static final String TAG = "PeriodPredictionService";

    public static final String ACTION_SCHEDULED_RECALCULATION
            = AppPreferences.APPLICATION_PREFIX + "action.SCHEDULED_RECALCULATION";

    private PeriodDaysManager periodDaysManager;
    private PeriodCalculator periodCalculator;

    public PeriodPredictionService() {
        super("PeriodPredictionService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        periodDaysManager = new PeriodDaysManager(getApplicationContext());
        periodCalculator = new PeriodCalculator(periodDaysManager);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (action == null) {
                Log.e(TAG, "Null action! Intent object: " + intent.toString());
                return;
            }

            switch (action) {
                case ACTION_SCHEDULED_RECALCULATION:
                    Log.i(TAG, "Scheduled recalculation!");
                    periodCalculator.calculate();
                    checkStatusAndSendNotifications();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action name :\"" + action + "\"");
            }
        }
    }

    private void checkStatusAndSendNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        LocalDate theNextDay = (new LocalDate()).plusDays(1);

        if (periodDaysManager.sendPeriodNotification()) {
            Set<LocalDate> period = periodDaysManager.getHistoricPeriodDays();

            if (period.contains(theNextDay)) {
                sendPeriodNotification(notificationManager);
            }
        }

        if (periodDaysManager.sendFertileNotification()) {
            Set<LocalDate> fertile = periodDaysManager.getHistoricFertileDays();

            if (fertile.contains(theNextDay)) {
                sendFertileNotification(notificationManager);
            }
        }

        if (periodDaysManager.sendOvulationNotification()) {
            Set<LocalDate> ovulation = periodDaysManager.getHistoricOvulationDays();

            if (ovulation.contains(theNextDay)) {
                sendOvulationNotification(notificationManager);
            }
        }
    }

    private void sendOvulationNotification(NotificationManager notificationManager) {
        sendNotification(notificationManager,AppPreferences.OVULATION_NOTIFICATION_ID,
                getString(R.string.ovulation_notification_title), getString(R.string.ovulation_notification_text));

    }

    private void sendFertileNotification(NotificationManager notificationManager) {
        sendNotification(notificationManager, AppPreferences.FERTILE_NOTIFICATION_ID,
                getString(R.string .fertile_notification_title), getString(R.string.fertile_notification_text));
    }

    private void sendPeriodNotification(NotificationManager notificationManager) {
        sendNotification(notificationManager, AppPreferences.PERIOD_NOTIFICATION_ID,
                getString(R.string.period_notification_title), getString(R.string.period_notification_text));
    }

    private void sendNotification(NotificationManager manager, int notificationId,
                                  String contentTitle, String contentText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_baseline_today_24)
                .setContentTitle(contentTitle)
                .setContentText(contentText);

        manager.notify(notificationId, builder.build());
    }
}
