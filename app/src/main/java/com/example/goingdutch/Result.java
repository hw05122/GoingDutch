package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

public class Result extends AppCompatActivity implements View.OnClickListener {
    TextView tvResult;
    Button btnOk, btnBack, btnShare;
    String strResult = "";

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
        String[] sendPerson = new String[size];
        int[] sendMoney = new int[size];
        String[][] attendPerson = new String[size][30];
        int[] attendCnt = new int[size];

        for (int i = 0; i < size; i++) {
            StringTokenizer tokenizer = new StringTokenizer(Add.content.get(i), " ");

            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();

                if (str.substring(0, 2).equals("결제")) {
                    sendPerson[i] = str.substring(4, str.length());
                } else if (str.substring(0, 2).equals("금액")) {
                    sendMoney[i] = Integer.parseInt(str.substring(3, str.length()));
                }
            }

            int index = Add.content.get(i).indexOf("참여자:");
            String attendString = Add.content.get(i).substring(index + 4, Add.content.get(i).length());

            int k = 0;
            StringTokenizer tokenizer2 = new StringTokenizer(attendString, " ");
            while (tokenizer2.hasMoreTokens()) {
                String str = tokenizer2.nextToken();

                attendPerson[i][k] = str;
                k++;
            }
            attendCnt[i] = k;
        }

        for (int i = 0; i < size; i++) {
            Log.d("young", sendPerson[i] + " " + String.valueOf(sendMoney[i]) + " " + String.valueOf(attendCnt[i]));
        }

        boolean sendPersonAttend = false;
        for (int i = 0; i < size; i++) {
            int n1 = sendMoney[i] / attendCnt[i];

            if (Add.moneySelect == 0) {//10원 올림
                Log.d("young", "10원단위올림" + String.valueOf((n1 + 9) / 10 * 10));
                n1 = (n1 + 9) / 10 * 10;
            } else if (Add.moneySelect == 1) {//100원 올림
                Log.d("young", "100원단위올림" + String.valueOf((n1 + 90) / 100 * 100));
                n1 = (n1 + 90) / 100 * 100;
            } else if (Add.moneySelect == 2) {//10원 내림
                Log.d("young", "10원단위내림" + String.valueOf((n1 - n1 % 10)));
                n1 = n1 - n1 % 10;
            } else if (Add.moneySelect == 3) {//100원 내림
                Log.d("young", "100원단위내림" + String.valueOf((n1 - n1 % 100)));
                n1 = n1 - n1 % 100;
            }

            for (int q = 0; q < attendCnt[i]; q++) {
                if (sendPerson[i].equals(attendPerson[i][q])) {

                } else {
                    strResult += attendPerson[i][q] + "->" + sendPerson[i] + ":" + n1 + "원\n";
                }
            }

            strResult += "\n";
        }
        tvResult.setText(strResult);
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
            intent.putExtra(Intent.EXTRA_SUBJECT, "N분의1");
            intent.putExtra(Intent.EXTRA_TEXT, strResult);

            Intent chooser = Intent.createChooser(intent, "공유");
            startActivity(chooser);
        }
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
}
