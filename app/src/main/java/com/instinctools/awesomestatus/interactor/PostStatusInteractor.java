package com.instinctools.awesomestatus.interactor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.instinctools.awesomestatus.model.PostStatusCallback;
import com.instinctools.awesomestatus.model.User;
import com.instinctools.awesomestatus.model.UserCallback;
import com.instinctools.awesomestatus.utils.FacebookManager;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class PostStatusInteractor implements FacebookCallback<LoginResult> {

    private static final String TAG = PostStatusInteractor.class.getSimpleName();

    @Nullable
    private String mLastStatusMessage;

    @NonNull
    private CallbackManager mCallbackManager;

    @NonNull
    private PostStatusCallback mPostStatusCallback;

    @NonNull
    private UserCallback mUserCallback;

    private ProfileTracker mProfileTracker;

    public PostStatusInteractor(@NonNull PostStatusCallback postStatusCallback, @NonNull UserCallback userCallback) {
        mPostStatusCallback = postStatusCallback;
        mUserCallback = userCallback;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loadUser() {
        User user = FacebookManager.getInstance().getUser();
        if (user == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                    mProfileTracker.stopTracking();
                    Profile.setCurrentProfile(profile2);
                    mUserCallback.onSuccess(FacebookManager.getInstance().getUser());
                }
            };
            mProfileTracker.startTracking();
        } else {
            mUserCallback.onSuccess(user);
        }
    }

    public void clearResources() {
        if (mProfileTracker != null) {
            mProfileTracker.stopTracking();
        }
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }

    public void postStatus(@NonNull Activity activity, @NonNull String message) {
        mLastStatusMessage = message;
        if (FacebookManager.getInstance().checkPublishPermissions()) {
            // post status
            postStatus(message);
        } else {
            // need request publish permission
            LoginManager.getInstance().logInWithPublishPermissions(activity,
                    FacebookManager.PUBLISH_PERMISSIONS);
        }
    }

    private void postStatus(@NonNull String message) {
        FacebookManager.getInstance().postStatus(message, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error = response.getError();
                if (error == null) {
                    mPostStatusCallback.onSuccess();
                } else {
                    mPostStatusCallback.onError(error.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if (!TextUtils.isEmpty(mLastStatusMessage)) {
            postStatus(mLastStatusMessage);
        }
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "Post status canceled.");
        mPostStatusCallback.onError(null);
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG, "Post status failed.", error);
        mPostStatusCallback.onError(error.getMessage());
    }
}
