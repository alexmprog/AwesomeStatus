package com.instinctools.awesomestatus.model;

/**
 * Created by alexmprog on 11.01.2016.
 */
public interface PostStatusCallback {

    void onSuccess();

    void onError(String message);
}
