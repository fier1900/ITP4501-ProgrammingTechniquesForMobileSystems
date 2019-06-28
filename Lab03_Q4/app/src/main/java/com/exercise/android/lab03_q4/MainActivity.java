package com.exercise.android.lab03_q4;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ListActivity /*** TO BE COMPLETED ***/
{
    private TextView selection;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** TO BE COMPLETED ***/
        selection = findViewById(R.id.selection);
        items = getResources().getStringArray(R.array.planets_array);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        /*** TO BE COMPLETED ***/
        String result = "Planet - " + items[position];
        selection.setText(result);
        parent.getSelectedItem();
    }
}
