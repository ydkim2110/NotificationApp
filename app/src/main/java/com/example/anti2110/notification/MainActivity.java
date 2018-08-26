package com.example.anti2110.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText title;
    private EditText message;

    private MediaSessionCompat mediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        title = findViewById(R.id.et_title);
        message = findViewById(R.id.et_message);
        Button channel1Btn = findViewById(R.id.channel_1_btn);
        Button channel2Btn = findViewById(R.id.channel_2_btn);

        mediaSessionCompat = new MediaSessionCompat(this, "tag");


        channel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle = title.getText().toString();
                String inputMessage = message.getText().toString();

                Intent activityIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, activityIntent, 0);

                Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.latte);

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setContentTitle(inputTitle)
                        .setContentText(inputMessage)
                        .setLargeIcon(picture)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(picture)
                                .bigLargeIcon(null))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .build();

                notificationManagerCompat.notify(1, notification);
            }
        });

        channel2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle = title.getText().toString();
                String inputMessage = message.getText().toString();

                Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.latte);

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.ic_two)
                        .setContentTitle(inputTitle)
                        .setContentText(inputMessage)
                        .setLargeIcon(artwork)
                        .addAction(R.drawable.ic_dislike, "Dislike", null)
                        .addAction(R.drawable.ic_previous, "Previous", null)
                        .addAction(R.drawable.ic_pause, "Pause", null)
                        .addAction(R.drawable.ic_next, "Next", null)
                        .addAction(R.drawable.ic_like, "Like", null)
                        .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(1, 2, 3)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setSubText("Sub Text")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();

                notificationManagerCompat.notify(2, notification);
            }
        });

    }
}
