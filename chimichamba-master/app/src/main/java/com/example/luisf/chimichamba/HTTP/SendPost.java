package com.example.luisf.chimichamba.HTTP;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by E0145 on 10/21/2017.
 */


public class SendPost extends AsyncTask<String, Void, String> {

    //renombrar simpre la clase main
    Sender auxClass;

    public SendPost(Sender auxClass){
        this.auxClass = auxClass;
    }

    @Override
    protected String doInBackground(String... strings) {
        String postJsonData = strings[1];
        StringBuffer response = null;
        InputStream iStream = null;
        OutputStream oStream = null;
        HttpURLConnection urlConnection = null;
        String strUrl= "http://13.58.255.254:3000/chimi"+strings[0];

        Log.i("info",strUrl);

        try {
            URL obj = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Setting basic post request
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();
            Log.i("Out","nSending 'POST' request to URL : " + strUrl);
            Log.i("Out","Post Data : " + postJsonData);

            int responseCode = con.getResponseCode();
            Log.i("Out","Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();


        } catch (Exception e) {
            Log.d("Exception", e.toString());
            return "server Error";
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        auxClass.answerFromServer(s);
    }
}

