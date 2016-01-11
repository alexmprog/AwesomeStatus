package com.instinctools.awesomestatus.model;

import android.support.annotation.NonNull;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class User {

    @NonNull
    private String mName;

    public User(@NonNull String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
