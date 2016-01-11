package com.instinctools.awesomestatus.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.instinctools.awesomestatus.interactor.SplashInteractor;
import com.instinctools.awesomestatus.utils.FlowManager;
import com.instinctools.awesomestatus.view.BaseView;
import com.instinctools.awesomestatus.view.SplashView;

import java.lang.ref.WeakReference;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class SplashPresenter implements BasePresenter {

    private static final long REDIRECT_DELAY = 1500;

    @NonNull
    private Handler mHandler;

    @NonNull
    private WeakReference<SplashView> mSplashView;

    @NonNull
    protected SplashInteractor mSplashInteractor;

    public SplashPresenter(@NonNull SplashView splashView) {
        this.mSplashView = new WeakReference<>(splashView);
        this.mSplashInteractor = new SplashInteractor();
        this.mHandler = new Handler();
    }

    @Override
    public void viewCreated() {
        mHandler.postDelayed(new RedirectRunnable(this), REDIRECT_DELAY);
    }

    @Override
    public void viewDestroyed() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public BaseView getView() {
        return mSplashView.get();
    }

    private static final class RedirectRunnable implements Runnable {

        private WeakReference<SplashPresenter> mSplashPresenter;

        private RedirectRunnable(@NonNull SplashPresenter splashPresenter) {
            this.mSplashPresenter = new WeakReference<>(splashPresenter);
        }

        @Override
        public void run() {
            SplashPresenter presenter = mSplashPresenter.get();
            if (presenter == null) {
                return;
            }

            BaseView view = presenter.getView();
            if (view == null) {
                return;
            }

            Context context = view.getContext();

            boolean isUserLogged = presenter.mSplashInteractor.isUserLogged();
            FlowManager flowManager = FlowManager.getInstance();
            if (isUserLogged) {
                flowManager.openPostScreen(context);
            } else {
                flowManager.openLoginScreen(context);
            }
            view.finishView();

        }
    }
}
