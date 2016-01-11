package com.instinctools.awesomestatus.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.instinctools.awesomestatus.R;
import com.instinctools.awesomestatus.presenter.SplashPresenter;
import com.instinctools.awesomestatus.view.SplashView;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class SplashActivity extends AppCompatActivity implements SplashView {

    @NonNull
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FacebookSdk.sdkInitialize(getApplicationContext());

        splashPresenter = new SplashPresenter(this);
        splashPresenter.viewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.viewDestroyed();
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishView() {
        finish();
    }
}
