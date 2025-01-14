package com.tigerlight;

import static com.tigerlight.registration.fragment.AlertFragment.jsonobjectToChange;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.tigerlight.registration.activity.MainActivity;
import com.tigerlight.registration.fragment.AlertDetailFragment;
import com.tigerlight.registration.util.Constant;
import com.tigerlight.util.CheckForeground;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAlertBroadcastReceiver extends WakefulBroadcastReceiver {

    private final String TAG_USER_NAME = "username";
    private String data = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Logged by Subhash Rathour ", "onReceive: "+intent);
        if (CheckForeground.isInForeGround() && !CheckForeground.isThreatScreenVisible()) {
            updateInFront(context, intent);
            playAlertSound(context);
//            showNotification(context, intent);
            return;
        } else {

            //  showNotification(context, intent);
            playAlertSound(context);
        }

        //        if (CheckForeground.isInForeGround() && !CheckForeground.isThreatScreenVisible()) {
//            updateInFront(context, intent);
//            return;
//        }
//        showNotification(context, intent);


    }

    private void playAlertSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.siren);
        mediaPlayer.start();
        // Release the MediaPlayer resources after playback
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
    }

    private void updateInFront(Context context, Intent intent) {
//        int alertCount = Preference.getInstance().mSharedPreferences.getInt("alert_count", 0);
//        alertCount += 1;
//        Preference.getInstance().savePreferenceData("alert_count", alertCount);

        Bundle extras = intent.getExtras();
        data = extras.getString("gcm.notification.data");
        if (data == null) {
            data = extras.getString("message");
        }
        if (data == null) {
            data = extras.getString("gcm.notification.alert");
        }
      if (data == null) {
            return;
        }
        try {
            jsonobjectToChange = null;
            jsonobjectToChange = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final AlertDetailFragment alertDetailFragment = new AlertDetailFragment();
        final Bundle bundle = new Bundle();
        final String jsonObject = jsonobjectToChange.toString();
        bundle.putString(Constant.JSON_OBJECT, jsonObject);
        alertDetailFragment.setArguments(bundle);
        CheckForeground.getActivity().getFragmentManager()
                .beginTransaction().add(
                        R.id.activity_registartion_fl_container, alertDetailFragment, alertDetailFragment.getClass().getSimpleName())
                .addToBackStack(alertDetailFragment.getClass().getSimpleName()).commit();
        //addFragment(new AlertDetailFragment());

        // Intent intent5 = new Intent("parth");
        //LocalBroadcastManager.getInstance(context).sendBroadcast(intent5);


    }

    private void showNotification(Context context, Intent intentData) {
//        int alertCount = Preference.getInstance().mSharedPreferences.getInt("alert_count", 0);
//        alertCount += 1;
//        Preference.getInstance().savePreferenceData("alert_count", alertCount);

        Bundle extras = intentData.getExtras();
        data = extras.getString("gcm.notification.data");
        if (data == null) {
            data = extras.getString("message");
        }
        if (data == null) {
            data = extras.getString("gcm.notification.alert");
        }
        if (data == null) {
            return;
        }
        String userName = "";
        String safeDangerString = " is in danger!";
        try {
            jsonobjectToChange = null;
            jsonobjectToChange = new JSONObject(data);
            userName = jsonobjectToChange.optString(TAG_USER_NAME);
            if (jsonobjectToChange.optInt("status") == 1) {
                safeDangerString = userName + " is safe.";
            } else {
                safeDangerString = userName + " is in danger!";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String jsonObject = jsonobjectToChange.toString();
        final Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constant.JSON_OBJECT, jsonObject);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.app_icon).setContentTitle("D.A.D.").setContentText(safeDangerString);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}


