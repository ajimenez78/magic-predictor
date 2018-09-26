package com.magicpredictor.arturo.mnemonicsender;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.magicpredictor.arturo.mnemonicsender.model.Card;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_ID = "com.magicpredictor.arturo.mnemonicsender.eventId";
    private SocketSender sSender;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sSender = new SocketSender();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        card = new Card(Card.Suit.CLUBS, Card.Value.FOUR);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        startWearableApp();
        if (sSender != null) {
            sSender.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sSender != null) {
            sSender.disconnect();
        }
    }

    public void onPassButtonClicked(View passButton) {
        Log.i("MainActivity", card.toString());
        card = card.nextCardInMnemonicOrder();
    }

    public void onSendButtonClicked(View sendButton) {
        sSender.sendMessage(card.toString());
        card = card.nextCardInMnemonicOrder();
    }

    private void startWearableApp() {
        int notificationId = 001;

        // Create intents for the reply actions
        Intent actionPassIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPassPendingIntent =
                PendingIntent.getActivity(this, 0, actionPassIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Intent actionSendIntent = new Intent(this, MainActivity.class);
        PendingIntent actionSendPendingIntent =
                PendingIntent.getActivity(this, 0, actionSendIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the actions
        NotificationCompat.Action passAction =
                new NotificationCompat.Action.Builder(R.drawable.notification_template_icon_bg,
                        "Pass", actionPassPendingIntent)
                        .build();
        NotificationCompat.Action sendAction =
                new NotificationCompat.Action.Builder(R.drawable.notification_template_icon_bg,
                        "Send", actionSendPendingIntent)
                        .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.magic)
                        .setContentTitle("Menmonic test")
                        .setContentText("Mnemonic body")
                        .extend(new WearableExtender().addAction(passAction).addAction(sendAction));

// Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

// Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private class SocketSender {

        /**
         * The web socket
         */
        private Socket mSocket;

        SocketSender() throws URISyntaxException {
            mSocket = IO.socket("http://nodejs-ajimenez.rhcloud.com:8000");
        }

        public void connect() {
            mSocket.connect();
        }

        public void disconnect() {
            mSocket.disconnect();
        }

        public void sendMessage(String message) {
            mSocket.emit("message", message);
        }
    }
}
