package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button btnLogin, btnRegister, btnFreeLogin;
    EditText etId, etPassword;
    double initTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText)findViewById(R.id.etId);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnFreeLogin = (Button)findViewById(R.id.btnFreeLogin);
        btnFreeLogin.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(view == btnLogin){

        }
        else if(view == btnRegister){

        }else if(view == btnFreeLogin){
            Intent intent = new Intent(getApplicationContext(), Main.class);
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
}
