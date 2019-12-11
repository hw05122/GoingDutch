package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button btnLogin, btnRegister, btnFreeLogin;
    public static EditText etId, etPassword;
    double initTime;
    public static boolean loginChk;

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDBHelper = new MyDBHelper(this);

        etId = (EditText)findViewById(R.id.etId);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnFreeLogin = (Button)findViewById(R.id.btnFreeLogin);
        btnFreeLogin.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(view == btnLogin){
            sqlDB = myDBHelper.getReadableDatabase();
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT * FROM USER;", null);

            loginChk = false;

            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(etId.getText().toString()) && cursor.getString(2).equals(etPassword.getText().toString())) {
                    loginChk = true;
                }
            }

            if(loginChk){
                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

            }

        }
        else if(view == btnRegister){
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        }else if(view == btnFreeLogin){
            loginChk = false;

            Toast.makeText(getApplicationContext(),"비회원로그인을 하였습니다.",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainFree.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(getApplicationContext(),"종료하려면 한 번 더 눌러주세요",Toast.LENGTH_SHORT).show();
                initTime = System.currentTimeMillis();
                return true;
            }
            else{
                finish();
                return true;
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
}
