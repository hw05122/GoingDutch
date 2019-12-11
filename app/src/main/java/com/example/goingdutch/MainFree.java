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

public class MainFree extends AppCompatActivity implements View.OnClickListener{
    ImageButton ibAdd,ibPercent, ibRandom, ibSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfree);

        ibAdd = (ImageButton)findViewById(R.id.ibAdd);
        ibAdd.setOnClickListener(this);

        ibPercent = (ImageButton)findViewById(R.id.ibPercent);
        ibPercent.setOnClickListener(this);

        ibRandom = (ImageButton)findViewById(R.id.ibRandom);
        ibRandom.setOnClickListener(this);

        ibSetting = (ImageButton)findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(this);
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
        }else if(view == ibSetting){
            Intent intent = new Intent(getApplicationContext(),Setting.class);
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
             Intent intent = new Intent(getApplicationContext(), Login.class);
             startActivity(intent);
             finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        return false;
    }
}
