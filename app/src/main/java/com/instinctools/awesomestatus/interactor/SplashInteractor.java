package com.instinctools.awesomestatus.interactor;

import com.facebook.AccessToken;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class SplashInteractor {

    public boolean isUserLogged() {
        return AccessToken.getCurrentAccessToken() != null;
    }
}
