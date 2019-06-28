package com.exercise.android.lab06_q3;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView textViewUrl;
    TextView textViewResult;
    ListView listViewCode;
    Button buttonGo;
    String[] resultArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUrl = findViewById(R.id.textViewUrl);
        textViewResult = findViewById(R.id.textViewResult);
        listViewCode = findViewById(R.id.listViewCode);
        buttonGo = findViewById(R.id.buttonGo);
        resultArray = new String[9];

        buttonGo.setOnClickListener(this);
        listViewCode.setOnItemClickListener(this);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }


    @Override
    public void onClick(View v) {
        InputStream inputStream = null;
        String result = "";
        URL url = null;

        try {
            url = new URL(textViewUrl.getText().toString());

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

            inputStream.close();
            //textViewResult.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("campuses");

            int i = 0;
            while(i < 9){
                JSONObject jObj = jsonArray.getJSONObject(i);
                resultArray[i] = jObj.getString("code");
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,resultArray);
        listViewCode.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        textViewResult.setText(resultArray[position] + " selected.");
    }
}
