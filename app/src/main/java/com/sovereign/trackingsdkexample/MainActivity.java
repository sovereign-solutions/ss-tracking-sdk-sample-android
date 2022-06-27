package com.sovereign.trackingsdkexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sovereign.trackingsdk.LocationSendCallBack;
import com.sovereign.trackingsdk.Tracking;

public class MainActivity extends AppCompatActivity {

    private Tracking mTrackingSDK;
    private boolean isTracking = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.text);
        final SwitchCompat switchCompat = findViewById(R.id.swithcer);
        Tracking.Builder builder = new Tracking.Builder(this);
        mTrackingSDK = builder.setApiUrl("https://testing.skedulomatic.com/api/ffms/vdms-tracking/push")
                .setAuthen("bearer EPiAx6m-rnza47HSCQFDHXdTGWqydTCrJzbJk0olSk8w1A0lQtnBhp8isC2OmH3ToSLM4KnWb246PEIqXWqw3pAr_VyUbnkbY57A3gId9H3CvKo8pbjHO_tg0nOOtNTzZUhMaYUJeTfs8McRT93gTHaonoijgwbXAlFiPslNkG_vQBSVd_hngVdkbX_lbYt8aMfSPpDipU4R_Jdn0aoyRnSeaAaijNJpCMnL0K2h_4JoDw40muuffgI_m-4ssl8qYgw961SoQtBbtjvmm3GnrhhNkObs89c1w-cY_0kL8JgJZrX1JOaOVZMxV22VpqJySOUL7Yx5YcHLdwLkfK1QBtmIsEzYk7_Vb2T43ik-DYL3i7KLSTAsFpqBdTz_EdcWPFF696rj3t51SoJB57_x03iNADcJesHtQaDc2I_0aGPTwdoqKCIWPAYBZENCuqx45pBDG_F7W39CKI-QKJHuQqQCu1y7o97cUpA-H-9v9rxnQ_Y8")
                .setTrackingDriver("thanh1308")
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
        switchCompat.setChecked(mTrackingSDK.isTracking());
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