package com.example.goingdutch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class Percent extends AppCompatActivity implements View.OnClickListener {
    Button btnOk;
    EditText etPerson, et1, et2, et3, et4, et5, etMoney;
    ImageButton ibPerson;
    TextView tv1, tv2, tv3, tv4, tv5;
    String[] person;
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);

        etPerson = (EditText) findViewById(R.id.etPerson);

        ibPerson = (ImageButton) findViewById(R.id.ibPerson);
        ibPerson.setOnClickListener(this);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        etMoney = (EditText) findViewById(R.id.etMoney);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
    }

    public void onClick(View view) {
        if (view == ibPerson) {
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);

            person = new String[50];
            cnt = 0;
            boolean chk = true;

            if (etPerson.getText().toString().isEmpty() || etPerson.getText().toString().equals(" ")) {
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                chk = false;
                et1.setVisibility(View.GONE);
                et2.setVisibility(View.GONE);
                et3.setVisibility(View.GONE);
                et4.setVisibility(View.GONE);
                et5.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
            } else if (etMoney.getText().toString().isEmpty() || etMoney.getText().toString().equals(" ")) {
                Toast.makeText(getApplicationContext(), "금액을 입력해주세요", Toast.LENGTH_SHORT).show();
                et1.setVisibility(View.GONE);
                et2.setVisibility(View.GONE);
                et3.setVisibility(View.GONE);
                et4.setVisibility(View.GONE);
                et5.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
            } else {
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
                    if (cnt > 5) {
                        Toast.makeText(getApplicationContext(), "수용인원은 1~5명입니다.", Toast.LENGTH_SHORT).show();
                        et1.setVisibility(View.GONE);
                        et2.setVisibility(View.GONE);
                        et3.setVisibility(View.GONE);
                        et4.setVisibility(View.GONE);
                        et5.setVisibility(View.GONE);
                        btnOk.setVisibility(View.GONE);
                    } else {
                        btnOk.setVisibility(View.VISIBLE);
                        switch (cnt) {
                            case 1:
                                et1.setVisibility(View.VISIBLE);
                                et2.setVisibility(View.GONE);
                                et3.setVisibility(View.GONE);
                                et4.setVisibility(View.GONE);
                                et5.setVisibility(View.GONE);
                                break;
                            case 2:
                                et1.setVisibility(View.VISIBLE);
                                et2.setVisibility(View.VISIBLE);
                                et3.setVisibility(View.GONE);
                                et4.setVisibility(View.GONE);
                                et5.setVisibility(View.GONE);
                                break;
                            case 3:
                                et1.setVisibility(View.VISIBLE);
                                et2.setVisibility(View.VISIBLE);
                                et3.setVisibility(View.VISIBLE);
                                et4.setVisibility(View.GONE);
                                et5.setVisibility(View.GONE);
                                break;
                            case 4:
                                et1.setVisibility(View.VISIBLE);
                                et2.setVisibility(View.VISIBLE);
                                et3.setVisibility(View.VISIBLE);
                                et4.setVisibility(View.VISIBLE);
                                et5.setVisibility(View.GONE);
                                break;
                            case 5:
                                et1.setVisibility(View.VISIBLE);
                                et2.setVisibility(View.VISIBLE);
                                et3.setVisibility(View.VISIBLE);
                                et4.setVisibility(View.VISIBLE);
                                et5.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                }
            }
        } else if (view == btnOk) {
            switch (cnt) {
                case 1:
                    func1();
                    break;
                case 2:
                    func2();
                    break;
                case 3:
                    func3();
                    break;
                case 4:
                    func4();
                    break;
                case 5:
                    func5();
                    break;
            }
        }
    }

    public void func1() {
        if (et1.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "퍼센트를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            int p1 = Integer.parseInt(et1.getText().toString());

            if (p1 == 100) {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);

                int money = Integer.parseInt(etMoney.getText().toString());
                int rand = (int) (Math.random() * cnt);
                int[] arrRand = new int[cnt];

                for (int i = 0; i < cnt; i++) {
                    while (true) {
                        arrRand[i] = (int) (Math.random() * cnt);
                        boolean same = false;

                        for (int j = 0; j < i; j++) {
                            if (arrRand[j] == arrRand[i]) {
                                same = true;
                                break;
                            }
                        }
                        if (!same) {
                            break;
                        }
                    }
                }

                tv1.setText(person[arrRand[0]] + " => " + (money * p1 / 100) + "원");
            } else if (p1 > 100) {
                String temp = String.valueOf(p1 - 100);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 빼주세요", Toast.LENGTH_SHORT).show();
            } else {
                String temp = String.valueOf(100 - p1);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 채워주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void func2() {
        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "퍼센트를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            int p1 = Integer.parseInt(et1.getText().toString());
            int p2 = Integer.parseInt(et2.getText().toString());

            if (p1 + p2 == 100) {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);

                int money = Integer.parseInt(etMoney.getText().toString());
                int rand = (int) (Math.random() * cnt);
                int[] arrRand = new int[cnt];

                for (int i = 0; i < cnt; i++) {
                    while (true) {
                        arrRand[i] = (int) (Math.random() * cnt);
                        boolean same = false;

                        for (int j = 0; j < i; j++) {
                            if (arrRand[j] == arrRand[i]) {
                                same = true;
                                break;
                            }
                        }
                        if (!same) {
                            break;
                        }
                    }
                }

                tv1.setText(person[arrRand[0]] + " => " + ((money * p1 / 100) + 9) / 10 * 10 + "원");
                tv2.setText(person[arrRand[1]] + " => " + ((money * p2 / 100) + 9) / 10 * 10 + "원");
            } else if (p1 + p2 > 100) {
                String temp = String.valueOf(p1 + p2 - 100);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 빼주세요", Toast.LENGTH_SHORT).show();
            } else {
                String temp = String.valueOf(100 - p1 - p2);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 채워주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void func3() {
        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "퍼센트를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            int p1 = Integer.parseInt(et1.getText().toString());
            int p2 = Integer.parseInt(et2.getText().toString());
            int p3 = Integer.parseInt(et3.getText().toString());

            if (p1 + p2 + p3 == 100) {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);

                int money = Integer.parseInt(etMoney.getText().toString());
                int rand = (int) (Math.random() * cnt);
                int[] arrRand = new int[cnt];

                for (int i = 0; i < cnt; i++) {
                    while (true) {
                        arrRand[i] = (int) (Math.random() * cnt);
                        boolean same = false;

                        for (int j = 0; j < i; j++) {
                            if (arrRand[j] == arrRand[i]) {
                                same = true;
                                break;
                            }
                        }
                        if (!same) {
                            break;
                        }
                    }
                }
                tv1.setText(person[arrRand[0]] + " => " + (money * p1 / 100) + "원");
                tv2.setText(person[arrRand[1]] + " => " + (money * p2 / 100) + "원");
                tv3.setText(person[arrRand[2]] + " => " + (money * p3 / 100) + "원");
            } else if (p1 + p2 + p3 > 100) {
                String temp = String.valueOf(p1 + p2 + p3 - 100);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 빼주세요", Toast.LENGTH_SHORT).show();
            } else {
                String temp = String.valueOf(100 - p1 - p2 - p3);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 채워주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void func4() {
        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() || et4.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "퍼센트를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            int p1 = Integer.parseInt(et1.getText().toString());
            int p2 = Integer.parseInt(et2.getText().toString());
            int p3 = Integer.parseInt(et3.getText().toString());
            int p4 = Integer.parseInt(et4.getText().toString());

            if (p1 + p2 + p3 + p4 == 100) {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.GONE);

                int money = Integer.parseInt(etMoney.getText().toString());
                int rand = (int) (Math.random() * cnt);
                int[] arrRand = new int[cnt];

                for (int i = 0; i < cnt; i++) {
                    while (true) {
                        arrRand[i] = (int) (Math.random() * cnt);
                        boolean same = false;

                        for (int j = 0; j < i; j++) {
                            if (arrRand[j] == arrRand[i]) {
                                same = true;
                                break;
                            }
                        }
                        if (!same) {
                            break;
                        }
                    }
                }
                tv1.setText(person[arrRand[0]] + " => " + (money * p1 / 100) + "원");
                tv2.setText(person[arrRand[1]] + " => " + (money * p2 / 100) + "원");
                tv3.setText(person[arrRand[2]] + " => " + (money * p3 / 100) + "원");
                tv4.setText(person[arrRand[3]] + " => " + (money * p4 / 100) + "원");
            } else if (p1 + p2 + p3 + p4 > 100) {
                String temp = String.valueOf(p1 + p2 + p3 + p4 - 100);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 빼주세요", Toast.LENGTH_SHORT).show();
            } else {
                String temp = String.valueOf(100 - p1 - p2 - p3 - p4);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 채워주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void func5() {
        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() || et4.getText().toString().isEmpty() || et5.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "퍼센트를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            int p1 = Integer.parseInt(et1.getText().toString());
            int p2 = Integer.parseInt(et2.getText().toString());
            int p3 = Integer.parseInt(et3.getText().toString());
            int p4 = Integer.parseInt(et4.getText().toString());
            int p5 = Integer.parseInt(et5.getText().toString());

            if (p1 + p2 + p3 + p4 + p5 == 100) {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);

                int money = Integer.parseInt(etMoney.getText().toString());
                int rand = (int) (Math.random() * cnt);
                int[] arrRand = new int[cnt];

                for (int i = 0; i < cnt; i++) {
                    while (true) {
                        arrRand[i] = (int) (Math.random() * cnt);
                        boolean same = false;

                        for (int j = 0; j < i; j++) {
                            if (arrRand[j] == arrRand[i]) {
                                same = true;
                                break;
                            }
                        }
                        if (!same) {
                            break;
                        }
                    }
                }
                tv1.setText(person[arrRand[0]] + " => " + (money * p1 / 100) + "원");
                tv2.setText(person[arrRand[1]] + " => " + (money * p2 / 100) + "원");
                tv3.setText(person[arrRand[2]] + " => " + (money * p3 / 100) + "원");
                tv4.setText(person[arrRand[3]] + " => " + (money * p4 / 100) + "원");
                tv5.setText(person[arrRand[4]] + " => " + (money * p5 / 100) + "원");
            } else if (p1 + p2 + p3 + p4 + p5 > 100) {
                String temp = String.valueOf(p1 + p2 + p3 + p4 + p5 - 100);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 빼주세요", Toast.LENGTH_SHORT).show();
            } else {
                String temp = String.valueOf(100 - p1 - p2 - p3 - p4 - p5);
                Toast.makeText(getApplicationContext(), temp + "퍼센트를 더 채워주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(Login.loginChk){
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), MainFree.class);
            startActivity(intent);
            finish();
        }

        return false;
    }
}
