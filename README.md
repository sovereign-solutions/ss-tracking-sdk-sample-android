# ss-tracking-sdk-sample-android

## Example
To run the example project, clone the repo, open and run in Android Studio.

## Installation AAR
Copy aar in libs folder to your project libs folder.

In file /app/build.gradle under dependencies add:

    implementation files('libs/trackingsdk-release.aar')
    implementation 'com.google.android.gms:play-services-location:21.0.1'
    implementation 'androidx.room:room-runtime:2.3.0'
    implementation 'com.google.code.gson:gson:2.8.6'
    
Open AndroidManifest.xml and add this under Application tag:

    <service android:name="com.sovereign.trackingsdk.TrackingService"
        android:foregroundServiceType="location"/>
    <service android:name="com.sovereign.trackingsdk.ARIntentService"/>
        
and some permissions:

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
    <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION" />
    <uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />

Sync project
Build and Run your app.

Usage:
    
    //init Tracking Instance
    Tracking.Builder builder = new Tracking.Builder(activity);
    builder.setApiUrl("https://testing.skedulomatic.com/api/app-base/vdms-tracking/push") // HOST + "/api/app-base/vdms-tracking/push"
        .setAuthen("bearer EPiAx6m-...") //access token
        .setTrackingDriver("username") //username
    mTrackingSDK = builder.build();
    mTrackingSDK.setUseActivityRegconition(true);

    //set trackerId
    String deviceId = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    mTrackingSDK.setTrackerId(deviceId + "@" + username);
    //set device status (1: active, 2: idle)
    mTrackingSDK.setTrackingStatus(1);
    //
    mTrackingSDK.startTracking(); //call this to startTracking.
    //mTrackingSDK.stopTracking(); //call this to stopTracking.

Authen:

    ApiUrl = HOST + "/api/app-base/vdms-tracking/push" // https://testing.skedulomatic.com/api/app-base/vdms-tracking/push
    LoginUrl = AUTHURL + "/oauth/token"                // https://accounts.skedulomatic.com/oauth/token
    
    use the LoginUrl with your username and password to get the `access token`, `refresh token`
    `curl --location 'https://accounts.skedulomatic.com/oauth/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=password' \
    --data-urlencode 'username=<username>' \
    --data-urlencode 'password=<password>'
    `
