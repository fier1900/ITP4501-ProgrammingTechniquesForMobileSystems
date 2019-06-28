package com.exercise.android.lab04_q3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private String[] columns = {"mid", "name", "password", "age"};
    private TableLayout tbData;
    private RadioGroup rgCase;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = new Result(this, (TableLayout) findViewById(R.id.tbData));
        rgCase = (RadioGroup) findViewById(R.id.rgCase);
        tbData = findViewById(R.id.tbData);

        initialDB();
    }

    public void initialDB() {
        try {
            /* Q3a: create a SQLite Database connection */
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.lab04_q3/MemberDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);

            /* Q3b: drop the table if exits */
            // SQL hints: DROP TABLE IF EXISTS Member;
            db.execSQL("DROP TABLE IF EXISTS Member;");

            /* Q3c: create a table Member */
            // SQL hints: CREATE TABLE Member (mid int PRIMARY KEY, name text, password text, age int);
            db.execSQL("CREATE TABLE Member (mid int PRIMARY KEY, name text, password text, age int);");

            /* Q3d: populate the table Member  */
            // SQL hints: INSERT INTO Member(mid, name, password, age) values (1001, 'Amy Carl', '12345', 16);
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1001, 'Amy Carl', '12345', 16);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1002, 'Helen Leung', '88888', 25);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1003, 'Robert Chan', 'iloveu', 61);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1004, 'Carol Wong', 'peterpan', 33);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1005, 'Carman Wong', 'pooh', 44);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1006, 'John Chan', 'johnchan', 28);");
            db.execSQL("INSERT INTO Member(mid, name, password, age) values (1007, 'Paul Lam', 'apple', 16);");

            Toast.makeText(this, "Table Member is created and initialised.", Toast.LENGTH_SHORT).show();


            /* Q3e: use rawQuery to show all records in TextView*/
            result.fillTable(db.rawQuery("SELECT * FROM Member", null));

            /* close the Database connection */
            db.close();

        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btnActionOnClick(View v) {
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.lab04_q3/MemberDB",
                    null, SQLiteDatabase.OPEN_READONLY);

            Cursor cursor;
            int cnt;

            switch (rgCase.getCheckedRadioButtonId()) {
                case R.id.rbCaseF:
                    /* Q3f */
                    result.fillTable(db.rawQuery("SELECT * FROM Member WHERE LENGTH(password)<6;", null));
                    break;

                case R.id.rbCaseG:
                    /* Q3g */
                    result.fillTable(db.query("Member", columns, "age >= 25", null, null, null, "age asc"));
                    break;

                case R.id.rbCaseH:
                    /* Q3h */
                    result.fillTable(db.rawQuery("SELECT * FROM Member WHERE age>=30;", null));

                    cursor = db.rawQuery("SELECT COUNT(*) FROM Member WHERE age>=30;", null);

                    cursor.moveToNext();
                    String text1 = "Count: " + cursor.getInt(0);

                    cursor.close();
                    Toast.makeText(this, text1, Toast.LENGTH_LONG).show();
                    break;

                case R.id.rbCaseI:
                    /* Q3i */
                    result.fillTable(db.query("Member", columns, "age>=30", null, null, null, null));

                    String[] stringArray = {"COUNT (*)"};
                    cursor = db.query("Member", stringArray, "age>=30", null, null, null, null);

                    cursor.moveToNext();
                    String text2 = "Count: " + cursor.getInt(0);

                    cursor.close();
                    Toast.makeText(this, text2, Toast.LENGTH_LONG).show();
                    break;
            }

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
