package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Result extends AppCompatActivity implements View.OnClickListener{
    Button btnOk, btnCancel, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnShare = (Button)findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(),Add.class);
            startActivity(intent);
            finish();
        }

        return false;
    }

    public void onClick(View view) {
        if(view == btnOk){
            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            finish();
        }
        else if(view == btnCancel){
            Intent intent = new Intent(getApplicationContext(),Add.class);
            startActivity(intent);
            finish();
        }
        else if(view == btnShare){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "제목");
            intent.putExtra(Intent.EXTRA_TEXT, "내용");

            Intent chooser = Intent.createChooser(intent, "공유");
            startActivity(chooser);
        }
    }
}
