package com.instinctools.awesomestatus.view;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by alexmprog on 11.01.2016.
 */
public interface BaseView {

    @NonNull
    Context getContext();

    void finishView();
}
