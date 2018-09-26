package com.magicpredictor.arturo.wearablesender;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {
    private static final String MNEMONIC_SENDING_CAPABILITY_NAME = "mnemonic_sending";
    public static final String MNEMONIC_ACTION_MESSAGE_PATH = "/mnemonic_action";
    private static final String ACTION_PASS = "com.magicpredictor.arturo.wearablesender.PASS";
    private static final String ACTION_SEND = "com.magicpredictor.arturo.wearablesender.SEND";

    private GoogleApiClient mGoogleApiClient;

    private TextView mTextView;
    private String sendingNodeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    private String pickBestNodeId(List<Node> nodes) {
        String bestNodeId = null;
        // Find a nearby node or pick one arbitrarily
        for (Node node : nodes) {
            if (node.isNearby()) {
                return node.getId();
            }
            bestNodeId = node.getId();
        }
        return bestNodeId;
    }

    private void setupMnemonicSending() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mGoogleApiClient ).await();

        sendingNodeId = pickBestNodeId(nodes.getNodes());
    }

    private void requestAction(final String action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setupMnemonicSending();
                if (sendingNodeId != null) {
                    Wearable.MessageApi.sendMessage(mGoogleApiClient, sendingNodeId,
                            MNEMONIC_ACTION_MESSAGE_PATH, action.getBytes(Charset.forName("UTF-8"))).await();
                } else {
                    // Unable to retrieve node with sending capability
                }
            }
        }).start();
    }

    public void onPassButtonClicked(View passButton) {
        Log.i("MainActivity", "onPassButtonClicked");
        requestAction(ACTION_PASS);
    }

    public void onSendButtonClicked(View sendButton) {
        Log.i("MainActivity", "onSendButtonClicked");
        requestAction(ACTION_SEND);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
