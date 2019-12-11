package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button btnIdChk, btnOk, btnCancel;
    EditText etName, etId, etPw1, etPw2;
    boolean idChk = false;

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDBHelper = new MyDBHelper(this);

        btnIdChk = (Button) findViewById(R.id.btnIdChk);
        btnIdChk.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etId = (EditText) findViewById(R.id.etId);
        etPw1 = (EditText) findViewById(R.id.etPw1);
        etPw2 = (EditText) findViewById(R.id.etPw2);

        etPw1.setTransformationMethod(new PasswordTransformationMethod());
        etPw2.setTransformationMethod(new PasswordTransformationMethod());
        final ImageView ivPwChk = (ImageView) findViewById(R.id.ivPwChk);
        etPw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPw1.getText().toString().equals((etPw2.getText().toString()))) {
                    ivPwChk.setImageResource(R.drawable.chko);
                } else {
                    ivPwChk.setImageResource(R.drawable.chkx);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etPw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPw1.getText().toString().equals((etPw2.getText().toString()))) {
                    ivPwChk.setImageResource(R.drawable.chko);
                } else {
                    ivPwChk.setImageResource(R.drawable.chkx);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == btnIdChk) {
            if (etId.getText().toString().isEmpty() || etId.getText().toString().equals(" ")) {
                Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

            } else {
                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM USER;", null);
                boolean sameId = false;

                while (cursor.moveToNext()) {
                    if (cursor.getString(1).equals(etId.getText().toString())) {
                        sameId = true;
                    }
                }

                if (sameId) {
                    Toast.makeText(getApplicationContext(), "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    idChk = true;
                    Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                sqlDB.close();
            }

        } else if (view == btnOk) {
            if (!idChk) {
                Toast.makeText(getApplicationContext(), "아이디 중복확인을 눌러주세요", Toast.LENGTH_SHORT).show();
            } else {
                String name = etName.getText().toString();
                String id = etId.getText().toString();
                String pw1 = etPw1.getText().toString();
                String pw2 = etPw2.getText().toString();

                if (name.isEmpty() || id.isEmpty() || pw1.isEmpty() || pw2.isEmpty() || !pw1.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), "회원가입 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO USER VALUES ('" + name + "' , '" + id + "' , '" + pw1 + "');");
                    sqlDB.close();

                    Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            }

        } else if (view == btnCancel) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        return false;
    }
}
