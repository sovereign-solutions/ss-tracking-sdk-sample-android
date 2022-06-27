# ss-tracking-sdk-sample-android

ss-tracking-sdk-sample-android
## Installation AAR
Copy arr in libs folder to your project libs folder.

In file /app/build.gradle under dependencies add line:
    implementation files('libs/trackingsdk-release.aar')
    implementation 'com.google.android.gms:play-services-location:18.0.0'
    implementation 'androidx.room:room-runtime:2.3.0'
    implementation 'com.google.code.gson:gson:2.8.6'

Open AndroidManifest.xml and add this under Application tag:
    <service android:name="com.sovereign.trackingsdk.TrackingService"
        android:foregroundServiceType="location"/>

Sync project
Build and Run your app.
