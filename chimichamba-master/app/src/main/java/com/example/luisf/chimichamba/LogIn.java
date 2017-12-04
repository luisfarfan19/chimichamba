package com.example.luisf.chimichamba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.Arrays;

public class LogIn extends AppCompatActivity {
    /*
    * Pantalla principal:
    * Incio de sesión con Facebook
    *
    * */
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //LoginButton loginButton;
        final TextView tvInfo;
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_log_in);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "user_friends", "public_profile"));

        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            //tvNombre.setText("Bienvenido " + Profile.getCurrentProfile().getName());
            String userID = Profile.getCurrentProfile().getId().toString();
            String imageURL = "http://graph.facebook.com/" + userID + "/picture?type=large";
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me?fields=picture.height(300)",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                String url = response.getJSONObject().getJSONObject("picture").getJSONObject("data").getString("url");
                                Intent intent = new Intent(LogIn.this, Busco.class);
                                intent.putExtra("NombreUsuario", Profile.getCurrentProfile().getName().toString());
                                intent.putExtra("IDUsuario", Profile.getCurrentProfile().getId().toString());
                                intent.putExtra("FotoUrlUsuario", url);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();
        }

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //tvInfo.setText("Bienvenido " + Profile.getCurrentProfile().getName());
                Log.d("onSuccess", AccessToken.getCurrentAccessToken().getToken());
//                Log.d("onSuccess", Profile.getCurrentProfile().getFirstName());

                String userID = Profile.getCurrentProfile().getId().toString();
                String imageURL = "http://graph.facebook.com/" + userID + "/picture?type=large";
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me?fields=picture.height(300)",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    String url = response.getJSONObject().getJSONObject("picture").getJSONObject("data").getString("url");
                                    Intent intent = new Intent(LogIn.this, Busco.class);
                                    intent.putExtra("NombreUsuario", Profile.getCurrentProfile().getName().toString());
                                    intent.putExtra("IDUsuario", Profile.getCurrentProfile().getId().toString());
                                    intent.putExtra("FotoUrlUsuario", url);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();

                Intent intent = new Intent(LogIn.this, Busco.class);
                intent.putExtra("NombreUsuario", Profile.getCurrentProfile().getName().toString());
                intent.putExtra("IDUsuario", Profile.getCurrentProfile().getId().toString());
                intent.putExtra("FotoUrlUsuario", imageURL);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
