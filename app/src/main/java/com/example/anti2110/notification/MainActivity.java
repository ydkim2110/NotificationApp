package com.example.anti2110.notification;

import android.app.Notification;
import android.app.NotificationChannel;
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

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setContentTitle(inputTitle)
                        .setContentText(inputMessage)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
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
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();

                notificationManagerCompat.notify(2, notification);
            }
        });

    }
}
