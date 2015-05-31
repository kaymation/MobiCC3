package com.mobiquity.kevinq.mobicc3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;


public class LoginActivity extends ActionBarActivity implements OnClickListener {

    private final static String DROPBOX_NAME = "dropbox_prefs";
    private final static String ACCESS_KEY = "cz18cxra5gt2e3g";
    private final static String ACCESS_SECRET = "chasvfmu23eszmk";
    private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
    private LinearLayout container;
    private DropboxAPI dropboxApi;
    private boolean isUserLoggedIn;
    private Button loginButton;
    private Button uploadFileBtn;
    private Button listFileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);
        uploadFileBtn = (Button) findViewById(R.id.uploadFileBtn);
        uploadFileBtn.setOnClickListener(this);
        listFileBtn = (Button) findViewById(R.id.listFilesBtn);
        listFileBtn.setOnClickListener(this);
        container = (LinearLayout) findViewById(R.id.container_files);

        loggedIn(false);

        AppKeyPair appKeyPair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
        AndroidAuthSession session;

        SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
        String key = prefs.getString(ACCESS_KEY, null);
        String secret = prefs.getString(ACCESS_SECRET, null);

        if (key != null && secret != null) {
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, token);
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
        }

        dropboxApi = new DropboxAPI(session);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AndroidAuthSession session = (AndroidAuthSession) dropboxApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();

                TokenPair tokens = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
                Editor editor = prefs.edit();
                editor.putString(ACCESS_KEY, tokens.key);
                editor.putString(ACCESS_SECRET, tokens.secret);
                editor.commit();

                loggedIn(true);
            } catch (IllegalStateException e) {
                Toast.makeText(this, "Error during Dropbox auth", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (isUserLoggedIn) {
                    dropboxApi.getSession().unlink();
                    loggedIn(false);
                } else {
                    ((AndroidAuthSession) dropboxApi.getSession())
                            .startAuthentication(LoginActivity.this);
                }
                break;
            case R.id.uploadFileBtn:
                Intent photoIntent = new Intent(this, UploadPhoto.class);
                startActivity(photoIntent);
                break;

            case R.id.recordSoundBtn:
                Intent soundIntent = new Intent(this, UploadAudio.class);
                startActivity(soundIntent);
                break;

            case R.id.listFilesBtn:
                Intent filesIntent = new Intent(this, ListFiles.class);
                startActivity(filesIntent);
                break;
            default:
                break;
        }
    }

    public void loggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
        uploadFileBtn.setEnabled(userLoggedIn);
        uploadFileBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
        listFileBtn.setEnabled(userLoggedIn);
        listFileBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
        loginButton.setText(userLoggedIn ? "Logout" : "Log in");
    }
}
