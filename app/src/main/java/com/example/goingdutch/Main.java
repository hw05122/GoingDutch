package com.example.goingdutch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    double initTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if(id == R.id.itemAdd){
            Intent intent = new Intent(getApplicationContext(),Add.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.itemPercent){
            Intent intent = new Intent(getApplicationContext(),Percent.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.itemRandom){
            Intent intent = new Intent(getApplicationContext(),Random.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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
