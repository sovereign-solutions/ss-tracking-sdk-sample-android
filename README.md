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
        
and some permissions:

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

Sync project
Build and Run your app.

Usage:

    //init Tracking Instance
    Tracking.Builder builder = new Tracking.Builder(activity);
    builder.setApiUrl("https://testing.skedulomatic.com/api/app-base/vdms-tracking/push")
        .setAuthen("bearer EPiAx6m-...") //access token
        .setTrackingDriver("username") //username
    mTrackingSDK = builder.build();
    //
    mTrackingSDK.startTracking(); //call this to startTracking.
    //mTrackingSDK.stopTracking(); //call this to stopTracking.
