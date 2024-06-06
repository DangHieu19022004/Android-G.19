package com.example.appdocsach.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;;
import android.net.NetworkRequest;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class Internet extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        connectivityManager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // Khi có kết nối mạng
                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(Network network) {
                // Khi mất kết nối mạng
                Toast.makeText(context, "Internet Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {
            return false;
        }
        Network network = connectivityManager.getActiveNetwork();
        if(network == null) {
            return false;
        }
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }
}