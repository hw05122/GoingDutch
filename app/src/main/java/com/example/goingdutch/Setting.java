package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    Button btnLogout, btnOut;

    MyDBHelper myDBHelper;
    MyDBHelper2 myDBHelper2;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        myDBHelper = new MyDBHelper(this);
        myDBHelper2 = new MyDBHelper2(this);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        btnOut = (Button) findViewById(R.id.btnOut);
        btnOut.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == btnLogout) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else if (view == btnOut) {
            if(Login.loginChk){
                AlertDialog.Builder digEnd = new AlertDialog.Builder(Setting.this);
                digEnd.setMessage("탈퇴를 하시겠습니까?");
                digEnd.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder digPw = new AlertDialog.Builder(Setting.this);
                        digPw.setTitle("비밀번호를 입력하세요");
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View root = inflater.inflate(R.layout.digpw, null);
                        digPw.setView(root);

                        final EditText etPw = (EditText) root.findViewById(R.id.etPw);
                        etPw.setTransformationMethod(new PasswordTransformationMethod());

                        digPw.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = Login.etId.getText().toString();
                                String pw = Login.etPassword.getText().toString();

                                if (etPw.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                                } else if (etPw.getText().toString().equals(pw)) {
                                    sqlDB = myDBHelper.getWritableDatabase();
                                    sqlDB.execSQL("DELETE FROM USER WHERE userId = '" + id + "';");
                                    sqlDB.close();

                                    sqlDB = myDBHelper2.getWritableDatabase();
                                    sqlDB.execSQL("DELETE FROM CONTENT WHERE userId = '" + id + "';");
                                    sqlDB.close();

                                    Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        digPw.setPositiveButton("취소", null);
                        digPw.show();
                    }
                });
                digEnd.setPositiveButton("아니요", null);
                digEnd.show();
            }else{
                Toast.makeText(getApplicationContext(),"회원만 사용가능합니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(Login.loginChk){
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(getApplicationContext(), MainFree.class);
                startActivity(intent);
                finish();
            }
        }

        return false;
    }

    public class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context) {
            super(context, "GoingDutch", null, 1);//디비생성
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE USER (userName CHAR(20), userId CHAR(20) PRIMARY KEY, userPassword CHAR(20));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS USER");//존재하면 테이블 삭제
            onCreate(db);
        }
    }

    public class MyDBHelper2 extends SQLiteOpenHelper {
        public MyDBHelper2(Context context) {
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
