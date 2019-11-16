package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity implements View.OnClickListener {
    TextView tvResult;
    Button btnOk, btnBack, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView) findViewById(R.id.tvResult);
        if (Add.content.size() == 0) {

        } else {
            result();
        }

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
    }

    public void result() {
        int size = Add.content.size();
        String str = "";
        for (int i = 0; i < size; i++) {
            str += Add.content.get(i) + "\n";
        }
        tvResult.setText(str);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), Add.class);
            startActivity(intent);
            finish();
        }

        return false;
    }

    public void onClick(View view) {
        if (view == btnOk) {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
            finish();
        } else if (view == btnBack) {
            Intent intent = new Intent(getApplicationContext(), Add.class);
            startActivity(intent);
            finish();
        } else if (view == btnShare) {
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
