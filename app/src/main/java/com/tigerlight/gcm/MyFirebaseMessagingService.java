package com.tigerlight.gcm;

import static com.tigerlight.registration.fragment.AlertFragment.jsonobjectToChange;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tigerlight.R;
import com.tigerlight.registration.activity.MainActivity;
import com.tigerlight.registration.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Logged by Subhash Rathour ", "onMessageReceived: " + remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d("Logged by Subhash Rathour ", "onMessageReceived: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d("Logged by Subhash Rathour ", "title: " + title);
            Log.d("Logged by Subhash Rathour ", "body: " + body);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sendNotification(title, body);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotification(String title, String body) {
        playAlertSound(getApplicationContext());
        // Create an Intent for the notification's click action (if required)
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Create a notification with NotificationCompat.Builder
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "channel_id")
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true) // Close the notification when tapped
                        .setContentIntent(pendingIntent);

        // Get the NotificationManager and display the notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            // Check for Android Oreo (API 26) and create a notification channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel_id",
                        "D.A.D",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            // Display the notification
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void playAlertSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.siren);
        mediaPlayer.start();
        // Release the MediaPlayer resources after playback
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("Logged by Subhash Rathour ", "onNewToken: " + token);
    }
}
