package com.instinctools.awesomestatus.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.instinctools.awesomestatus.R;
import com.instinctools.awesomestatus.presenter.LoginPresenter;
import com.instinctools.awesomestatus.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @NonNull
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.viewCreated();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.onLoginResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.button_login)
    public void onLoginButtonClicked() {
        mLoginPresenter.login(this);
    }

    @Override
    public void showError(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
