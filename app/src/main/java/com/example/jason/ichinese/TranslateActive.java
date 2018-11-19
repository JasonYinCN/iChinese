package com.example.jason.ichinese;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TranslateActive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translatelayout);
        System.out.println("translate activity on creat");
    }

    public void btnBackClicked(View view){
        System.out.println("btn back clicked");
        setResult(1, (new Intent()).setAction("ok"));
        finish();
    }
}


