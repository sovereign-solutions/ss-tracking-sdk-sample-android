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
//        use the LoginUrl with your username and password to get the `token`, `refreshToken`
//        `curl --location 'https://accounts.skedulomatic.com/oauth/token' \
//        --header 'Content-Type: application/x-www-form-urlencoded' \
//        --data-urlencode 'grant_type=password' \
//        --data-urlencode 'username=<username>' \
//        --data-urlencode 'password=<password>'
        String username = "sales_testing_1";

        String token = "bearer pJDEr8Q-wfmzZVFlt6WJxqQEoqjbDyMzv3x_NKkj1Oouc24eQKGsH4bVVveWSji4dpY6WTBxjg4VutgvmuNpHnVu1gyn0g8oMXQ7X-vu9SiAHF9eX4T0msUgkPvXppqxjWm2DQzicTRVC2qN3uYEDW6CsFbhqFVe4wakbU5NXgYN2qBIN2zYXkvyfObBDijSIUA9E30C992-ffA3HItmWV51FRKlX-R6bsIqsM1CePITQB0FOzBXPPH0NmjCp6G69h-Gi9OKvGmCYHcwcB3K_OstNnIZ9nueORJSbKtEt4McJ5ywsOiUzs_E3DdehN4-HukJTwIeOw7-3C4gP9FCB7p2UUQc3ZoKIs3V-2KvkcVC2yaq1F5PRafzrLQ5YItT95uK8PTEzVEMyGOeO3wmL3yvCekFegKWP2SFNdl6H6Yzf5mDt01BRW67Yvhrc-wBtMiPRa11JsRbi4kX_RGcq_0Aoxg";
        String refreshToken = "dfWI_9JQNwcpNJOjwez5XBc5UPJYLJ6DBdgAVfNL_LH1C7qru4v6I3DlX1ZboxZ08uNsL6BfecixN40XS65Bd0tGtWg5ed9yHvGli9k2sNHidQ1IreWwkF4IwT9oPutBoS2PFQrjcdjPe1oF97bNaS9cecS9kTtM27zKUaJ0n0-b03uYCshWDm-0pbCn_TAB7-8ecGu2DfHym474laq4OJ-GwHA12qFj2ExuY7sq0ghU9NzqDHqjsDA_oNauPDO5d7buhJxAOq0i3GKJ4s9xTU3YkA9qC4zJc3z470Rnrr71YT_SxejPd6PTdVNRTTMuSzLBiOxZa6vBL5G4znvVrkQMyL4E9q1FXxai9SStOH6uEodqDpzv1K4KwNk9zbjus-1TTpj5Hn-77nQ3XRqZeEQX7_tcBEtsM7QCpV_EMM8RQbbGhTzPaT-UtT3YGr3_w26de6iAtChKxuAzkPOpFplACBk";
//        expires_in * 1000.0 + now()
        long expireIn = 0;
        mTrackingSDK = builder.setApiUrl("https://sales.grow-matic.com/api/app-base/vdms-tracking/push") // HOST + "/api/app-base/vdms-tracking/push"
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
        //set device status (1 active, 2 idle)
        mTrackingSDK.setTrackingStatus(1);
        //
        switchCompat.setChecked(mTrackingSDK.isTracking());
        mTrackingSDK.setUseActivityRegconition(true);
//        mTrackingSDK.setCallback(new LocationSendCallBack() {
//            @Override
//            public void onSendLocationResult(String resultString) {
//                Log.d("onSendLocationResult", resultString);
//            }
//
//            @Override
//            public void onFailed(String errorString) {
//
//            }
//            @Override
//            public void trackingStarted() {
//                isTracking = true;
//                textView.setText("Tracking started");
//            }
//
//            @Override
//            public void trackingStopped() {
//                isTracking = false;
//                textView.setText("Tracking stopped");
//                switchCompat.setChecked(false);
//            }
//        });
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