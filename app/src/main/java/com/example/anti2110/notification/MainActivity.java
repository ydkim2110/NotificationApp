package com.example.anti2110.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText title;
    private EditText message;

    private MediaSessionCompat mediaSessionCompat;

    static List<Message> MESSAGES = new ArrayList<>();

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

        MESSAGES.add(new Message("Good Morning!", "Jim"));
        MESSAGES.add(new Message("Hello", null));
        MESSAGES.add(new Message("Hi", "Jenny"));

        channel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChannel1Notification(MainActivity.this);
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

    public static void sendChannel1Notification (Context context) {
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();

        Intent replyIntent;
        PendingIntent replyPendingIntent = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0);
        } else {
            //start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.cancel(id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_like,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Group Chat");

        for (Message chatMessage : MESSAGES) {
            NotificationCompat.MessagingStyle.Message notificationMessage = new NotificationCompat.MessagingStyle.Message(
                    chatMessage.getText(),
                    chatMessage.getTimestamp(),
                    chatMessage.getSender()
            );
            messagingStyle.addMessage(notificationMessage);
        }

        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }


}
