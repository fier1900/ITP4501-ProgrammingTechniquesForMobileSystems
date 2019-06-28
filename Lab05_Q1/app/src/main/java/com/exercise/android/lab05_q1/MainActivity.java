package com.exercise.android.lab05_q1;

import android.net.Uri;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    String[] items = {"Send SMS", "Search on Google", "Wiktionary", "Wikipedia ", "Show Map", "Show Street View", "Show Contact"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                // missing codes
                int phoneNumber = 65833001;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", "my message");
                startActivity(intent);
                break;
            case 1:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=IVE"));
                startActivity(intent);
                break;
            case 2:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wiktionary.org/wiki/mobile"));
                startActivity(intent);
                break;
            case 3:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.m.wikipedia.org/wiki/Android"));
                startActivity(intent);
                break;
            case 4:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:22.3199,114.036"));
                startActivity(intent);
                break;
            case 5:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=22.3192,114.1768"));
                startActivity(intent);
                break;
            case 6:
                // missing codes
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                startActivity(intent);
                break;
        }
    }
}
