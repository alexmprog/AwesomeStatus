package com.instinctools.awesomestatus.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.instinctools.awesomestatus.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class FacebookManager {

    private static final String FACEBOOK_FEED_ENDPOINT = "/me/feed";

    private static final String FACEBOOK_STATUS_MESSAGE_FIELD = "message";

    private static final String PUBLISH_ACTION = "publish_actions";

    public static final List<String> READ_PERMISSIONS = Arrays.asList(
            "public_profile");

    public static final List<String> PUBLISH_PERMISSIONS = Arrays.asList(PUBLISH_ACTION);

    private static volatile FacebookManager sInstance;

    public static FacebookManager getInstance() {
        if (sInstance == null) {
            synchronized (FacebookManager.class) {
                if (sInstance == null) {
                    sInstance = new FacebookManager();
                }
            }
        }
        return sInstance;
    }

    private User mCurrentUser;

    private FacebookManager() {
    }

    public boolean isUserLogged() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    public boolean checkPublishPermissions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains(PUBLISH_ACTION);
    }

    public void postStatus(@NonNull String message, @NonNull GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString(FACEBOOK_STATUS_MESSAGE_FIELD, message);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                FACEBOOK_FEED_ENDPOINT,
                params,
                HttpMethod.POST,
                callback
        ).executeAsync();
    }

    public void setUser() {
        Profile currentProfile = Profile.getCurrentProfile();
        if (currentProfile != null) {
            mCurrentUser = new User(currentProfile.getName());
        }
    }

    public User getUser() {
        if (mCurrentUser == null) {
            setUser();
        }
        return mCurrentUser;
    }

}
