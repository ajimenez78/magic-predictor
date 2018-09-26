package com.magicpredictor.arturo.wearablesender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.magicpredictor.arturo.wearablesender.model.Card;

import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {
    private static final String MNEMONIC_ACTION_MESSAGE_PATH = "/mnemonic_action";
    private static final String ACTION_PASS = "com.magicpredictor.arturo.wearablesender.PASS";
    private static final String ACTION_SEND = "com.magicpredictor.arturo.wearablesender.SEND";

    private GoogleApiClient mGoogleApiClient;

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

        startWearableApp();

        if (sSender != null) {
            sSender.connect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopWearableApp();

        if (sSender != null) {
            sSender.disconnect();
        }
    }

    public void onPassButtonClicked(View passButton) {
        Log.i("MainActivity", card.toString());
        pass();
    }

    public void onSendButtonClicked(View sendButton) {
        send();
    }

    private void pass() {
        card = card.nextCardInMnemonicOrder();
    }

    private void send() {
        sSender.sendMessage(card.toString());
        card = card.nextCardInMnemonicOrder();
    }

    private void startWearableApp() {
        mGoogleApiClient = new GoogleApiClient.Builder( this )
            .addApi( Wearable.API )
            .addConnectionCallbacks(this)
            .build();

        mGoogleApiClient.connect();
    }

    private void stopWearableApp() {
        Wearable.MessageApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mGoogleApiClient, this );
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(MNEMONIC_ACTION_MESSAGE_PATH)) {
            String action = new String(messageEvent.getData(), Charset.forName("UTF-8"));
            Log.i("MainActivity", "Action message received: " + action);
            if (ACTION_PASS.equals(action)) {
                pass();
            } else if (ACTION_SEND.equals(action)) {
                send();
            }
        }
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
