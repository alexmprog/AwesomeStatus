package com.instinctools.awesomestatus;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // initilize facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
