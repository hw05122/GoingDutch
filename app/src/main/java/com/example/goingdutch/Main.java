package com.example.goingdutch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener{
    double initTime;
    ImageButton ibAdd,ibPercent, ibRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibAdd = (ImageButton)findViewById(R.id.ibAdd);
        ibAdd.setOnClickListener(this);

        ibPercent = (ImageButton)findViewById(R.id.ibPercent);
        ibPercent.setOnClickListener(this);

        ibRandom = (ImageButton)findViewById(R.id.ibRandom);
        ibRandom.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(view == ibAdd){
            Intent intent = new Intent(getApplicationContext(),Add.class);
            startActivity(intent);
            finish();
        }else if(view ==ibPercent){
            Intent intent = new Intent(getApplicationContext(),Percent.class);
            startActivity(intent);
            finish();
        }else if(view == ibRandom){
            Intent intent = new Intent(getApplicationContext(),Random.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.function,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.itemLogout){
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(getApplicationContext(),"상단의 로그아웃 버튼을 눌러주세요",Toast.LENGTH_SHORT).show();
            }
        }

        return false;
    }
}
