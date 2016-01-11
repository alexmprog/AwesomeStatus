package com.instinctools.awesomestatus.view;

import android.support.annotation.NonNull;

/**
 * Created by alexmprog on 11.01.2016.
 */
public interface PostStatusView extends BaseView {

    void setUsername(@NonNull String userName);

    void showMessage(@NonNull String message);

    void setStatusEditorEnabled(boolean isEnabled);
}
