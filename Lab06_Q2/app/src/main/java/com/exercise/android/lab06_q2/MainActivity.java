package com.exercise.android.lab06_q2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    Button buttonGo;
    EditText editTextUrl;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGo = findViewById(R.id.buttonGo);
        editTextUrl = findViewById(R.id.editTextUrl);
        webView = findViewById(R.id.webView);

        // The following code is to force WebView to load links
        // To force the WebView to load links ...
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            // define an inner class to handle onClick events
            // refer to lecture notes WebViewExample.java
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });

        editTextUrl.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    openBrowser();
                    return true;
                }
                return false;
            }
        });
    }

    public void openBrowser () {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(editTextUrl.getText().toString());
    }
}