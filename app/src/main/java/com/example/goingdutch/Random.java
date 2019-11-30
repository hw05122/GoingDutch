package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class Random extends AppCompatActivity implements View.OnClickListener {
    Button btnOne;
    TextView tvOne;
    EditText etPerson;
    LinearLayout llOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        btnOne = (Button) findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);

        etPerson = (EditText) findViewById(R.id.etPerson);
        tvOne = (TextView) findViewById(R.id.tvOne);

        llOne = (LinearLayout) findViewById(R.id.llOne);
    }

    public void onClick(View view) {
        if (view == btnOne) {
            llOne.setVisibility(View.INVISIBLE);

            final String[] person = new String[50];
            int cnt = 0;
            boolean chk = true;

            if (etPerson.getText().toString().isEmpty()|| etPerson.getText().toString().equals(" ")) {
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                chk = false;
            }


            StringTokenizer tokenizer = new StringTokenizer(etPerson.getText().toString(), " ");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                for (int i = 0; person[i] != null; i++) {
                    if (person[i].equals(str)) {
                        Toast.makeText(getApplicationContext(), "같은 이름이 존재합니다.", Toast.LENGTH_SHORT).show();
                        chk = false;
                    }
                }
                person[cnt++] = str;
            }

            if (chk) {
                AlertDialog.Builder starB = new AlertDialog.Builder(Random.this);
                starB.setTitle("한 사람을 고르고 있는 중입니다.");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View root = inflater.inflate(R.layout.progress, null);
                starB.setView(root);
                starB.setCancelable(false);
                final AlertDialog adB = starB.create();
                adB.show();

                final int CNT = cnt;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int rand = (int) (Math.random() * CNT);

                        adB.dismiss();
                        tvOne.setText(person[rand]);
                        llOne.setVisibility(View.VISIBLE);
                    }
                }, 3000);

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
            finish();
        }

        return false;
    }
}
