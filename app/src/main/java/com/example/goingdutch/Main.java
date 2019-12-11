package com.example.goingdutch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main extends AppCompatActivity implements View.OnClickListener {
    ImageButton ibAdd, ibPercent, ibRandom, ibSetting;
    ListView lvContent;

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this);

        ibAdd = (ImageButton) findViewById(R.id.ibAdd);
        ibAdd.setOnClickListener(this);

        ibPercent = (ImageButton) findViewById(R.id.ibPercent);
        ibPercent.setOnClickListener(this);

        ibRandom = (ImageButton) findViewById(R.id.ibRandom);
        ibRandom.setOnClickListener(this);

        ibSetting = (ImageButton) findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(this);

        ArrayList<String> al = new ArrayList<String>();

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM CONTENT WHERE userId = '" + Login.etId.getText().toString() + "';", null);
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(Login.etId.getText().toString())) {
                al.add(cursor.getString(1) + " \n" + cursor.getString(3));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, al);
        cursor.close();
        sqlDB.close();

        lvContent = (ListView) findViewById(R.id.lvContent);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder digProfile = new AlertDialog.Builder(Main.this);
                digProfile.setTitle(((TextView) view).getText().toString());
                digProfile.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg, title = "";
                        msg = ((TextView) view).getText().toString();

                        StringTokenizer tokenizer = new StringTokenizer(msg, " ");
                        while (tokenizer.hasMoreTokens()) {
                            title = tokenizer.nextToken();
                            break;
                        }

                        sqlDB = myDBHelper.getWritableDatabase();
                        sqlDB.execSQL("DELETE FROM CONTENT WHERE userId = '" + Login.etId.getText().toString() + "' AND title = '" + title + "';");
                        sqlDB.close();

                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                        finish();
                    }
                });
                digProfile.setPositiveButton("공유", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg, title = "", strResult = "";
                        msg = ((TextView) view).getText().toString();

                        StringTokenizer tokenizer = new StringTokenizer(msg, " ");
                        while (tokenizer.hasMoreTokens()) {
                            title = tokenizer.nextToken();
                            break;
                        }
                        int index = msg.indexOf("[");
                        String str = msg.substring(index, msg.length());

                        strResult += "\n<" + title + ">\n" + str;

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "N분의1");
                        intent.putExtra(Intent.EXTRA_TEXT, strResult);

                        Intent chooser = Intent.createChooser(intent, "공유");
                        startActivity(chooser);
                    }
                });

                digProfile.show();
            }
        });
        lvContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Long", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        lvContent.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


    }

    public void onClick(View view) {
        if (view == ibAdd) {
            Intent intent = new Intent(getApplicationContext(), Add.class);
            startActivity(intent);
            finish();
        } else if (view == ibPercent) {
            Intent intent = new Intent(getApplicationContext(), Percent.class);
            startActivity(intent);
            finish();
        } else if (view == ibRandom) {
            Intent intent = new Intent(getApplicationContext(), Random.class);
            startActivity(intent);
            finish();
        } else if (view == ibSetting) {
            Intent intent = new Intent(getApplicationContext(), Setting.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.function, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemLogout) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(getApplicationContext(), "상단에 있는 로그아웃 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();

        }

        return false;
    }

    public class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context) {
            super(context, "GoingDutch2", null, 1);//디비생성
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE CONTENT (userId CHAR(20), title CHAR(20), attendance CHAR(30), content CHAR(100));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CONTENT");//존재하면 테이블 삭제
            onCreate(db);
        }
    }
}
