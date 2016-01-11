package com.instinctools.awesomestatus.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.instinctools.awesomestatus.view.activity.LoginActivity;
import com.instinctools.awesomestatus.view.activity.PostStatusActivity;
import com.instinctools.awesomestatus.view.activity.SplashActivity;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class FlowManager {

    private static volatile FlowManager sInstance;

    public static FlowManager getInstance() {
        if (sInstance == null) {
            synchronized (FlowManager.class) {
                if (sInstance == null) {
                    sInstance = new FlowManager();
                }
            }
        }
        return sInstance;
    }

    private FlowManager() {
    }

    public void openLoginScreen(@NonNull Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void openPostScreen(@NonNull Context context) {
        Intent intent = new Intent(context, PostStatusActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void openSplashScreen(@NonNull Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
