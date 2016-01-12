package com.instinctools.awesomestatus.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.instinctools.awesomestatus.R;
import com.instinctools.awesomestatus.interactor.PostStatusInteractor;
import com.instinctools.awesomestatus.model.PostStatusCallback;
import com.instinctools.awesomestatus.model.User;
import com.instinctools.awesomestatus.model.UserCallback;
import com.instinctools.awesomestatus.utils.FlowManager;
import com.instinctools.awesomestatus.utils.Utils;
import com.instinctools.awesomestatus.view.PostStatusView;

import java.lang.ref.WeakReference;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class PostStatusPresenter implements BasePresenter, PostStatusCallback, UserCallback {

    @NonNull
    private WeakReference<PostStatusView> mPostStatusView;

    @NonNull
    private PostStatusInteractor mPostStatusInteractor;

    public PostStatusPresenter(@NonNull PostStatusView loginView) {
        this.mPostStatusView = new WeakReference<>(loginView);
        this.mPostStatusInteractor = new PostStatusInteractor(this, this);
    }

    public void disconnectFromFacebook() {
        mPostStatusInteractor.logout();

        PostStatusView view = getView();
        if (view == null) {
            return;
        }

        FlowManager.getInstance().openSplashScreen(view.getContext());
        view.finishView();
    }

    public void updateStatus(@NonNull Activity activity, @NonNull String message) {
        PostStatusView view = getView();
        if (view == null) {
            return;
        }

        Context context = view.getContext();
        if (Utils.isNetworkConnected(context)) {
            view.setStatusEditorEnabled(false);
            mPostStatusInteractor.postStatus(activity, message);
        } else {
            view.showMessage(context.getString(R.string.check_connection));
        }
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        mPostStatusInteractor.onLoginResult(requestCode, resultCode, data);
    }

    @Override
    public void viewCreated() {
        mPostStatusInteractor.loadUser();
    }

    @Override
    public void viewDestroyed() {
        mPostStatusInteractor.clearResources();
    }

    @Override
    public PostStatusView getView() {
        return mPostStatusView.get();
    }

    @Override
    public void onSuccess() {
        PostStatusView view = getView();
        if (view == null) {
            return;
        }

        view.setStatusEditorEnabled(true);
        view.showMessage(view.getContext().getString(R.string.status_updated));
    }

    @Override
    public void onError(String message) {
        PostStatusView view = getView();
        if (view == null) {
            return;
        }

        view.setStatusEditorEnabled(true);
        if (!TextUtils.isEmpty(message)) {
            view.showMessage(message);
        }
    }

    @Override
    public void onSuccess(User user) {
        PostStatusView view = getView();
        if (view == null) {
            return;
        }

        view.setUsername(user.getName());
        view.setStatusEditorEnabled(true);
    }
}
