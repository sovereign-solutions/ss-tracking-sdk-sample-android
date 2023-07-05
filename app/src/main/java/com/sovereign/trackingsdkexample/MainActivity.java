package com.sovereign.trackingsdkexample;

import static android.content.Context.POWER_SERVICE;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sovereign.trackingsdk.LocationSendCallBack;
import com.sovereign.trackingsdk.Tracking;

public class MainActivity extends AppCompatActivity {

    private Tracking mTrackingSDK;
    private boolean isTracking = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.text);
        final SwitchCompat switchCompat = findViewById(R.id.swithcer);
        Tracking.Builder builder = new Tracking.Builder(this);
        String username = "thanh13";
        String token = "bearer EPiAx6m-.....";
        String refreshToken = "bearer Rnza47H-....";
//        expires_in * 1000.0 + now()
        long expireIn = 0;
        mTrackingSDK = builder.setApiUrl("https://testing.skedulomatic.com/api/app-base/vdms-tracking/push") // HOST + "/api/app-base/vdms-tracking/push"
                .setAuthen(token)
                .setRefreshToken(refreshToken)
                .setTokenExpired(expireIn)
                .setAuthenURL("https://accounts.skedulomatic.com/oauth/token") // AUTH_URL + "/oauth/token"
                .setTrackingDriver(username)
                .setTrackingInterval(5000)
                .setLocationUpdateFrequency(5000)
                .setNotificationActionLaunchLabel("launch")
                .setNotificationActionCloseLabel("close")
                .build();
//        mTrackingSDK.setDialogRequestBackgroundLocationTitle("access background location dialog title")
//                .setDialogRequestBackgroundLocationMessage("access background location dialog message")
//                .setNotificationContentText("Tracking")
//                .setNotificationContentTitle("Tracking")
//                .setNotificationIconRes(R.mipmap.ic_launcher)
        //set trackerId
        String deviceId = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        mTrackingSDK.setTrackerId(deviceId + "@" + username);
        switchCompat.setChecked(mTrackingSDK.isTracking());
        mTrackingSDK.setUseActivityRegconition(true);
        mTrackingSDK.setCallback(new LocationSendCallBack() {
            @Override
            public void onSendLocationResult(String resultString) {
                Log.d("onSendLocationResult", resultString);
            }

            @Override
            public void onFailed(String errorString) {

            }
            @Override
            public void trackingStarted() {
                isTracking = true;
                textView.setText("Tracking started");
            }

            @Override
            public void trackingStopped() {
                isTracking = false;
                textView.setText("Tracking stopped");
                switchCompat.setChecked(false);
            }
        });
        switchCompat.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (buttonView, isChecked) -> {
            if (isChecked) {
                if (!isTracking) {
                    mTrackingSDK.startTracking();
                }
            } else {
                mTrackingSDK.stopTracking();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTrackingSDK.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTrackingSDK.onResume(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mTrackingSDK.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}