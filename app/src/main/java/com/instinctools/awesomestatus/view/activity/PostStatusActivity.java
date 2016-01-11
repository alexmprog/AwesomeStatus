package com.instinctools.awesomestatus.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.instinctools.awesomestatus.R;
import com.instinctools.awesomestatus.presenter.PostStatusPresenter;
import com.instinctools.awesomestatus.view.PostStatusView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexmprog on 11.01.2016.
 */
public class PostStatusActivity extends AppCompatActivity implements PostStatusView {

    @Bind(R.id.edit_text_post)
    protected EditText mPostText;

    @Bind(R.id.text_user_name)
    protected TextView mUserNameText;

    @Bind(R.id.button_update)
    protected Button mUpdateButton;

    @NonNull
    private PostStatusPresenter mPostStatusPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);
        ButterKnife.bind(this);

        mPostStatusPresenter = new PostStatusPresenter(this);
    }

    @OnClick(R.id.button_disconnect)
    public void onDisconnectButtonClicked() {
        mPostStatusPresenter.disconnectFromFacebook();
    }

    @OnClick(R.id.button_update)
    public void onUpdateStatusButtonClicked() {
        String statusText = mPostText.getText().toString();
        if (TextUtils.isEmpty(statusText)) {
            Toast.makeText(this, R.string.empty_post_message, Toast.LENGTH_SHORT).show();
            return;
        }
        mPostStatusPresenter.updateStatus(this, statusText);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPostStatusPresenter.viewCreated();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPostStatusPresenter.onLoginResult(requestCode, resultCode, data);
    }

    @Override
    public void setUsername(@NonNull String userName) {
        mUserNameText.setText(userName);
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStatusEditorEnabled(boolean isEnabled) {
        mPostText.setEnabled(isEnabled);
        mUpdateButton.setEnabled(isEnabled);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishView() {
        finish();
    }
}
