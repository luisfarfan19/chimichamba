package com.example.luisf.chimichamba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    /*
    * Pantalla principal:
    * Incio de sesión con Facebook
    *
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //LoginButton loginButton;
        final TextView tvInfo;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button bLoginNOFb = (Button) findViewById(R.id.bLogIn);
        bLoginNOFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Busco.class);
                startActivity(intent);
            }
        });
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }*/
        /*callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                tvInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                // App code
                tvInfo.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                tvInfo.setText("Login attempt failed.");
            }
        });*/
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/
}
