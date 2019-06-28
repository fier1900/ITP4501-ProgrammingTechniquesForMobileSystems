package com.exercise.android.lab06_q1;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity {
    /* Called when the activity is first created. */
    private EditText edTextUrl;
    private TextView textViewGreeting;
    DownloadTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTextUrl = findViewById(R.id.edTextUrl);
        textViewGreeting = findViewById(R.id.textViewGreeting);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    public void getGreeting(View view) {
        if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            textViewGreeting.setText("Loading...");
            task = new DownloadTask();
            task.execute(edTextUrl.getText().toString());
        }
    }

    public void changeRed(View view) {
        textViewGreeting.setBackgroundColor(Color.RED);
    }

    public void changeGreen(View view) {
        textViewGreeting.setBackgroundColor(Color.GREEN);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... values) {

            InputStream inputStream = null;
            String result = "";
            URL url = null;

            try {
                url = new URL(values[0]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // Make GET request
                con.setRequestMethod("GET");  // May omit this line since "GET" is the default.
                con.connect();


                // Get response string from inputstream of the connection

                inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;

                Log.d("doInBackground", "get data complete");
                inputStream.close();
                textViewGreeting.setText(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            textViewGreeting.setText(result);
        }
    }

}
