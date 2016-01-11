package com.instinctools.awesomestatus.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.instinctools.awesomestatus.R;
import com.instinctools.awesomestatus.interactor.LoginInteractor;
import com.instinctools.awesomestatus.model.LoginCallback;
import com.instinctools.awesomestatus.model.User;
import com.instinctools.awesomestatus.utils.FlowManager;
import com.instinctools.awesomestatus.utils.Utils;
import com.instinctools.awesomestatus.view.LoginView;

import java.lang.ref.WeakReference;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class LoginPresenter implements BasePresenter, LoginCallback {

    @NonNull
    private WeakReference<LoginView> mLoginView;

    @NonNull
    private LoginInteractor mLoginInteractor;

    public LoginPresenter(@NonNull LoginView loginView) {
        this.mLoginView = new WeakReference<>(loginView);
        this.mLoginInteractor = new LoginInteractor(this);
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        mLoginInteractor.onLoginResult(requestCode, resultCode, data);
    }

    public void login(@NonNull Activity activity) {
        LoginView view = getView();
        if (view == null) {
            return;
        }

        if (Utils.isNetworkConnected(activity)) {
            mLoginInteractor.login(activity);
        } else {
            view.showError(activity.getString(R.string.check_connection));
        }
    }

    @Override
    public void viewCreated() {
        // do nothing
    }

    @Override
    public void viewDestroyed() {
        // do nothing
    }

    @Override
    public LoginView getView() {
        return mLoginView.get();
    }

    @Override
    public void onSuccess(User user) {
        LoginView view = getView();
        if (view == null) {
            return;
        }

        FlowManager.getInstance().openPostScreen(view.getContext());
        view.finishView();
    }

    @Override
    public void onError(String errorMessage) {
        LoginView view = getView();
        if (view == null || TextUtils.isEmpty(errorMessage)) {
            return;
        }

        view.showError(errorMessage);
    }
}
