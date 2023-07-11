package com.example.finalcpn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        getSupportActionBar().hide();
    }


    public void onButton2Click(View view) {
        Intent intent=new Intent(Selection.this,MainActivity.class);
        startActivity(intent);
    }

    public void onButton1Click(View view) {
        Intent intent=new Intent(Selection.this,MainActivity.class);
        startActivity(intent);
    }

}