package com.instinctools.awesomestatus.model;

/**
 * Created by alexmprog on 11.01.2016.
 */
public interface LoginCallback {

    void onSuccess(User user);

    void onError(String errorMessage);
}
