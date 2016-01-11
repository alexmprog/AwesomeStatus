package com.instinctools.awesomestatus.interactor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.instinctools.awesomestatus.model.LoginCallback;
import com.instinctools.awesomestatus.utils.FacebookManager;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class LoginInteractor implements FacebookCallback<LoginResult> {

    private static final String TAG = LoginInteractor.class.getSimpleName();

    @NonNull
    private LoginCallback mLoginCallback;

    @NonNull
    private CallbackManager mCallbackManager;

    public LoginInteractor(@NonNull LoginCallback loginCallback) {
        this.mLoginCallback = loginCallback;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    public void login(@NonNull Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, FacebookManager.READ_PERMISSIONS);
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        FacebookManager.getInstance().setUser();
        mLoginCallback.onSuccess(FacebookManager.getInstance().getUser());
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "User canceled login");
        mLoginCallback.onError(null);
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG, "Login failed", error);
        mLoginCallback.onError(error.getMessage());
    }
}
