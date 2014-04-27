package com.polatic.pantaudki.network;

import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Handling network receiver intent
 * reference : http://www.xinotes.net/notes/note/1526/
 */
public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            String typeName = info.getTypeName();
            String subtypeName = info.getSubtypeName();
            boolean available = info.isAvailable();

            LogManager.print("Network Type: " + typeName + ", subtype: " + subtypeName
                    + ", available: " + available);
        }
    }
}
