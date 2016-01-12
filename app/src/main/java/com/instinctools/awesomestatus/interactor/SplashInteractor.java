package com.instinctools.awesomestatus.interactor;

import com.instinctools.awesomestatus.utils.FacebookManager;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class SplashInteractor {

    public boolean isUserLogged() {
        return FacebookManager.getInstance().isUserLogged();
    }
}
