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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText title;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        title = findViewById(R.id.et_title);
        message = findViewById(R.id.et_message);
        Button channel1Btn = findViewById(R.id.channel_1_btn);
        Button channel2Btn = findViewById(R.id.channel_2_btn);


        channel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle = title.getText().toString();
                String inputMessage = message.getText().toString();

                Intent activityIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, activityIntent, 0);

                Intent broadcastIntent = new Intent(MainActivity.this, NotificationReceiver.class);
                broadcastIntent.putExtra("toastMessage", inputMessage);
                PendingIntent actionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.latte);

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setContentTitle(inputTitle)
                        .setContentText(inputMessage)
                        .setLargeIcon(largeIcon)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(getString(R.string.logn_dummy_test))
                                .setBigContentTitle("Big Content Title")
                                .setSummaryText("Summary Text"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                        .build();

                notificationManagerCompat.notify(1, notification);
            }
        });

        channel2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle = title.getText().toString();
                String inputMessage = message.getText().toString();

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.ic_two)
                        .setContentTitle(inputTitle)
                        .setContentText(inputMessage)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("This is line1")
                                .addLine("This is line2")
                                .addLine("This is line3")
                                .addLine("This is line4")
                                .addLine("This is line5")
                                .addLine("This is line6")
                                .setBigContentTitle("Big Content Title")
                                .setSummaryText("Summary Text"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();

                notificationManagerCompat.notify(2, notification);
            }
        });

    }
}
