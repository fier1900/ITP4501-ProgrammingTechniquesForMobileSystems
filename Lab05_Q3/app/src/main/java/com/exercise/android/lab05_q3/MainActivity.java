package com.exercise.android.lab05_q3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class MainActivity extends Activity
{
    public static final int aboutBtnID = Menu.FIRST;
    public static final int exitBtnID = Menu.FIRST + 1;
    public static final int helpBtnID = Menu.FIRST + 2;
    public static final int fileBtnID = Menu.FIRST + 3;
    public static final int searchBtnID= Menu.FIRST + 4;
    public static final int repairBtnID = Menu.FIRST + 5;
    public static final int editBtnID = Menu.FIRST + 6;
    public static final int openBtnID = Menu.FIRST + 7;
    public static final int saveBtnID = Menu.FIRST + 8;
    public static final int delBtnID = Menu.FIRST + 9;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, aboutBtnID, 0, "About").setIcon(R.drawable.about);
        menu.add(0, exitBtnID, 0, "Exit").setIcon(R.drawable.exit).getIcon();
        menu.add(0, helpBtnID, 0, "Help").setIcon(R.drawable.help);

        SubMenu filesubmenu = menu.addSubMenu("File").setIcon(R.drawable.file);
        filesubmenu.setHeaderIcon(R.drawable.file);
        filesubmenu.add(0, openBtnID, 0, "Open").setIcon(R.drawable.search);

        /* 	COMPLETE THIS PART */

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch( item.getItemId() )
        {
            case aboutBtnID:
                openDialog();
                break;
            case exitBtnID:
                finish();
                break;
            case delBtnID:
                finish();
                break;
        }
        return true;
    }
    public void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This is an Options Menu");
        builder.setTitle("About");
        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener()   {
            @Override
            public void onClick(DialogInterface dialog, int which)    {
            }
        }).show();
    }
}