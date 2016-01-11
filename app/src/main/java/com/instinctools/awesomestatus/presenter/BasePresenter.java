package com.instinctools.awesomestatus.presenter;

import com.instinctools.awesomestatus.view.BaseView;

/**
 * Created by alexmprog on 11.01.2016.
 */
public interface BasePresenter {

    void viewCreated();

    void viewDestroyed();

    BaseView getView();
}
